# OEE and ISO 22400 KPIs for Manufacturing Operations

Overall Equipment Effectiveness (OEE) is the de-facto composite KPI used to
measure production efficiency of a single machine, production line, or cell.
It multiplies three factors, each expressed as a ratio between 0 and 1:

- **Availability** = Run Time / Planned Production Time
- **Performance** = (Ideal Cycle Time × Total Count) / Run Time
- **Quality** = Good Count / Total Count

`OEE = Availability × Performance × Quality`

World-class OEE is typically cited as 85%. Discrete manufacturing averages
sit in the 40–60% band, which means most plants have significant hidden
capacity before investing in new equipment.

## ISO 22400

ISO 22400-2:2014 standardizes Manufacturing Operations Management (MOM) KPIs
so that results are comparable across sites, vendors, and MES products. It
defines ~34 KPIs grouped in production, quality, maintenance, inventory and
logistics categories. Examples:

- **APQ** (Availability, Performance, Quality) — the OEE components above.
- **TEEP** (Total Effective Equipment Performance) = OEE × Utilization,
  normalizes by calendar time rather than scheduled time.
- **MTBF** (Mean Time Between Failures) — reliability indicator.
- **MTTR** (Mean Time To Repair) — maintainability indicator.
- **FPY** (First Pass Yield) — quality indicator at line level.

## Six Big Losses

OEE maps onto the "Six Big Losses" (Nakajima):
1. Equipment Failure (Availability loss)
2. Setup & Adjustments (Availability loss)
3. Idling & Minor Stops (Performance loss)
4. Reduced Speed (Performance loss)
5. Process Defects (Quality loss)
6. Reduced Yield at Startup (Quality loss)

## Practical notes

- Stop-cause coding is the highest-ROI foundation for OEE. Without it,
  Availability is just a number and improvement teams have nothing to act on.
- Ideal Cycle Time must be the engineered/nameplate cycle, not the observed
  median, otherwise Performance silently hides slow-running.
- Compute OEE per shift and per product SKU — averaging across product
  families masks mix-dependent losses.
- Integrate OEE computation into the MES or historian so it is always aligned
  with ISA-95 Level 3 order execution.
