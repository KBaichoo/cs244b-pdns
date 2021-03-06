# -*- coding: utf-8 -*-
# Generated by the protocol buffer compiler.  DO NOT EDIT!
# source: domain_lookup.proto

from google.protobuf import descriptor as _descriptor
from google.protobuf import message as _message
from google.protobuf import reflection as _reflection
from google.protobuf import symbol_database as _symbol_database
# @@protoc_insertion_point(imports)

_sym_db = _symbol_database.Default()




DESCRIPTOR = _descriptor.FileDescriptor(
  name='domain_lookup.proto',
  package='edu.cs244b.common',
  syntax='proto3',
  serialized_options=b'\n\021edu.cs244b.commonB\022DomainLookupProtosP\001',
  serialized_pb=b'\n\x13\x64omain_lookup.proto\x12\x11\x65\x64u.cs244b.common\"\x1b\n\x07Message\x12\x10\n\x08hostName\x18\x01 \x01(\t\"2\n\tDNSRecord\x12\x10\n\x08hostName\x18\x01 \x01(\t\x12\x13\n\x0bipAddresses\x18\x02 \x03(\t\"S\n\x0c\x44NSRecordP2P\x12/\n\tdnsRecord\x18\x01 \x01(\x0b\x32\x1c.edu.cs244b.common.DNSRecord\x12\x12\n\nexpiryTime\x18\x02 \x01(\x03\"K\n\nP2PMessage\x12+\n\x07message\x18\x01 \x01(\x0b\x32\x1a.edu.cs244b.common.Message\x12\x10\n\x08hopCount\x18\x02 \x01(\x05\x32\xac\x01\n\x13\x44omainLookupService\x12\x45\n\tGetDomain\x12\x1a.edu.cs244b.common.Message\x1a\x1c.edu.cs244b.common.DNSRecord\x12N\n\x0cGetDomainP2P\x12\x1d.edu.cs244b.common.P2PMessage\x1a\x1f.edu.cs244b.common.DNSRecordP2PB)\n\x11\x65\x64u.cs244b.commonB\x12\x44omainLookupProtosP\x01\x62\x06proto3'
)




_MESSAGE = _descriptor.Descriptor(
  name='Message',
  full_name='edu.cs244b.common.Message',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='hostName', full_name='edu.cs244b.common.Message.hostName', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=42,
  serialized_end=69,
)


_DNSRECORD = _descriptor.Descriptor(
  name='DNSRecord',
  full_name='edu.cs244b.common.DNSRecord',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='hostName', full_name='edu.cs244b.common.DNSRecord.hostName', index=0,
      number=1, type=9, cpp_type=9, label=1,
      has_default_value=False, default_value=b"".decode('utf-8'),
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='ipAddresses', full_name='edu.cs244b.common.DNSRecord.ipAddresses', index=1,
      number=2, type=9, cpp_type=9, label=3,
      has_default_value=False, default_value=[],
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=71,
  serialized_end=121,
)


_DNSRECORDP2P = _descriptor.Descriptor(
  name='DNSRecordP2P',
  full_name='edu.cs244b.common.DNSRecordP2P',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='dnsRecord', full_name='edu.cs244b.common.DNSRecordP2P.dnsRecord', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='expiryTime', full_name='edu.cs244b.common.DNSRecordP2P.expiryTime', index=1,
      number=2, type=3, cpp_type=2, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=123,
  serialized_end=206,
)


_P2PMESSAGE = _descriptor.Descriptor(
  name='P2PMessage',
  full_name='edu.cs244b.common.P2PMessage',
  filename=None,
  file=DESCRIPTOR,
  containing_type=None,
  fields=[
    _descriptor.FieldDescriptor(
      name='message', full_name='edu.cs244b.common.P2PMessage.message', index=0,
      number=1, type=11, cpp_type=10, label=1,
      has_default_value=False, default_value=None,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
    _descriptor.FieldDescriptor(
      name='hopCount', full_name='edu.cs244b.common.P2PMessage.hopCount', index=1,
      number=2, type=5, cpp_type=1, label=1,
      has_default_value=False, default_value=0,
      message_type=None, enum_type=None, containing_type=None,
      is_extension=False, extension_scope=None,
      serialized_options=None, file=DESCRIPTOR),
  ],
  extensions=[
  ],
  nested_types=[],
  enum_types=[
  ],
  serialized_options=None,
  is_extendable=False,
  syntax='proto3',
  extension_ranges=[],
  oneofs=[
  ],
  serialized_start=208,
  serialized_end=283,
)

_DNSRECORDP2P.fields_by_name['dnsRecord'].message_type = _DNSRECORD
_P2PMESSAGE.fields_by_name['message'].message_type = _MESSAGE
DESCRIPTOR.message_types_by_name['Message'] = _MESSAGE
DESCRIPTOR.message_types_by_name['DNSRecord'] = _DNSRECORD
DESCRIPTOR.message_types_by_name['DNSRecordP2P'] = _DNSRECORDP2P
DESCRIPTOR.message_types_by_name['P2PMessage'] = _P2PMESSAGE
_sym_db.RegisterFileDescriptor(DESCRIPTOR)

Message = _reflection.GeneratedProtocolMessageType('Message', (_message.Message,), {
  'DESCRIPTOR' : _MESSAGE,
  '__module__' : 'domain_lookup_pb2'
  # @@protoc_insertion_point(class_scope:edu.cs244b.common.Message)
  })
_sym_db.RegisterMessage(Message)

DNSRecord = _reflection.GeneratedProtocolMessageType('DNSRecord', (_message.Message,), {
  'DESCRIPTOR' : _DNSRECORD,
  '__module__' : 'domain_lookup_pb2'
  # @@protoc_insertion_point(class_scope:edu.cs244b.common.DNSRecord)
  })
_sym_db.RegisterMessage(DNSRecord)

DNSRecordP2P = _reflection.GeneratedProtocolMessageType('DNSRecordP2P', (_message.Message,), {
  'DESCRIPTOR' : _DNSRECORDP2P,
  '__module__' : 'domain_lookup_pb2'
  # @@protoc_insertion_point(class_scope:edu.cs244b.common.DNSRecordP2P)
  })
_sym_db.RegisterMessage(DNSRecordP2P)

P2PMessage = _reflection.GeneratedProtocolMessageType('P2PMessage', (_message.Message,), {
  'DESCRIPTOR' : _P2PMESSAGE,
  '__module__' : 'domain_lookup_pb2'
  # @@protoc_insertion_point(class_scope:edu.cs244b.common.P2PMessage)
  })
_sym_db.RegisterMessage(P2PMessage)


DESCRIPTOR._options = None

_DOMAINLOOKUPSERVICE = _descriptor.ServiceDescriptor(
  name='DomainLookupService',
  full_name='edu.cs244b.common.DomainLookupService',
  file=DESCRIPTOR,
  index=0,
  serialized_options=None,
  serialized_start=286,
  serialized_end=458,
  methods=[
  _descriptor.MethodDescriptor(
    name='GetDomain',
    full_name='edu.cs244b.common.DomainLookupService.GetDomain',
    index=0,
    containing_service=None,
    input_type=_MESSAGE,
    output_type=_DNSRECORD,
    serialized_options=None,
  ),
  _descriptor.MethodDescriptor(
    name='GetDomainP2P',
    full_name='edu.cs244b.common.DomainLookupService.GetDomainP2P',
    index=1,
    containing_service=None,
    input_type=_P2PMESSAGE,
    output_type=_DNSRECORDP2P,
    serialized_options=None,
  ),
])
_sym_db.RegisterServiceDescriptor(_DOMAINLOOKUPSERVICE)

DESCRIPTOR.services_by_name['DomainLookupService'] = _DOMAINLOOKUPSERVICE

# @@protoc_insertion_point(module_scope)
