package csd.wallet.Models;

import java.io.Serializable;
import java.util.List;

public class ListWrapper implements Serializable {
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
