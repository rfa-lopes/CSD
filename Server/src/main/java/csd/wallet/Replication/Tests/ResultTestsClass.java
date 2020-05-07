package csd.wallet.Replication.Tests;

import csd.wallet.Replication.Result;
import csd.wallet.Services.Tests.ServiceTestsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ok;

@Service
public class ResultTestsClass implements ResultTestsInterface{

    @Autowired
    ServiceTestsClass tests;

    @Override
    public Result<String> test1() {
        return ok(tests.test1());
    }

    @Override
    public Result<String> test2() {
        return ok(tests.test2());
    }

    @Override
    public Result<String> test3() {
        return ok(tests.test3());
    }

    @Override
    public Result<Void> test4() {
        tests.test4();
        return ok();
    }
}
