(ns dash-test.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash-test.util :as util]))

(defn no-cursor-test [test-case owner]
  (reify
    om/IRender (render [_]
      (let [{:keys [id should test-fn should-be raw-fn args]} test-case
            results (util/run-test! test-fn args)]
        (dom/div
        (if (= should-be results)
          #js {:className "passed"}
          #js {:className "failed"})
        (dom/h3 #js {:className "test-name"} (str "Test " id " — " raw-fn))
          (dom/h2 #js {:className "test-desc"} (str "Should " should))
          (dom/img (if (= should-be (test-fn args))
            #js {:src "img/check.png" :className "status-image"}
            #js {:src "img/x.png" :className "status-image"}))

        (dom/table #js {:className "test-table"}
          (if args 
          (dom/tr #js {:className "test-row"}
           (dom/td #js {:className "test-row-title"} "Input")
           (dom/td #js {:className "test-row-data"} args)))
          (dom/tr #js {:className "test-row"}
           (dom/td #js {:className "test-row-title"} "Output")
           (dom/td #js {:className "test-row-data"} (test-fn args)))
          (dom/tr #js {:className "test-row"}
           (dom/td #js {:className "test-row-title"} "Expected")
           (dom/td #js {:className "test-row-data"} should-be))))))))

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
      (let [{:keys [nsp tests]} nsp-tests]
        (dom/div #js {:id (str "tests-" nsp) :className "ns-view"}
          (dom/h2 #js {:className "ns-title"} (str nsp ""))
          (apply dom/ul nil
            (om/build-all test-view tests)))))))

(defn ns-tests-view [all-tests owner]
  "ns-tests-view renders all tests-views for all test namespaces."
  (reify
    om/IRender (render [_]
      (let [all (:tests all-tests)]
        (apply dom/div #js {:className "ns-views"}
          (dom/h3 #js {:id "test-title"} "Testing View")
          (om/build-all tests-view all))))))
