package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.TransferModel1;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@PropertySource("classpath:application.properties")
public class TransferServiceClass implements TransferServiceInter {

    private RestTemplate restTemplate;

    @Value("${client.server.url}")
    private String SERVER_URL;

    private static String BASE = "/transfers";

    /* Methods */
    private static String ADD = "/add";
    private static String REMOVE = "/remove";
    private static String TRANSFER = "/transfer";
    private static String GLOBAL = "globaltransfers";
    private static String WALLET = "wallettransfers";


    public TransferServiceClass(){
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<Void> transfer(long fromId, long toId, long amount) {

        String url = createURL(TRANSFER);
        TransferModel1 body = new TransferModel1(fromId, toId, amount);
        ResponseEntity<Void> response  = restTemplate.postForEntity(url, body, Void.class);
        return response;
    }

    /* Auxiliary Methods ------------------------------------------------------------------- */

    private <T> String createURL(String method){
        return String.format(SERVER_URL + BASE + method);
    }
}
