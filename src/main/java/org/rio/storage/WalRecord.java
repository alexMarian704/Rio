package org.rio.storage;

import java.util.List;

public record WalRecord(String key, List<String> values) {
}
