package CSD.Wallet.Models;

import java.util.List;

public class ListWrapper {
    List<Transfer> list;

    public ListWrapper(){}

    public ListWrapper(List<Transfer> list){
        this.list = list;
    }

    public void setList(List<Transfer> list) {this.list = list; }

    public List<Transfer> getList(){
        return list;
    }
}
