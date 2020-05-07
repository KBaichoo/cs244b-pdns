package edu.cs244b.server;

import edu.cs244b.common.DNSRecord;
import edu.cs244b.common.DomainLookupServiceGrpc;
import edu.cs244b.common.DomainLookupServiceGrpc.DomainLookupServiceBlockingStub;
import edu.cs244b.common.HostName;
import edu.cs244b.mappings.ConstantsMappingStore;
import edu.cs244b.mappings.LookupResult;
import edu.cs244b.mappings.Manager;
import edu.cs244b.server.DomainLookupServer.DomainLookupService;
import io.grpc.StatusRuntimeException;
import io.grpc.inprocess.InProcessChannelBuilder;
import io.grpc.inprocess.InProcessServerBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import io.grpc.testing.GrpcCleanupRule;

import java.util.*;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class DomainLookupServerTest {

    @Rule
    public final GrpcCleanupRule grpcCleanup = new GrpcCleanupRule();

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    String setupServer(Manager.MappingStore mappings, Map<String, Peer> peers) throws Exception {
        String serverName = InProcessServerBuilder.generateName();

        grpcCleanup.register(
                InProcessServerBuilder.forName(serverName)
                        .directExecutor().addService(
                        new DomainLookupService(new Manager(mappings), peers)
                ).build().start()
        );

        return serverName;
    }

    DomainLookupServiceBlockingStub setupClient(String serverName) {
        return DomainLookupServiceGrpc.newBlockingStub(
                grpcCleanup.register(InProcessChannelBuilder.forName(serverName).directExecutor().build())
        );
    }

    @Test
    public void domainLookup_HostnameNotFound() throws Exception {
        String serverName = setupServer(
                new ConstantsMappingStore(Collections.emptySet()),
                Collections.emptyMap()
        );
        DomainLookupServiceBlockingStub stub = setupClient(serverName);

        exceptionRule.expect(StatusRuntimeException.class);
        exceptionRule.expectMessage("NOT_FOUND");

        stub.getDomain(HostName.newBuilder().setName("facebook.com").build());
    }

    @Test
    public void domainLookup_DirectLookup() throws Exception {
        Set<LookupResult> mappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.DIRECT, "facebook.com", "1.2.3.4")
        );

        String serverName = setupServer(
                new ConstantsMappingStore(mappings),
                Collections.emptyMap()
        );
        DomainLookupServiceBlockingStub stub = setupClient(serverName);
        DNSRecord reply = stub.getDomain(HostName.newBuilder().setName("facebook.com").build());

        assertEquals(1, reply.getIpAddressesCount());
        assertEquals("1.2.3.4", reply.getIpAddresses(0));
    }

    @Test
    public void domainLookup_IndirectLookup() throws Exception {
        // Setup peer.
        Set<LookupResult> friendServerMappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.DIRECT, "usa.gov", "1.2.3.4")
        );
        String friendServer = setupServer(new ConstantsMappingStore(friendServerMappings), Collections.emptyMap());

        // Setup server.
        Set<LookupResult> mappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.INDIRECT, "usa.gov", "usgov")
        );
        Map<String, Peer> peers = Collections.singletonMap("usgov", new Peer("usgov", setupClient(friendServer)));
        String serverName = setupServer(new ConstantsMappingStore(mappings), peers);

        // Test indirect call.
        DomainLookupServiceBlockingStub stub = setupClient(serverName);
        DNSRecord reply = stub.getDomain(HostName.newBuilder().setName("usa.gov").build());

        assertEquals(1, reply.getIpAddressesCount());
        assertEquals("1.2.3.4", reply.getIpAddresses(0));
    }

    @Test
    public void domainLookup_IndirectLookupHostnameNotFound() throws Exception {
        // Setup peer.
        Set<LookupResult> friendServerMappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.DIRECT, "facebook.com", "1.2.3.4")
        );
        String friendServer = setupServer(new ConstantsMappingStore(friendServerMappings), Collections.emptyMap());

        // Setup server.
        Set<LookupResult> mappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.INDIRECT, "usa.gov", "usgov")
        );
        Map<String, Peer> peers = Collections.singletonMap("usgov", new Peer("usgov", setupClient(friendServer)));
        String serverName = setupServer(new ConstantsMappingStore(mappings), peers);

        // Test indirect call.
        DomainLookupServiceBlockingStub stub = setupClient(serverName);

        exceptionRule.expect(StatusRuntimeException.class);
        exceptionRule.expectMessage("NOT_FOUND");

        stub.getDomain(HostName.newBuilder().setName("usa.gov").build());
    }

    @Test
    public void domainLookup_IndirectLookupPeerNotFound() throws Exception {
        // Setup peer.
        Set<LookupResult> friendServerMappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.DIRECT, "usa.gov", "1.2.3.4")
        );
        String friendServer = setupServer(new ConstantsMappingStore(friendServerMappings), Collections.emptyMap());

        // Setup server.
        Set<LookupResult> mappings = Collections.singleton(
                new LookupResult(LookupResult.MappingType.INDIRECT, "usa.gov", "usgov")
        );
        Map<String, Peer> peers = Collections.singletonMap("differentpeer", new Peer("differentpeer", setupClient(friendServer)));
        String serverName = setupServer(new ConstantsMappingStore(mappings), peers);

        // Test indirect call.
        DomainLookupServiceBlockingStub stub = setupClient(serverName);

        exceptionRule.expect(StatusRuntimeException.class);
        exceptionRule.expectMessage("INTERNAL");

        stub.getDomain(HostName.newBuilder().setName("usa.gov").build());
    }

}