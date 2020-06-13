package CSD.Wallet.Services.LocalRepo;

import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;


public class LocalRepo {

    private static String JWT;

    public static void setJWT(String JWT) {
        LocalRepo.JWT = JWT;
    }

    public static String getJWT() {
       return JWT;
    }

}
