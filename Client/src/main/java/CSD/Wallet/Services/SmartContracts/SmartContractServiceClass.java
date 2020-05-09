package CSD.Wallet.Services.SmartContracts;

import CSD.Wallet.Models.SmartContract;
import CSD.Wallet.SmartContracts.SmartContractInterfaceClient;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

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

    @Override
    public ResponseEntity<Void> execute(long owner, String pathToSmartContractJavaFile) throws FileNotFoundException {
        String url = createURL(EXEC);

        File file = new File(pathToSmartContractJavaFile);
        //if(!file.exists())
            //throw new FileNotFoundException();

        // Compile source file.
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, pathToSmartContractJavaFile);

        String classFile = pathToSmartContractJavaFile.replaceAll(".java", ".class");

        file = new File(classFile);

        SmartContract body = new SmartContract(owner, file);

        ResponseEntity<Void> response = restTemplate.postForEntity(url, body, Void.class);
        return response;
    }

    private <T> String createURL(String method) {
        return String.format(SERVER_URL + BASE + method);
    }

}
