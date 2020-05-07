package edu.cs244b.server;

import edu.cs244b.common.DNSRecord;
import edu.cs244b.common.DomainLookupServiceGrpc;
import edu.cs244b.common.HostName;
import edu.cs244b.common.NullOrEmpty;
import edu.cs244b.mappings.JSONMappingStore;
import edu.cs244b.mappings.LookupResult;
import edu.cs244b.mappings.Manager;
import io.grpc.*;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


public class DomainLookupServer {
    private static final Logger logger = LoggerFactory.getLogger(DomainLookupServer.class);

    private final int port;
    private final Server server;

    public DomainLookupServer(int port) throws IOException {
        this(
                port,
                new JSONMappingStore(ServerUtils.defaultJSONLookupDB()),
                ServerUtils.loadPeers(ServerUtils.defaultPeerList())
        );
    }

    /** Create a DomainLookup server listening on {@code port} using {@code lookupFile} database. */
    public DomainLookupServer(int port, Manager.MappingStore mappingStore, Map<String, Peer> peers) {
        this(ServerBuilder.forPort(port), mappingStore, peers, port);
    }

    /** Create a DomainLookup server using serverBuilder as a base and lookup entries as data. */
    public DomainLookupServer(
            ServerBuilder<?> serverBuilder,
            Manager.MappingStore mappingStore,
            Map<String, Peer> peers,
            int port) {
        this.port = port;
        server = serverBuilder.addService(
                new DomainLookupService(
                        new Manager(mappingStore),
                        peers
                )
        ).build();
    }

    /** Start serving requests. */
    public void start() throws IOException {
        server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may have been reset by its JVM shutdown hook.
                System.err.println("*** shutting down gRPC server since JVM is shutting down");
                try {
                    DomainLookupServer.this.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace(System.err);
                }
                System.err.println("*** server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8980;
        if (args.length > 0 && NullOrEmpty.isFalse(args[0])) {
            port = Integer.parseInt(args[0]);
        }

        DomainLookupServer server = new DomainLookupServer(port);
        server.start();
        server.blockUntilShutdown();
    }

    /**
     * Implementation of DomainLookupService.
     * <p>See domain_lookup.proto for details of the methods.
     */
    static class DomainLookupService extends DomainLookupServiceGrpc.DomainLookupServiceImplBase {
        private final Manager mappingManager;
        private final Map<String, Peer> peers;

        DomainLookupService(final Manager manager, Map<String, Peer> peers) {
            this.mappingManager = manager;
            this.peers = peers;
        }

        @Override
        public void getDomain(final HostName hostName, StreamObserver<DNSRecord> responseObserver) {
            if (NullOrEmpty.isTrue(hostName.getName())) {
                responseObserver.onError(new StatusException(Status.INVALID_ARGUMENT));
                return;
            }

            LookupResult lookupResult = mappingManager.lookupHostname(hostName.getName());
            if (lookupResult == null) {
                responseObserver.onError(new StatusException(Status.NOT_FOUND));
                return;
            }

            if (lookupResult.getType() == LookupResult.MappingType.INDIRECT) {
                String peerName = lookupResult.getValue();
                if (!peers.containsKey(peerName)) {
                    responseObserver.onError(new StatusException(Status.INTERNAL));
                    return;
                }

                Peer peer = peers.get(peerName);

                indirectResolution(responseObserver, lookupResult.getHostname(), peer);
            } else {
                responseObserver.onNext(buildResponse(lookupResult));
                responseObserver.onCompleted();
            }
        }

        private void indirectResolution(StreamObserver<DNSRecord> responseObserver, String key, Peer peer) {
            try {
                responseObserver.onNext(peer.getStub().getDomain(HostName.newBuilder().setName(key).build()));
                responseObserver.onCompleted();
            } catch (StatusRuntimeException sre) {
                switch (sre.getStatus().getCode()) {
                    case NOT_FOUND:
                        responseObserver.onError(new StatusException(Status.NOT_FOUND));
                        break;
                    default:
                        responseObserver.onError(new StatusException(Status.INTERNAL));
                }
            }
        }

        private DNSRecord buildResponse(final LookupResult result) {
            return DNSRecord.newBuilder()
                    .setHostName(result.getHostname())
                    .addIpAddresses(result.getValue())
                    .build();
        }

    }
}

