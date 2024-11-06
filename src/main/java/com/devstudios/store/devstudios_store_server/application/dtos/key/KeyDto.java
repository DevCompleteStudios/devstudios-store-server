package com.devstudios.store.devstudios_store_server.application.dtos.key;

import com.devstudios.store.devstudios_store_server.application.interfaces.projections.IKeyProjection;




public class KeyDto implements IKeyProjection {

    private String value;
    private String currentUserRobloxId;
    private Long id;


    public KeyDto(){}

    public KeyDto(String value, String currentUserRobloxId) {
        this.value = value;
        this.currentUserRobloxId = currentUserRobloxId;
    }



    @Override
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    @Override
    public String getCurrentUserRobloxId() {
        return currentUserRobloxId;
    }
    public void setCurrentUserRobloxId(String currentUserRobloxId) {
        this.currentUserRobloxId = currentUserRobloxId;
    }
    @Override
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

}
