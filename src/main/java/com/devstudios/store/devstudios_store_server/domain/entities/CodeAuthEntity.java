package com.devstudios.store.devstudios_store_server.domain.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;



@Entity
@Table(name="auths_codes")
public class CodeAuthEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique=true)
    private String code;
    private LocalDateTime expiredDate;


    public CodeAuthEntity(){}

    public CodeAuthEntity(String code){
        this.code = code;
    }


    @PrePersist
    public void PrePersist(){
        this.expiredDate = LocalDateTime.now().plusMinutes(5);
    }


    public String getCode() {
        return code;
    }
    public LocalDateTime getExpiredDate() {
        return expiredDate;
    }
    public Long getId() {
        return id;
    }


}
