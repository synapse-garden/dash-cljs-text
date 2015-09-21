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

(defn with-cursors [args state]
  "Takes a vector of args and a state cursor.  Creates a new :results vector
  in state, containing nils.  Appends each arg with an Om cursor into the
  results vector in state."
  (if-not (contains? state :results)
    (do
      (om/update! state :results (into [] (take (count args) (repeat nil))))
      (for [[arg i result] (map vector args (iterate inc 0) (get state :results))]
        (conj arg (get result i))))))
