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

## Git and pull requests

- Do every change in its own git worktree, branched from the latest `origin/main`, and never commit in the main
  checkout, because agents and humans working in parallel then never share a working tree:
  `git fetch origin main && git worktree add .claude/worktrees/<name> -b <branch> origin/main`.
- Bring a worktree branch up to date by rebasing it onto `origin/main` (`git rebase origin/main`), not by merging main
  into it, and push it with `git push --force-with-lease`, because a linear branch is easier to review and the lease
  refuses to overwrite commits you have not fetched. Rebase and force-push only your own worktree branch: never
  `--force` without a lease, and never rewrite `main`.
- Write every pull request title as `type(scope): lower-case summary (chiyanram/agent-factory#N)`, the format
  `.cz.toml` shares with chiyanram/skills and chiyanram/agent-factory, because the squash merge makes the title the
  commit subject on `main` and the `pr-title` workflow fails any other form. `cz commit` prompts for each part;
  `cz check --message "<title>"` checks one. Branch commits are dropped by the squash, so they can be anything.
- Open every pull request as a draft from `.github/pull_request_template.md`, fill in every line and leave no
  `<placeholder>`, because the squash merge makes the description the commit body on `main`, where it cannot be
  edited. The `pr-body` workflow (`scripts/pr_body.py`) fails a ready PR with a missing line, a placeholder or an
  unticked item.

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
