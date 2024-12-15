package com.example.schedule.service;
import org.springframework.stereotype.Service;

import com.example.schedule.model.GroupFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {
/*
    private final String uploadDir = "uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        // Создаём уникальное имя файла
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Создаём директорию, если её ещё нет
        new File(uploadDir).mkdirs();

        // Сохраняем файл на диск
        file.transferTo(filePath.toFile());

        // Возвращаем путь к сохранённому файлу
        return filePath.toString();
    } */
}
