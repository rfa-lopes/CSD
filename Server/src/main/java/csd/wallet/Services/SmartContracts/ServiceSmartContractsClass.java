package csd.wallet.Services.SmartContracts;

import csd.wallet.Exceptions.AccountsExceptions.AuthenticationErrorException;
import csd.wallet.Models.SmartContract;

import csd.wallet.WebFilters.AuthenticatorFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Policy;
import java.util.Base64;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface {

    @Value("${bftsmart.id}")
    int this_id;

    @Override
    public void executeSmartContract(long accId, SmartContract smartContract) throws AuthenticationErrorException, IOException, IllegalAccessException, InstantiationException {

        if (accId == AuthenticatorFilter.FAIL_AUTH)
            throw new AuthenticationErrorException();

        byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
        String sourceCode = new String(sourceCodeByte);

        try {
            Path sourcePath = Paths.get("SmartContract/", "SmartContractClient.java");
            System.out.println("OLA3");
            Files.write(sourcePath, sourceCode.getBytes("UTF-8"));

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            compiler.run(null, null, null, sourcePath.toFile().getAbsolutePath());
            Path compiled = sourcePath.getParent().resolve("SmartContractClient.class");
            Thread thread = new Thread() {
                public void run() {
                    try {
                        System.setProperty("java.security.policy", "SmartContract/SC.policy");
                        System.setSecurityManager(new SecurityManager());

                        URL classUrl = compiled.getParent().toFile().toURI().toURL();
                        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
                        Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);

                        clazz.newInstance();

                    } catch (Exception e) {
                        System.out.println("OLA-T");
                    }
                }
            };
            thread.start();

        } catch (Exception e) {
            System.out.println("OLA-E");
        }
    }
}
