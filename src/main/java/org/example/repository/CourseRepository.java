package org.example.repository;

import org.example.model.manyToMany.Course;
import org.example.model.oneToMany.BlogPost;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
}