package org.example.model.oneToOne;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "students")
public class Student {
    @Id
    private String id;

    private String username;

    @DBRef
    private Profile profile;

    public Student(String id, String username, Profile profile) {
        this.id = id;
        this.username = username;
        this.profile = profile;
    }

    public Student(String username, Profile profile) {
        this.profile = profile;
        this.username = username;
    }
}
