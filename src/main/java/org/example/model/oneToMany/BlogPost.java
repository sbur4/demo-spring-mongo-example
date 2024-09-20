package org.example.model.oneToMany;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Document(collection = "blogposts")
public class BlogPost {
    @Id
    private String id;

    private String title;
    private String content;

    @Field("comments")
//    @JsonDeserialize(as = List.class)
    @JsonManagedReference
    private List<Comment> comments;

    public BlogPost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public BlogPost(String title, String content, List<Comment> comments) {
        this.title = title;
        this.content = content;
        this.comments = comments;
    }

//    @JsonCreator
//    public BlogPost(@JsonProperty("title") String title,
//                    @JsonProperty("content") String content,
//                    @JsonProperty("comments") List<Comment> comments) {
//        this.title = title;
//        this.content = content;
//        this.comments = comments;
//    }
}
