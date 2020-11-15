package pl.karczewski.cinema.domain

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MovieController {
    @GetMapping("/movies")
    fun allMovies(): List<Movie> {
        return listOf(Movie("Lord of the rings"))
    }
}


data class Movie(
    val title: String
)