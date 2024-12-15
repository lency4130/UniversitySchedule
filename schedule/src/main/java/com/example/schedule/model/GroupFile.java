package com.example.schedule.model;

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

    // Конструкторы
    public GroupFile() {}

    public GroupFile(StudentGroup studentGroup, String filePath) {
        this.studentGroup = studentGroup;
        this.filePath = filePath;
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

    public void setGroup(Long groupId) {
        this.studentGroup = studentGroup;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
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
