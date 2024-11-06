package com.devstudios.store.devstudios_store_server.application.interfaces.projections;



public interface IRatingProjection extends IProjection {

    public int getStars();
    public String getContent();
    public IUserPreviewProjection getUser();

}
