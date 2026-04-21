# Andon Systems and Lean Manufacturing

**Andon** (行灯) is a Japanese term for a paper lantern; in manufacturing
it refers to a visual management system that lets operators signal the
state of a workstation at a glance. Andon is one of the original pillars
of the Toyota Production System (TPS) alongside Jidoka (autonomation) and
Just-in-Time.

## Purpose

Andon makes abnormal conditions immediately visible so that:

- Problems are detected *where and when* they occur.
- Work is stopped before defects propagate downstream (*stop the line*).
- Management can deploy support to the right station within seconds.
- The team accumulates structured data on causes of interruption.

## Typical signaling

- **Green** — running normally.
- **Yellow** — minor issue, operator calls for support (material,
  changeover).
- **Red** — line stopped; requires intervention.
- **Blue** — quality issue (used in some automotive standards).
- **White** — end of shift / end of job.

In a modern plant the physical stack light is mirrored in a digital
dashboard fed by PLC inputs or operator HMI clicks, and logged in the MES
for OEE and downtime analysis.

## Andon cord

In the Toyota Production System, any operator can pull the andon cord to
halt the line. This is a cultural artifact as much as a technical one:
empowering operators to stop a multi-million-dollar line in order to
prevent defects is non-trivial and requires explicit leadership support.

## Relationship to other Lean concepts

- **Jidoka** — automation with a human touch; a machine detects its own
  defect and stops. Andon signals this stop to humans.
- **Poka-yoke** — mistake-proofing; prevents errors rather than detecting
  them. Complements Andon.
- **5S / Visual Management** — Andon is an instance of visual management;
  works only if the workspace is organized and signals are unambiguous.
- **Kaizen** — each Andon event is a raw data point for continuous
  improvement.

## Digital Andon in Industry 4.0

Digital Andon systems integrate with MES, maintenance, and quality:

- Automated call-for-help routes to the right specialist (electrical,
  mechanical, quality) via Slack/Teams/mobile push.
- MTTR and MTBF are computed per stop category.
- Andon events are correlated with OEE losses and SPC alerts.
- Escalation ladders: if not acknowledged in N seconds, page the next
  tier.

A well-designed digital Andon is the single most visible artifact of an
Industry 4.0 transformation for shop-floor operators — *if* it replaces
paper and radio calls rather than being added on top of them.
