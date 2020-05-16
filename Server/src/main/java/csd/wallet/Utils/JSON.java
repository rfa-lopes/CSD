package csd.wallet.Utils;

import com.google.gson.Gson;

public class JSON {

    private static final Gson gson = new Gson();

    public static String toJson(Object obj){
        return gson.toJson(obj);
    }

    public static Object toObj(String obj, Class cl){
        return gson.fromJson(obj, cl);
    }

}
