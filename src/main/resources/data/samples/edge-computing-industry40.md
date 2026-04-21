# Edge Computing in Industry 4.0

**Edge computing** places compute resources close to the data source —
inside the plant, next to the line, or inside the device — instead of in
a remote datacenter or public cloud. In Industry 4.0 it is a pragmatic
answer to four constraints:

1. **Latency** — closed-loop control and vision inspection need
   deterministic sub-10 ms responses.
2. **Bandwidth** — high-frequency sensor streams (10 kHz vibration, 4K
   camera) cannot be economically shipped to the cloud.
3. **Availability** — production must continue during WAN outages.
4. **Data sovereignty** — regulated industries (pharma GxP, defense)
   require data residency and controlled transfer.

## Edge layers

- **Device edge** — MCUs on sensors/actuators; TinyML inference in
  single-digit milliwatts (e.g. defect classification on a camera
  module).
- **Edge gateway** — x86 or ARM compute node next to PLCs/SCADA. Hosts
  protocol translation (OPC UA ↔ MQTT Sparkplug B), buffering, and mid-
  complexity ML.
- **On-premise edge / "fog"** — rack-mount servers in an MDF/IDF or a
  plant datacenter. Runs MES components, historians, Kubernetes
  clusters for PdM model serving.

## Reference stack

| Concern | Common choices |
|--------|----------------|
| OS | Ubuntu Core, Yocto, Wind River Linux, Red Hat Device Edge |
| Orchestration | K3s, KubeEdge, Azure IoT Edge, AWS Greengrass |
| Runtime | Docker/containerd, WASM (wasmCloud), OCI-compatible |
| Messaging | MQTT (HiveMQ, EMQX), Kafka, NATS JetStream |
| Time-series | InfluxDB, TimescaleDB, VictoriaMetrics |
| ML serving | NVIDIA Triton, ONNX Runtime, TensorRT, OpenVINO |
| Observability | OpenTelemetry, Prometheus, Grafana Loki |

## Architectural patterns

- **Edge → Cloud telemetry** with store-and-forward during WAN outages.
- **Twin synchronization** — AAS sub-models cached at the edge, deltas
  synced to a cloud repository.
- **Model distribution** — train in cloud, package and sign the model,
  deploy to the edge via GitOps (Argo CD, Flux).
- **Split inference** — lightweight anomaly detection at the edge,
  heavier RUL regression in the cloud on aggregated features.

## Operational concerns

- **Fleet management** — provisioning, certificate rotation, secure boot,
  remote attestation. Easy for 10 nodes, hard for 10,000.
- **Update strategy** — A/B partitions and rollback; never SSH-apt-upgrade
  a production edge node.
- **Security** — zero-trust; every east-west flow mutually
  authenticated; segregate OT VLANs from IT; follow IEC 62443.
- **Cost model** — edge hardware + lifecycle management vs cloud data
  egress + latency cost. Rarely a clean comparison; always compute
  3-year TCO at fleet scale.

Edge is not "cloud minus the network". It is a distributed system with
partial failure as the normal state of operation; design accordingly.
