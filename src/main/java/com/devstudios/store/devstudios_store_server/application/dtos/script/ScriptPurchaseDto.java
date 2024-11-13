package com.devstudios.store.devstudios_store_server.application.dtos.script;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IKeyProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPreviewProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPurchaseProjection;



public class ScriptPurchaseDto implements IScriptPurchaseProjection {

    private String uuid;
    private IKeyProjection key;
    private IScriptPreviewProjection script;
    private Double amount;
    private Boolean isActive;


    public ScriptPurchaseDto(){}

    public ScriptPurchaseDto(String uuid, IKeyProjection key, IScriptPreviewProjection script) {
        this.uuid = uuid;
        this.key = key;
        this.script = script;
    }




    @Override
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    @Override
    public IKeyProjection getKey() {
        return key;
    }
    public void setKey(IKeyProjection key) {
        this.key = key;
    }
    @Override
    public IScriptPreviewProjection getScript() {
        return script;
    }
    public void setScript(IScriptPreviewProjection script) {
        this.script = script;
    }
    @Override
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
