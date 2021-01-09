package pl.karczewski.cinema.common

import java.time.Instant

data class InformationResponseBody(
    val message: String,
    val timestamp: Instant = Instant.now(),
)
