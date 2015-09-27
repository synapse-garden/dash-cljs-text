(ns dash-test.all-tests
  (:require [dash.core :as dash-core]
            [dash-test.tests.util :as util-tests]
            [dash-test.tests.core :as core-tests]))

(def tests
  [{:nsp "dash.util"
    :tests util-tests/tests}
   {:nsp "dash.core"
    :tests core-tests/tests}])
