# CMMS and Preventive Maintenance Strategy

A **Computerized Maintenance Management System (CMMS)** is the system of
record for maintenance operations: assets, work orders, spare parts
inventory, maintenance plans, labor, and history. Common commercial
products: IBM Maximo, SAP PM/S4 EAM, Infor EAM, UpKeep, Fiix, MaintainX.

## Core CMMS entities

- **Asset hierarchy** — site → area → system → equipment → component.
- **Work orders (WO)** — corrective (CM), preventive (PM), predictive
  (PdM), shutdown/turnaround.
- **Work requests (WR)** — tickets opened by operators, later approved
  and converted to WOs.
- **PM plans** — triggers by calendar, runtime counter, cycle count, or
  condition.
- **Spare parts** — BOM, on-hand, reorder point, vendor, lead time.
- **Labor & skills** — technicians, skills matrix, certifications.
- **Failure codes** — problem / cause / remedy taxonomy (ISO 14224 for
  oil & gas, VDI 2893 for manufacturing).

## Maintenance strategy matrix

| Strategy | Trigger | Fit |
|----------|---------|-----|
| Reactive (run-to-failure) | Actual failure | Low-criticality, abundant spares, no safety impact |
| Preventive (time-based, PM) | Calendar or runtime | Wear-dominated, predictable failure modes |
| Condition-Based (CBM) | Threshold on measured parameter | Gradual degradation, monitorable |
| Predictive (PdM) | ML-forecasted failure window | Sufficient sensor data and sample size |
| Prescriptive (RxM) | PdM + recommended action | Mature PdM + decision-support models |

The "right" strategy is *per failure mode*, not per asset. A single pump
can have bearing failure (condition-based), seal failure (time-based),
and impeller erosion (predictive) — all managed simultaneously.

## Reliability-Centered Maintenance (RCM)

RCM (SAE JA1011/JA1012) is a structured method to select the strategy
per failure mode, based on seven questions:

1. Functions?
2. Functional failures?
3. Failure modes?
4. Failure effects?
5. Failure consequences?
6. Proactive tasks?
7. Default actions if no proactive task is effective?

RCM outputs feed directly into CMMS PM plans and into PdM model scoping.

## KPIs

- **PM compliance** = PMs completed on time / PMs scheduled.
- **Schedule compliance** = WOs closed in the planned week / WOs
  scheduled.
- **Wrench time** = hands-on-tool time / total labor time; industry
  median is ~35%, best-in-class ~55%.
- **MTBF, MTTR** — see ISO 22400.
- **Backlog weeks** = open WO labor hours / weekly labor capacity;
  healthy range 2–4 weeks.

## Integration with MES / ERP / IIoT

- **MES → CMMS**: downtime codes trigger automatic CM work orders.
- **IIoT → CMMS**: PdM/CBM alerts trigger work requests with recommended
  action and spare parts.
- **ERP ↔ CMMS**: ERP owns spare parts procurement; CMMS owns
  reservation and consumption.
