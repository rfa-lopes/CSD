package CSD.Wallet.Services.LocalRepo;

import CSD.Wallet.Crypto.Deterministic.HomoDet;
import CSD.Wallet.Crypto.Order.HomoOpeInt;
import CSD.Wallet.Crypto.Random.HomoRand;
import CSD.Wallet.Crypto.Searchable.HomoSearch;
import CSD.Wallet.Crypto.Sum.HomoAdd;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static CSD.Wallet.Services.LocalRepo.KeyType.*;



public class LocalRepo {

    private static LocalRepo instance = null;

    private static String JWT;

    private static String RND_KEY = "rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJMAAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAADQUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABAp1f7PQGVQWORqO2g4Dg74";
    private static String DET_KEY = "rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJMAAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAADQUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABBJa3wh2XYYTNKUKTresnmz";
    private static String OPE_KEY = "1007773682087351521";
    private static String RND_IV = "Z+7gfGlBVBK+YmiyrV6Wow==";
    private static String PL_KEY = "rO0ABXNyABhobGliLmhqLm1saWIuUGFpbGxpZXJLZXkAAAAAAAAAAQIAB0wAAWd0ABZMamF2YS9tYXRoL0JpZ0ludGVnZXI7TAAGbGFtYmRhcQB+AAFMAAJtdXEAfgABTAABbnEAfgABTAAHbnNxdWFyZXEAfgABTAABcHEAfgABTAABcXEAfgABeHBzcgAUamF2YS5tYXRoLkJpZ0ludGVnZXKM/J8fqTv7HQMABkkACGJpdENvdW50SQAJYml0TGVuZ3RoSQATZmlyc3ROb256ZXJvQnl0ZU51bUkADGxvd2VzdFNldEJpdEkABnNpZ251bVsACW1hZ25pdHVkZXQAAltCeHIAEGphdmEubGFuZy5OdW1iZXKGrJUdC5TgiwIAAHhw///////////////+/////gAAAAF1cgACW0Ks8xf4BghU4AIAAHhwAAACACEB0OLTHzbykKDQt1yNMjspP8fXDdVasiSoq1ksbQtkJExcGvm8xJ+nKgFz9UqqnQ/tF+tQf6e0bVM17H6bJ+5vs7aaS0MiUCyEANnhQGYNllV3A51cmJbhMIQypQK0yyTHv3GjU7Pl9BV3twzzcF7Q1dzt2byA3posXEGoVTcMaE6YSpCTURh6AQpPNNuC1xbunQ3dCygnklH+kKBGb7fko2dn1A/cS1wRVDVtzxPHgebt9Pbb7WEsQvA1O9WBRkUi4eXnboGi2zncVZ+4wtlyg/2aY8B0xKpACgE67Zm7vKBaoXPzLTpfqSs+BpmNiArKrJ3b992dtMcNE/NUCVx+/4XN1k7HR/FoZYOy4pOKs9QACQTn3wbXskb2z+tJukXEH/WjQyCuwIwBugUeXTOVt89y/WJBvwP3+F7NOHhOzcYkYCEYzsGd9I0qOODdCyF1VNXVy4OSDeucSf5IIVVY7WAFythJJn6clijVEbXBigKddxH3xUwrICxMB959DSv5aBPNagcU/nwXsz6FW71HXARfKuopoHrKAJai7HS5D2NRzY1ubOLb+xnQJd0/TZOqKjkNZUdafLkJSB1r//LQR7TvIPLQY3fsX+cSwwhZ7XUdapk8dfCS4FSF2W/HA8Fk2BLR8/N8WSA+OuFtd/2Y66HuJIXzuIZvMtrMnxzteHNxAH4AA////////////////v////4AAAABdXEAfgAHAAABAEt1mxh5xz+nec++k0tvZnrs120o7hUwn8UPnuMAyg8x92LoMp57037NYIUHrLQqw0hlHdr8zhxMUQDAl9KC3opdNqtTsip9kmr+v1rVFTKAAySkvR5zB2u9uDgkO0SK91XjnzV1mVp9eZAIL3N9kQS3RCNvC0EfbfBhUYE1aq3qcdCq9NM8icxzvWLKjCZ50mvc69tYh25AbeJxIxYMAlewbJvQLY5LetQXuMsoYy7zc+ydIoCYScqF93iYNcjT//oZpaxBpmUSyEm6ZuYQep1auQNrKfhc4JWSyaoFhyCj82niQmKBoEIpxvBIF6DmUyEoDzu75uTOddhHEFfOnyh4c3EAfgAD///////////////+/////gAAAAF1cQB+AAcAAAEAKVrYKLeXog0N4TPUgalLF+M7ZQZWOzU/0uWNQp4Sc2kx3MK6yJwo9uOrFqutCFNQ0n4odvEw054yj8I4oP0r6JifFmyPb3jE/S+F0EPF6k6HkQD8srG7Rv3G5QCXEpUUj6VJnr99127jVVeL92yQCxMuoSRpBJ4bvNSXDY/qVVDSkzNSjfqt6auVpfhuNROwNQKxNNvd7rwOWah7OlRSUgs5YOKCJ8bSHrEU0tkcAstfZqzs4enpNWPmmMOzHpDRtKCgvw4Wq+/FNBmAloDC081dd9kQx7ZbVyV7NiGWDURVeC0g9lcgEjoFgWWFarOHgYcJpnjEg1+3si4wZK2I0XhzcQB+AAP///////////////7////+AAAAAXVxAH4ABwAAAQCW6zYw845/TvOffSaW3sz12a7aUdwqYT+KHz3GAZQeY+7F0GU896b9msEKD1loVYaQyju1+Zw4mKIBgS+lBb0Uum1Wp2RU+yTV/X61qiplAAZJSXo85g7Xe3BwSHaJFe6rxz5q6zK0+vMgEF7m+yIJbohG3haCPtvgwqMCatVb1nOAjD26ob/h7PVSeYMSNsXvuu0nN7RINxwFWmN1XC00sudP9qt0CGAzUX0mixyicJd5kTXcIY1suJsDvGHyXarjDr2YJ21y194gqymYL8APlUqyTzr7XdOmhXpIkekOiBqu5m6BEn2YCnr/iQsbHkT/zXl2Qz4QjGPUcsb8RDaTeHNxAH4AA////////////////v////4AAAABdXEAfgAHAAACAFj4e53mwtTqm08O3P+nxfKs6jNTNqGimzs/kSNah+jfYwpYQBlsMONaj2WLECP9rSALBnBOSE/xjvdEuYtwiyJbSQF+VCw+YC8CMZAE5mJhEpgu6A8TSkciXsm02trxo34CqG+FJGHXN70wo2mRZdYciy/6ir+t10D8X/yV0oRjCzO4VWAUUG793xwmCNJNMrL2q/6t5WNN76i9GZRyhwiSH7ulvsJTv9yT13dQC/89aJJ6wHzApZ2ObC6tAGod5gafp3q+m8XLq+2dCnASk2mIMvMZA7JPBouPFCZ5Z6tgrKvYE9QiOUkJ323MF7z5sO7i86bq61Fr5o0rZTiA60prCcIl37x7vUkN6d/AvvB+Vseaqmn/xq1st5D3XzqZe3RK2KFuYSQW4nC8/k4Yen55YKWUYtaB30aQl8wWLFWt6t+MN63ewozaPa4MfccOYaSkpf99cTpPmz9193c82ieMhnLeWDoOnIoekFEtguDlpxQzLMWLnz0Ft7QZPtRH+IRbGcZgxLEcjs6TUSzJ63AVyM2z+ucGubenQ8kjSYxkNT0ZEe38PzXlg1UbaqouCV4B+4/yknj4Fel45SMN1GXEWx+yIubUgubmgjM4CDvzABwC3UqlLhwUKSObFMhVXUwPxio2asCac4CVqMD2rmzx+ZVgBsZ3UAgttQJxulhpeHNxAH4AA////////////////v////4AAAABdXEAfgAHAAAAgOyH3c6e8CvCvuWDSmxkYcSYZ+jwZnFy1tVbKuoksBEv+19v0CTE4ZlcuHywXmE6j6KRJMcMQBvH6UwhhCjWI7VVy9w9e40Z0kc8Xo8rPpz7nMl9MSpJYpScr8y1+5IznIRqEregJtJgzrknxLBBzkKi2QdC6EXk7CGNm/S1WcBJeHNxAH4AA////////////////v////4AAAABdXEAfgAHAAAAgKNXWIV1OICGRpUJmf5g4Vx/mSyAIDP432rlTTMklBdVVq6ohiuSj9EuaY7f2/UJ+g0PMinOsN4Pw1/xB82KkfWZD5YCKJOO4AZQ18yg0C3ZQw8uR7zBQX3esBo+i0iZo69xDzIb6Gqy6DP3NCuXg1wapFO74ypiA1aWSLGXTTf7eA==";
    private static String SR_KEY = "rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJMAAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAADQUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABAP8n/2CJV8J27cOZd6W+5p";

    private static Map<String,String> onionKeys;


    private LocalRepo(){
        onionKeys = new HashMap<String,String>();
        onionKeys.put(RND.name(),RND_KEY);
        onionKeys.put(DET.name(),DET_KEY);
        onionKeys.put(IV.name(),RND_IV);
        onionKeys.put(OPE.name(),OPE_KEY);
        onionKeys.put(PL.name(),PL_KEY);
        onionKeys.put(SR.name(),SR_KEY);
    }

    public static LocalRepo getInstance(){
        if(instance == null)
            instance = new LocalRepo();
        return instance;
    }

    public void setJWT(String JWT) {
        LocalRepo.JWT = JWT;
    }

    public String getJWT() {
       return JWT;
    }

    public String getKey(KeyType keytype){
        return onionKeys.get(keytype.name());
    }






}
