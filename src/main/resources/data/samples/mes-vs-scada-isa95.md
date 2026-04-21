# MES vs SCADA in the ISA-95 Reference Model

ISA-95 / IEC 62264 defines a five-level hierarchy for industrial systems:

| Level | Domain | Typical systems | Time horizon |
|-------|--------|-----------------|--------------|
| 0 | Physical process | Sensors, actuators | ms |
| 1 | Sensing & manipulation | PLCs, safety controllers | ms – s |
| 2 | Monitoring & supervisory | SCADA, HMI, historians | s – min |
| 3 | Manufacturing operations | MES, LIMS, CMMS | min – shift |
| 4 | Business planning & logistics | ERP | hours – months |

## SCADA (Level 2)

Supervisory Control And Data Acquisition is the real-time visualization and
control layer. It aggregates PLC tags from Level 1, drives HMIs, raises
alarms, and persists process data to a historian (e.g. OSIsoft PI, InfluxDB,
Aveva Historian). SCADA operates on cycle times measured in seconds and its
scope is the **asset**: pumps, motors, valves, reactors, lines.

## MES (Level 3)

A Manufacturing Execution System orchestrates *operations* over a
production shift: work orders, routings, operator dispatch, genealogy,
quality gates, OEE calculation, electronic batch records. MES scope is the
**order** and the **product**, not the single actuator.

Common MES functional areas (per MESA-11 and ISA-95 Part 3):
- Production tracking & dispatching
- Resource allocation & status
- Performance analysis (OEE, yield)
- Quality management (SPC, holds, deviations)
- Maintenance management (often offloaded to CMMS)
- Document control (SOPs, recipes)
- Labor & operator tracking
- Product genealogy & traceability

## Integration patterns

- **Level 2 → Level 3**: OPC UA pulls PLC/SCADA tags into the MES; the MES
  also ingests alarm & event streams from SCADA.
- **Level 3 ↔ Level 4**: ERP (SAP, Oracle, Dynamics) pushes production
  orders and BOMs *down*; MES reports confirmations, consumption, and WIP
  *up*. ISA-95 Part 5 (B2MML) standardizes the XML/JSON payloads.
- **Data historian**: both SCADA and MES typically read/write the same
  historian, which becomes the single source of truth for time-series.

## Confusion to avoid

- SCADA is not a MES. Adding work-order dropdowns to an HMI is not an MES.
- MES is not a BI dashboard. It is a transactional system that *executes*
  manufacturing, not one that merely reports on it.
- OEE can be computed either at SCADA or MES level, but only at MES level
  can it be associated with the order, operator, and BOM that caused it.
