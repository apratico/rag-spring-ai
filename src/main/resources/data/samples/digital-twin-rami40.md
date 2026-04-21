# Digital Twin and the RAMI 4.0 Reference Architecture

A **Digital Twin** is a virtual representation of a physical asset that is
kept synchronized with the asset over its lifecycle through bidirectional
data exchange. The Digital Twin Consortium definition emphasizes three
traits: it is *synchronized at a specified frequency and fidelity*, it
drives *outcomes*, and it spans *the lifecycle*.

## Twin maturity spectrum

- **Digital Model** — CAD/simulation, no live data link.
- **Digital Shadow** — one-way, physical → digital (sensor telemetry).
- **Digital Twin** — bidirectional, physical ↔ digital (setpoints, commands).

Most "digital twin" deployments are actually digital shadows; the
commercial/control feedback loop is the hard part, not the visualization.

## RAMI 4.0

The Reference Architectural Model for Industrie 4.0 (DIN SPEC 91345) is a
three-axis model:

- **Layers** (6): Asset, Integration, Communication, Information,
  Functional, Business.
- **Life Cycle & Value Stream** (IEC 62890): Type vs Instance, Development
  vs Maintenance/Usage.
- **Hierarchy Levels** (IEC 62264/61512): Product, Field Device, Control
  Device, Station, Work Center, Enterprise, Connected World.

An Asset Administration Shell (AAS, IEC 63278) is the canonical digital
representation of an Industry 4.0 asset inside RAMI — the "passport" that
carries identification, capabilities, and sub-models (nameplate,
maintenance, energy, safety, etc.). AAS sub-models are standardized by the
Industrial Digital Twin Association (IDTA).

## Common sub-models (IDTA)

- `Nameplate` — manufacturer, model, serial, compliance.
- `TechnicalData` — mechanical, electrical, environmental properties.
- `Maintenance` — service plans, PM intervals, spare parts.
- `HandoverDocumentation` — manuals, certificates, CE declarations.
- `TimeSeriesData` — time-series telemetry pointers.
- `CarbonFootprint` — PCF computation inputs (PAS 2050 / ISO 14067).

## Implementation pattern

1. Represent each asset type and instance as an AAS (JSON or AASX).
2. Host the AAS in a repository (Eclipse BaSyx, Fraunhofer FA³ST).
3. Connect live telemetry via OPC UA Companion Specs and/or MQTT Sparkplug B.
4. Build function-layer services on top (simulation, PdM, energy).
5. Expose via REST or GraphQL to Level 4 systems and external partners.

## Why it matters

AAS + RAMI 4.0 is the only credible candidate for a vendor-neutral,
machine-readable interoperability layer across the Industry 4.0 stack.
Without it, every MES/ERP/PdM integration is bespoke.
