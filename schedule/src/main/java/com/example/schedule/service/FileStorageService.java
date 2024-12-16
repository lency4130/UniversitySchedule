package com.example.schedule.service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.schedule.model.GroupFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

	private final String uploadDir = System.getProperty("user.home") + "/uploads/";

    public String saveFile(MultipartFile file) throws IOException {
        // Путь к директории uploads в рабочей директории

        // Создаём директорию, если её ещё нет
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                throw new IOException("Не удалось создать директорию для загрузки файлов: " + uploadDir);
            }
        }

        // Создаём уникальное имя файла
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        // Логирование пути
        System.out.println("Сохранение файла по пути: " + filePath.toString());

        // Сохраняем файл на диск
        file.transferTo(filePath.toFile());

        return filePath.toString();
    }
    
    

}
