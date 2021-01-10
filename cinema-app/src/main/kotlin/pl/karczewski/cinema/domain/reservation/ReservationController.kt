package pl.karczewski.cinema.domain.reservation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.karczewski.cinema.common.InformationResponseBody
import java.security.Principal

@RestController
@RequestMapping("/api/v1")
class ReservationController(private val service: ReservationService) {
    @GetMapping("/projection/{projectionId}/seats")
    fun projectionSeats(@PathVariable projectionId: String): ProjectionSeatsDto {
        return service.getProjectionData(projectionId = projectionId.toLong())
    }

    @PostMapping("/seats")
    private fun reserveSeats(@RequestBody body: ReserveSeatsDto, principal: Principal): InformationResponseBody {
        return service.reserveSeats(body, principal)
    }
}

data class ReserveSeatsDto(
    val seatIds: List<Long>
)
