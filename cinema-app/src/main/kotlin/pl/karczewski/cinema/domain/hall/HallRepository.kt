package pl.karczewski.cinema.domain.hall

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HallRepository : CrudRepository<Hall, Long>
