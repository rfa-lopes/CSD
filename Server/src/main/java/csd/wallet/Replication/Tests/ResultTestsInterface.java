package csd.wallet.Replication.Tests;

import csd.wallet.Replication.Result;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import org.springframework.stereotype.Service;

@Service
public interface ResultTestsInterface {

    SignedResult test1();

    SignedResult test2();

    SignedResult test3();

    SignedResult test4();
}
