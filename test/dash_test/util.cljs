(ns dash-test.util
  (:require [om.core :as om :include-macros true]))

(defn- vec-of-vec? [v]
  (apply (every-pred vector?) v))

(defn- mapv-applicator [f]
  (fn [v] (apply f v)))

(defn run-test! [f args]
  "Run a function f with the given args and return the results in a map.
  If the first arg is a cursor, f is expected to transact or update the
  cursor."
  (let [f (if (vec-of-vec? args) (mapv-applicator f) f)]
    (mapv f args)))
