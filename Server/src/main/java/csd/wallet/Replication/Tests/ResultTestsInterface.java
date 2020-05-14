package csd.wallet.Replication.Tests;

import csd.wallet.Replication.Result;
import csd.wallet.Replication.ServiceProxy.SignedResult;
import org.springframework.stereotype.Service;

@Service
public interface ResultTestsInterface {

    Result test1();

    Result test2();

    Result test3();

    Result test4();
}
