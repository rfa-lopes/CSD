package CSD.Wallet.Services.SmartContracts;

import CSD.Wallet.Models.SmartContract;
import CSD.Wallet.SmartContracts.SmartContractClient;
import org.mdkt.compiler.InMemoryJavaCompiler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
@PropertySource("classpath:application.properties")
public class SmartContractServiceClass implements SmartContractServiceInter{

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
    public ResponseEntity<Void> execute(long owner, String pathToSmartContractJavaFile) throws Exception {
        String url = createURL(EXEC);

        StringBuilder contentBuilder = new StringBuilder();
        Stream<String> stream = Files.lines( Paths.get(pathToSmartContractJavaFile), StandardCharsets.UTF_8);
        stream.forEach(s -> contentBuilder.append(s).append("\n"));
        String sourceCode = contentBuilder.toString();

        SmartContract smc = new SmartContract(owner, sourceCode);

        //Class<?> smartcontractclass = InMemoryJavaCompiler.newInstance().compile(SmartContractClient.class.getName(), sourceCode);
        //SmartContractClient a = (SmartContractClient)smartcontractclass.newInstance();
        //a.execute();

        ResponseEntity<Void> response = restTemplate.postForEntity(url, smc, Void.class);

        return response;
    }

    private <T> String createURL(String method) {
        return String.format(SERVER_URL + BASE + method);
    }

}
