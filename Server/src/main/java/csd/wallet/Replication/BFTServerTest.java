package csd.wallet.Replication;

import bftsmart.tom.MessageContext;
import bftsmart.tom.ServiceReplica;
import bftsmart.tom.server.defaultservices.DefaultSingleRecoverable;
import csd.wallet.Exceptions.TransfersExceptions.InvalidAmountException;
import csd.wallet.Exceptions.TransfersExceptions.TransferToSameWalletException;
import csd.wallet.Exceptions.WalletExceptions.EmptyWalletNameException;
import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.ListWrapper;
import csd.wallet.Models.Transfer;
import csd.wallet.Models.Wallet;
import csd.wallet.Services.Tests.ServiceTestsClass;
import csd.wallet.Services.Transfers.ServiceTransfersClass;
import csd.wallet.Services.Wallets.ServiceWalletsClass;
import csd.wallet.Utils.RequestType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.*;

@Component
public class BFTServerTest extends DefaultSingleRecoverable  implements Serializable {

	@Autowired
	ServiceTransfersClass transfers;

	@Autowired
	ServiceWalletsClass wallets;

	public BFTServerTest(@Value("${server.port}") int serverport) {

		new ServiceReplica(serverport % 4, this, this);
	}

	@Override
	public void installSnapshot(byte[] bytes) {

	}

	@Override
	public byte[] getSnapshot() {
		return new byte[0];
	}

	@Override
	public byte[] appExecuteOrdered(byte[] command, MessageContext messageContext) {
		byte[] reply = null;
		boolean hasReply = false;
		long id;
		AddRemoveForm form;
		try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
			 ObjectInput objIn = new ObjectInputStream(byteIn);
			 ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
			RequestType reqType = (RequestType) objIn.readObject();
			switch (reqType) {
				case TRANSFERS_TRANSFER:
					try {
						Transfer transfer = (Transfer) objIn.readObject();
						transfers.transfer(transfer);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					} catch (InvalidAmountException | TransferToSameWalletException e) {
						objOut.writeObject(ResponseEntity.badRequest().build());
					}
					hasReply = true;
					break;

				case TRANSFERS_ADD:
					try {
						form = (AddRemoveForm) objIn.readObject();
						transfers.addMoney(form);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					} catch (InvalidAmountException e) {
						objOut.writeObject(ResponseEntity.badRequest().build());
					}
					hasReply = true;
					break;

				case TRANSFERS_REMOVE:
					try {
						form = (AddRemoveForm) objIn.readObject();
						transfers.removeMoney(form);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					} catch (InvalidAmountException e) {
						objOut.writeObject(ResponseEntity.badRequest().build());
					}
					break;

				case WALLET_CREATE:
					try {
						Wallet wallet = (Wallet) objIn.readObject();
						id = wallets.createWallet(wallet);
						objOut.writeObject(id);
					} catch (EmptyWalletNameException e) {
						objOut.writeObject(ResponseEntity.badRequest().build());
					}
					hasReply = true;
					break;

				case WALLET_DELETE:
					try {
						id = (long) objIn.readObject();
						wallets.deleteWallet(id);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					}
					hasReply = true;
					break;
			}
			if (hasReply) {
				objOut.flush();
				byteOut.flush();
				reply = byteOut.toByteArray();
			} else {
				reply = new byte[0];
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			// logger.log(Level.SEVERE, "Ocurred during map operation execution", e);
			return reply;
		}
		return null;
	}

	@Override
	public byte[] appExecuteUnordered(byte[] command, MessageContext messageContext) {
		byte[] reply = null;
		boolean hasReply = false;
		ListWrapper list = null;
		long id;
		try (ByteArrayInputStream byteIn = new ByteArrayInputStream(command);
			 ObjectInput objIn = new ObjectInputStream(byteIn);
			 ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			 ObjectOutput objOut = new ObjectOutputStream(byteOut);) {
			RequestType reqType = (RequestType) objIn.readObject();
			switch (reqType) {
				case TRANSFERS_GLOBALTRANSFERS:
					list = transfers.ledgerOfGlobalTransfers();
					objOut.writeObject(list);
					hasReply = true;
					break;

				case TRANSFERS_WALLETTRANSFERS:
					id = (long) objIn.readObject();
					list = transfers.ledgerOfWalletTransfers(id);
					objOut.writeObject(list);
					hasReply = true;
					break;

				case WALLET_INFO:
					try {
						id = (long) objIn.readObject();
						Wallet wallet = wallets.getWalletInfo(id);
						objOut.writeObject(wallet);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					}
					hasReply = true;
					break;
				case WALLET_AMOUNT:
					try {
						id = (long) objIn.readObject();
						long amount = wallets.getCurrentAmount(id);
						objOut.writeObject(amount);
					} catch (WalletNotExistsException e) {
						objOut.writeObject(ResponseEntity.notFound().build());
					}
					hasReply = true;
					break;
			}
			if (hasReply) {
				objOut.flush();
				byteOut.flush();
				reply = byteOut.toByteArray();
			} else {
				reply = new byte[0];
			}

		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			// logger.log(Level.SEVERE, "Ocurred during map operation execution", e);
			return reply;
		}
		return null;
	}

}
