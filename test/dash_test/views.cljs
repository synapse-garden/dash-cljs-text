(ns dash-test.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [clojure.string :as string]
            [dash.util :as util]
            [dash-test.util :as test-util]))

(defn no-cursor-test [test-case owner]
  (reify
    om/IRender (render [_]
      (let [{:keys [id should test-fn should-be raw-fn args]} test-case
            fn-name (-> raw-fn (string/split #"/") last reverse rest reverse string/join str)
            results (test-util/run-test! test-fn args)
            num-cases (count args)]
        (dom/div
        (if (= should-be results)
          #js {:className "passed"}
          #js {:className "failed"})
        (dom/h3 #js {:className "test-name"} fn-name )
        (dom/p #js {:className "test-desc"} (str "Should " should))
        (dom/img (if (= should-be results)
          #js {:src "img/check.png" :className "status-image"}
          #js {:src "img/x.png" :className "status-image"}))

        (dom/table #js {:className "test-table"}
          (dom/tr #js {:className "test-row-titles"}
            (dom/td #js {:className "test-col"} "Input")
            (dom/td #js {:className "test-col"} "Output")
            (dom/td #js {:className "test-col"} "Expected"))

        (for [case (range num-cases)]
          (dom/tr {:className "test-row"}
            (dom/td #js {:className "test-col"} (str (nth args case)))
            (dom/td #js {:className "test-col"} (str (nth results case)))
            (dom/td #js {:className "test-col"} (str (nth should-be case)))
          ))))))))

(defn test-view [test-case owner]
  "test-view renders a single test div with class 'passed or 'failed."
  (reify
    om/IRender (render [_]
      (om/build no-cursor-test test-case))))

(defn tests-view [nsp-tests owner]
  "tests-view renders a vector of test maps.  Each should have a :nsp string
   and :tests vector."
  (reify
    om/IRender (render [_]
      (let [{:keys [nsp tests]} nsp-tests
            num-tests (count tests)]
        (dom/div #js {:id (str "tests-" nsp) :className "ns-view"}
          (dom/h2 #js {:className "ns-title"} (str nsp " — " num-tests (util/pluralize " Test" num-tests)))
          (apply dom/ul nil
            (om/build-all test-view tests)))))))

(defn ns-tests-view [all-tests owner]
  "ns-tests-view renders all tests-views for all test namespaces."
  (reify
    om/IRender (render [_]
      (let [all (:tests all-tests)
            num-views (count all)]
        (apply dom/div #js {:className "ns-views"}
          (dom/h3 #js {:id "test-title"} (str "Tests View"" — " num-views (util/pluralize" Namespace" num-views)))
          (om/build-all tests-view all))))))
