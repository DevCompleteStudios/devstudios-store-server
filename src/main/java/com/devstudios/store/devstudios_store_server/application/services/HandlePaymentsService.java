package com.devstudios.store.devstudios_store_server.application.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devstudios.store.devstudios_store_server.application.interfaces.enums.TypePayment;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptPurchaseRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.IScriptRepository;
import com.devstudios.store.devstudios_store_server.application.interfaces.repositories.ISubscriptionPurchaseRepository;
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

    @Value("${url.client}")
    private String urlCLient;
    @Value("${discord.url}")
    private String discordLink;
    @Value("${youtube.url}")
    private String ytLink;

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IScriptRepository scriptRepository;
    @Autowired
    private IMailerService mailerService;
    @Autowired
    private ISubscriptionRepository subscriptionRepository;
    @Autowired
    IScriptPurchaseRepository scriptPurchaseRepository;
    @Autowired
    ISubscriptionPurchaseRepository subscriptionPurchaseRepository;



    private void BuyScript( UserEntity user, Long scriptId, String orderId ){
        try {
            ScriptEntity script = scriptRepository.findById(scriptId)
                .orElseThrow( () -> CustomException.notFoundException("Not exists"));

            ScriptPurchaseEntity purchase = new ScriptPurchaseEntity();
            purchase.setScript(script);
            purchase.setUser(user);
            purchase.setAmount(script.getPrice());
            purchase.setUuid(orderId);

            KeyEntity key = new KeyEntity();
            purchase.setKey(key);

            user.getScriptsPurchases().add(purchase);
            userRepository.save(user);

            this.notifyClientPurchaseSucces(user.getEmail(), "Script", script.getName(), script.getPrice(), purchase.getUuid());
        } catch (Exception e) {
            notifyError(user.getEmail());
        }
    }

    private void BuySubscription( UserEntity user, Long id, String orderId ){
        try {
            SubscriptionEntity sub = subscriptionRepository.findById(id)
                .orElseThrow( () -> CustomException.notFoundException("Subscription not found"));

            SubscriptionPurchaseEntity purchase = new SubscriptionPurchaseEntity();
            purchase.setSubscription(sub);
            purchase.setUser(user);
            purchase.setAmount(sub.getPrice());
            purchase.setUuid(orderId);

            KeyEntity key = new KeyEntity();
            purchase.setKey(key);

            user.getSubscriptionPurchases().add(purchase);
            userRepository.save(user);

            this.notifyClientPurchaseSucces(user.getEmail(), "Subscription", sub.getName(), sub.getPrice(), purchase.getUuid());
        } catch( Exception ex ){
            this.notifyError(user.getEmail());
        }
    }

    private void cancelBuy( String orderId ){
        Optional<ScriptPurchaseEntity> purchase = scriptPurchaseRepository.findByUuid(orderId);

        purchase.ifPresent( p -> {
            p.setIsActive(false);
            p.getKey().setIsActive(false);

            scriptPurchaseRepository.save(p);
            System.out.println("La compra se ha cancelado correctamente");
        });
    }

    private void cancelSubscripton( String orderId ){
        Optional<SubscriptionPurchaseEntity> entity = subscriptionPurchaseRepository.findByUuid(orderId);

        entity.ifPresent( s -> {
            s.setIsActive(false);
            s.getKey().setIsActive(false);

            subscriptionPurchaseRepository.save(s);
        });
    }


    private void notifyError(String email){
        throw CustomException.internalServerException("Un usuario compro y no recibio su pago. Email del usuario: " + email + " fecha: " + LocalDateTime.now());
    }

    private void notifyClientPurchaseSucces(String email, String itemType, String itemName, Double amount, String orderId){
        String html = """
                <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; line-height: 1.6; color: #333; background-color: #f4f4f4;">
                    <div style="max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 30px; border: 1px solid #e0e0e0; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1);">
                        <!-- Header -->
                        <div style="border-bottom: 2px solid #000; padding-bottom: 20px; margin-bottom: 30px;">
                            <h2 style="color: #000; font-size: 24px; font-weight: bold; margin: 0;">Thank you for your purchase at DevStudios!</h2>
                        </div>

                        <!-- Content -->
                        <div style="margin-bottom: 30px;">
                            <p style="margin: 0 0 15px 0;">Hello <strong>{{userName}}</strong>,</p>
                            <p style="margin: 0 0 15px 0;">We are pleased to inform you that your purchase of <strong>{{itemType}}</strong>: <em>{{itemName}}</em> has been successfully completed.</p>

                            <!-- Purchase Details -->
                            <div style="background-color: #f8f8f8; padding: 20px; border-radius: 6px; margin: 20px 0;">
                                <ul style="list-style-type: none; margin: 0; padding: 0;">
                                    <li style="padding: 8px 0; border-bottom: 1px solid #e0e0e0;"><strong>Product:</strong> {{itemName}}</li>
                                    <li style="padding: 8px 0; border-bottom: 1px solid #e0e0e0;"><strong>Type:</strong> {{itemType}}</li>
                                    <li style="padding: 8px 0; border-bottom: 1px solid #e0e0e0;"><strong>Amount:</strong> ${{amount}}</li>
                                    <li style="padding: 8px 0; border-bottom: 1px solid #e0e0e0;"><strong>Date:</strong> {{purchaseDate}}</li>
                                    <li style="padding: 8px 0;"><strong>Order ID:</strong> {{orderId}}</li>
                                </ul>
                            </div>

                            <p style="margin: 0 0 15px 0;">You can now enjoy your service. If you have any questions or need assistance, feel free to contact us.</p>
                            <p style="margin: 0 0 15px 0;">We'd appreciate it if you could leave us a positive review using your Order ID: <strong>{{orderId}}</strong>. Your feedback helps us improve and serve you better.</p>
                        </div>

                        <!-- CTA Buttons -->
                        <div style="text-align: center; margin: 30px 0;">
                            <a href="{{viewOrderUrl}}" style="display: inline-block; padding: 12px 24px; margin: 10px; background-color: #000; color: #fff; text-decoration: none; border-radius: 4px; font-weight: bold;">View Order</a>
                        </div>

                        <!-- Social Links -->
                        <div style="text-align: center; padding: 20px 0; border-top: 1px solid #e0e0e0; margin-top: 30px;">
                            <a href="{{discordUrl}}" style="display: inline-block; margin: 0 15px; color: #000; text-decoration: none; font-weight: bold;">Discord</a>
                            <a href="{{youtubeUrl}}" style="display: inline-block; margin: 0 15px; color: #000; text-decoration: none; font-weight: bold;">YouTube</a>
                        </div>

                        <!-- Footer -->
                        <div style="text-align: center; color: #666; font-size: 0.9em; margin-top: 30px; padding-top: 20px; border-top: 1px solid #e0e0e0;">
                            <p style="margin: 0 0 10px 0;">Best regards,<br>The DevStudios Team</p>
                            <p style="margin: 0; font-style: italic;">This is an automated message, please do not reply to this email.</p>
                        </div>
                    </div>
                </body>
            """;
            html = html.replace("{{userName}}", email)
                .replace("{{itemType}}", itemType)
                .replace("{{itemName}}", itemName)
                .replace("{{amount}}", amount.toString())
                .replace("{{purchaseDate}}", LocalDateTime.now().toString())
                .replace("{{viewOrderUrl}}", this.urlCLient + "/profile")
                .replace("{{discordUrl}}", this.discordLink)
                .replace("{{orderId}}", orderId)
                .replace("{{youtubeUrl}}", this.ytLink);

        mailerService.sendEmail(html, email, "Purchase succes");
    }





    public void HandlePayment(String email, Long id, String type, String orderId){
        Optional<UserEntity> user = userRepository.findByEmail(email);

        if( user.isPresent() ){
            if( TypePayment.ONE_PAYMENT.name().equals(type) ){
                this.BuyScript(user.get(), id, orderId);
            } else {
                this.BuySubscription(user.get(), id, orderId);
            }
        }
    }

    public void handleRefunded( String orderId, String type ){
        if( TypePayment.ONE_PAYMENT.name().equals(type) ){
            this.cancelBuy(orderId);
        } else if ( TypePayment.SUBSCRIPTION.name().equals(type) ){
            this.cancelSubscripton(orderId);
        }
    }

}
