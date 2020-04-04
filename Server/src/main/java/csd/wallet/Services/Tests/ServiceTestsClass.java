package csd.wallet.Services.Tests;

import org.springframework.stereotype.Service;

@Service
public class ServiceTestsClass implements ServiceTestsInterface {

    @Override
    public String test1() {
        return "Hello World! (Get)";
    }

    @Override
    public String test2() {
        return "Hello World! (Post)";
    }

}
