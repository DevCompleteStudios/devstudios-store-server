package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.time.LocalDateTime;



public interface IProjection {

    public Long getId();
    public LocalDateTime getCreatedAt();
    public LocalDateTime getUpdatedAt();
    public Boolean getIsActive();

}
