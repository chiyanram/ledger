Why: <the reason for this change, in one sentence>. Spec: <the approved spec; with no spec, the ticket>

Breaking change: <no; or yes, and what the factory must change to keep reading the ledger. A yes also takes "!" after the title's scope and a "BREAKING CHANGE:" footer above the Refs line>
Rollback: <revert this PR; no data changes. Or how to undo it; an applied migration is undone by a new one>
Where to look: <one to three places where a mistake would cost most>

## Evidence at <commit>

| criterion | command or test | result |
|---|---|---|
| <AC-1, as the spec words it> | <the spec by name, or the command> | <passed> |

## Change

<each changed file marked A, M or D; for one file, a single "Change:" line>

## Checklist

- [ ] Approval: <a person posted `/approved` on this draft before any production code; or no production code>
- [ ] Tests: <the Spock specs added or changed, each as unit (`src/test`) or integration (`src/integrationTest`); or none, and why>
- [ ] Migration: <the new Flyway file, and that no applied migration was edited; or none>
- [ ] Docs: <`README.md` or `AGENTS.md` changed; or none, and why>
- [ ] Checks: `./gradlew check` green

Refs chiyanram/agent-factory#<ticket>
