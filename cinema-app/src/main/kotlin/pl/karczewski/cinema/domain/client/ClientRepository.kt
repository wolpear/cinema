package pl.karczewski.cinema.domain.client

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : CrudRepository<Client, Long> {

    fun findByEmail(email: String): Client?
}
