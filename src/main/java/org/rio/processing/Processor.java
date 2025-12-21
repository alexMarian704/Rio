package org.rio.processing;

import java.util.List;
import java.util.concurrent.Future;

public interface Processor {

    Future<String> process(List<String> data);

    void start();

    void stop();
}
