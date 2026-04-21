# MQTT Sparkplug B for Industrial IoT

MQTT is a lightweight publish/subscribe protocol over TCP, designed for
constrained devices and unreliable networks. It is widely used at the
factory floor for OT → IT data movement, but *vanilla* MQTT is a pure
transport: it does not define topic structure, payload schema, or state
management. Sparkplug B fills that gap.

Sparkplug is an open specification published by the Eclipse Foundation
(ISO/IEC 20922 companion). Version B is the current stable release.

## Topic namespace

```
spBv1.0/<group_id>/<message_type>/<edge_node_id>[/<device_id>]
```

- `group_id` — logical grouping (site, line).
- `edge_node_id` — Sparkplug Edge Node (gateway).
- `device_id` — optional, for devices behind the edge node.

Message types:

| Type | Direction | Purpose |
|------|-----------|---------|
| `NBIRTH` | Edge → App | Birth certificate of an Edge Node |
| `NDATA`  | Edge → App | Data from an Edge Node |
| `NDEATH` | LWT       | Death of an Edge Node |
| `DBIRTH` | Edge → App | Birth of a device |
| `DDATA`  | Edge → App | Data from a device |
| `DDEATH` | Edge → App | Death of a device |
| `NCMD`   | App → Edge | Command to an Edge Node |
| `DCMD`   | App → Edge | Command to a device |
| `STATE`  | App → Broker | Host Application state |

## Payload

Sparkplug B encodes payloads in Google Protocol Buffers (schema
`sparkplug_b.proto`). Each payload carries a monotonically increasing
sequence number (`seq`), a timestamp, and a list of metrics. Each metric
has a name, alias (integer shorthand), data type, timestamp, and value.

Key ideas:

- **Birth certificates** declare the full set of metrics an Edge Node or
  device will publish; subsequent DATA messages can use integer aliases
  instead of string names, shrinking payloads dramatically.
- **bdSeq** (birth/death sequence) disambiguates reconnects.
- **Store-and-forward** at the Edge keeps data during broker outages.

## State management

Host Applications publish their `STATE` and subscribe to all `NBIRTH`,
`NDEATH`, `NDATA`. A missed NDEATH (via LWT) tells the app that an Edge
Node is gone. This gives Sparkplug stateful, session-aware semantics on top
of otherwise fire-and-forget MQTT.

## Why it matters

- Vendor-neutral, schema-aware IIoT standard; works with any MQTT broker
  (HiveMQ, EMQX, Mosquitto 2.x, AWS IoT Core).
- First-class fit for Unified Namespace (UNS) architectures where the
  broker is the single source of truth for real-time factory state.
- Plays well with OPC UA gateways: many Edge products translate OPC UA →
  Sparkplug B so north-bound consumers are protocol-homogeneous.
