package csd.wallet.Replication.Tests;

import csd.wallet.Replication.Result;
import org.springframework.stereotype.Service;

@Service
public interface ResultTestsInterface {

    Result<String> test1();

    Result<String> test2();

    Result<String> test3();

    Result<Void> test4();
}
