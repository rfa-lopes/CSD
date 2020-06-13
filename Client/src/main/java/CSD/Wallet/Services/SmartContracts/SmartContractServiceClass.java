package CSD.Wallet.Services.SmartContracts;

import CSD.Wallet.Models.SignedResults;
import CSD.Wallet.Models.SmartContract;

import CSD.Wallet.Services.LocalRepo.LocalRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.stream.Stream;

@Service
@PropertySource("classpath:application.properties")
public class SmartContractServiceClass implements SmartContractServiceInter {

	private static final String BACKSLASH = "/";

	private RestTemplate restTemplate;

	@Value("${client.server.url}")
	private String SERVER_URL;

	private static String BASE = "/smartcontract";

	/* Methods */
	private static String EXEC = "/execute";

	public SmartContractServiceClass() {
		restTemplate = new RestTemplate();
	}

	@Override
	public ResponseEntity<SignedResults>execute(long owner, String pathToSmartContractJavaFile) throws Exception {
		String url = createURL(EXEC);

		StringBuilder contentBuilder = new StringBuilder();
		Stream<String> stream = Files.lines(Paths.get(pathToSmartContractJavaFile), StandardCharsets.UTF_8);
		stream.forEach(s -> contentBuilder.append(s).append("\n"));
		String sourceCode = contentBuilder.toString();

		SmartContract smc = new SmartContract(owner, Base64.getEncoder().encodeToString(sourceCode.getBytes()));

		HttpEntity<SmartContract> entity = new HttpEntity<>(smc, createHeaders());
		ResponseEntity<SignedResults> signedResults = restTemplate.exchange(url, HttpMethod.POST, entity,
				SignedResults.class);
		return signedResults;
	}

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
