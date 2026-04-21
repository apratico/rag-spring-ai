# OPC UA for Industrial Integration

OPC UA (IEC 62541) is the dominant machine-to-machine communication
standard in manufacturing. It succeeds OPC Classic (COM/DCOM) and adds:

- Platform independence (C, .NET, Java, Python, Rust SDKs).
- Service-oriented architecture over TCP binary or HTTPS.
- Strong security: X.509 certificates, user tokens, message signing and
  encryption (Basic256Sha256, Aes256_Sha256_RsaPss).
- Rich **information model**: nodes, references, types — not just tags.
- Pub/Sub (UDP or MQTT) in addition to client/server (since v1.04).

## Information model

Where OPC Classic exposes a flat list of tags, OPC UA exposes a typed
object graph. Every node has a NodeId, a BrowseName, a DisplayName, and
references to other nodes. Reference types include:
`HasComponent`, `HasProperty`, `HasTypeDefinition`, `Organizes`, etc.

This lets a client discover semantics at runtime: "this node is an
instance of `MotorType`, which has components `Speed` (AnalogItemType),
`Current`, `Temperature` and a `StartStop` method".

## Companion specifications

OPC UA Companion Specs standardize information models per industry or
equipment class. Dozens exist; notable ones:

- **OPC UA for Machinery** — cross-cutting nameplate, identification,
  monitoring patterns; the umbrella for other machine-specific specs.
- **Euromap 77 / 82 / 83** — plastics and rubber machinery.
- **VDMA 40502** — robotics.
- **PackML (OMAC)** — state model for packaging machines.
- **Weihenstephan Standards** — food & beverage.
- **OPC UA for Machine Tools (umati)** — CNC machine tools.

Companion specs enable plug-and-produce: a MES that speaks OPC UA for
Machinery can ingest data from any compliant machine without
vendor-specific drivers.

## Pub/Sub

Client/server is elegant but scales poorly past a few thousand variables
at high rates. OPC UA Pub/Sub addresses this:

- **UDP multicast** — low-latency, deterministic on TSN-enabled networks.
- **MQTT / AMQP** — cloud- and WAN-friendly; JSON or UADP binary
  encoding.

Pub/Sub is the recommended path for Industry 4.0 north-bound data
pipelines. Many deployments use OPC UA Pub/Sub over MQTT into a broker,
then Sparkplug B on top for session management.

## Security gotchas

- Anonymous + None-security endpoints are common in the field for
  convenience — never expose them outside the control network.
- Certificate lifecycle is frequently neglected; a GDS (Global Discovery
  Server) or a PKI solution is required for fleets > 20 servers.
- Reverse-connect (aka RC) is useful when a server must initiate the TCP
  handshake through a firewall to reach a cloud-side client.
