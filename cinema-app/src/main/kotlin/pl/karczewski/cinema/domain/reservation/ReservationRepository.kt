package pl.karczewski.cinema.domain.reservation

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MovieProjectionRepository : CrudRepository<MovieProjection, Long>

@Repository
interface SeatReservationRepository : CrudRepository<SeatReservation, Long>
