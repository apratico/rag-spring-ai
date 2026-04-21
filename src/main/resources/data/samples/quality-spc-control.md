# Statistical Process Control (SPC) in Manufacturing Quality

SPC is the application of statistical methods to monitor and control a
process so that it operates at its full potential. Pioneered by Walter
Shewhart at Bell Labs in the 1920s and systematized by Deming, it remains
the backbone of modern quality systems (ISO 9001, IATF 16949 in
automotive, AS9100 in aerospace).

## Core distinction: common-cause vs special-cause variation

- **Common cause** — inherent variation of a stable process (material
  tolerances, measurement noise, ambient conditions).
- **Special cause** — external, assignable disturbances (tool wear,
  operator change, bad batch of raw material).

A process is *in statistical control* when only common-cause variation is
present. SPC's purpose is to detect special causes quickly so operators
can intervene before defects are produced.

## Control charts

- **X̄-R / X̄-s charts** — variable data in subgroups; X̄ tracks the mean,
  R (range) or s (stdev) tracks within-subgroup variation.
- **I-MR (Individuals / Moving Range)** — low-volume or 100%-inspection
  processes where subgrouping is not practical.
- **p / np charts** — attribute data, proportion or count of defectives.
- **c / u charts** — count of defects per unit or per area of
  opportunity.
- **EWMA / CUSUM** — sensitive to small sustained shifts; better than
  Shewhart charts for drift detection.

Control limits are set at ±3σ from the process mean — *not* spec limits.
This is a common and costly confusion: control limits describe the voice
of the process; specification limits describe the voice of the customer.

## Nelson / Western Electric rules

Rules to detect out-of-control conditions beyond a single point outside
±3σ:

1. One point > 3σ from the center line.
2. Nine points in a row on the same side of the center line.
3. Six points in a row steadily increasing or decreasing.
4. Fourteen points in a row alternating up and down.
5. Two out of three points > 2σ on the same side.
6. Four out of five points > 1σ on the same side.
7. Fifteen points in a row within ±1σ (stratification / over-control).
8. Eight points in a row outside ±1σ on either side.

## Capability indices

Once a process is in control, capability measures compare process spread
to specification:

- `Cp = (USL − LSL) / (6σ)` — short-term capability, ignores centering.
- `Cpk = min((USL − μ)/3σ, (μ − LSL)/3σ)` — accounts for centering.
- `Pp / Ppk` — long-term (overall) equivalents, using overall stdev.

Rule of thumb: Cpk ≥ 1.33 is acceptable, ≥ 1.67 is capable, ≥ 2.00 is
"six sigma" capable. These thresholds assume normality — always check.

## Integration with MES

Modern MES/QMS platforms compute SPC charts in real time from operator
entries, gauge interfaces (digital calipers, CMMs, vision systems), or
PLC tags. Out-of-control events should automatically raise a Hold on the
affected lot and open a non-conformance record for disposition.
