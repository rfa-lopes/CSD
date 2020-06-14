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

        File f = new File("/tmp/SmartContractClient.java");
        FileWriter fileWriter = new FileWriter(f);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        printWriter.print(sourceCode);
        printWriter.close();

        //String tmpProperty = System.getProperty("java.io.tmpdir");
        //Path sourcePath = Paths.get(tmpProperty, "SmartContractClient.java");
        //Files.write(sourcePath, sourceCode.getBytes("UTF-8"));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, f.getAbsolutePath());
        String compiledPath = f.getParent();

        Policy.setPolicy(new PluginSecurityPolicy());
        //System.setSecurityManager(new SecurityManager());

        PluginLoader loader = new PluginLoader();
        loader.setPluginDirectory(compiledPath);
        String[] pluginNames = loader.listPlugins();

        Class<?> pluginClass = loader.loadClass(pluginNames[0]);

        if (pluginClass != null)
            pluginClass.newInstance();

        //URL classUrl = compiled.getParent().toFile().toURI().toURL();
        //URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
        //Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);

        //clazz.newInstance();
    }
}
