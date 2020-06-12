package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;

import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.Base64;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface {


    static {
        //if (System.getSecurityManager() == null) {


/*          Policy.setPolicy(new SmartContractsPolicy());
            System.setSecurityManager(new SecurityManager());*/
        //}
    }

    @Override
    public void executeSmartContract(SmartContract smartContract) throws Exception {

        /*Policy.setPolicy(new SandboxSecurityPolicy());
        System.setSecurityManager(new SecurityManager());
        System.out.println("POLICIES INIT DONE");*/

        byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
        String sourceCode = new String(sourceCodeByte);
        String tmpProperty = System.getProperty("java.io.tmpdir");
        Path sourcePath = Paths.get(tmpProperty, "SmartContractClient.java");
        Files.write(sourcePath, sourceCode.getBytes("UTF-8"));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, null, sourcePath.toFile().getAbsolutePath());

        Path compiled = sourcePath.getParent().resolve("SmartContractClient.class");

        URL classUrl = compiled.getParent().toFile().toURI().toURL();
        //URLClassLoader classLoader = new PluginClassLoader(classUrl);
        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classUrl });
        Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);

        clazz.newInstance();
    }
}
