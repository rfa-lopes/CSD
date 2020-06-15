package CSD.Wallet.Commands.Wallet;

import CSD.Wallet.Crypto.Deterministic.HomoDet;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Utils.OnionBuilderOperation;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Wallet;
import CSD.Wallet.Services.LocalRepo.KeyType;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import CSD.Wallet.Services.Wallets.WalletServiceInter;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.util.Base64;

import static CSD.Wallet.Crypto.Utils.OnionBuilderOperation.Operation.ONION_EQUALITY;
import static CSD.Wallet.Services.LocalRepo.KeyType.*;

@ShellComponent
public class WalletCommandsClass implements WalletCommandsInter {

    private static final String MESSAGE_404 = "Wallet ID doesn't exist.";
    private static final String MESSAGE_400 = "Bad request, try again.";
    private static final String MESSAGE_ERROR = "Something went wrong.";
    private static final String WRONG_SIGNATURE = "Wrong signatures.";
    private static final String UNAUTHORIZED = "You don't have permissions to execute this operation!";
    private static final String MESSAGE_TIMEOUT = "Time out request.";

    private final WalletServiceInter service;

    @Autowired
    public WalletCommandsClass(WalletServiceInter service, Environment env) {
        System.setProperty("javax.net.ssl.trustStore", env.getProperty("client.ssl.trust-store"));
        System.setProperty("javax.net.ssl.trustStorePassword", env.getProperty("client.ssl.trust-store-password"));
        this.service = service;
    }


    @Override
    @ShellMethod("Create new wallet.")
    public String createWallet(
            @ShellOption({"-n", "-name"}) String name) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.create(name);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                int id = (Integer) res.getResult();
                return "New wallet created!" + "\n" + "Id:" + id;
            default:
                return MESSAGE_ERROR;
        }
    }

    @Override
    @ShellMethod("Delete wallet.")
    public String deleteWallet(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.delete(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                return "Wallet deleted!";
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Get amount of money in wallet")
    public String getAmount(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.getAmount(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                BigInteger amount_add = (BigInteger) res.getResult();
                BigInteger amount = OnionBuilderOperation.decryptHomoAdd(amount_add);
                return "Your wallet has the amount of:  " + amount;
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }

    }

    @Override
    @ShellMethod("Get Wallet's information")
    public String getInfoWallet(
            @ShellOption({"-id"}) long id) throws URISyntaxException {

        ResponseEntity<SignedResults> signedResults = service.getInfo(id);

        SignedResults s = signedResults.getBody();
        Result res = s.getResult();

        if (VerifySignatures.verify(s.getSignatureReceive(), res))
            return WRONG_SIGNATURE;

        switch (res.getError()) {
            case "OK":
                ObjectMapper mapper = new ObjectMapper();
                Wallet result = mapper.convertValue(res.getResult(), Wallet.class);

                BigInteger amount_add = new BigInteger(result.getAmount_add());
                BigInteger amount = OnionBuilderOperation.decryptHomoAdd(amount_add);
                result.setAmount_add(amount.toString());

                LocalRepo l = LocalRepo.getInstance();
                String name_det_rnd = result.getName();
                String RNDKey = l.getKey(RND);
                byte[] RNDKeySecretBytes = Base64.getDecoder().decode(RNDKey);
                SecretKey RNDKeySecret = new SecretKeySpec(RNDKeySecretBytes, 0, RNDKeySecretBytes.length, "AES");
                String name_det = HomoRand.decrypt(RNDKeySecret, Base64.getDecoder().decode(l.getKey(IV)), name_det_rnd);

                String DETKey = l.getKey(DET);
                byte[] DETKeySecretBytes = Base64.getDecoder().decode(DETKey);
                SecretKey DETKeySecret = new SecretKeySpec(DETKeySecretBytes, 0, DETKeySecretBytes.length, "AES");
                String name = HomoDet.decrypt(DETKeySecret, name_det);

                result.setName(name);
                return "Your wallet's information:" + result.getInfo();
            case "BAD_REQUEST":
                return MESSAGE_400;
            case "NOT_FOUND":
                return MESSAGE_404;
            case "TIME_OUT":
                return MESSAGE_TIMEOUT;
            case "UNAUTHORIZED":
                return UNAUTHORIZED;
            default:
                return MESSAGE_ERROR;
        }

    }

}
