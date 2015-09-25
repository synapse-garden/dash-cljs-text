(ns dash-test.tests.core
  (:require [dash.core :as dash-core]))

(def tests
  [{:should "return false given 'nil' and 'false' and true given anything else"
    :test-fn boolean
    :args [false nil 0 1 3.1415 "a"]
    :should-be [false false true true true true]
    :raw-fn '(dash.core/bool-check)}
   ])
