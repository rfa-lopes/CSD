package CSD.Wallet.Services.Wallets;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Wallet;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@Service
@PropertySource("classpath:application.properties")
public class WalletServiceClass implements WalletServiceInter {

    private RestTemplate restTemplate;

    @Value("${client.server.url}")
    private String SERVER_URL;


    private static String BASE = "/wallets";

    private static String BACKSLASH = "/";
    /* Methods */

    private static String CREATE = "/create";
    private static String DELETE = "/delete";
    private static String AMOUNT = "/amount";
    private static String INFO = "/info";


    public WalletServiceClass() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<SignedResults> create(String name) {
        String url = createURL(CREATE);
        HttpEntity<Wallet> entity = new HttpEntity<Wallet>(new Wallet(name), createHeaders());
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }


    @Override
    public ResponseEntity<SignedResults> delete(long id) throws URISyntaxException {
        String url = createURL(DELETE);
        String idToDelete = createIdURL(id);
        HttpEntity<Long> entity = new HttpEntity<Long>(id, createHeaders());
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(new URI(url + idToDelete), HttpMethod.DELETE, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> getAmount(long id) throws URISyntaxException {
        String url = createURL(AMOUNT);
        String idToGet = createIdURL(id);
        HttpEntity<Long> entity = new HttpEntity<Long>(id, createHeaders());
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(new URI(url + idToGet), HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> getInfo(long id) throws URISyntaxException {
        String url = createURL(INFO);
        String idToGet = createIdURL(id);
        HttpEntity<Long> entity = new HttpEntity<Long>(id, createHeaders());
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(new URI(url + idToGet), HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    /* Auxiliary Methods ------------------------------------------------------------------- */

    private <T> String createURL(String method) {
        return String.format(SERVER_URL + BASE + method);
    }

    private <T> String createIdURL(Long id) {
        return String.format(BACKSLASH + Long.toString(id));
    }

    private HttpHeaders createHeaders() {
        return new HttpHeaders() {
            {
                set("Authorization", LocalRepo.getInstance().getJWT());
            }
        };
    }

}
