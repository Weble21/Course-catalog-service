package com.kotlinspring.service

import com.kotlinspring.dto.InstructorDTO
import com.kotlinspring.entity.Instructor
import com.kotlinspring.repository.InstructorRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    companion object : KLogging()

    fun createInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }
        instructorRepository.save(instructorEntity)

        logger.info("Saved instructor is : $instructorEntity")

        return instructorEntity.let {
            InstructorDTO(it.id, it.name)
        }
    }
}
