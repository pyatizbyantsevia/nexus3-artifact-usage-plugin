package org.sonatype.nexus.plugins.artifactusage.internal;

import  java.io.IOException;
import java.util.Objects;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventAware.Asynchronous;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.repository.storage.AssetCreatedEvent;

import com.google.common.eventbus.Subscribe;
import org.sonatype.nexus.repository.storage.AssetDeletedEvent;

@Singleton
@Named
public class MavenAssetEventHandler
        extends StateGuardLifecycleSupport
        implements EventAware, Asynchronous {

    private JarParser jarParser;
    private BlobStoreManager blobStoreManager;
    private MavenReverseDependencyCalculator mavenReverseDependencyCalculator;

    @Inject
    public MavenAssetEventHandler(JarParser jarParser, BlobStoreManager blobStoreManager, MavenReverseDependencyCalculator mavenReverseDependencyCalculator) {
        this.jarParser = jarParser;
        this.blobStoreManager = blobStoreManager;
        this.mavenReverseDependencyCalculator = mavenReverseDependencyCalculator;
    }

    @Subscribe
    public void mavenAssetCreatedEventHandler(final AssetCreatedEvent event) throws IOException {
        if (Objects.equals(event.getAsset().contentType(), "application/java-archive")) {
            jarParser.readMetaInfContent(blobStoreManager.get("default").get(event.getAsset().blobRef().getBlobId()).getInputStream());
        }
    }

    @Subscribe
    public void mavenAssetDeletedEventHandler(final AssetDeletedEvent event) throws IOException {

        if (Objects.equals(event.getAsset().contentType(), "application/java-archive")) {
            log.info(String.valueOf(mavenReverseDependencyCalculator.getMap()));
        }
    }

}
