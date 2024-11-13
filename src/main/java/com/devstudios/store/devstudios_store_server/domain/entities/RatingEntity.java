package com.devstudios.store.devstudios_store_server.domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "ratings")
public class RatingEntity extends EntityBase {

    private int stars;
    private String content;

    @OneToOne
    @JoinColumn(name = "script_purchase_id")
    private ScriptPurchaseEntity purchase;

    @ManyToOne
    @JoinTable(name = "script_id")
    private ScriptEntity script;


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
    public ScriptPurchaseEntity getPurchase() {
        return purchase;
    }
    public void setPurchase(ScriptPurchaseEntity purchase) {
        this.purchase = purchase;
    }
    public ScriptEntity getScript() {
        return script;
    }
    public void setScript(ScriptEntity script) {
        this.script = script;
    }


}
