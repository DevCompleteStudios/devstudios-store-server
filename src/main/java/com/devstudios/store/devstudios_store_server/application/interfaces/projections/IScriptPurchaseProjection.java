package com.devstudios.store.devstudios_store_server.application.interfaces.projections;




public interface IScriptPurchaseProjection {

    public String getUuid();
    public IKeyProjection getKey();
    public IScriptPreviewProjection getScript();

}
