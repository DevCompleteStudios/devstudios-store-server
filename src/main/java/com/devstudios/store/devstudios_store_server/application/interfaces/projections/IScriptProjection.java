package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.util.List;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.ScriptMethodPayment;




public interface IScriptProjection extends IProjection {

    public String getName();
    public String getDescription();
    public Double getPrice();
    public ScriptMethodPayment getMethodPayment();
    public List<IRatingProjection> getRatings();
    public List<IScriptPurchasePreviewProjection> getPurchases();

}


