package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.time.LocalDateTime;




public interface ISubscriptionPurchaseProjection extends IProjection {

    public LocalDateTime getDateExpired();
    public LocalDateTime getDateAvailableForNewUser();
    public String getUuid();
    public IKeyProjection getKey();
    public ISubscriptionPreviewProjection getSubscription();

}
