package csd.wallet.Replication.Tests;

import csd.wallet.Replication.Result;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import csd.wallet.Services.Tests.ServiceTestsClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static csd.wallet.Replication.Result.ok;
import static csd.wallet.Replication.ServiceProxy.SignedResult.createSignedResult;

@Service
public class ResultTestsClass implements ResultTestsInterface{

    @Autowired
    ServiceTestsClass tests;

    @Override
    public SignedResult test1() {
        return createSignedResult(ok(tests.test1()));
    }

    @Override
    public SignedResult test2() {
        return createSignedResult(ok(tests.test2()));
    }

    @Override
    public SignedResult test3() {
        return createSignedResult(ok(tests.test3()));
    }

    @Override
    public SignedResult test4() {
        tests.test4();
        return createSignedResult(ok());
    }

}
