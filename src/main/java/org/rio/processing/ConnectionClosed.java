package org.rio.processing;

import java.io.IOException;

public class ConnectionClosed extends IOException {
    public ConnectionClosed(String message) {
        super(message);
    }
}
