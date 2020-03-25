package csd.wallet.Models;

import java.util.List;

public class ListWrapper {
    private List<Transfer> list;

    public ListWrapper(List<Transfer> list){
        this.list = list;
    }

    public List<Transfer> getList(){
        return list;
    }
}
