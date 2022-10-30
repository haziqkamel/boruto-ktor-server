# Ktor - BorutoServer #

## Requirements
1. Java 11

### gradle.properties
````
ktorVersion=2.1.0
kotlinVersion=1.7.10
logbackVersion=1.2.11
koinVersion=3.2.0
kotlin.code.style=official

org.gradle.java.home=C:\\Program Files\\AdoptOpenJDK\\jdk-11.0.11.9-hotspot
````

## GitHub Actions
File Location: .github/workflows/***.yml

### Events(Workflow Triggers)
1. Repository-related
   1. push: pushing a commit
   2. fork: repository was forked
   3. watch: repository was starred
   4. pull_request: pull request action (opened, closed, ...)
   5. issues: an issue was opened, deleted, ...
   6. discussion: discussion action (created, deleted, ...)
   7. create: a branch or tag was created
   8. issue_comment: issue or pull request comment action
   9. many more... refer the reference attached
2. Other
   1. workflow_dispatch: manually trigger workflow
   2. repository_dispatch: REST API request triggers workflow
   3. schedule: workflow is schedule
   4. workflow_call: can be called by other workflows

Reference: https://docs.github.com/en/actions/using-workflows/events-that-trigger-workflows

### What are actions?
Action: A (custom) application that performs a (typically complex) frequently repeated task

Alternatively a command ("run"): A (typically simple) shell command that's defined by you
- You can build your own Actions but you can also use official or community Actions

Actions Setup Java JDK: https://github.com/marketplace/actions/setup-java-jdk

### GitHub Hosted Runners
Reference: https://docs.github.com/en/actions/using-github-hosted-runners/about-github-hosted-runners


