package com.devstudios.store.devstudios_store_server.application.dtos.user;

import java.time.LocalDateTime;
import java.util.List;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IUserPreviewProjection;
import com.devstudios.store.devstudios_store_server.domain.entities.RoleEntity;




public class UserPreviewDto implements IUserPreviewProjection {

    private List<RoleEntity> roles;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;


    public UserPreviewDto(){}

    public UserPreviewDto(List<RoleEntity> roles, Long id, LocalDateTime createdAt, LocalDateTime updatedAt,
            Boolean isActive) {
        this.roles = roles;
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
    }




    @Override
    public List<RoleEntity> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
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
