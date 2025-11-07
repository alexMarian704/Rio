package org.rio.processing;

import java.util.concurrent.Future;

public interface Processor {

    Future<String> process(String line);

    void start();

    void stop();
}
