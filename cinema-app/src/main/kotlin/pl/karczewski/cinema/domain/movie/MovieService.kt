package pl.karczewski.cinema.domain.movie

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import pl.karczewski.cinema.domain.reservation.MovieProjectionDto

@Service
class MovieService(
    val repository: MovieRepository,
    val configuration: MovieConfiguration
) {
    val categoryCacheMap = loadCategoriesFromMovieDBToLocalMapCache()

    private fun uriComponentBuilder() = UriComponentsBuilder.newInstance()
        .scheme("https")
        .host("api.themoviedb.org")
        .queryParam("api_key", configuration.tmdbApiKey)

    private fun loadCategoriesFromMovieDBToLocalMapCache(): Map<Int, String> {
        val url = uriComponentBuilder().path("3/genre/movie/list").toUriString()
        val response = restTemplate.exchange(url, HttpMethod.GET, null, ResponseCategoriesTMDB::class.java)

        return response.body?.genres?.associateBy({ it.id }, { it.name })!!
    }

    fun loadMultipleMoviesPagesFromMovieDBToLocalDBCache() {
        for (pageNum in 1..3) {
            loadMoviesFromMovieDBToLocalDBCache(pageNum)
        }
    }

    private fun loadMoviesFromMovieDBToLocalDBCache(pageNum: Int = 1) {
        val url = uriComponentBuilder().path("3/movie/popular").queryParam("page", pageNum).toUriString()
        val response = restTemplate.exchange(
            url,
            HttpMethod.GET,
            null,
            ResponseMoviesTMDB::class.java
        )

        response.body?.results
            ?.filter { it.genreIds.isNotEmpty() && it.overview.isNotBlank() }
            ?.map { it.toMovie(this) }
            ?.map { repository.save(it) }
    }

    fun fetchAllMovies(): List<Movie> {
        val movies = repository.findAll().toList()
        return movies
    }

    fun fetchAllMoviesDto(): List<MovieDto> {
        return repository.findAll().map { it.toMovieDto() }
    }

    fun fetchMovieProjections(movieId: Long): List<MovieProjectionDto> {
        val movie = repository.findById(movieId).get()
        return movie.projections?.map { it.toProjectionDto() }!!
    }

    fun fetchMovieDto(movieId: Long): MovieDto {
        return repository.findById(movieId).get().toMovieDto()
    }

    companion object {
        val restTemplate = RestTemplate()
    }
}

data class ResponseMoviesTMDB(
    val results: List<MovieTMDB>
)

data class MovieTMDB(
    val title: String,
    @JsonProperty("vote_average") val voteAverage: Float,
    @JsonProperty("genre_ids") val genreIds: List<Int>,
    @JsonProperty("poster_path") val posterPath: String,
    val overview: String
) {
    fun toMovie(movieService: MovieService): Movie {
        return Movie(
            null,
            title = title,
            genre = genreIds.joinToString { movieService.categoryCacheMap.getOrDefault(it, "unknown category") },
            voteAverage = voteAverage,
            posterPath = "https://image.tmdb.org/t/p/w300$posterPath",
            overview = overview
        )
    }
}

data class ResponseCategoriesTMDB(
    val genres: List<CategoryTMDB>
)

data class CategoryTMDB(
    val id: Int,
    val name: String
)
