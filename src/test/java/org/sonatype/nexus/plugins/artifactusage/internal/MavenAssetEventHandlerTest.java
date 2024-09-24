package org.sonatype.nexus.plugins.artifactusage.internal;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.sonatype.nexus.blobstore.api.Blob;
import org.sonatype.nexus.blobstore.api.BlobId;
import org.sonatype.nexus.blobstore.api.BlobStore;
import org.sonatype.nexus.blobstore.api.BlobStoreManager;
import org.sonatype.nexus.plugins.artifactusage.internal.JarParser;
import org.sonatype.nexus.plugins.artifactusage.internal.MavenAssetEventHandler;
import org.sonatype.nexus.plugins.artifactusage.internal.MavenReverseDependencyCalculator;
import org.sonatype.nexus.repository.storage.Asset;
import org.sonatype.nexus.repository.storage.AssetCreatedEvent;
import org.sonatype.nexus.repository.storage.AssetDeletedEvent;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.mockito.Mockito.*;

public class MavenAssetEventHandlerTest {

    @Mock
    private JarParser jarParser;

    @Mock
    private BlobStoreManager blobStoreManager;

    @Mock
    private MavenReverseDependencyCalculator mavenReverseDependencyCalculator;

    @Mock
    private Asset asset;

    @Mock
    private Blob blob;

    @Mock
    private BlobStore blobStore;

    @InjectMocks
    private MavenAssetEventHandler eventHandler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    public void testMavenAssetCreatedEventHandler() throws IOException {
//        when(asset.contentType()).thenReturn("application/java-archive");
//        when(asset.blobRef()).thenReturn(new BlobId("blob-id"));
//        when(blobStoreManager.get("default")).thenReturn(blobStore);
//        when(blobStore.get(any(BlobId.class))).thenReturn(blob);
//        InputStream inputStream = new ByteArrayInputStream("dummy content".getBytes());
//        when(blob.getInputStream()).thenReturn(inputStream);
//
//        AssetCreatedEvent event = new AssetCreatedEvent(asset);
//        eventHandler.mavenAssetCreatedEventHandler(event);
//
//        verify(jarParser, times(1)).readMetaInfContent(any(InputStream.class));
//    }

    @Test
    public void testMavenAssetDeletedEventHandler() throws IOException {
        when(asset.contentType()).thenReturn("application/java-archive");

        AssetDeletedEvent event = new AssetDeletedEvent(asset);
        eventHandler.mavenAssetDeletedEventHandler(event);

        verify(mavenReverseDependencyCalculator, times(1)).getMap();
    }

//    @Test
//    public void testMavenAssetCreatedEventHandler_NonJar() throws IOException {
//        when(asset.contentType()).thenReturn("application/zip");
//
//        AssetCreatedEvent event = new AssetCreatedEvent(asset);
//        eventHandler.mavenAssetCreatedEventHandler(event);
//
//        verify(jarParser, times(0)).readMetaInfContent(any(InputStream.class));
//    }

//    @Test
//    public void testMavenAssetDeletedEventHandler_NonJar() throws IOException {
//        when(asset.contentType()).thenReturn("application/zip");
//
//        AssetDeletedEvent event = new AssetDeletedEvent(asset);
//        eventHandler.mavenAssetDeletedEventHandler(event);
//
//        verify(mavenReverseDependencyCalculator, times(0)).getMap();
//    }
}
