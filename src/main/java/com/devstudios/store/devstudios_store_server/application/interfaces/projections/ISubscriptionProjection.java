package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.util.List;




public interface ISubscriptionProjection extends IProjection {

    public List<String> getContent();
    public Long getDaysDuration();
    public String getName();
    public String getDescription();
    public Double getPrice();

}
