package CSD.Wallet.Services.Wallets;

import CSD.Wallet.Models.WalletModel1;
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
    public ResponseEntity<Long> create(String name) {
        String url = createURL(CREATE);
        WalletModel1 body = new WalletModel1(name);
        ResponseEntity<Long> response = restTemplate.postForEntity(url, body, Long.class);
        return response;
    }

    @Override
    public void delete(long id) throws URISyntaxException {
        String url = createURL(DELETE);
        String idToDelete = createIdURL(id);
        restTemplate.delete(new URI(url + idToDelete));
    }

    @Override
    public ResponseEntity<Long> getAmount(long id) throws URISyntaxException {
        String url = createURL(AMOUNT);
        String idToGet = createIdURL(id);
        ResponseEntity<Long> response = restTemplate.getForEntity(new URI(url + idToGet), Long.class);
        return response;
    }

    @Override
    public ResponseEntity<WalletModel1> getInfo(long id) throws URISyntaxException {
        String url = createURL(INFO);
        String idToGet = createIdURL(id);
        ResponseEntity<WalletModel1> response = restTemplate.getForEntity(new URI(url + idToGet), WalletModel1.class);
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
