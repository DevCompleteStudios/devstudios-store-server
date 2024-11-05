package com.devstudios.store.devstudios_store_server.application.interfaces.projections;

import java.util.List;

import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;



public interface IUserProjection extends IProjection {

    public String getEmail();
    public List<RoleEntity> getRoles();
    public List<IScriptPurchaseProjection> getScriptsPurchases();
    public List<ISubscriptionPurchaseProjection> getSubscriptionsPurchases();

}
