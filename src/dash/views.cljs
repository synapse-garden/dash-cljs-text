(ns dash.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash.util :as util]
            [dash.core :as core]
            ))

(defn view-switcher [cursor]
  (reify om/IRender (render [_]
    (let [current-view (first cursor)]
    ; Note: Need to have a style for invisible divs.
    ; Om functions only work on one root element so it must be a div to contain more elements.
    ; Alternatively, figure out how to work with seq or similar to not need a parent div.
    (dom/div nil
        (dom/button
          (if (= current-view 0)
            #js {:className "view-switcher" :disabled true}
            #js {:className "view-switcher" :onClick #(om/update! cursor [0] 0)})
          (str "View A"))
    
        (dom/button
          (if (= current-view 1)
            #js {:className "view-switcher" :disabled true}
            #js {:className "view-switcher" :onClick #(om/update! cursor [0] 1)})
          (str "View B"))

        (dom/button
          (if (= current-view 2)
            #js {:className "view-switcher" :disabled true}
            #js {:className "view-switcher" :onClick #(om/update! cursor [0] 2)})
          (str "View C"))
    )))))

(defn view-a [cursor]
  (reify om/IRender (render [_]
    (dom/div nil
      (dom/h1 nil "This is View A")
      (dom/h4 nil (str "(Also known in the atom as View " (str (get-in cursor [:view 0])) ")"))
      (dom/p nil "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")
      (om/build view-switcher (:view cursor))
      ))))

(defn view-b [cursor]
  (reify om/IRender (render [_]
    (dom/div nil
      (dom/h1 nil "This is View B")
      (dom/h4 nil (str "(Also known in the atom as View " (str (get-in cursor [:view 0])) ")"))
      (dom/p nil "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB")
      (om/build view-switcher (:view cursor))
      ))))

(defn view-c [cursor]
  (reify om/IRender (render [_]
    (dom/div nil
      (dom/h1 nil "This is View C")
      (dom/h4 nil (str "(Also known in the atom as View " (str (get-in cursor [:view 0])) ")"))
      (dom/p nil "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC")
      (om/build view-switcher (:view cursor))
      ))))

(defn views-view [cursor owner] ;META
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
