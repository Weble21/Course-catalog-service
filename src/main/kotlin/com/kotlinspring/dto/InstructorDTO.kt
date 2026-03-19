package com.kotlinspring.dto

import com.kotlinspring.entity.Course
import jakarta.persistence.CascadeType
import jakarta.persistence.OneToMany
import jakarta.validation.constraints.NotBlank


data class InstructorDTO (
    val id : Int?,

    @get:NotBlank(message = "instructorDTO.name must not be blank")
    val name: String

)