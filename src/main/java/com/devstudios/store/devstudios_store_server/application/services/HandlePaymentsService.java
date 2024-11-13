package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IUserRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.services.IMailerService;
import com.devstudios.store.devstudios_store_server.domain.entities.KeyEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.ScriptPurchaseEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionEntity;
import com.devstudios.store.devstudios_store_server.domain.entities.SubscriptionPurchaseEntity;
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
    @Autowired
    ISubscriptionRepository subscriptionRepository;



    private void BuyScript( UserEntity user, Long scriptId ){
        try {
            ScriptEntity script = scriptRepository.findById(scriptId)
                .orElseThrow( () -> CustomException.notFoundException("Not exists"));

            ScriptPurchaseEntity purchase = new ScriptPurchaseEntity();
            purchase.setScript(script);
            purchase.setUser(user);
            purchase.setAmount(script.getPrice());

            KeyEntity key = new KeyEntity();
            purchase.setKey(key);

            user.getScriptsPurchases().add(purchase);
            userRepository.save(user);
        } catch (Exception e) {
            notifyError(user.getEmail());
        }
    }

    private void BuySubscription( UserEntity user, Long id ){
        try {
            SubscriptionEntity sub = subscriptionRepository.findById(id)
                .orElseThrow( () -> CustomException.notFoundException("Subscription not found"));

            SubscriptionPurchaseEntity purchase = new SubscriptionPurchaseEntity();
            purchase.setSubscription(sub);
            purchase.setUser(user);
            purchase.setAmount(sub.getPrice());

            KeyEntity key = new KeyEntity();
            purchase.setKey(key);

            user.getSubscriptionPurchases().add(purchase);
            userRepository.save(user);
        } catch (Exception e) {
            notifyError(user.getEmail());
        }
    }


    private void notifyError(String email){
        throw CustomException.internalServerException("Un usuario compro y no recibio su pago. Email del usuario: " + email + " fecha: " + LocalDateTime.now());
    }



    public void HandlePayment(String email, String orderId, String type){
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if( user.isPresent() ){
            Long id = Long.parseLong(orderId);
            if( TypePayment.ONE_PAYMENT.name().equals(type) ){
                this.BuyScript(user.get(), id);
            } else {
                this.BuySubscription(user.get(), id);
            }
        }
    }

}
