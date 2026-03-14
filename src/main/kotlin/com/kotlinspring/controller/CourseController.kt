package com.kotlinspring.controller

import com.kotlinspring.CourseCatalogServiceApplication
import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
@Validated
class CourseController(val courseService : CourseService) {

    //CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO) : CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    //RETRIEVE
    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required = false) courseName: String?) : List<CourseDTO>
    = courseService.retrieveAllCourses(courseName)

    //UPDATE
    //courseID
    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable("course_id") courseId : Int)
    = courseService.updateCourse(courseId, courseDTO)

    //DELETE
    @DeleteMapping("/{course_id}")
    //@ResponseStatus(HttpStatus.NO_CONTENT) -> 이건 반환값이 없으면 사용
    fun deleteCourse(@PathVariable("course_id") courseId: Int)
    = courseService.deleteCourse(courseId)
}