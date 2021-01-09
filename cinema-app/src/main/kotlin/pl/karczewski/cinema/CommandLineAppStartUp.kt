package pl.karczewski.cinema

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pl.karczewski.cinema.domain.client.ClientBody
import pl.karczewski.cinema.domain.client.ClientService
import pl.karczewski.cinema.domain.hall.HallService
import pl.karczewski.cinema.domain.movie.MovieService

@Component
class CommandLineAppStartUp(
    val clientService: ClientService,
    val movieService: MovieService,
    val hallService: HallService,
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

        hallService.createHall(hallName = "A", numRows = 8, numColumns = 12)
    }
}
