package org.example.model.manyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Data
@Document(collection = "applicants")
public class Applicant {
    @Id
    private String id;
    private String name;

    @DBRef
    @JsonManagedReference
    private Set<Course> courses = new HashSet<>(); // Set of courses to handle unique courses

    public Applicant(String name) {
        this.name = name;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }
}
