package pl.karczewski.cinema.domain.movie

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/movies")
class MovieController(val service: MovieService) {
    @GetMapping("/")
    fun allMovies(): List<Movie> {
        return service.fetchAllMovies()
    }
}
