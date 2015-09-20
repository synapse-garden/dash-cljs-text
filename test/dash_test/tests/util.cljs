(ns dash-test.tests.util
  (:require [dash.util :as dash-util]))

(def tests
  [{:should "format a name as suitable for :className"
    :test-fn dash-util/name-as-id
    :args ["Some complicated name" "that'sok?" "funky runes ᛋᚳ"]
    :should-be ["Some_complicated_name" "that_sok?" "funky_runes___"]
    :raw-fn '(dash.util/name-as-id)}

   {:should "format a name as a keyword"
    :test-fn dash-util/name-as-kw
    :args ["Some complicated name" "that'sok?" "funky runes ᛋᚳ"]
    :should-be [:Some_complicated_name :that_sok? :funky_runes___]
    :raw-fn '(dash.util/name-as-kw)}

   {:should "abbreviate a name"
    :test-fn dash-util/abbreviate
    :args ["Kevin Weber" "Bodie Solomon" "SingleWord" "Way more than two words"]
    :should-be ["KW" "BS" "S" "WW"]
    :raw-fn '(dash.util/abbreviate)}

   {:should "construct a URI given a root and a path"
    :test-fn dash-util/uri
    :args [["http://foo.com/" "bar"]
           ["http://bar.com/" "foo"]]
    :should-be ["http://foo.com/bar"
                "http://bar.com/foo"]
    :raw-fn '(dash.util/uri)}
   ])
