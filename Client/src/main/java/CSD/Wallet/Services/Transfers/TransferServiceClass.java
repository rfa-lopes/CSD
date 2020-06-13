package CSD.Wallet.Services.Transfers;

import CSD.Wallet.Models.AddRemoveForm;
import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.Transfer;
import CSD.Wallet.Services.LocalRepo.LocalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;

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
		HttpEntity<Transfer> entity = new HttpEntity<Transfer>(body, createHeaders());
		ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
				SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> addAmount(long id, long amount) {
		String url = createURL(ADD);
		AddRemoveForm f = new AddRemoveForm();
		f.setId(id);
		f.setAmount(amount);
		HttpEntity<AddRemoveForm> entity = new HttpEntity<AddRemoveForm>(f, createHeaders());
		ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
				SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> removeAmount(long id, long amount) {
		String url = createURL(REMOVE);
		AddRemoveForm f = new AddRemoveForm();
		f.setId(id);
		f.setAmount(amount);
		HttpEntity<AddRemoveForm> entity = new HttpEntity<>(f, createHeaders());
		ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
				SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> listGlobalTransfers() throws URISyntaxException {
		String url = createURL(GLOBAL);
		HttpEntity entity = new HttpEntity(createHeaders());
		ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.GET, entity,
				SignedResults.class);
		return signedResults;
	}

	@Override
	public ResponseEntity<SignedResults> listWalletTransfers(long id) throws URISyntaxException {
		String url = createURL(WALLET);
		String idToGet = url + BACKSLASH + id;
		HttpEntity<Long> entity = new HttpEntity<Long>(id, createHeaders());
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

	private HttpHeaders createHeaders() {
		return new HttpHeaders() {
			{
				set("Authorization", LocalRepo.getJWT());
			}
		};
	}

}
