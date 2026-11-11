# ledger

ledger records each factory ticket and each review round, so the factory can report rounds, buckets and tokens. It runs locally only, with no UI and no auth.

## Prerequisites

- [mise](https://mise.jdx.dev), which installs the JDK that `mise.toml` declares (Temurin 27). Run `mise install`.
- Docker, for the integration tests (Testcontainers, PostgreSQL 18).
- A local PostgreSQL at `localhost:5432`, database `ledger`, user `ledger`, for `bootRun`, as `application.yaml` sets.

## Commands

- `./gradlew test` runs the unit tests (Spock, no container, no Spring context).
- `./gradlew integrationTest` runs the integration tests against a PostgreSQL 18 container; it needs Docker.
- `./gradlew check` runs the unit tests, then the integration tests; it is what CI runs.
- `./gradlew bootRun` starts the app against the local PostgreSQL.

## Rules

[AGENTS.md](AGENTS.md) holds the rules agents and contributors follow. Its three MUSTs:

- Write production code only after a human posts `/approved` on the draft pull request, and leave the merge to the human.
- Keep `./gradlew check` green, because CI runs it on every pull request.
- Put every integration test under `src/integrationTest`.
