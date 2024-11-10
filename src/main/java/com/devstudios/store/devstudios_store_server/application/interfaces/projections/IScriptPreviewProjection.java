package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;



public interface IScriptPreviewProjection  {

    public Long getId();
    public String getName();
    public String getDescription();
    public Double getPrice();
    public ScriptMethodPayment getMethodPayment();
    public Boolean getIsActive();
    public String getImage();

}
