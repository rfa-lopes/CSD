package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Crypto.Order.HomoOpeInt;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Sum.HomoAdd;
import CSD.Wallet.Crypto.Utils.OnionBuilderOperation;
import CSD.Wallet.Crypto.Utils.PaillierKey;
import CSD.Wallet.Models.AddRemoveForm;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Services.LocalRepo.KeyType;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import CSD.Wallet.Utils.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Base64;

import static CSD.Wallet.Crypto.Utils.OnionBuilderOperation.Operation.ONION_ORDER;
import static CSD.Wallet.Services.LocalRepo.KeyType.IV;
import static CSD.Wallet.Services.LocalRepo.KeyType.RND;

@Service
@PropertySource("classpath:application.properties")
public class TransferServiceClass implements TransferServiceInter {

    private static final String BACKSLASH = "/";

    private RestTemplate restTemplate;

    @Value("${client.server.url}")
    private String SERVER_URL;

    private static String BASE = "/transfers";

    /* Methods */
    private static String ADD = "/add";
    private static String REMOVE = "/remove";
    private static String TRANSFER = "/transfer";
    private static String GLOBAL = "/globaltransfers";
    private static String WALLET = "/wallettransfers";

    public TransferServiceClass() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<SignedResults> transfer(long fromId, long toId, long amount) {
        String url = createURL(TRANSFER);

        BigInteger amount_normal = BigInteger.valueOf(amount);
        BigInteger amount_add = OnionBuilderOperation.encryptHomoAdd(amount_normal);
        Transfer body = new Transfer(fromId, toId, amount_add.toString(), 0);

        String keys = "HOMOADD:" + LocalRepo.getInstance().getPk().getNsquare().toString();

        HttpEntity<Transfer> entity = new HttpEntity<>(body, createHeaders(keys));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> addAmount(long id, long amount) {
        String url = createURL(ADD);
        AddRemoveForm f = new AddRemoveForm();
        f.setId(id);

        BigInteger amount_normal = BigInteger.valueOf(amount);
        BigInteger amount_add = OnionBuilderOperation.encryptHomoAdd(amount_normal);
        f.setAmount_add(amount_add.toString());

        f.setAmount_ope_rnd(OnionBuilderOperation.generateOnion(ONION_ORDER, BigInteger.valueOf(amount).toByteArray()));

        String HomoAddKey = "HOMOADD:" + LocalRepo.getInstance().getPk().getNsquare().toString();
        String HomoRNDKey = "RND:" + LocalRepo.getInstance().getKey(RND);
        String HomoRNDKeyIV = "IV:" + LocalRepo.getInstance().getKey(IV);
        String keys = HomoAddKey + " " + HomoRNDKey + " " + HomoRNDKeyIV;

        HttpEntity<AddRemoveForm> entity = new HttpEntity<>(f, createHeaders(keys));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> removeAmount(long id, long amount) {
        String url = createURL(REMOVE);
        AddRemoveForm f = new AddRemoveForm();
        f.setId(id);

        BigInteger amount_normal = BigInteger.valueOf(amount);
        BigInteger amount_add = OnionBuilderOperation.encryptHomoAdd(amount_normal);
        f.setAmount_add(amount_add.toString());

        f.setAmount_ope_rnd(OnionBuilderOperation.generateOnion(ONION_ORDER, BigInteger.valueOf(amount).toByteArray()));

        String HomoAddKey = "HOMOADD:" + LocalRepo.getInstance().getPk().getNsquare().toString();
        String HomoRNDKey = "RND:" + LocalRepo.getInstance().getKey(RND);
        String HomoRNDKeyIV = "IV:" + LocalRepo.getInstance().getKey(IV);
        String keys = HomoAddKey + " " + HomoRNDKey + " " + HomoRNDKeyIV;

        HttpEntity<AddRemoveForm> entity = new HttpEntity<>(f, createHeaders(keys));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> listGlobalTransfers() throws URISyntaxException {
        String url = createURL(GLOBAL);
        HttpEntity entity = new HttpEntity(createHeaders(""));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> listWalletTransfers(long id) throws URISyntaxException {
        String url = createURL(WALLET);
        String idToGet = url + BACKSLASH + id;
        HttpEntity<Long> entity = new HttpEntity<>(id, createHeaders(""));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(idToGet, HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    /*
     * Auxiliary Methods
     * -------------------------------------------------------------------
     */

    private <T> String createURL(String method) {
        return String.format(SERVER_URL + BASE + method);
    }

    private HttpHeaders createHeaders(String keys) {
        return new HttpHeaders() {
            {
                set("Authorization", LocalRepo.getInstance().getJWT());
                if (!keys.equals(""))
                    set("keys", keys);
            }
        };
    }

}
