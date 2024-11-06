package com.devstudios.store.devstudios_store_server.application.dtos.user;

import java.time.LocalDateTime;
import java.util.List;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IScriptPurchaseProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.ISubscriptionPurchaseProjection;
import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;




public class UserDto implements IUserProjection {

    private String email;
    private List<RoleEntity> roles;
    private List<IScriptPurchaseProjection> scriptsPurchases;
    private List<ISubscriptionPurchaseProjection> subscriptionsPurchases;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;


    public UserDto(){}

    public UserDto(String email, List<RoleEntity> roles, List<IScriptPurchaseProjection> scriptsPurchases,
            List<ISubscriptionPurchaseProjection> subscriptionsPurchases, Long id, LocalDateTime createdAt,
            LocalDateTime updatedAt, Boolean isActive) {
        this.email = email;
        this.roles = roles;
        this.scriptsPurchases = scriptsPurchases;
        this.subscriptionsPurchases = subscriptionsPurchases;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }




    @Override
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public List<RoleEntity> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
    @Override
    public List<IScriptPurchaseProjection> getScriptsPurchases() {
        return scriptsPurchases;
    }
    public void setScriptsPurchases(List<IScriptPurchaseProjection> scriptsPurchases) {
        this.scriptsPurchases = scriptsPurchases;
    }
    @Override
    public List<ISubscriptionPurchaseProjection> getSubscriptionsPurchases() {
        return subscriptionsPurchases;
    }
    public void setSubscriptionsPurchases(List<ISubscriptionPurchaseProjection> subscriptionsPurchases) {
        this.subscriptionsPurchases = subscriptionsPurchases;
    }
    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Override
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    @Override
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    @Override
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
