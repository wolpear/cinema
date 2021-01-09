<template>
  <div>
    <v-row dense>
      <v-col v-for="movie in movies" :key="movie.id">
        <v-card
            class="mx-auto"
            max-width="240"
        >
          <v-img
              max-width="300"
              min-width="200"
              :src="movie.posterPath"
              class="mx-auto"
          ></v-img>
          <v-card-title style="word-break: keep-all">
            {{ movie.title }}
          </v-card-title>
          <v-card-text>
            <v-row
              class="align-center mx-auto"
            >
              <v-rating
                  :value="movie.voteAverage / 2"
                  length="5"
                  color="primary"
                  size="12"
                  readonly
                  half-increments
              ></v-rating>
              <span class="text--darken-2 caption">
                ({{ movie.voteAverage }})
              </span>
            </v-row>
            <v-row class="mt-5 mx-auto">
              {{ movie.overview }}
            </v-row>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import http from '@/common/http';

export default {
  name: 'Home',
  data: () => ({
    loading: true,
    movies: [],
  }),
  mounted() {
    this.loadMovies();
  },
  methods: {
    loadMovies() {
      http.get('/api/v1/movies').then((response) => {
        this.movies = response.data;
      });
    },
    readMoreClicked(movie) {
      // eslint-disable-next-line no-param-reassign
      movie.readMoreActive = !movie.readMoreActive;
    },
  },
};
</script>

<style scoped>

</style>
