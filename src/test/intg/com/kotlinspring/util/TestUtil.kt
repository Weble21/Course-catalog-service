package com.kotlinspring.util

import com.kotlinspring.dto.CourseDTO
import com.kotlinspring.entity.Course
import com.kotlinspring.entity.Instructor


fun courseEntityList() = listOf(
    Course(null,
        "Build RestFul APis using SpringBoot and Kotlin", "Development"),
    Course(null,
        "Build Reactive Microservices using Spring WebFlux/SpringBoot", "Development"
        ,
    ),
    Course(null,
        "Wiremock for Java Developers", "Development" ,
    )
)

fun instructorEntityList() = listOf(
    Instructor(null,
        "Donggeon"),
    Instructor(null,
        "Sandra"),
    Instructor(null,
        "Dilip")
)

fun courseDTO(
    id: Int? = null,
    name: String = "Default Kotlin Spring Boot",
    category: String = "Dilip Sundarraj",
    //instructorId: Int? = 1
) = CourseDTO(
    id,
    name,
    category,
    //instructorId
)