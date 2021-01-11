<template>
  <div>
    <v-row dense>
      <v-col>
        <v-card
            class="mx-auto"
            max-width="1000"
        >
          <v-card-title style="word-break: keep-all">
            My reservations
          </v-card-title>
          <v-card-text>
            <div v-if="projections.length === 0">
              No reservations :(
            </div>
            <v-simple-table v-else dense>
              <template v-slot:default>
                <thead>
                <tr>
                  <th>
                    Movie
                  </th>
                  <th>
                    Time
                  </th>
                  <th>
                    Hall
                  </th>
                  <th>
                    Seats
                  </th>
                </tr>
                </thead>
                <tbody>
                <tr
                    v-for="projection in projections"
                    :key="projection.id"
                >
                  <td>{{ projection.movie.title }}</td>
                  <td>{{ new Date(projection.datetime).toLocaleString(dateRegion) }}</td>
                  <td>{{ projection.hall.name }}</td>
                  <td>
                    <ul>
                      <li v-for="seat in projection.seats" :key="seat.id">
                        Row: {{ seat.numRow }}, Column: {{ seat.numCol }}
                      </li>
                    </ul>
                  </td>
                </tr>
                </tbody>
              </template>

            </v-simple-table>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import http from '@/common/http';
import dateRegion from '@/common/formats';

export default {
  name: 'MovieReservations',
  data: () => ({
    dateRegion,
    projections: [],
  }),
  mounted() {
    this.loadReservations();
  },
  methods: {
    loadReservations() {
      http.get('/api/v1/reservations').then((response) => {
        this.projections = response.data.projections;
      });
    },
  },
  computed: {
  },
};
</script>

<style scoped>

</style>
