package pl.karczewski.cinema.domain.client

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*
import pl.karczewski.cinema.common.InformationResponseBody

@RestController
@RequestMapping("/api/v1/clients")
class ClientController(val clientService: ClientService) {
    @GetMapping
    fun allClients(): List<Client> {
        return clientService.fetchAllClients()
    }

    @PostMapping
    fun createClient(@RequestBody clientBody: ClientBody): InformationResponseBody {
        return clientService.createClient(clientBody)
    }
}

data class ClientBody(
    val firstName: String,
    val lastName: String,
    val email: String,
    val plaintextPassword: String
) {
    fun toClient(passwordEncoder: PasswordEncoder): Client {
        return Client(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = passwordEncoder.encode(plaintextPassword)
        )
    }
}
