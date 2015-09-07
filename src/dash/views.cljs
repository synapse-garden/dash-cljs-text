(ns dash.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash.util :as util]
            [dash.core :as core]))

(defn view-switcher [state]
  (reify om/IRender (render [_]
    (let [current-view (:view state)]
    (dom/button (if-not (= current-view 0)
                #js {:className "view-switcher" :onClick (core/upsert-view state 0)}
                #js {:className "view-switcher-disabled" :onClick nil})
                "View A")
    (dom/button (if-not (= current-view 1)
                #js {:className "view-switcher" :onClick (core/upsert-view state 1)}
                #js {:className "view-switcher-disabled" :onClick nil})
                "View B")
    (dom/button (if-not (= current-view 2)
                #js {:className "view-switcher" :onClick (core/upsert-view state 2)}
                #js {:className "view-switcher-disabled" :onClick nil})
                "View C")
    ))))

(defn view-a [state]
  (reify om/IRender (render [_]
    (dom/h2 nil "This is View A")
    (dom/p nil "AAAAAAAAAAAA")
    (om/build view-switcher (state)))))

(defn view-b [state]
  (reify om/IRender (render [_]
    (dom/h2 nil "This is View B")
    (dom/p nil "BBBBBBBBBBBB")
    (om/build view-switcher (state)))))

(defn view-c [state]
  (reify om/IRender (render [_]
    (dom/h2 nil "This is View C")
    (dom/p nil "CCCCCCCCCCCC")
    (om/build view-switcher (state)))))

(defn views-view [state owner] ;META
  (reify om/IRender (render [_]
    (dom/div #js {:id "test-container"}
      (dom/h3 nil "Login Test View")
      (om/build login-view ())))))

(defn list-view [this-list owner]
  (reify
    om/IRender (render [_]
      (let [{:keys [list-name tasks]} this-list]
        (dom/div #js {:id (str "list-" (dash-util/name-as-id list-name))}
          (dom/h3 #js {:className "list-title"} (str list-name))
          (apply dom/ul #js {:className "list-contents"}
            (map
               (fn [task] (let [{:keys [title completed date timedue]} task]
                 (dom/li
                   (if completed
                     #js {:className "task-completed"}
                     #js {:className "task-incomplete"})
                    (str title)
                    (dom/div #js {:className "info-date"}
                     date)
                    (dom/div #js {:className "info-timedue"}
                     timedue)
                     ))) tasks)))))))

(defn lists-view [app owner]
  (reify
    om/IRender (render [_]
      (dom/div #js {:id "lists-view"}
        (dom/h2 #js {:id "lists-view_title"} (str (:user app) "'s Todo Lists"))
        (apply dom/div #js {:className "list-view"}
          (om/build-all list-view (:lists app)))))))

(defn data-view [app owner]
  "A simple root Om view which renders the given app atom."
  (reify
    om/IRender (render [_]
      (dom/div nil (str @app)))))
