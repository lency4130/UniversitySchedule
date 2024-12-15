package com.example.schedule.service;

import com.example.schedule.model.StudentGroup;
import com.example.schedule.model.Subject;
import com.example.schedule.model.Grade;
import com.example.schedule.model.Teacher;
import com.example.schedule.model.Lesson;
import com.example.schedule.model.Student;
import com.example.schedule.repository.GradeRepository;
import com.example.schedule.repository.LessonRepository;
import com.example.schedule.repository.StudentGroupRepository;
import com.example.schedule.repository.SubjectRepository;
import com.example.schedule.repository.TeacherRepository;
import com.example.schedule.repository.TimeSlotRepository;
import org.springframework.stereotype.Service;
import com.example.schedule.dto.LessonDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
import com.example.schedule.model.TimeSlot;
@Service
public class LessonService {

	private final LessonRepository lessonRepository;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final StudentGroupRepository studentGroupRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final GradeRepository gradeRepository;

    public LessonService(LessonRepository lessonRepository, 
                         SubjectRepository subjectRepository, 
                         TeacherRepository teacherRepository, 
                         StudentGroupRepository studentGroupRepository, 
                         TimeSlotRepository timeSlotRepository,
                         GradeRepository gradeRepository) {
        this.lessonRepository = lessonRepository;
        this.subjectRepository = subjectRepository;
        this.teacherRepository = teacherRepository;
        this.studentGroupRepository = studentGroupRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.gradeRepository = gradeRepository;
    }

    public List<Lesson> getAllLessons() {
        return lessonRepository.findAll();
    }
    
    public List<Lesson> getLessonsByTeacher(Teacher teacher) {
        return lessonRepository.findByTeacher(teacher);
    }

    public Optional<Lesson> getLessonById(Long id) {
        return lessonRepository.findById(id);
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public void deleteLesson(Long id) {
    	lessonRepository.deleteById(id);
    }
    
    public List<Lesson> getLessonsByGroup(StudentGroup group) {
        return lessonRepository.findByStudentGroup(group);
    }
    

    private LessonDTO convertToDTO(Lesson lesson, Grade grade) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setSubjectName(lesson.getSubject().getName());
        dto.setTeacherName(lesson.getTeacher().getFullName());
        dto.setTeacherEmail(lesson.getTeacher().getEmail());
        dto.setGroupName(lesson.getStudentGroup().getGroupName());
        dto.setTimeSlot(lesson.getTimeSlot().getTimeLabel());
        dto.setLessonDate(lesson.getLessonDate());

        if (grade != null) {
            dto.setGrade(grade.getGrade());
            dto.setComment(grade.getComment());
        }

        return dto;
    }
    
    public List<LessonDTO> getLessonsWithGradesForStudent(Student student, StudentGroup group) {
        List<Grade> grades = gradeRepository.findByStudent(student);
        List<Lesson> lessons = lessonRepository.findByStudentGroup(group); // фильтр по группе

        return lessons.stream()
                .map(lesson -> {
                    Grade grade = grades.stream()
                            .filter(g -> g.getLesson().equals(lesson))
                            .findFirst()
                            .orElse(null);
                    return convertToDTO(lesson, grade);
                })
                .collect(Collectors.toList());
    }
    
    public Lesson addLesson(Long subjectId, Long teacherId, Long groupId, Long timeSlotId, LocalDate lessonDate) {
        // Найти связанные сущности по их ID
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found"));
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
        StudentGroup studentGroup = studentGroupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Student Group not found"));
        TimeSlot timeSlot = timeSlotRepository.findById(timeSlotId)
                .orElseThrow(() -> new IllegalArgumentException("Time Slot not found"));

        // Создать новый урок
        Lesson lesson = new Lesson();
        lesson.setSubject(subject);
        lesson.setTeacher(teacher);
        lesson.setStudentGroup(studentGroup);
        lesson.setTimeSlot(timeSlot);
        lesson.setLessonDate(lessonDate);

        // Сохранить урок в базе данных
        return lessonRepository.save(lesson);
    }

    
}
