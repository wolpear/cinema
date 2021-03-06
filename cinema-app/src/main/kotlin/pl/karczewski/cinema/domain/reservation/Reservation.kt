package pl.karczewski.cinema.domain.reservation

import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.hall.Hall
import pl.karczewski.cinema.domain.hall.HallDto
import pl.karczewski.cinema.domain.movie.Movie
import pl.karczewski.cinema.domain.movie.MovieDto
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
    var movie: Movie?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "projection")
    var seats: MutableList<SeatReservation>?
) {
    fun toProjectionDto(): MovieProjectionDto {
        return MovieProjectionDto(
            id = id!!,
            datetime = datetime!!,
            hall = hall?.toHallDto()!!,
            freeSeats = seats?.count { !it.taken }!!
        )
    }

    fun toProjectionWithSeatsDto(seats: List<SeatReservationDto>): MovieProjectionWithSeatsDto {
        return MovieProjectionWithSeatsDto(
            id = id!!,
            datetime = datetime!!,
            movie = movie?.toMovieDto()!!,
            hall = hall?.toHallDto()!!,
            seats = seats
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
    var client: Client?,
    @ManyToOne(optional = false)
    var projection: MovieProjection?
) {
    val taken: Boolean
        get() = client != null

    fun toSeatReservationDto(): SeatReservationDto {
        return SeatReservationDto(
            id = id!!,
            numRow = numRow!!,
            numCol = numCol!!,
            taken = taken
        )
    }
}

data class MovieProjectionDto(
    val id: Long,
    val datetime: LocalDateTime,
    val hall: HallDto,
    val freeSeats: Int
)

data class MovieProjectionWithSeatsDto(
    val id: Long,
    val datetime: LocalDateTime,
    val hall: HallDto,
    val movie: MovieDto,
    val seats: List<SeatReservationDto>
)

data class SeatReservationDto(
    val id: Long,
    val numRow: Int,
    val numCol: Int,
    val taken: Boolean
)
