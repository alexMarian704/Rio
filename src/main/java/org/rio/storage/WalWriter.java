package org.rio.storage;

import org.rio.storage.exceptions.WalRecoveryException;
import org.rio.storage.exceptions.WriteToDiskException;

import java.util.List;

public class WalWriter implements WriteAheadLog {

    private final DurabilityMode durabilityMode;

    public WalWriter(DurabilityMode durabilityMode) {

        this.durabilityMode = durabilityMode;
    }

    @Override
    public void append(List<String> data) throws WriteToDiskException {

    }

    @Override
    public void sync() throws WriteToDiskException {

    }

    @Override
    public List<WalRecord> replay() throws WalRecoveryException {
        return List.of();
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }
}
