(ns dash-test.tests.math
  (:require [dash.math :as dash-math]))

(def tests
  [{:should "return an unchanged input using addition."
    :test-fn dash-math/additive-identity
    :args [0 1 -1 31.8532 -1000]
    :should-be [0 1 -1 31.8532 -1000]
    :raw-fn '(dash.math/additive-identity)}

   {:should "return an unchanged input using multiplication."
    :test-fn dash-math/multiplicative-identity
    :args [0 1 -1 0.323232 -40.9123]
    :should-be [0 1 -1 0.323232 -40.9123]
    :raw-fn '(dash.math/multiplicative-identity)}

    {:should "linearly interpolate between two values."
   	 :test-fn dash-math/lerp
   	 :args [[0 1 0.5] [8 12 0.75] [0 100 0.33] [-10 -20 0.5] [20 10 0.25] [50 100 -0.5]]
   	 :should-be [0.5 11 33 -15 17.5 25]
   	 :raw-fn '(dash.math/lerp)}

   	{:should "remap a number in one range to another range."
   	 :test-fn dash-math/remap
   	 :args [[0 1 50 100 0.5] [10 20 30 40 10] [5 7 100 300 4] [0 -40 100 140 -10] [10 20 4 5 12]]
   	 :should-be [75 30 0 110 4.2]
   	 :raw-fn '(dash.math/remap)}

   	{:should "remap a number in one range to between the range of zero and one."
   	 :test-fn dash-math/normalize
   	 :args [[0 1 0.5] [1 2 1.75] [-10 -110 -90] [-20 20 10] [-20 20 -100]]
   	 :should-be [0.5 0.75 0.8 0.75 -2.0]
   	 :raw-fn '(dash.math/normalize)}

   	{:should "softly clamp an input to between negative one and one with specified tightness."
   	 :test-fn #(= (<= 1 (dash-math/soft-clamp %)) (>= -1 (dash-math/soft-clamp %)))
   	 :args [100 0 -1000 [200000 0.8] [-800000 -0.5]]
   	 :should-be [true true true true true]
   	 :raw-fn '(dash.math/soft-clamp)}

   	{:should "softly clamp an input to between zero and one with specified tightness."
   	 :test-fn #(= (<= 1 (dash-math/soft-clamp-pos %)) (>= 0 (dash-math/soft-clamp-pos %)))
   	 :args [100 0 -1000 [200000 0.8] [-800000 0.1]]
   	 :should-be [true true true true true]
   	 :raw-fn '(dash.math/soft-clamp-pos)}
   ])
