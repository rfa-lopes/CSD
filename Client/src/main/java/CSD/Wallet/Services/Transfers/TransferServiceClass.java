package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Crypto.Order.HomoOpeInt;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Searchable.HomoSearch;
import CSD.Wallet.Crypto.Sum.HomoAdd;
import CSD.Wallet.Crypto.Utils.OnionBuilderOperation;
import CSD.Wallet.Crypto.Utils.PaillierKey;
import CSD.Wallet.Models.*;
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
import static CSD.Wallet.Services.LocalRepo.KeyType.*;
import static CSD.Wallet.Utils.JSON.toObj;

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
    private static String DATE_TRANSFER = "/datetransfers";

    public TransferServiceClass() {
        restTemplate = new RestTemplate();
    }

    @Override
    public ResponseEntity<SignedResults> transfer(long fromId, long toId, long amount) {
        String url = createURL(TRANSFER);

        BigInteger amount_normal = BigInteger.valueOf(amount);
        BigInteger amount_add = OnionBuilderOperation.encryptHomoAdd(amount_normal);
        Transfer body = new Transfer(fromId, toId, amount_add.toString(), 0);

        //Data encriptada para search
        String date = body.getActualDate();
        LocalRepo repo = LocalRepo.getInstance();

        String SRKey = repo.getKey(SR);
        byte[] SRKeySecretBytes = Base64.getDecoder().decode(SRKey);
        SecretKeySpec SRKeySecret = new SecretKeySpec(SRKeySecretBytes, 0, SRKeySecretBytes.length, "AES");

      //  String nextDayDate = body.getNextDay();
       // System.out.println(nextDayDate);

       // Para testar (dia seguinte)
       // String endDate =  HomoSearch.encrypt(SRKeySecret,nextDayDate);
      //  System.out.println(date);

        String endDate =  HomoSearch.encrypt(SRKeySecret,date);
        body.setDate(endDate);
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
        String keys = HomoAddKey + " " + HomoRNDKey;

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
        String keys = HomoAddKey + " " + HomoRNDKey;

        HttpEntity<AddRemoveForm> entity = new HttpEntity<>(f, createHeaders(keys));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> listGlobalTransfers() throws URISyntaxException {
        String url = createURL(GLOBAL);
        HttpEntity entity = new HttpEntity(createHeaders(null));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> listWalletTransfers(long id) throws URISyntaxException {
        String url = createURL(WALLET);
        String idToGet = url + BACKSLASH + id;
        HttpEntity<Long> entity = new HttpEntity<>(id, createHeaders(null));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(idToGet, HttpMethod.GET, entity,
                SignedResults.class);
        return signedResults;
    }

    @Override
    public ResponseEntity<SignedResults> listDateTransfers(String date) throws URISyntaxException {
        String url = createURL(DATE_TRANSFER);
        LocalRepo repo = LocalRepo.getInstance();

        String srKey = repo.getKey(SR);
        byte[] srKeyBytes= Base64.getDecoder().decode(srKey);
        SecretKeySpec srKeySecret = new SecretKeySpec(srKeyBytes, 0, srKeyBytes.length, "AES");
        String dateEnc = HomoSearch.wordDigest64(srKeySecret, date);

        HttpEntity entity = new HttpEntity(new StringWrapper(dateEnc),createHeaders(null));
        ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
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
                if (keys != null)
                    set("keys", keys);
            }
        };
    }

}
