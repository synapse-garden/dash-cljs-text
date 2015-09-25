(ns dash.root
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash.views :as dash-views]
            [dash.core :as dash-core]
            [dash.util :as util]))

(enable-console-print!)

(defonce app-state (atom {}))
(def backend "http://localhost:3449/test/")

(om/root
 ;dash-views/lists-view
 dash-views/data-view
 app-state
 {:target (. js/document (getElementById "dash"))})

(dash-core/fetch-updates!
 (util/uri backend "get-data")
 :app
 (om/root-cursor app-state))
