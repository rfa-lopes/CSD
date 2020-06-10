package csd.wallet.Services.SmartContracts;

import csd.wallet.Models.SmartContract;

import csd.wallet.Utils.VerifySmartContractSignature;
import org.springframework.stereotype.Service;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.security.Permissions;

import java.security.SignedObject;

import java.util.Base64;
import java.util.PropertyPermission;

@Service
public class ServiceSmartContractsClass implements ServiceSmartContractsInterface {

	@Override
	public void executeSmartContract(SmartContract smartContract) throws Exception {
		//if (VerifySmartContractSignature.verifies(smartContract)) {
			//SmartContract sc = (SmartContract) smartContract.getObject();
			byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
			String sourceCode = new String(sourceCodeByte);
			String tmpProperty = System.getProperty("java.io.tmpdir");
			Path sourcePath = Paths.get(tmpProperty, "SmartContractClient.java");
			Files.write(sourcePath, sourceCode.getBytes("UTF-8"));

<<<<<<< HEAD
		/*Permissions perms = new Permissions();
		perms.add(new PropertyPermission("*", "read"));
		perms.add(new RuntimePermission("getenv.TIKA_CONFIG"));*/

		byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
		String sourceCode = new String(sourceCodeByte);

		String tmpProperty = System.getProperty("java.io.tmpdir");
		Path sourcePath = Paths.get(tmpProperty, "SmartContractClient.java");
		Files.write(sourcePath, sourceCode.getBytes("UTF-8"));
=======
			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
			compiler.run(null, null, null, sourcePath.toFile().getAbsolutePath());
>>>>>>> d2507565e9b9b997e28b2679b661ed11bb0d2a77

			Path compiled = sourcePath.getParent().resolve("SmartContractClient.class");

			URL classUrl = compiled.getParent().toFile().toURI().toURL();
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{classUrl});
			Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);

<<<<<<< HEAD
		URL classUrl = compiled.getParent().toFile().toURI().toURL();
		URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classUrl });

		Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);
=======
			clazz.newInstance();
>>>>>>> d2507565e9b9b997e28b2679b661ed11bb0d2a77

		}
	}
//}