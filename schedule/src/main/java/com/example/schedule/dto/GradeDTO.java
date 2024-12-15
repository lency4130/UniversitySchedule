package com.example.schedule.dto;

public class GradeDTO {
    private Long id;
    private Long studentId; // Добавляем поле studentId
    private String studentName;
    private int grade;
    private String comment;

    public GradeDTO(Long id, Long studentId, String studentName, int grade, String comment) {
        this.id = id;
        this.studentId = studentId; // Инициализируем поле
        this.studentName = studentName;
        this.grade = grade;
        this.comment = comment;
    }


    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }
    // Геттер для id
    public Long getId() {
        return id;
    }

    // Сеттер для id
    public void setId(Long id) {
        this.id = id;
    }

    // Геттер для studentName
    public String getStudentName() {
        return studentName;
    }

    // Сеттер для studentName
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    // Геттер для grade
    public int getGrade() {
        return grade;
    }

    // Сеттер для grade
    public void setGrade(int grade) {
        this.grade = grade;
    }

    // Геттер для comment
    public String getComment() {
        return comment;
    }

    // Сеттер для comment
    public void setComment(String comment) {
        this.comment = comment;
    }
}
