package pl.karczewski.cinema.domain.reservation

import org.springframework.stereotype.Service
import pl.karczewski.cinema.domain.hall.Hall
import pl.karczewski.cinema.domain.movie.Movie
import java.time.LocalDateTime
import javax.transaction.Transactional

@Service
class ReservationService(
    val movieProjectionRepository: MovieProjectionRepository,
    val seatReservationRepository: SeatReservationRepository
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
}
