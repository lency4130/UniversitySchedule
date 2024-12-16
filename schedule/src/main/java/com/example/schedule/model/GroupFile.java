package com.example.schedule.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.*;

@Entity
public class GroupFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    	
    @ManyToOne
    @JoinColumn(name = "group_id", nullable = false)
    private StudentGroup studentGroup;

    @Column(name = "file_path", nullable = false)
    private String filePath;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;
    
    

    // Конструкторы
    public GroupFile() {}

    public GroupFile(StudentGroup studentGroup, String filePath, String comment, LocalDateTime createdAt, Teacher teacher) {
        this.studentGroup = studentGroup;
        this.filePath = filePath;
        this.comment = comment;
        this.createdAt = createdAt;
        this.teacher = teacher;
    }

    // Геттеры и сеттеры
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudentGroup getGroup() {
        return studentGroup;
    }

    public void setGroup(StudentGroup studentGroup) {
        this.studentGroup = studentGroup;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
    
    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Teacher getTeacher() {
        return teacher;
    }

    // Сеттер для teacher
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    // Сеттер для createdAt
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupFile groupFile = (GroupFile) o;
        return Objects.equals(id, groupFile.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
