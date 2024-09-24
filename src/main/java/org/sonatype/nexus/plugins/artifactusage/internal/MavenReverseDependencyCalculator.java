package org.sonatype.nexus.plugins.artifactusage.internal;

import org.sonatype.nexus.blobstore.api.BlobStoreManager;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Singleton
@Named
public class MavenReverseDependencyCalculator {

    private Map<GAV, ArrayList<GAV>> map = new HashMap<>();

    public void addReverseDependency(GAV dependentArtifact, ArrayList<GAV> mainArtifacts) {
        for (GAV mainArtifact : mainArtifacts) {
            map.computeIfAbsent(mainArtifact, k -> new ArrayList<>()).add(dependentArtifact);
        }
    }

    public Map<GAV, ArrayList<GAV>> getMap() {
        return map;
    }

    public void deleteReverseDependency(GAV mainArtifact, ArrayList<GAV> dependentArtifacts) {

    }
}
