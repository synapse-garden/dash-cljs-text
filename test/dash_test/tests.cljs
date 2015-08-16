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
               :args {}
               :should-be "Get this working"
               :raw-fn '(dash.core/fetch-updates)}
             ]}
    ])
