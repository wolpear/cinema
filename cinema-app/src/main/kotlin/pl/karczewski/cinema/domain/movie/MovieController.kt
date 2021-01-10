package pl.karczewski.cinema.domain.movie

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.karczewski.cinema.domain.reservation.MovieProjectionDto

@RestController
@RequestMapping("/api/v1/movies")
class MovieController(val service: MovieService) {
    @GetMapping()
    fun allMovies(): MoviesDto {
        return MoviesDto(service.fetchAllMoviesDto())
    }

    @GetMapping("/{movieId}")
    fun movie(@PathVariable movieId: String): MovieDto {
        return service.fetchMovieDto(movieId.toLong())
    }

    @GetMapping("/{movieId}/projections")
    fun movieProjections(@PathVariable movieId: String): MovieProjectionsDto {
        return MovieProjectionsDto(service.fetchMovieProjections(movieId.toLong()))
    }
}

data class MoviesDto(
    val movies: List<MovieDto>
)

data class MovieProjectionsDto(
    val projections: List<MovieProjectionDto>
)
