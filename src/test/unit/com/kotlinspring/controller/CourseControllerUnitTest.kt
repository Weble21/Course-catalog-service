package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.service.CourseService
import com.kotlinspring.service.GreetingService
import com.kotlinspring.util.courseDTO
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    //유닛 테스트 공통 ~
    @Autowired
    lateinit var webTestClient: WebTestClient
    @MockkBean
    lateinit var courseServiceMockk : CourseService //~ 공통

    @Test
    fun addCourse() {

        val courseDTO = CourseDTO(
            null, "rBuild Restful APIs using SpringBoot and Kotlin", "Dilip Sundarraj", 1
        )

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)

        val savedCourseDTO = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedCourseDTO!!.id != null
        }
    }

    @Test
    fun addCourse_validation() {

        val courseDTO = CourseDTO(
            null, "", "", 1
        )

        every { courseServiceMockk.addCourse(any()) } returns courseDTO(id = 1)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("courseDTO.category must not be blank, courseDTO.name must not be blank", response)
    }

    @Test
    fun addCourse_runtimeException() {

        val courseDTO = CourseDTO(
            null, "Build Restful APIs using SpringBoot and Kotlin", "Dilip Sundarraj", 1
        )

        val errorMessage = "Unexpected Error Occurred"
        every { courseServiceMockk.addCourse(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient
            .post()
            .uri("/v1/courses")
            .bodyValue(courseDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }

    @Test
    fun retrieveAllCourses() {

        //list가 반환값이라 returnsMany 써야됨
        every { courseServiceMockk.retrieveAllCourses(any()) }.returnsMany(
            listOf(courseDTO(id = 1), courseDTO(id = 2, name = "Build"))
        )
        val courseDTOs = webTestClient
            .get()
            .uri("/v1/courses")
            .exchange()
            .expectStatus().isOk
            .expectBodyList(CourseDTO::class.java)
            .returnResult()
            .responseBody

        println("courseDTOs : $courseDTOs")
        assertEquals(2, courseDTOs!!.size)

    }

    @Test
    fun updateCourse() {
        //existing course
        val course = Course(null,
            "Build RESTful APIs using SpringBoot and Kotlin", "Development")

        every { courseServiceMockk.updateCourse(any(), any()) } returns courseDTO(id = 100,
            name = "Build RESTful APIs using SpringBoot and Kotlin1")
        //courseRepository.save(course) , 단위테스트라 repository 제거 -> course.id 못줌
        //courseId
        //Updated CourseDTO
        val updatedCourseDTO = CourseDTO(null,
            "Build RESTful APIs using SpringBoot and Kotlin1", "Development")

        val updatedCourse = webTestClient
            .put()
            .uri("/v1/courses/{courseId}", 100) //통합테스트에서는 course.id로 하지만, 여기서는 X
            .bodyValue(updatedCourseDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(CourseDTO::class.java)
            .returnResult()
            .responseBody

        assertEquals("Build RESTful APIs using SpringBoot and Kotlin1", updatedCourse!!.name)
    }
    @Test
    fun deleteCourse() {

       every{ courseServiceMockk.deleteCourse(any()) } returns "Course was deleted"

        val deleteCourse = webTestClient
            .delete()
            .uri("/v1/courses/{courseId}", 100)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("Course was deleted", deleteCourse)

    }
    //return값이 없으면 just runs 로 구현
}