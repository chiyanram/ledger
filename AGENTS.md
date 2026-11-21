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

These rules are for people and interactive agent sessions. A factory run follows its task instead: it merges
`origin/main` on a conflict, never rebases, and never pushes, because the engine pushes fast-forward only.

- Do every change in its own git worktree, branched from the latest `origin/main`, and never commit in the main
  checkout, because agents and humans working in parallel then never share a working tree:
  `git fetch origin main && git worktree add .claude/worktrees/<name> -b <branch> origin/main`. A cloud session's own
  fresh clone, on the branch the session was given, counts as its worktree, because git cannot check that branch out a
  second time.
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
  edited. That body is the whole description in the template's shape, not a short note: the reason, the readiness
  lines, the evidence, the change and the checklist, with the footers last.
- Mark a breaking change three ways that agree: `Breaking change: yes` in the description, `!` after the title's
  scope, and a `BREAKING CHANGE:` footer, because the commit on `main` must say it in the same marks the other two
  repos release by. The `pr-body` workflow (`scripts/pr_body.py`) fails a ready PR with a missing line, a
  placeholder, an unticked item, or breaking-change marks that disagree. It skips a PR the factory opened (branch
  `factory/<ticket>`), whose body is the engine's own lines and then the implementer's PR1–PR7 sections.

## Read when relevant

Use the named skill (rulebook plugin) before you act.

- Java code, Spring beans and configuration, errors and failure paths: the `code` skill.
- A test, its tier, Spock specs and integration tests: the `testing` skill.
- A Flyway migration: the `migrations` skill.
- Log calls: the `obs` skill.
