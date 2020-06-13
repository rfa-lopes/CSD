package csd.wallet.Replication.Operations.Tests;

import csd.wallet.Replication.Operations.Result;
import csd.wallet.Services.Tests.ServiceTestsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Operations.Result.ok;

@Service
public class ResultTestsClass implements ResultTestsInterface{

    @Autowired
    ServiceTestsClass tests;

    @Override
    public Result test1() {
        return ok(tests.test1());
    }

    @Override
    public Result test2() {
        return ok(tests.test2());
    }

    @Override
    public Result test3() {
        return ok(tests.test3());
    }

    @Override
    public Result test4() {
        tests.test4();
        return ok();
    }

}
