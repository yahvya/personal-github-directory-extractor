# Github directory extractor

> Utilitary software , to download directories directly from a GitHub public repository

## Important informations

- **Licence** : Licence MIT
> You are free to use the framework in commercial and non-commercial projects. However, creating and selling a modified version of the framework itself or his documentation - as a competing product - is not permitted.
- **Creation date** : 12.08.2024

## Team

- Bathily Yahaya : Developer

## GitHub Structure

### Branch

- master (complete versions) - PROTECTED BY PULL REQUEST
- dev (developed features) - PROTECTED BY PULL REQUEST
- autotests (developed automatic tests) - PROTECTED BY PULL REQUEST
- design (available design elements) - PROTECTED BY PULL REQUEST
- documentation (developed documentation) - PROTECTED BY PULL REQUEST
- features/<branch_name>/<feature_name>

### Folder structure

- dev (application code)
- autotests (automatic tests)
- design (design elements)
- documentation (documentation elements)
- versions (application versions)
- devtools (custom development tools)
- .github/workflows (github action configs)

### GitHub Flow

- main : the main branch merge elements from (dev, autotests, design, documentation)
- (dev, autotests, design, documentation) : these branches merge elements from feature branches
- features/<branch_name>/<feature_name> : features branches are created from the <branch_name> branch

### Commit norm

Each commit line is prefixed by :

- ADD : for new code
- FIX : for changes due to a problem
- MODIFY : for changes
- DELETE : for deletion

## Prerequisites

- Git
- Make
- Docker
- Java

## Technologies

- GitHub : Code version manager
- Make : Quick commands for utilities
- Docker
- Java
- Spring

## Quick launch process

### For the project

- Launch the makefile command "project-print-init"

## Util links 

- [GitHub Project](https://github.com/users/yahvya/projects/17)