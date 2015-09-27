(ns dash.math
  (:require [clojure.string :as string]
            [cljs.reader :as reader]))

(defn lerp [a b x]
  "Linear interpolation between two values"
  (+ a (* x (- b a))))

(defn remap [aa ab ba bb value]
  "Remaps a number in one range to another range"
   (lerp ba bb (/ (- value aa) (- ab aa)))
)

(defn normalize [a b x]
  "Normalizes a number from an arbitrary range to 0-1"
  (remap a b 0 1 x))

(defn soft-clamp
  "Softly clamps all values to between -1 and 1"
  ([x tightness] (/ (Math/atan (* x (+ tightness 1))) Math/PI))
  ([x] (soft-clamp x 0))
  )

(defn soft-clamp-pos
  "Softly clamps all values to between 0 and 1"
  ([x tightness] (+ (* (soft-clamp x tightness) 0.5) 0.5))
  ([x] (soft-clamp-pos x 0))
  )

(defn additive-identity [x]
  "Returns an unchanged input using addition."
  (+ x 0))

(defn multiplicative-identity [x]
  "Returns an unchanged input using addition."
  (* x 1))
