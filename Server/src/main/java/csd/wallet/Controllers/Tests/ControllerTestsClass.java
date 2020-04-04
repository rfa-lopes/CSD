package csd.wallet.Controllers.Tests;

import bftsmart.tom.ServiceProxy;
import csd.wallet.Services.Tests.ServiceTestsClass;
import csd.wallet.Utils.Logger;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import java.io.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerTestsClass implements ControllerTestsInterface {

	@Autowired
	ServiceTestsClass tests;

	@Autowired
	ServiceProxy serviceProxy;

	@Override
	public ResponseEntity<String> test1() {
		Logger.info("Request: TEST1");
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
				ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

			objOut.writeObject(RequestType.TEST_1);

			objOut.flush();
			byteOut.flush();
		
			byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());

			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
					ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return ResponseEntity.ok((String) objIn.readObject());
			}

		} catch (IOException | ClassNotFoundException e) {
			Logger.error("Controller: TEST1");
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<String> test2() {
		Logger.info("Request: TEST2");
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut)) {

			objOut.writeObject(RequestType.TEST_2);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());

			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return ResponseEntity.ok((String) objIn.readObject());
			}

		} catch (IOException | ClassNotFoundException e) {
			Logger.error("Controller: TEST2");
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}
}
