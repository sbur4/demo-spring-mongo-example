package org.example.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.User;
import org.example.model.manyToMany.Applicant;
import org.example.model.manyToMany.Course;
import org.example.model.oneToMany.BlogPost;
import org.example.model.oneToMany.Comment;
import org.example.model.oneToOne.Profile;
import org.example.model.oneToOne.Student;
import org.example.repository.ApplicantRepository;
import org.example.repository.BlogPostRepository;
import org.example.repository.CommentRepository;
import org.example.repository.CourseRepository;
import org.example.repository.ProfileRepository;
import org.example.repository.StudentRepository;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class Controller {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final MongoTemplate mongoTemplate;
    // 1 to 1
    @Autowired
    private final StudentRepository studentRepository;
    @Autowired
    private final ProfileRepository profileRepository;
    // 1 to M
    @Autowired
    private BlogPostRepository blogPostRepository;
    @Autowired
    private CommentRepository commentRepository;
    // M to M
    @Autowired
    private ApplicantRepository applicantRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping("/user/{name}")
    public User createUsers(@PathVariable String name) {
        Optional<User> userOpt = userRepository.findByName(name);

        if (userOpt.isPresent()) {
            return userOpt.get();
        }

        User user = userRepository.save(new User(name));
        log.debug("Created user: {}", user);

        return user;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        log.debug("Received users: {}", users);

        return users;
    }

    @GetMapping("/user/{name}")
    public User getUser(@PathVariable String name) {
        User user = userRepository.findByName(name).orElse(new User("user"));
        log.debug("Received user: {}", user);

        return user;
    }

    @PutMapping("/user/{name}/{newName}")
    public User updateUser(@PathVariable String name, @PathVariable String newName) {
        Optional<User> userOpt = userRepository.findByName(name);
        Optional<User> newUserOpt = userRepository.findByName(newName);

        if (userOpt.isPresent() && !newUserOpt.isPresent()) {
            log.debug("Update user: {}", userOpt.get());
            updateUserName(name, newName);

            return userRepository.findByName(newName).get();
        }
        if (!userOpt.isPresent() && newUserOpt.isPresent()) {
            return userRepository.save(new User(name));
        }

        return new User();
    }

    private void updateUserName(String oldName, String newName) {
        Query query = new Query(where("name").is(oldName));
        Update update = new Update().set("name", newName);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @DeleteMapping("/user/{name}")
    public void deleteUser(@PathVariable String name) {
        userRepository.deleteByName(name);
        log.debug("Delete user: {}", name);
    }

    // 1 to 1
    @PostMapping("/students")
    public Student createStudent() {
        Profile profile = profileRepository.save(new Profile("fine", "12345"));
        return studentRepository.save(new Student("tester", profile));
    }

    // 1 to M
    @PostMapping("/posts")
    public BlogPost createPosts() {
        BlogPost blogPost = blogPostRepository.save(new BlogPost("new post", "test"));

        Comment comment1 = commentRepository.save(new Comment("user1", "abc", blogPost));
        Comment comment2 = commentRepository.save(new Comment("user2", "def", blogPost));

        List<Comment> comments = Stream.of(comment1, comment2).collect(Collectors.toList());

        // update
        Query query = new Query().addCriteria(Criteria.where("_id").is(blogPost.getId()));
        Update update = new Update().set("comments", comments);
        mongoTemplate.upsert(query, update, BlogPost.class);

//        mongoTemplate.update(BlogPost.class)
//                .matching(where("_id").is(blogPost.getId()))
//                .apply(new Update().push("comments", comment1))
//                .first();

        return blogPostRepository.findById(blogPost.getId()).get();
    }

    // M to M
    @PostMapping("/courses")
    public Applicant createCourse() {
        Applicant applicant = applicantRepository.save(new Applicant("applicant"));
        Course course = courseRepository.save(new Course("course title"));

        applicant.addCourse(course);
        course.addStudent(applicant);

        applicantRepository.save(applicant);
        courseRepository.save(course);

        return applicantRepository.findById(applicant.getId()).get();
    }

    //  transaction
    @PostMapping("/transaction")
    public List<User> transactionUsers() {
        return whenPerformMongoTransaction_thenSuccess();
    }

    @Transactional
    public List<User> whenPerformMongoTransaction_thenSuccess() {
        userRepository.save(new User("John"));
        userRepository.save(new User("Ringo"));
//        Query query = new Query().addCriteria(Criteria.where("name").is("John").and("Ringo"));
        Query query = new Query();
        query.addCriteria(new Criteria().orOperator(
                Criteria.where("name").is("John"),
                Criteria.where("name").is("Ringo")
        ));
        List<User> users = mongoTemplate.find(query, User.class);
        return users;
    }
}
