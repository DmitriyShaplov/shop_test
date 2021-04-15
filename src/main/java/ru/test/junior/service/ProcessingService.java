package ru.test.junior.service;

import java.io.File;

public interface ProcessingService {
    void process(File input, File output) throws Exception;
}
