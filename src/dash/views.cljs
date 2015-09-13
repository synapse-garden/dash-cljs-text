(ns dash.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash.util :as util]
            [dash.core :as core]
            ))

(defn tab-view [cursor]
  (reify om/IRender (render [_]
    (let [cur-view (first (:current-tab cursor))
          tabs (:tab-list cursor)
          num-tabs (count tabs)]
    (dom/div #js {:className "tab-view"}
        (dom/div #js {:className "tab-bar hidden-div"}
          (for [i (range 0 num-tabs)]
            (dom/button
            (if (= cur-view i)
              #js {:className (str "tab numtabs-" num-tabs) :disabled true}
              #js {:className (str "tab numtabs-" num-tabs) :onClick #(om/update! (:current-tab cursor) [0] i)})
            (str "Tab " (+ i 1)))))

        (om/build (nth tabs cur-view) nil)
    )))))

(defn canvas-view [cursor]
  (reify om/IRender (render [_]
    (let []
    (dom/div nil 
      (dom/canvas nil #js{:script "
        var c = document.getElementById('myCanvas');
        var ctx = c.getContext('2d');
        ctx.beginPath();
        ctx.arc(95,50,40,0,2*Math.PI);
        ctx.stroke();
        "})
    )))))

(defn dash-loading []
  (reify om/IRender (render [_]
  (dom/div #js {:className "dash-loading"} "Loading - Please Wait!"))))

(defn root-view [cursor owner] ;META
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
