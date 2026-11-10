# AGENTS.md

The ledger records each factory ticket and each review round, so the factory can report rounds, buckets and tokens
(agent-factory #62). Java 27 · Spring Boot 4.1 · PostgreSQL 18 · Flyway · Spock 2 · Testcontainers · Gradle.
It runs locally only: no UI and no auth.

## MUSTs

- Write production code only after a human posts `/approved` on the draft pull request, and leave the merge to the
  human, because a spec is cheap to fix before code and the merge is the review gate.
- Keep `./gradlew check` green, because CI runs it on every pull request and the human merges only on green.
- Put every integration test under `src/integrationTest`, because `check` runs that suite in its own task, with
  PostgreSQL in a container.

## Read when relevant

Read the named reference in full before you act.

- Java code: use the `code` skill (rulebook plugin) and read its `java` reference.
- Spring beans and configuration: use the `code` skill (rulebook plugin) and read its `spring` reference.
- Errors and failure paths: use the `code` skill (rulebook plugin) and read its `errors` reference.
- Choosing a test's tier: use the `testing` skill (rulebook plugin) and read its `strategy` reference.
- Spock specs: use the `testing` skill (rulebook plugin) and read its `spock` reference.
- Integration tests: use the `testing` skill (rulebook plugin) and read its `integration-tests` reference.
- A Flyway migration: use the `migrations` skill (rulebook plugin) and read its `postgres-design` reference.
- Log calls: use the `obs` skill (rulebook plugin) and read its `logging` reference.
