package com.devstudios.store.devstudios_store_server.application.interfaces.projections;



public interface IScriptPreviewProjection  {

    public Long getId();
    public String getName();
    public String getDescription();
    public Double getPrice();
    public Boolean getIsAvailableInSubscription();

}
