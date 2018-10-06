module.exports = {
  entry: "./build/js/output.js",
  output: {
    filename: "main.bundle.js",
    path: __dirname + '/bin/bundle'
  },
  resolve: {
    modules: ["./node_modules", "./build/kotlin-js-min/main"]
  }
}