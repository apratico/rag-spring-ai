# Predictive Maintenance (PdM) in Industry 4.0

Maintenance strategies evolved in four stages:

1. **Reactive (Run-to-Failure)** — fix after breakdown. Highest downtime.
2. **Preventive (Time-Based, PM)** — fixed-interval service, regardless of
   asset condition. Standard CMMS approach; over-services healthy assets.
3. **Condition-Based (CBM)** — service when a measured parameter crosses a
   threshold (vibration RMS, temperature, oil particle count).
4. **Predictive (PdM)** — forecast remaining useful life (RUL) or time-to-
   failure using sensor data and ML, then schedule service inside that
   window.

## Data sources

- **Vibration** — accelerometers at 5–25 kHz; FFT features (1x/2x/3x rpm,
  bearing defect frequencies BPFO/BPFI/BSF/FTF).
- **Temperature** — infrared / thermocouples / RTDs on bearings and motor
  windings.
- **Acoustic emission** — ultrasonic for leak and early bearing defects.
- **Current & power signature** — motor current signature analysis (MCSA)
  for broken rotor bars, eccentricity, stator faults.
- **Process context** — load, speed, product SKU from SCADA/MES.

## Modeling approaches

- **Anomaly detection**: Isolation Forest, One-Class SVM, autoencoders.
  Good when failure labels are scarce — almost always the case in
  brownfield plants.
- **Remaining Useful Life regression**: LSTM/GRU/Transformers on multivariate
  time-series (NASA C-MAPSS is the canonical benchmark).
- **Survival analysis**: Weibull AFT, Cox proportional hazards — produce
  calibrated probabilities over time horizons.
- **Physics-informed hybrids**: combine first-principles degradation models
  (Paris law for crack growth, Arrhenius for thermal aging) with ML
  residuals. Higher sample efficiency and better extrapolation.

## Deployment architecture

Sensors → Edge gateway (OPC UA / MQTT Sparkplug B) → Message broker →
Stream processor (Flink / Kafka Streams) → Feature store → Model serving →
MES / CMMS work-order creation. Inference can be split:

- **Edge**: sub-second anomaly detection on raw vibration.
- **Cloud**: RUL models that need longer history and heavier compute.

## Pitfalls

- Label leakage from post-failure maintenance records — always validate
  that training labels are anchored in time correctly.
- Concept drift after retrofits, recipe changes, or seasonal load shifts;
  retrain on a rolling window.
- ROI must be computed against Preventive, not Reactive. Reactive is a
  strawman; Preventive is what most plants already do.
