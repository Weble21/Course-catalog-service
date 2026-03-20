package com.kotlinspring.repository

import com.kotlinspring.util.PostgreSQLContainerInitializer
import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntity
import com.kotlinspring.util.instructorEntityList
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.containers.PostgreSQLContainer

@DataJpaTest
@ActiveProfiles("test")
class InstructorRepositoryIntgTest : PostgreSQLContainerInitializer() {

    @Autowired
    lateinit var instructorRepository : InstructorRepository

    @BeforeEach
    fun setup() {
        instructorRepository.deleteAll()

        val instructor = instructorEntity()
        instructorRepository.save(instructor)

    }
}