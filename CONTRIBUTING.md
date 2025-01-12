# Contributing to this repository

## Getting started

1. Fork the repository on GitHub
2. Clone the forked repository to your machine
3. Install the necessary development tools. In order to develop plugin, you need the following:
   - Java Development Kit (JDK) 8;
   - Apache Maven 3.6.3 or above;
   - Any IDE which supports importing Maven projects.

## Build and deploy

```sh
mvn clean package -PbuildKar
```
After that, place the file target/nexus3-repository-artifact-usage-plugin-*-bundle.kar in the Nexus container at /opt/sonatype/nexus/deploy

### Pull request management

After testing any changes to this plugin in your fork, create a pull request on this project to merge your code with the original plugin's code

# Links

- Help for developing Nexus plugin bundle: https://github.com/sonatype/nexus-plugin-bundle
- Community plugin example: https://github.com/groupe-edf/nexus-report-plugin
- Official development guide: https://help.sonatype.com/en/bundle-development-overview.html
- Official plugins examples: https://github.com/sonatype/nexus-public/tree/main/plugins
- Introduction to developing a plugin for NXRM3 https://github.com/sonatype-nexus-community/nexus-development-guides/blob/master/docs/format-plugin.md
