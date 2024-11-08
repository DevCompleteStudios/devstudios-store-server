package com.devstudios.store.devstudios_store_server.domain.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;



@Entity
@Table(name="users")
public class UserEntity extends EntityBase {

    @Column(unique=true)
    private String email;
    private String password;

    @OneToOne(cascade = CascadeType.PERSIST, orphanRemoval=true)
    @JoinColumn(name="code_auth_id")
    private CodeAuthEntity codeAuth;

    @ManyToMany(cascade=CascadeType.PERSIST)
    private List<RoleEntity> roles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade=CascadeType.PERSIST)
    private List<SubscriptionPurchaseEntity> subscriptionsPurchases = new ArrayList<>();

    @OneToMany(mappedBy="user", cascade=CascadeType.MERGE)
    private List<ScriptPurchaseEntity> scriptsPurchases = new ArrayList<>();


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public List<RoleEntity> getRoles() {
        return roles;
    }
    public void setRoles(List<RoleEntity> roles) {
        this.roles = roles;
    }
    public List<SubscriptionPurchaseEntity> getSubscriptionPurchases() {
        return subscriptionsPurchases;
    }
    public void setSubscriptionPurchases(List<SubscriptionPurchaseEntity> subscriptionsPurchases) {
        this.subscriptionsPurchases = subscriptionsPurchases;
    }
    public List<ScriptPurchaseEntity> getScriptsPurchases() {
        return scriptsPurchases;
    }
    public void setScriptsPurchases(List<ScriptPurchaseEntity> scriptsPurchases) {
        this.scriptsPurchases = scriptsPurchases;
    }
    public CodeAuthEntity getCodeAuth() {
        return codeAuth;
    }
    public void setCodeAuth(CodeAuthEntity codeAuth) {
        this.codeAuth = codeAuth;
    }

}

