package pl.karczewski.cinema.domain.client

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import pl.karczewski.cinema.common.InformationResponseBody

@Service
class ClientService(val repository: ClientRepository, val passwordEncoder: PasswordEncoder) {

    fun createClient(clientBody: ClientBody): InformationResponseBody {
        val client = clientBody.toClient(passwordEncoder)
        try {
            repository.save(client)
            return InformationResponseBody("Successfully created user with e-mail ${client.email}!")
        } catch (e: DataIntegrityViolationException) {
            throw UserWithEmailExistsException("User with e-mail ${client.email} already exists!")
        }
    }
}

@ResponseStatus(value = HttpStatus.CONFLICT)
data class UserWithEmailExistsException(
    override val message: String
) : RuntimeException(message)
