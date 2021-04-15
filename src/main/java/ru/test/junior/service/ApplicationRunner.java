package ru.test.junior.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.test.junior.dto.ErrorResult;
import ru.test.junior.enums.Operation;

import java.io.File;
import java.nio.file.Paths;
import java.util.Map;

@Component
public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Map<String, ProcessingService> serviceMap;

    @Override
    public void run(String... args) throws Exception {
        if (args.length != 3) {
            throw new RuntimeException("Неверное количество аргументов! должно быть 3");
        }
        Operation operation = getOperation(args);
        File input = Paths.get(args[1]).toFile();
        File output = Paths.get(args[2]).toFile();
        try {
            serviceMap.get(operation.name()).process(input, output);
        } catch (Exception e) {
            ErrorResult errorResult = new ErrorResult(e.getMessage());
            objectMapper.writeValue(output, errorResult);
        }
    }

    private Operation getOperation(String[] args) {
        Operation operation;
        try {
            operation = Operation.of(args[0]);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Неверный аргумент операции, должен быть search/stat");
        }
        return operation;
    }
}
