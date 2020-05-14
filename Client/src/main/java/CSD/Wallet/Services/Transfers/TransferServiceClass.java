package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.AddRemoveForm;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Utils.Convert;
import CSD.Wallet.Utils.Logger;
import CSD.Wallet.Utils.Result;
import CSD.Wallet.Utils.VerifySignatures;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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
	public ResponseEntity<Void> transfer(long fromId, long toId, long amount) {

		String url = createURL(TRANSFER);
		Transfer body = new Transfer(fromId, toId, amount);
		ResponseEntity<SignedResults> signedResults = restTemplate.postForEntity(url, body, SignedResults.class);
		SignedResults s = signedResults.getBody();
		if(VerifySignatures.verify(s.getSignatureReceive(), s.getResult()))
			return signedResults.ok().build();
		return ResponseEntity.status(422).build(); //Unprocessable Entity
	}

	@Override
	public ResponseEntity<Void> addAmount(long id, long amount) {
		String url = createURL(ADD);
		AddRemoveForm f = new AddRemoveForm();

		f.setId(id);
		f.setAmount(amount);

		ResponseEntity<SignedResults> signedResults = restTemplate.postForEntity(url, f, SignedResults.class);
		SignedResults s = signedResults.getBody();

		Logger.warn("CENAS: " + Base64.getEncoder().encodeToString(s.getResult()));

		if(!VerifySignatures.verify(s.getSignatureReceive(), s.getResult()))
			return signedResults.status(422).build(); //Unprocessable Entity
		return signedResults.ok().build();
	}

	@Override
	public ResponseEntity<Void> removeAmount(long id, long amount) {

		String url = createURL(REMOVE);
		AddRemoveForm f = new AddRemoveForm();

		f.setId(id);
		f.setAmount(amount);
		ResponseEntity<Void> response = restTemplate.postForEntity(url, f, Void.class);
		return response;

	}

	@Override
	public ResponseEntity<List<Transfer>> listGlobalTransfers() throws URISyntaxException {
		String url = createURL(GLOBAL);
		ResponseEntity<List<Transfer>> response = restTemplate.getForEntity(new URI(url), (Class<List<Transfer>>)(Object)List.class);
		return response;
	}

	@Override
	public ResponseEntity<List<Transfer>> listWalletTransfers(long id) throws URISyntaxException {
		String url = createURL(WALLET);
		String idToGet = url + BACKSLASH + id;
		ResponseEntity<List<Transfer>> response = restTemplate.getForEntity(new URI(idToGet), (Class<List<Transfer>>)(Object)List.class);
		return response;
	}

	/*
	 * Auxiliary Methods
	 * -------------------------------------------------------------------
	 */

	private <T> String createURL(String method) {
		return String.format(SERVER_URL + BASE + method);
	}

}
