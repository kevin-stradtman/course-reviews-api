package com.stradtkt.core;

import com.stradtkt.course.Course;
import com.stradtkt.course.CourseRepository;
import com.stradtkt.review.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Component
public class DatabaseLoader implements ApplicationRunner {
    private final CourseRepository courses;

    @Autowired
    public DatabaseLoader(CourseRepository courses) {
        this.courses = courses;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Course course = new Course("Java Basics", "https://teamtreehouse.com/library/java-basics");
        course.addReview(new Review(5, "Such a great course"));
        courses.save(course);
        String[] templates = {
                "Up and Running with %s",
                "%s Basics",
                "%s for Beginners",
                "%s for Experts",
                "Under the hood with %s"
        };
        String[] buzzwords = {
                "Spring Rest Data",
                "Java 11",
                "Scala",
                "Groovy",
                "Hibernate",
                "Spring HATEOAS"
        };
        List<Course> bunchOfCourses = new ArrayList<>();
        IntStream.range(0, 100)
                .forEach(i -> {
                    String template = templates[i % templates.length];
                    String buzzword = buzzwords[i % buzzwords.length];
                    String title = String.format(template, buzzword);
                    Course c = new Course(title, "http://teamtreehouse.com");
                    c.addReview(new Review(i % 5, String.format("More %s please!!!", buzzword)));
                    bunchOfCourses.add(c);
                });
        courses.save(bunchOfCourses);
    }
}
