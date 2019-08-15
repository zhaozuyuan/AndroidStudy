package com.example.hp.kaifayishu.async;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

/**
 * @author:zuyuan
 * @date：2018/11/19
 * @note:
 */
public class TestExecutor implements Executor {
    @Override
    public void execute(@NonNull Runnable command) {
        command.run();
    }
}
