# Nexus artifact-usage plugin

 This plugin for Sonatype Nexus Repository 3 allows the user to get the list of artifacts depending on the given artifact.

 Inspired by [Saleem Shafi | Nexus Artifact Usage plugin](https://github.com/saleemshafi/nexus-artifact-usage-plugin) for Nexus 2

# Status: In development, not ready for use

# Build and deploy

Prerequisites:

* Java 8;
* Maven (I use 3.6.3)

To build sources:

```
mvn clean package -PbuildKar
```

After that, place the file target/nexus3-repository-artifact-usage-plugin-*-bundle.kar in the nexus container at /opt/sonatype/nexus/deploy

# Notes

#### Help for developing Nexus plugin bundle: https://github.com/sonatype/nexus-plugin-bundle

#### Community plugin example: https://github.com/groupe-edf/nexus-report-plugin

#### Official development guide: https://help.sonatype.com/en/bundle-development-overview.html

#### Official plugins examples: https://github.com/sonatype/nexus-public/tree/main/plugins

#### Introduction to developing a plugin for NXRM3 https://github.com/sonatype-nexus-community/nexus-development-guides/blob/master/docs/format-plugin.md
