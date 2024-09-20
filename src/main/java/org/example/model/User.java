package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

//@Accessors(chain = true)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "users")
public class User {
    @MongoId
    @Field(name = "_id", targetType = FieldType.OBJECT_ID)
    private String id;

    @Field(targetType = FieldType.STRING, name = "name")
    @Indexed(unique = true)
    private String name;

    @CreatedDate
    @Field(targetType = FieldType.DATE_TIME)
    private Instant createdDate;

    @LastModifiedDate
    @Field(targetType = FieldType.TIMESTAMP)
    private Instant lastModifiedDate;

    public User(String name) {
        this.name = name;
    }

}
