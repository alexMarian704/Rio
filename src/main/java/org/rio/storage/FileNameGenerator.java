package org.rio.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileNameGenerator {

    private final static int ID_LENGTH = 12;

    private final static Logger LOGGER = LoggerFactory.getLogger(FileNameGenerator.class);
    private final Path directory;
    private final String extension;
    private long nextId;

    public FileNameGenerator(String directoryPath, String extension) throws IOException {
        if (extension == null || extension.isBlank()) {
            throw new IllegalArgumentException("Invalid extension");
        }
        if (!extension.startsWith(".")) {
            throw new IllegalArgumentException("Extension must start with .");
        }
        if (directoryPath == null || directoryPath.isBlank()) {
            throw new IllegalArgumentException("Invalid directory path");
        }

        this.directory = Paths.get(directoryPath);
        this.extension = extension;
        init();
    }

    private void init() throws IOException {
        Files.createDirectories(directory);

        try (Stream<Path> stream = Files.list(directory)) {
            stream.filter(Files::isRegularFile)
                    .map((file) -> file.getFileName().toString())
                    .filter((name) -> name.endsWith(extension))
                    .forEachOrdered((fileName) -> {
                        try {
                            String idName = fileName.substring(0, fileName.length() - extension.length());
                            long id = Long.parseLong(idName);
                            nextId = Math.max(nextId, id + 1);
                        } catch (NumberFormatException e) {
                            LOGGER.warn("Invalid file name: {}", fileName);
                        }
                    });
        }
    }

    public synchronized Path getNextFilePath() {
        String stringId = String.valueOf(nextId++);
        int restLength = ID_LENGTH - stringId.length();
        if (restLength < 0) {
            throw new IllegalStateException("Id too large: " + stringId);
        }

        String front = "0".repeat(restLength);

        return directory.resolve(front + stringId + extension);
    }
}
