package com.devstudios.store.devstudios_store_server.application.dtos.rating;

import java.time.LocalDateTime;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IRatingProjection;



public class RatingDto implements IRatingProjection {

    private int stars;
    private String content;
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;



    public int getStars() {
        return stars;
    }
    public void setStars(int stars) {
        this.stars = stars;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public Boolean getIsActive() {
        return isActive;
    }
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
