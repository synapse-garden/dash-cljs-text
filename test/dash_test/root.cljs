(ns dash-test.root
  (:require [om.core :as om :include-macros true]
            [dash-test.core :as core]
            [dash-test.views :as views]
            [dash-test.util :as util]))

(enable-console-print!)

(defonce test-state (atom {}))

(core/refresh-tests! test-state)

(om/root
  views/ns-tests-view
  test-state
  {:target (. js/document (getElementById "tests"))})
