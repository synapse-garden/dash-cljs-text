(ns dash.core
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [dash.util :as dash-util]
            [clojure.string :as string]
            [om.core :as om :include-macros true]
            [cljs.reader :as reader]
            [goog.events :as events]
            [cljs.core.async :as async :refer [put! chan <!]]
            [ajax.core :as ajax :refer [GET]]))

(defn- upsert-item [state item & ks]
  "Insert or update an item the given atom at the given keys [ks].
   Item should be a map containing a :name string."
  (let [kw-name (dash-util/name-as-kw (:name item))]
    (swap! state
      (assoc-in @state (conj ks kw-name) (dash-util/ensure-has-id item)))))

(defn upsert-task [state task]
  "Insert or update a task in the given atom."
  (upsert-item state task :lists))

(defn- update! [state korks]
  (fn [result]
    (om/transact! state korks (fn [m] (merge m result)))))

(defn- err [{:keys [status status-text]}]
  (do
    (.log js/console "http request error: ")
    (.log js/console "    status: " status-text)
    (js/alert (str "HTTP request error: " status-text))))

(defn upsert-view [state new-view]
  "Change the view of the given atom"
  ;(swap! state #(assoc % :view new-view))
  (swap! state assoc :view [new-view])
  ;(upsert-item state new-view :view)
  )

(defn fetch-updates! [uri state & korks]
  "Return the map with any pending updates applied in the state at the
  set of keys korks e.g. [:data :user]."
    (GET uri
         :handler (update! state korks)
         :response-format (ajax/transit-response-format)
         :error-handler err))
