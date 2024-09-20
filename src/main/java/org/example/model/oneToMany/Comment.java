package org.example.model.oneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "comments")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, prop = "id")
public class Comment {
    @Id
    private String id;

    private String username;
    private String text;

    @DBRef
    //    @DocumentReference(lazy=true)
    @JsonBackReference
    private BlogPost blogPost;

    public Comment(String username, String text) {
        this.username = username;
        this.text = text;
    }

    public Comment(String username, String text, BlogPost blogPost) {
        this.username = username;
        this.text = text;
        this.blogPost = blogPost;
    }
}
