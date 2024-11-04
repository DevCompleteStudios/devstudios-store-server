package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="scripts_purchases")
public class ScriptPurchaseEntity extends EntityBase {

    private final String uuid = UUID.randomUUID().toString();

    @ManyToOne
    @JoinColumn(name="script_id")
    private ScriptEntity script;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



    public String getUuid() {
        return uuid;
    }
    public ScriptEntity getScript() {
        return script;
    }
    public void setScript(ScriptEntity script) {
        this.script = script;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }

}
