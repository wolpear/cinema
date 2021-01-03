package pl.karczewski.cinema.domain.client

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.*

@Entity
class Client(
    @Id
    @GeneratedValue
    var id: Long? = null,
    @Column(nullable = false, unique = false)
    var firstName: String,
    @Column(nullable = false, unique = false)
    var lastName: String,
    @Column(nullable = false, unique = true)
    var email: String,
    @Column(nullable = false, unique = false)
    @JsonIgnore
    var password: String,
)
