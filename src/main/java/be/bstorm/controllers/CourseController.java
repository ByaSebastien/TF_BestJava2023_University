package be.bstorm.controllers;

import be.bstorm.models.entities.Course;
import be.bstorm.repositories.CourseRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;


// @RestController à la place @Controller
// Défini que la class ci-dessous doit être gérée comme une application REST => Renvoyé du JSON selon le retour de l'action
@RestController
@RequestMapping(path = {"/courses"})
public class CourseController {
    private final CourseRepository courseRepository;

    public CourseController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Renvoyé le type de la valeur au lieu de renvoyer le nom d'un template
    @GetMapping
    public List<Course> readAllAction() {
        return this.courseRepository.findAll();
    }

    @GetMapping(path = {"/{id}"})
    public Course readOneAction(
            @PathVariable("id") String id
    ) {
        return this.courseRepository.findById(id).orElseThrow();
    }

    @PostMapping
    public Course createAction(
            //@RequestBody car tout message échangé dans le cadre d'une application REST est échangé dans le body de la requête HTTP
            @RequestBody Course course
    ) {
        return this.courseRepository.save(course);
    }

    @PutMapping(path = {"/{id}"})
    public Course replaceOneAction(
            @PathVariable("id") String id,
            @RequestBody Course course
    ) {
        Course toUpdate = this.courseRepository.findById(id).orElseThrow();
        course.setId(toUpdate.getId());

        return this.courseRepository.save(course);
    }

    @PatchMapping(path = {"/{id}"})
    public Course updateOneAction(
            @PathVariable("id") String id,
            @RequestBody Course course
    ) {
        Course original = this.courseRepository.findById(id)
                .orElseThrow();

        if (course.getName() != null) {
            original.setName(course.getName());
        }
        if (course.getSummary() != null) {
            original.setSummary(course.getSummary());
        }

        return this.courseRepository.save(original);
    }

    @DeleteMapping(path = {"/{id}"})
    public void deleteOneAction(
            @PathVariable("id") String id
    ) {
        this.courseRepository.deleteById(id);
    }
}
