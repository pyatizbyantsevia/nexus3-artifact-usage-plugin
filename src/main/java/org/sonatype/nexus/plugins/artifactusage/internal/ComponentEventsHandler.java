package org.sonatype.nexus.plugins.artifactusage.internal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.sonatype.nexus.common.event.EventAware;
import org.sonatype.nexus.common.event.EventAware.Asynchronous;
import org.sonatype.nexus.common.stateguard.StateGuardLifecycleSupport;
import org.sonatype.nexus.repository.storage.ComponentCreatedEvent;
import org.sonatype.nexus.repository.storage.ComponentDeletedEvent;

import com.google.common.eventbus.Subscribe;

@Singleton
@Named
public class ComponentEventsHandler
    extends StateGuardLifecycleSupport
    implements EventAware, Asynchronous
{
  @Subscribe
  public void on(final ComponentCreatedEvent event) {
    log.info("Component created in repository:" + event.getRepositoryName());
    log.info("Component created with group:" + event.getEntity().getEntityMetadata());
  }

  @Subscribe
  public void on(final ComponentDeletedEvent event) {
    log.info("Component deleted" + event.getComponent());
  }
}

