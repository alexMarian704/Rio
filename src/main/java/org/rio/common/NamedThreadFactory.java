package org.rio.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;

public class NamedThreadFactory implements ThreadFactory {

    private final String name;
    private final AtomicLong id;

    public NamedThreadFactory(String name) {

        this.name = name;
        this.id = new AtomicLong();
    }

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r, name + id.incrementAndGet());
        thread.setDaemon(false);

        return thread;
    }
}
