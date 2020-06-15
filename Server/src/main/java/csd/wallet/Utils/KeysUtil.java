package csd.wallet.Utils;

import java.util.HashMap;
import java.util.Map;

public class KeysUtil {

    public enum KeyType {
        RND, DET, OPE, HOMOADD, IV
    }

    private Map<String, String> keysMap;

    public KeysUtil(String keys) {
        keysMap = new HashMap<>();
        String[] tmp = keys.split(" ");
        String[] type_value;

        for (String key : tmp) {
            //"RND:KJAGHDKAHSGDASHKJDGHAS= DET:JHSGDSJAHGDASGJDHA=="
            type_value = key.split(":");
            String keyType = type_value[0];
            String keyValue = type_value[1];
            keysMap.put(keyType, keyValue);
        }
    }

    public String getKey(KeyType type) {
        return keysMap.get(type.name());
    }

}
