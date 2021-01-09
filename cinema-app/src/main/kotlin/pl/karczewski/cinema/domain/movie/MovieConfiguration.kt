package pl.karczewski.cinema.domain.movie

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("movie")
data class MovieConfiguration(
    val tmdbApiKey: String
)
