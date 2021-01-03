package pl.karczewski.cinema

import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import pl.karczewski.cinema.domain.client.Client
import pl.karczewski.cinema.domain.client.ClientRepository

@Component
class CommandLineAppStartUp(val repository: ClientRepository, val passwordEncoder: PasswordEncoder) : CommandLineRunner {

    override fun run(vararg args: String?) {
        repository.save(Client(null, "Jakub", "Karczewski", "wolpear@gmail.com", passwordEncoder.encode("password")))
    }
}
