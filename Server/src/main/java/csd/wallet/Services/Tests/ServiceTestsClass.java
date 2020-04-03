package csd.wallet.Services.Tests;

import csd.wallet.Utils.Logger;
import org.springframework.stereotype.Service;

@Service
public class ServiceTestsClass implements ServiceTestsInterface {

    @Override
    public String test1() {
        Logger.info("Replicate is working!");
        return "Hello World! (Get)";
    }

    @Override
    public String test2() {
        Logger.info("Replicate is working!");
        return "Hello World! (Post)";
    }

}
