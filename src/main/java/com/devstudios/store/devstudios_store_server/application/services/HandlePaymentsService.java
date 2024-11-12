package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.UserEntity;
import com.devstudios.store.devstudios_store_server.infrastructure.CustomExceptions.CustomException;

@Service
public class HandlePaymentsService {

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IScriptRepository scriptRepository;
    @Autowired
    IMailerService mailerService;



    private void BuyScript( UserEntity user, Long scriptId ){
        try {
            ScriptEntity script = scriptRepository.findById(scriptId)
                .orElseThrow( () -> CustomException.notFoundException("Not exists"));

            ScriptPurchaseEntity purchase = new ScriptPurchaseEntity();
            purchase.setScript(script);
            purchase.setUser(user);

            KeyEntity key = new KeyEntity();
            purchase.setKey(key);

            user.getScriptsPurchases().add(purchase);
            userRepository.save(user);
        } catch (Exception e) {
            throw CustomException.internalServerException("Un usuario compro y no recibio su pago. Email del usuario: " + user.getEmail() + " fecha: " + LocalDateTime.now());
        }
    }

    private void BuySubscription( UserEntity user, Long id ){}



    public void HandlePayment(String email, String orderId, String type){
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if( user.isPresent() ){
            Long id = Long.parseLong(orderId);
            if( TypePayment.ONE_PAYMENT.name().equals(type) ){
                System.out.println("Se hizo una compra por 1 solo pago");
                this.BuyScript(user.get(), id);
            } else {
                System.out.println("se hizo una compra por suscripcion");
                this.BuySubscription(user.get(), id);
            }
        }
    }

}
