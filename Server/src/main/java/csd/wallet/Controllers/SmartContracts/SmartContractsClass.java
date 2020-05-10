package csd.wallet.Controllers.SmartContracts;

import csd.wallet.Controllers.RestResource;

import csd.wallet.Models.SmartContract;
import csd.wallet.Replication.BFTClient;

import csd.wallet.Utils.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import static csd.wallet.Replication.Result.ok;

@RestController
public class SmartContractsClass extends RestResource implements SmartContractsInterface {

	@Autowired
	BFTClient bftClient;

	@SuppressWarnings("all")
	@Override
	public ResponseEntity<Void> executeSmartContract(SmartContract smartContract) {
		Logger.info("Request: SMART CONTRACT EXECUTE");

		byte[] sourceCodeByte = Base64.getDecoder().decode(smartContract.getCode());
		String sourceCode = new String(sourceCodeByte);

		try {
			String tmpProperty = System.getProperty("java.io.tmpdir");
			Path sourcePath = Paths.get(tmpProperty, "SmartContractClient.java");
			Files.write(sourcePath, sourceCode.getBytes("UTF-8"));

			JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

			compiler.run(null, null, null, sourcePath.toFile().getAbsolutePath());

			Path compiled = sourcePath.getParent().resolve("SmartContractClient.class");

			URL classUrl = compiled.getParent().toFile().toURI().toURL();
			URLClassLoader classLoader = URLClassLoader.newInstance(new URL[] { classUrl });
			Class<?> clazz = Class.forName("SmartContractClient", true, classLoader);

			clazz.newInstance();

		} catch (Exception e) {

		}
		return super.getResponse(ok());

	}
}
