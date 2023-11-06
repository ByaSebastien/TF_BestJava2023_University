package be.bstorm.controllers;

import be.bstorm.models.entities.Course;
import be.bstorm.models.form.FCourseCreate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


// @RestController à la place @Controller
// Défini que la class ci-dessous doit être gérée comme une application REST => Renvoyé du JSON selon le retour de l'action
@RestController
@RequestMapping(path = {"/courses"})
public class CourseController {
    private final List<Course> courses = new ArrayList<>();

    // Renvoyé le type de la valeur au lieu de renvoyer le nom d'un template
    @GetMapping
    public List<Course> readAllAction() {
        return courses;
    }

    @GetMapping(path = {"/{id}"})
    public Course readOneAction(
            @PathVariable("id") String id
    ) {
        return courses.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
    }

    @PostMapping
    public Course createAction(
            //@RequestBody car tout message échangé dans le cadre d'une application REST est échangé dans le body de la requête HTTP
            @RequestBody Course course
    ) {
        course.setId(courses.size() + 1+ "");
        courses.add(course);

        return course;
    }

    @PutMapping(path = {"/{id}"})
    public Course replaceOneAction(
            @PathVariable("id") String id,
            @RequestBody Course course
    ) {
        Course original = courses.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();
        int index = courses.indexOf(original);

        courses.set(index, course);

        return course;
    }

    @PatchMapping(path = {"/{id}"})
    public Course updateOneAction(
            @PathVariable("id") String id,
            @RequestBody Course course
    ) {
        Course original = courses.stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();

        if (course.getName() != null) {
            original.setName(course.getName());
        }
        if (course.getSummary() != null) {
            original.setSummary(course.getSummary());
        }

        return original;
    }
}
