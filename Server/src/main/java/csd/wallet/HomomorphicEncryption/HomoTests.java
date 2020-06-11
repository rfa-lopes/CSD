package csd.wallet.HomomorphicEncryption;

import csd.wallet.Exceptions.WalletExceptions.WalletNotExistsException;
import csd.wallet.Models.AddRemoveForm;
import csd.wallet.Models.Wallet;
import csd.wallet.Repository.TransferRepository;
import csd.wallet.Repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public class HomoTests {

    @Autowired
    private static TransferRepository transfers;

    @Autowired
    private static WalletRepository wallets;

    public static void main(String[] args) {

        long id = 0;

        try {
            //CLIENT
            PaillierKey pk = HomoAdd.generateKey(); //No create gera a key e envia o nsquare para o servidor

            BigInteger amount = new BigInteger("200");
            BigInteger amountCifrado = HomoAdd.encrypt(amount, pk);

            AddRemoveForm addMoney = new AddRemoveForm();
            addMoney.setId(0);
            addMoney.setAmount(amountCifrado.longValue());


            //SERVIDOR

            //Verificar se [ 0 < amount < 999999999] COMO???

            //Verificar se o ID existe e ir buscar a wallet
            Wallet w = wallets.findById(id).orElseThrow(() -> new WalletNotExistsException(id));

            //Adicionar o amount à wallet
            BigInteger newAmount = HomoAdd.sum(BigInteger.valueOf(w.getAmount()), amountCifrado, pk.getNsquare());
            w.setAmount(newAmount.longValue());

            //Guardar wallet na bd
            wallets.save(w);


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
