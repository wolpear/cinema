<template>
  <div>
    <v-row dense>
      <v-col>
        <v-card
            class="mx-auto"
            max-width="900"
        >
          <v-card>
            <v-card-title>
              {{ movie.title }}
            </v-card-title>
            <v-card-text>
              <v-row class="mb-3 mx-auto">
                <v-img :src="movie.posterPath" min-width="200" max-width="250"></v-img>
                <v-col>
                  {{ movie.overview }}
                </v-col>
              </v-row>

              <v-simple-table>
                <template v-slot:default>
                  <thead>
                    <tr>
                      <th>
                        Date
                      </th>
                      <th>
                        Free seats
                      </th>
                      <th>
                        Reserve
                      </th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="projection in projections" :key="projection.id">
                      <td>{{new Date(projection.datetime).toLocaleString(dateRegion)}}</td>
                      <td>
                        {{ projection.freeSeats }} of
                        {{ projection.hall.numColumns * projection.hall.numRows }}
                      </td>
                      <td>
                        <v-btn
                            small
                            :disabled="projection.freeSeats === 0"
                            :to="`/movie-reservation/${projection.id}`"
                        >
                          Reserve
                        </v-btn>
                      </td>
                    </tr>
                  </tbody>
                </template>
              </v-simple-table>
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
  name: 'MovieProjections',
  props: {
    movieId: String,
  },
  data: () => ({
    dateRegion,
    projections: [],
    movie: {},
  }),
  mounted() {
    Promise.all([
      http.get(`/api/v1/movies/${this.movieId}/projections`),
      http.get(`/api/v1/movies/${this.movieId}`),
    ]).then((results) => {
      this.projections = results[0].data.projections;
      this.movie = results[1].data;
    });
  },
  methods: {

  },
};
</script>

<style scoped>

</style>
