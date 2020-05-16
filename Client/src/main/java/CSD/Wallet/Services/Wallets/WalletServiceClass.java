package CSD.Wallet.Services.Wallets;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Wallet;
import CSD.Wallet.Utils.ConvertHttpStatus;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
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
        ResponseEntity<SignedResults> signedResults = restTemplate.postForEntity(url,new Wallet(name), SignedResults.class);
        return signedResults;
    }

    @Override
    public void delete(long id) throws URISyntaxException {
        String url = createURL(DELETE);
        String idToDelete = createIdURL(id);
        restTemplate.delete(new URI(url + idToDelete));
    }

    @Override
    public ResponseEntity<SignedResults> getAmount(long id) throws URISyntaxException {
        String url = createURL(AMOUNT);
        String idToGet = createIdURL(id);
        ResponseEntity<SignedResults> response = restTemplate.getForEntity(new URI(url + idToGet), SignedResults.class);
        return response;
    }

    @Override
    public ResponseEntity<SignedResults> getInfo(long id) throws URISyntaxException {
        String url = createURL(INFO);
        String idToGet = createIdURL(id);
        ResponseEntity<SignedResults> response = restTemplate.getForEntity(new URI(url + idToGet), SignedResults.class);
        return response;
    }



    /* Auxiliary Methods ------------------------------------------------------------------- */

    private <T> String createURL(String method) {
        return String.format(SERVER_URL + BASE + method);
    }

    private <T> String createIdURL(Long id) {
        return String.format(BACKSLASH + Long.toString(id));
    }
}
