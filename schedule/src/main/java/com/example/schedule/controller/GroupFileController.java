package com.example.schedule.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.schedule.model.GroupFile;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.StudentGroup;
import com.example.schedule.model.Teacher;
import com.example.schedule.repository.GroupFileRepository;
import com.example.schedule.service.FileStorageService;
import com.example.schedule.service.StudentGroupService;
import com.example.schedule.service.TeacherService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/files")
public class GroupFileController {

    private final Path uploadDir = Paths.get("uploads");

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private GroupFileRepository groupFileRepository;
    
    @Autowired
    private StudentGroupService studentGroupService;
    
    @Autowired
    private TeacherService teacherService;

    // ==========================
    // Загрузка файла (Upload)
    // ==========================
    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(
            @RequestParam Long groupId,
            @RequestParam MultipartFile file,
            @RequestParam String comment
    ) {
    	
    	
    	
    	System.out.println(groupId);
    	System.out.println(file);
    	System.out.println(comment);
    	
        try {
            String filePath = fileStorageService.saveFile(file);
            
            StudentGroup studentGroup = studentGroupService.getStudentGroupById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
            
            /* Teacher teacher = teacherService.findTeacherById(groupId)
                    .orElseThrow(() -> new IllegalArgumentException("Teacher not found with id: " + teacherId));
            System.out.println(teacher.getEmail()); */
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();
            Teacher teacher = teacherService.findTeacherByEmail(email);
            
            
            
            // Сохраняем информацию о файле в базе данных
            GroupFile groupFile = new GroupFile();
            groupFile.setGroup(studentGroup);
            groupFile.setTeacher(teacher);
            groupFile.setFilePath(filePath);
            groupFile.setComment(comment);
            groupFileRepository.save(groupFile);

            return ResponseEntity.ok("Файл успешно загружен: " + filePath);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Ошибка при загрузке файла: " + e.getMessage());
        }
    }

    // ==========================
    // Скачивание файла (Download)
    // ==========================
    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        Optional<GroupFile> groupFileOptional = groupFileRepository.findById(id);
        System.out.println(id);
        if (groupFileOptional.isPresent()) {
            GroupFile groupFile = groupFileOptional.get();
            Path filePath = uploadDir.resolve(groupFile.getFilePath());

            try {
                Resource resource = new UrlResource(filePath.toUri());
                if (resource.exists() || resource.isReadable()) {
                    return ResponseEntity.ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + groupFile.getFilePath() + "\"")
                            .body(resource);
                } else {
                    return ResponseEntity.notFound().build();
                }
            } catch (MalformedURLException e) {
                return ResponseEntity.status(500).build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ==========================
    // Получение списка файлов
    // ==========================
    @GetMapping("/list")
    public ResponseEntity<List<GroupFile>> listFiles() {
        List<GroupFile> files = groupFileRepository.findAll();
        return ResponseEntity.ok(files);
    }
    
    
    @GetMapping("/by-group/{groupId}")
    public ResponseEntity<List<GroupFile>> getFilesByGroup(@PathVariable Long groupId) {
        // Получаем группу через Optional
        StudentGroup studentGroup = studentGroupService.getStudentGroupById(groupId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Group not found"));
        System.out.println("Student group found");
        // Возвращаем уроки для группы
        List<GroupFile> files = groupFileRepository.findByStudentGroup(studentGroup);
        return ResponseEntity.ok(files);
    }
}
