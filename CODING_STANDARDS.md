# Coding Standards and Code Review Checklist

## Purpose
This document defines coding standards and a reviewer checklist to be used during sprint code reviews. It is intentionally pragmatic and focused on the `stock_alert_notif` Java/Maven project.

## Scope
Applies to all Java source files under `src/main/java` and unit tests under `src/test/java`.

## General Principles
- Keep changes small and focused for easier review.
- Prefer clarity and correctness over cleverness.
- Follow YAGNI and KISS principles.
- Tests accompany behavior changes (TDD preferred).

## Formatting & Style
- Use 4 spaces for indentation; do not use tabs.
- Line length: aim for <= 100 characters.
- Place braces on the same line (K&R style):
  public void foo() {
      // ...
  }
- One statement per line.
- Use blank lines to separate logical blocks (methods, groups of fields).

## Naming
- Packages: all lowercase (e.g., `edu.gmu.cs321`).
- Classes: PascalCase (e.g., `AlertManager`).
- Methods and variables: camelCase (e.g., `createAlert`, `targetPrice`).
- Constants: UPPER_SNAKE_CASE.
- Test class names: classname + `Test` (e.g., `AlertTest`).
- Test method names: descriptive, using the pattern `methodUnderTest_scenario_expectedResult`.

## Java Language Practices

## Logging
- Use a logger (e.g., SLF4J) rather than System.out/err in production code.

# Coding Standards

This file is a concise, practical set of standards and a short reviewer checklist for a small student project (2 people). Keep it simple and friendly.

Scope
- Applies to `src/main/java` and `src/test/java` in this Maven project.

Quick Principles
- Keep changes small and focused. Small Pull requests are easier to review.
- Prefer readable code rather than brilliant shortcuts.
- Tests should demonstrate intended behavior. 

Naming 
- Line length ~100 chars max when practical.
- Test classes end with `Test`; test method names should be descriptive (e.g. `createAlert_validInput_succeeds`).

Java practices
- Make fields `final` when they shouldn't change.
- Return unmodifiable lists or defensive copies from getters.
- Validate public inputs (throw `IllegalArgumentException` for bad args).
- Prefer simple, well-documented solutions. Add JavaDoc for non-obvious public APIs.

Testing expectations
- For the chosen domain object tests (e.g., `Alert`): each of create, update, get should have at least two tests — one valid-input test and one invalid-input test.
- Keep tests run locally.

Light load of Pull Requests and Review checklist
- [ ] Build runs locally: `mvn -q test` (no CI required for classwork unless you want it).
- [ ] New/changed behavior has unit tests.
- [ ] Code is readable and follows basic naming/formatting rules above.
- [ ] No credentials or secrets added.

Meetings
- Quick face-to-face or call for code reviews. Reviews can be around 15-30 minutes.
- Only concise and constructive comments. If something is urgent, discuss in person rather than blocking the Pull Request for minor problem.
- Keep an agreed habit: when merging, ensure tests pass locally and the PR author self-checks the quick checklist.
