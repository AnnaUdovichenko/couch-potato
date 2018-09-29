module.exports = {
  entry: "./build/js/output.js",
  output: {
    filename: "main.bundle.js",
    path: require('path').resolve(__dirname, './build/bundle')
  },
  resolve: {
    modules: ["./node_modules", "./build/kotlin-js-min/main"]
  }
}