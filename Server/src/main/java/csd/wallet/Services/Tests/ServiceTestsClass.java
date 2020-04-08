package csd.wallet.Services.Tests;

import org.springframework.stereotype.Service;

@Service
public class ServiceTestsClass implements ServiceTestsInterface {

    @Override
    public String test1() {
        return "Hello World! (1)";
    }

    @Override
    public String test2() {
        return "Hello World! (2)";
    }

}
