package csd.wallet.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private Logger LOG;
    private static Log instance = null;

    private Log(Class<?> clazz){
        LOG = LoggerFactory.getLogger(clazz);
    }

    public void warn(String string, Object ...f){
        String warn = String.format(string, f);
        LOG.warn(warn);
    }

    public void info(String string, Object... f){
        LOG.info(String.format(string, f));
    }

    public void error(String string, Object... f){
        LOG.error(String.format(string, f));
    }

    public static Log getInstance(Class<?> clazz)
    {
        if (instance == null)
            instance = new Log(clazz);
        return instance;
    }

}