package pl.karczewski.cinema.domain.movie

import pl.karczewski.cinema.domain.reservation.MovieProjection
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob
import javax.persistence.OneToMany

@Entity
class Movie(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var title: String?,
    @Column(nullable = false, unique = false)
    var genre: String?,
    @Column(nullable = false, unique = false)
    var voteAverage: Float?,
    @Column(nullable = false, unique = false)
    var posterPath: String?,
    @Lob
    @Column(nullable = false, unique = false)
    var overview: String?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.EAGER, mappedBy = "movie")
    var projections: MutableList<MovieProjection>? = null,
) {
    fun toMovieDto(): MovieDto {
        return MovieDto(
            id = id!!,
            title = title!!,
            genre = genre!!,
            voteAverage = voteAverage!!,
            posterPath = posterPath!!,
            overview = overview!!,
            availableProjections = projections?.any { projection -> projection.seats?.any { !it.taken }!! }!!
        )
    }
}

data class MovieDto(
    var id: Long,
    var title: String,
    var genre: String,
    var voteAverage: Float,
    var posterPath: String,
    var overview: String,
    var availableProjections: Boolean
)
