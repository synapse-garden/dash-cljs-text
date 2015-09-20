(ns dash-test.util)

(defn- vec-of-vec? [v]
  (apply (every-pred vector?) v))

(defn- mapv-applicator [f]
  (fn [v] (apply f v)))

(defn run-test [f args]
  "Run a function f with the given args and return the results in a map."
  (let [f (if (vec-of-vec? args) (mapv-applicator f) f)]
    (mapv f args)))
