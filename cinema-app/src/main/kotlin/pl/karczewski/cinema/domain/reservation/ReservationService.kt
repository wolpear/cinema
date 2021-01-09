package pl.karczewski.cinema.domain.reservation

import org.springframework.stereotype.Service
import pl.karczewski.cinema.domain.hall.Hall
import pl.karczewski.cinema.domain.movie.Movie
import java.time.LocalDateTime

@Service
class ReservationService(
    val movieProjectionRepository: MovieProjectionRepository,
    val seatReservationRepository: SeatReservationRepository
) {
    fun createProjection(movie: Movie, hall: Hall, datetime: LocalDateTime) {
        val seats = mutableListOf<SeatReservation>()
        for (row in 1..hall.numRows!!) {
            for (col in 1..hall.numColumns!!) {
                seats.add(
                    SeatReservation(
                        numRow = row,
                        numCol = col,
                        client = null,
                        projection = null
                    )
                )
            }
        }

        val projection = MovieProjection(
            datetime = datetime,
            hall = hall,
            movie = movie,
            seats = seats
        )
        movieProjectionRepository.save(projection)
    }
}
