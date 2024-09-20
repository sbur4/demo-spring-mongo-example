package org.example.model.manyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "courses")
public class Course {
    @Id
    private String id;
    private String title;

    @DBRef
    @JsonBackReference
    private Set<Applicant> students = new HashSet<>(); // Set of students to handle unique students

    public Course(String title) {
        this.title = title;
    }

    public void addStudent(Applicant student) {
        this.students.add(student);
    }
}
