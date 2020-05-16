package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.AddRemoveForm;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Utils.*;
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
import java.util.Map;

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
		Transfer body = new Transfer(fromId, toId, amount);
		ResponseEntity<SignedResults> signedResults = restTemplate.postForEntity(url, body, SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> addAmount(long id, long amount) {
		String url = createURL(ADD);
		AddRemoveForm f = new AddRemoveForm();
		f.setId(id);
		f.setAmount(amount);
		ResponseEntity<SignedResults> signedResults = restTemplate.postForEntity(url, f, SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> removeAmount(long id, long amount) {

		String url = createURL(REMOVE);
		AddRemoveForm f = new AddRemoveForm();

		f.setId(id);
		f.setAmount(amount);
		ResponseEntity<SignedResults> response = restTemplate.postForEntity(url, f, SignedResults.class);
		return response;

	}

	@Override
	public ResponseEntity<SignedResults> listGlobalTransfers() throws URISyntaxException {
		String url = createURL(GLOBAL);
		ResponseEntity<SignedResults> signedResults = restTemplate.getForEntity(new URI(url), SignedResults.class);;
        return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> listWalletTransfers(long id) throws URISyntaxException {
		String url = createURL(WALLET);
		String idToGet = url + BACKSLASH + id;
		ResponseEntity<SignedResults> response = restTemplate.getForEntity(new URI(idToGet), SignedResults.class);
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
