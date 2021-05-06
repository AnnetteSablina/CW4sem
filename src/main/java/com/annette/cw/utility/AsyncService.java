package com.annette.cw.utility;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncService {
    private ExecutorService service = Executors.newCachedThreadPool();

    public void execute(Runnable runnable) {
        service.execute(runnable);
    }
}
