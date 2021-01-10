package pl.karczewski.cinema.domain.hall

import org.springframework.stereotype.Service

@Service
class HallService(
    val hallRepository: HallRepository,
) {
    fun createHall(hallName: String, numRows: Int, numColumns: Int): Hall {
        val hall = Hall(
            id = null,
            name = hallName,
            numRows = numRows,
            numColumns = numColumns
        )
        hallRepository.save(hall)
        return hall
    }
}
