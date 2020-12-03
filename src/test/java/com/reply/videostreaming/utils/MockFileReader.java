package com.reply.videostreaming.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Convert the given json file path to byte array, used with Wire Mock.
 * @author sraamasubbu
 */
@Component
public class MockFileReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(MockFileReader.class);

    public String readFile(String path) {
        Optional<List<String>> lines = Optional.empty();
        try {
            Path filePath = Paths.get(ClassLoader.getSystemResource(path).toURI());
            lines = Optional.of(Files.readAllLines(filePath));
        } catch (URISyntaxException e) {
            LOGGER.error("Error during URI parsing", e);
        } catch (IOException e) {
            LOGGER.error("Error during reading the file.", e);
        }

        return lines.map(strings -> String.join("", strings))
            .orElse(StringUtils.EMPTY);
    }
}