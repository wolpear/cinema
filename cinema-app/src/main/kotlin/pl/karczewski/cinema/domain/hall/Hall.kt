package pl.karczewski.cinema.domain.hall

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Hall(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    @Column(nullable = false, unique = true)
    var name: String?,
    @Column(nullable = false, unique = false)
    var numRows: Int?,
    @Column(nullable = false, unique = false)
    var numColumns: Int?,
)
