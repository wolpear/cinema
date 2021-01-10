<template>
  <div>
    <v-row dense>
      <v-col>
        <v-card
            class="mx-auto"
        >
          <v-card>
            <v-card-title>
              Reserving: {{ movie.title }}
              at {{new Date(this.projection.datetime).toLocaleString(dateRegion)}}
            </v-card-title>
            <v-card-text>
              <v-row
                align="center"
                justify="center"
              >
                <div class="cinema-screen">
                  Screen
                </div>
              </v-row>
              <v-row v-for="row in seats" :key="row[0].id" class="mx-auto mt-5">
                <v-col v-for="seat in row" :key="seat.id">
                  <v-row
                      align="center"
                      justify="center"
                      class="mx-auto"
                      :style="`border: solid black 1px; background-color: ${getSeatStyle(seat)}`"
                      @click="flipSeatState(seat)"
                  >
                    {{ seat.numRow }}
                  </v-row>
                </v-col>
              </v-row>
              <v-row class="mx-auto mt-5">
                <v-col class="mx-auto">
                  <div v-if="emptyReservation" class="red--text">
                    No seats have been chosen!
                  </div>
                  <div v-else>
                    <div>List of seats to reserve</div>
                    <v-simple-table  dense>
                      <template v-slot:default>
                        <thead>
                        <tr>
                          <th class="text-left">
                            Row
                          </th>
                          <th class="text-left">
                            Column
                          </th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr
                            v-for="seat in seatsToReserve"
                            :key="seat.id"
                        >
                          <td>{{ seat.numRow }}</td>
                          <td>{{ seat.numCol }}</td>
                        </tr>
                        </tbody>
                      </template>
                    </v-simple-table>
                  </div>
                </v-col>
              </v-row>
              <v-row class="mx-auto mt-2">
                <v-col class="mx-auto">
                  <v-btn :disabled="emptyReservation || !!success" @click="reserveSeats" small>
                    Compete reservation
                  </v-btn>
                </v-col>
              </v-row>
              <v-row class="mt-2">
                <v-alert
                    v-if="this.success"
                    type="success"
                    border="left"
                    text
                    dense
                >
                  {{ this.success }}
                  Redirecting to main page
                </v-alert>
                <v-alert
                    v-if="this.error"
                    type="error"
                    border="left"
                    text
                    dense
                >
                  {{ this.error }}
                </v-alert>
              </v-row>
            </v-card-text>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import http from '@/common/http';
import dateRegion from '@/common/formats';

export default {
  name: 'MovieReservation',
  props: {
    projectionId: String,
  },
  data: () => ({
    success: '',
    error: '',
    dateRegion,
    movie: {},
    projection: {},
    seats: [],
  }),
  mounted() {
    http.get(`/api/v1/projection/${this.projectionId}/seats`)
      .then((response) => {
        this.movie = response.data.movie;
        this.projection = response.data.projection;
        const seats = response.data.seats.map((seat) => {
          const newSeat = seat;
          newSeat.toReserve = false;
          return newSeat;
        });
        const numRows = seats.map((seat) => seat.numRow).reduce((a, b) => Math.max(a, b));
        const rearangedSeats = new Array(numRows).fill(null).map(() => []);
        seats.forEach((seat) => {
          rearangedSeats[seat.numRow - 1].push(seat);
        });
        this.seats = rearangedSeats;
      });
  },
  methods: {
    getSeatStyle(seat) {
      if (seat.taken) { return '#e70505'; }
      if (seat.toReserve) { return '#ffcc00'; }
      return '#33cc33';
    },
    flipSeatState(seat) {
      if (!seat.taken) {
        // eslint-disable-next-line no-param-reassign
        seat.toReserve = !seat.toReserve;
      }
    },
    reserveSeats() {
      const seatIds = this.seatsToReserve.map((seat) => seat.id);
      http.post(
        '/api/v1/seats',
        { seatIds },
      ).then((response) => {
        this.success = response.data.message;
        setTimeout(this.redirectToHome, 3000);
      }).catch((error) => {
        this.error = error.response.data.message;
      });
    },
    redirectToHome() {
      this.$router.push({ name: 'Home' });
    },
  },
  computed: {
    seatsToReserve() {
      return this.seats.flatMap((seat) => seat).filter((seat) => seat.toReserve);
    },
    emptyReservation() {
      return this.seatsToReserve.length === 0;
    },
  },
};
</script>

<style scoped>
.cinema-screen {
  border: black solid 1px;
  background-color: darkgray;
  font-weight: bold;
  min-width: 60%;
  text-align: center;
}
</style>
