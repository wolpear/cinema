package pl.karczewski.cinema

import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component
import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.client.ClientRepository
import pl.karczewski.cinema.infrastructure.security.passwordEncoder

@Component
class CommandLineAppStartUp( val repository: ClientRepository): CommandLineRunner {

    override fun run(vararg args: String?) {
        println("dupa")
        repository.save(Client(null, "Jakub", "Karczewski", "wolpear@gmail.com", passwordEncoder().encode("password")))
        println("dupaaaaaaa")
    }
}