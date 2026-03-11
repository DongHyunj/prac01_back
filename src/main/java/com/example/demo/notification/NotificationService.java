package com.example.demo.notification;

import com.example.demo.notification.model.NotificationEntity;
import com.example.demo.notification.model.NotificationDto;
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
        this.pushService.setPublicKey("BLHgfPga02L2u89uc4xjhbUFTy_U04rQCjGq7o24oxtqfVmAPHTxOmp6xndSHZtGQpmt7gqTFdMXco2gRNP7_p8");
        this.pushService.setPrivateKey("pWhOI-mTyOyx5hogOmKRiYHDCtm_IMpnz1lzWNdMfKU");
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
