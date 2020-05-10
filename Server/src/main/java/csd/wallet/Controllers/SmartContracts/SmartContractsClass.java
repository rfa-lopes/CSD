package csd.wallet.Controllers.SmartContracts;

import bftsmart.tom.core.messages.TOMMessageType;
import csd.wallet.Controllers.RestResource;

import csd.wallet.Enums.RequestType;
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
		return super.getResponse(bftClient.getInvoke(RequestType.SMART_CONTRACT_EXECUTE, TOMMessageType.UNORDERED_REQUEST));
	}
}
