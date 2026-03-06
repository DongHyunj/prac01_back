package com.example.demo.notification;

import com.example.demo.notification.model.NotificationDto;
import com.example.demo.notification.model.NotificationEntity;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.ExecutionException;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PushService pushService;

    public NotificationService(NotificationRepository notificationRepository) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        this.notificationRepository = notificationRepository;

        if (Security.getProperty(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        this.pushService = new PushService();
        this.pushService.setPublicKey("BPaZq-g4ccJZP5w6rdCs8ZBle2U0z4WLztW-ezkCh-fg3auvG1-kk-PYznP80COID18IzxQlKytBooY2ge40Ftg");
        this.pushService.setPrivateKey("bBwweQ4tGhVGbhZDIohGK1U7ML7XYhLDwlNA7Ia2p4I");
        this.pushService.setSubject("우리 사이트이다");
    }

    public void subscribe(NotificationDto.Subscribe dto) {
        notificationRepository.save(dto.toEntity());
    }

    public void send(NotificationDto.Send dto) throws GeneralSecurityException, JoseException, IOException, ExecutionException, InterruptedException {
        NotificationEntity entity = notificationRepository.findById(dto.getIdx()).orElseThrow();

        Subscription.Keys keys = new Subscription.Keys(
                entity.getP256dh(),
                entity.getAuth()
        );
        Subscription subscription = new Subscription(entity.getEndpoint(), keys);

        Notification notification = new Notification(subscription, NotificationDto.Payload.from(dto).toString());
        pushService.send(notification);

    }
}