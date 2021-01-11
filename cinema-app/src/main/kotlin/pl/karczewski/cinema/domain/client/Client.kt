package pl.karczewski.cinema.domain.client

import com.fasterxml.jackson.annotation.JsonIgnore
import pl.karczewski.cinema.domain.reservation.SeatReservation
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = false)
    var firstName: String?,
    @Column(nullable = false, unique = false)
    var lastName: String?,
    @Column(nullable = false, unique = true)
    var email: String?,
    @Column(nullable = false, unique = false)
    @JsonIgnore
    var password: String?,
    @OneToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY, mappedBy = "client")
    var seatReservations: MutableList<SeatReservation>? = null
)
