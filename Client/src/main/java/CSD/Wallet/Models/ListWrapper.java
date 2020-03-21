package CSD.Wallet.Models;

import java.util.List;

public class ListWrapper {
    private List<TransferModel1> list;

    public ListWrapper(List<TransferModel1> list){
        this.list = list;
    }

    public List<TransferModel1> getList(){
        return list;
    }
}
