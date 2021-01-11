package pl.karczewski.cinema

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pl.karczewski.cinema.domain.client.ClientBody
import pl.karczewski.cinema.domain.client.ClientService
import pl.karczewski.cinema.domain.hall.HallService
import pl.karczewski.cinema.domain.movie.MovieService
import pl.karczewski.cinema.domain.reservation.ReservationService
import java.time.LocalDateTime
import java.time.Month

@Component
private class CommandLineAppStartUp(
    val clientService: ClientService,
    val movieService: MovieService,
    val hallService: HallService,
    val reservationService: ReservationService
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        movieService.loadMultipleMoviesPagesFromMovieDBToLocalDBCache()

        clientService.createClient(
            ClientBody(
                firstName = "Jakub",
                lastName = "Karczewski",
                email = "123@wp.pl",
                plaintextPassword = "123"
            )
        )

        clientService.createClient(
            ClientBody(
                firstName = "Adam",
                lastName = "Testowy",
                email = "adam@wp.pl",
                plaintextPassword = "adam"
            )
        )

        val hall = hallService.createHall(hallName = "A", numRows = 8, numColumns = 12)
        reservationService.createProjection(
            movie = movieService.fetchAllMovies()[0],
            hall = hall,
            datetime = LocalDateTime.of(2021, Month.JANUARY, 20, 14, 20)
        )

        reservationService.createProjection(
            movie = movieService.fetchAllMovies()[0],
            hall = hall,
            datetime = LocalDateTime.of(2021, Month.JANUARY, 20, 21, 30)
        )

        reservationService.createProjection(
            movie = movieService.fetchAllMovies()[1],
            hall = hall,
            datetime = LocalDateTime.of(2021, Month.JANUARY, 20, 12, 0,0)
        )

        for (seatId in 30..34) {
            reservationService.reserveSeat(seatId = seatId.toLong(), clientService.fetchClient(2))
        }
    }
}
