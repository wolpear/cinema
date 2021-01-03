module.exports = {
  transpileDependencies: [
    'vuetify',
  ],
  devServer: {
    proxy: 'http://localhost:9000',
    watchOptions: {
      pool: true,
    },
  },
};
