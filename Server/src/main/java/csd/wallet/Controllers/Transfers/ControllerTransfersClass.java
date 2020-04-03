package csd.wallet.Controllers.Transfers;

import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Utils.RequestType;
import csd.wallet.Utils.ResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import bftsmart.tom.ServiceProxy;

import java.io.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
public class ControllerTransfersClass implements ControllerTransfersInterface,Serializable {

	@Autowired
	ServiceTransfersClass transfers;
	@Autowired
	ServiceProxy serviceProxy;


	@Override
	public ResponseEntity<Void> addMoney(AddRemoveForm idAmount) {
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

			objOut.writeObject(RequestType.TRANSFERS_ADD);
			objOut.writeObject(idAmount);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
			if (reply.length == 0)
				return null;
			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return response((ResponseType) objIn.readObject());
			}
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<Void> removeMoney(AddRemoveForm idAmount) {
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

			objOut.writeObject(RequestType.TRANSFERS_REMOVE);
			objOut.writeObject(idAmount);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
			if (reply.length == 0)
				return null;
			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return response((ResponseType) objIn.readObject());
			}
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<Void> transfer(Transfer transfer) {
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

			objOut.writeObject(RequestType.TRANSFERS_TRANSFER);
			objOut.writeObject(transfer);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeOrdered(byteOut.toByteArray());
			if (reply.length == 0)
				return null;
			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return response((ResponseType) objIn.readObject());
			}
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}
	}

	@Override
	public ResponseEntity<ListWrapper> ledgerOfGlobalTransfers() {
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

			objOut.writeObject(RequestType.TRANSFERS_GLOBALTRANSFERS);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());
			if (reply.length == 0)
				return null;
			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return ResponseEntity.ok((ListWrapper) objIn.readObject());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}

	}

	@Override
	public ResponseEntity<ListWrapper> ledgerOfWalletTransfers(long id) {
		try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {

			objOut.writeObject(RequestType.TRANSFERS_WALLETTRANSFERS);
			objOut.writeObject(id);

			objOut.flush();
			byteOut.flush();

			byte[] reply = serviceProxy.invokeUnordered(byteOut.toByteArray());
			if (reply.length == 0)
				return null;
			try (ByteArrayInputStream byteIn = new ByteArrayInputStream(reply);
				 ObjectInput objIn = new ObjectInputStream(byteIn)) {
				return ResponseEntity.ok((ListWrapper) objIn.readObject());
			}
		} catch (Exception e) {
			return ResponseEntity.status(INTERNAL_SERVER_ERROR).build();
		}

	}

	private ResponseEntity<Void> response(ResponseType responseType) {
		switch (responseType){
			case OK:
				return ResponseEntity.ok().build();
			case NOT_FOUND:
				return ResponseEntity.notFound().build();
			case BAD_REQUEST:
				return ResponseEntity.badRequest().build();
			default: return null; //?
		}
	}

}
