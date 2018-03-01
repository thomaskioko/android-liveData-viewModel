package com.thomaskioko.livedatademo.util;


import com.thomaskioko.livedatademo.repository.util.AppExecutors;

import java.util.concurrent.Executor;

public class InstantAppExecutors extends AppExecutors {
    private static Executor instant = command -> command.run();

    public InstantAppExecutors() {
        super(instant, instant, instant);
    }
}
