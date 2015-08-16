(ns dash-test.tests
  (:require [dash.core :as dash-core]
            [dash.util :as dash-util]))

(defn tests []
    [{:nsp "dash.util"
      :tests [{:should "format a name as suitable for :className"
               :test-fn #(mapv dash-util/name-as-id %)
               :args ["Some complicated name" "that'sok?" "funky runes ᛋᚳ"]
               :should-be ["Some_complicated_name" "that_sok?" "funky_runes___"]
               :raw-fn '(dash.util/name-as-id)}

              {:should "format a name as a keyword"
               :test-fn #(mapv dash-util/name-as-kw %)
               :args ["Some complicated name" "that'sok?" "funky runes ᛋᚳ"]
               :should-be [:Some_complicated_name :that_sok? :funky_runes___]
               :raw-fn '(dash.util/name-as-kw)}
             ]}

     {:nsp "dash.core"
      :tests [{:should "retrieve a test map"
               :test-fn #(-> (dash-core/fetch-updates %) :lists :Soon :tasks :Get_this_working :title)
               :args ["http://localhost:3449/test/test-data" (atom {})]
               :should-be "Get this working"
               :raw-fn '(dash.core/fetch-updates)}

              {:should "return false given 'nil' and 'false' and true given anything else"
               :test-fn #(mapv boolean %)
               :args [false nil 0 1 3.1415 "a"]
               :should-be [false false true true true true]
               :raw-fn '(dash.core/bool-check)}
             ]}
    ])
