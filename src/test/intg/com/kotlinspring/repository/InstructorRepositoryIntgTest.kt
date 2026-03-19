package com.kotlinspring.repository

import com.kotlinspring.util.courseEntityList
import com.kotlinspring.util.instructorEntityList
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
class InstructorRepositoryIntgTest {
    @Autowired
    lateinit var InstructorRepository : InstructorRepository

    @BeforeEach
    fun setup() {
        InstructorRepository.deleteAll()
        val instructors = instructorEntityList()
        InstructorRepository.saveAll(instructors)
    }
}