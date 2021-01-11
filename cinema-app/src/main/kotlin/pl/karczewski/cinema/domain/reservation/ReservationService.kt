package pl.karczewski.cinema.domain.reservation

import org.springframework.stereotype.Service
import pl.karczewski.cinema.common.InformationResponseBody
import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.client.ClientService
import pl.karczewski.cinema.domain.hall.Hall
import pl.karczewski.cinema.domain.movie.Movie
import pl.karczewski.cinema.domain.movie.MovieDto
import java.security.Principal
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class ReservationService(
    private val movieProjectionRepository: MovieProjectionRepository,
    private val seatReservationRepository: SeatReservationRepository,
    private val clientService: ClientService
) {

    @Transactional
    fun createProjection(movie: Movie, hall: Hall, datetime: LocalDateTime) {
        val projection = MovieProjection(
            datetime = datetime,
            hall = hall,
            movie = movie,
            seats = null
        )
        movieProjectionRepository.save(projection)

        for (row in 1..hall.numRows!!) {
            for (col in 1..hall.numColumns!!) {
                val seat = SeatReservation(
                    numRow = row,
                    numCol = col,
                    client = null,
                    projection = projection
                )
                seatReservationRepository.save(seat)
            }
        }
    }

    internal fun getProjectionData(projectionId: Long): ProjectionSeatsDto {
        val projection = movieProjectionRepository.findById(projectionId).get()
        val seats = projection.seats!!
        return ProjectionSeatsDto(
            movie = projection.movie?.toMovieDto()!!,
            projection = projection.toProjectionDto(),
            seats = seats.map { it.toSeatReservationDto() }
        )
    }

    fun reserveSeat(seatId: Long, client: Client) {
        val seat = seatReservationRepository.findById(seatId).get()
        seat.client = client
        seatReservationRepository.save(seat)
    }

    @Transactional
    internal fun reserveSeats(reserveSeatsDto: ReserveSeatsDto, principal: Principal): InformationResponseBody {
        return try {
            val client = clientService.fetchClient(principal.name)
            val seats = seatReservationRepository.findAllById(reserveSeatsDto.seatIds)
            seats.map {
                it.client = client
                it
            }.let { seatReservationRepository.saveAll(it) }
            InformationResponseBody(message = "Successfully reserved seats!")
        } catch (ex: Exception) {
            InformationResponseBody(message = "Error reserving seats!")
        }
    }

    internal fun clientReservations(principal: Principal): ClientProjectionsDto {
        val client = clientService.fetchClient(principal.name)
        val projectionSeatsAssociation = client.seatReservations
            ?.groupBy { it.projection }
            ?.mapKeys { entry ->
                entry.key?.toProjectionWithSeatsDto(entry.value.map { it.toSeatReservationDto() })
            }!!

        return ClientProjectionsDto(
            projections = projectionSeatsAssociation.keys.map { it!! }
        )
    }
}

internal data class ProjectionSeatsDto(
    val movie: MovieDto,
    val projection: MovieProjectionDto,
    val seats: List<SeatReservationDto>
)

internal data class ClientProjectionsDto(
    val projections: List<MovieProjectionWithSeatsDto>
)
