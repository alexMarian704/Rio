package org.rio.storage;

import org.rio.storage.exceptions.WalRecoveryException;
import org.rio.storage.exceptions.WriteToDiskException;

import java.util.List;

public interface WriteAheadLog {

    void append(List<String> data) throws WriteToDiskException;

    void sync() throws WriteToDiskException;

    List<WalRecord> replay() throws WalRecoveryException;

    void start();

    void stop();
}
