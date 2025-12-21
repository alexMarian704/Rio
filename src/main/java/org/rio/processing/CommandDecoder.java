package org.rio.processing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.rio.constants.MemoryConstants.MAX_LINE_SIZE;
import static org.rio.constants.ResponseConstants.INVALID_COMMAND;
import static org.rio.constants.ResponseConstants.NULL_VALUE;

public class CommandDecoder {

    private static final int MAX_NUMBER_OF_ARGUMENTS = Short.MAX_VALUE;
    private static final char CR = '\r';
    private static final char LF = '\n';

    private final InputStream inputStream;
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public CommandDecoder(InputStream inputStream) {

        this.inputStream = inputStream;
    }

    public List<String> decodeCommand() {

        try {
            int numberOfArguments = readInt();
            if (numberOfArguments <= 0 || numberOfArguments > MAX_NUMBER_OF_ARGUMENTS) {
                throw new IOException("Number of arguments has an invalid value");
            }

            List<String> result = new ArrayList<>(numberOfArguments);

            while (result.size() < numberOfArguments) {
                String content = readBulkString();
                result.add(content);
            }
            readCRLF();

            return result;
        } catch (ConnectionClosed e) {

            throw new RuntimeException(e.getMessage());
        } catch (Exception e) {

            logger.warn("Exception while processing command", e);
            throw new RuntimeException(INVALID_COMMAND + ", " + e.getMessage());
        }
    }

    private long readNumber() throws IOException {

        String line = readLine();
        return Long.parseLong(line);
    }

    private int readInt() throws IOException {

        String line = readLine();
        return Integer.parseInt(line);
    }

    private String readLine() throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int data;
        int bytesRead = 0;

        while ((data = inputStream.read()) != -1) {
            if (data == CR) {
                int next = inputStream.read();
                if (next == LF) {
                    break;
                }

                outputStream.write(CR);
                outputStream.write(next);
                bytesRead += 2;
            } else {
                outputStream.write(data);
                bytesRead++;
            }

            if (bytesRead > MAX_LINE_SIZE) {
                throw new IOException("Input is too large, max length: " + MAX_LINE_SIZE);
            }
        }
        if (data == -1) {
            throw new ConnectionClosed("Connection closed");
        }

        return outputStream.toString(StandardCharsets.UTF_8);
    }

    private String readBulkString() throws IOException {

        long length = readNumber();
        if (length > MAX_LINE_SIZE) {
            throw new IOException("Input is too large, max length: " + MAX_LINE_SIZE);
        }
        if (length == -1) {
            readCRLF();
            return NULL_VALUE;
        }
        if (length <= 0) {
            throw new IOException("Input cannot be less than 0");
        }
        byte[] string = new byte[(int) length];
        int byteRead = 0;

        while (byteRead < length) {
            int result = inputStream.read(string, byteRead, (int) length - byteRead);
            if (result == -1) {
                break;
            }
            byteRead += result;
        }

        readCRLF();

        return new String(string);
    }

    private void readCRLF() throws IOException {

        int cr = inputStream.read();
        int lf = inputStream.read();
        if (cr != CR || lf != LF) {
            throw new IOException("Invalid CRLF");
        }
    }
}
