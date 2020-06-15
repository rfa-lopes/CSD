package CSD.Wallet.Services.Authentication;

import CSD.Wallet.Crypto.OnionBuilder;
import CSD.Wallet.Crypto.Utils.OnionBuilderOperation;
import CSD.Wallet.Models.Account;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static CSD.Wallet.Crypto.Utils.OnionBuilderOperation.Operation.*;

@Service
@PropertySource("classpath:application.properties")
public class AuthServiceClass implements AuthServiceInter {


    @Value("${client.server.url}")
    private String SERVER_URL;

    private RestTemplate restTemplate;

    private static String BASE = "/accounts";

    private static String LOGIN = "/login";

    private static String CREATE = "/create";

    public AuthServiceClass() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<SignedResults> login(String username, String password) {
        return request(createURL(LOGIN),username,password);
    }

    @Override
    public ResponseEntity<SignedResults> create(String username, String password) {
        return request(createURL(CREATE),username,password);
    }

    private <T> String createURL(String method) {
        return String.format(SERVER_URL  + BASE + method);
    }

    private ResponseEntity<SignedResults> request(String url, String username, String password)  {
        String onionUsername = OnionBuilderOperation.generateOnion(ONION_EQUALITY, username.getBytes());
        HttpEntity<Account> entity = new HttpEntity<>(new Account(onionUsername,password));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }
}
