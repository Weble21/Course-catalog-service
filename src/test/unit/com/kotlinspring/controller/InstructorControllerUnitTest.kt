package com.kotlinspring.controller

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient
    @MockkBean
    lateinit var instructorServiceMockk : InstructorService //~ 공통

    @Test
    fun createInstructor() {

        val instructorDTO = InstructorDTO(
            null, "brick"
        )

        every { instructorServiceMockk.createInstructor(any()) } returns InstructorDTO(id = 1, name = instructorDTO.name)

        val savedInstructorDTO = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody

        Assertions.assertTrue {
            savedInstructorDTO!!.id != null
        }
    }
    @Test
    fun createInstructor_validation() {

        val instructorDTO = InstructorDTO(
            null, ""
        )

        every { instructorServiceMockk.createInstructor(any()) } returns InstructorDTO(id = 1, name = instructorDTO.name)

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("instructorDTO.name must not be blank", response)
    }


    @Test
    fun createInstructor_runtimeException() {

        val instructorDTO = InstructorDTO(
            null, "Dilip Sundarraj"
        )

        val errorMessage = "Unexpected Error Occurred"
        every { instructorServiceMockk.createInstructor(any()) } throws RuntimeException(errorMessage)

        val response = webTestClient
            .post()
            .uri("/v1/instructors")
            .bodyValue(instructorDTO)
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals(errorMessage, response)
    }
}

