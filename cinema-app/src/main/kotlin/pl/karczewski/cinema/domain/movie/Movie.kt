package pl.karczewski.cinema.domain.movie

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Lob

@Entity
data class Movie(
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
)
