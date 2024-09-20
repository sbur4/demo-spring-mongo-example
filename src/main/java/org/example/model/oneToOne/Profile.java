package org.example.model.oneToOne;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document(collection = "profiles")
public class Profile {
    @Id
    private String id;
    private String bio;
    private String phoneNumber;

    public Profile(String bio, String phoneNumber) {
        this.bio = bio;
        this.phoneNumber = phoneNumber;
    }
}
