package pl.karczewski.cinema.domain.reservation

import com.fasterxml.jackson.annotation.JsonBackReference
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonManagedReference
import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.hall.Hall
import pl.karczewski.cinema.domain.movie.Movie
import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany

@Entity
class MovieProjection(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = false)
    var datetime: LocalDateTime?,
    @ManyToOne
    var hall: Hall?,
    @ManyToOne
    @JsonBackReference
    var movie: Movie?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "projection")
    @JsonManagedReference
    var seats: MutableList<SeatReservation>?
) {
    fun toProjectionDto(): MovieProjectionDto {
        return MovieProjectionDto(
            id = id!!,
            datetime = datetime!!,
            hall = hall!!,
            freeSeats = seats?.count { !it.taken }!!
        )
    }
}

@Entity
data class SeatReservation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = false)
    var numRow: Int?,
    @Column(nullable = false, unique = false)
    var numCol: Int?,
    @ManyToOne(optional = true)
    @JsonIgnore
    var client: Client?,
    @ManyToOne(optional = false)
    @JsonBackReference
    var projection: MovieProjection?
) {
    val taken: Boolean
        get() = client != null
}

data class MovieProjectionDto(
    val id: Long,
    val datetime: LocalDateTime,
    val hall: Hall,
    val freeSeats: Int
)
