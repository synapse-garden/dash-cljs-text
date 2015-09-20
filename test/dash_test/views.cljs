(ns dash-test.views
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [dash-test.util :as util]))

(defn test-view [test-case owner]
  "test-view renders a single test div with class 'passed or 'failed."
  (reify
    om/IRender (render [_]
      (let [{:keys [id should test-fn should-be raw-fn args]} test-case
            test-fn #(mapv test-fn %)
            result (test-fn args)]
        (dom/div
         (if (= should-be result)
           #js {:className "passed"}
           #js {:className "failed"})
         (dom/h3 #js {:className "test-name"} (str "Test " id))
         (dom/h2 #js {:className "test-desc"} (str raw-fn " should " should))
         (dom/ul nil
          ;(dom/li #js {:className "test-fn"} (str "Tests " raw-fn))
           (if args (dom/li #js {:className "test-args"} (str "Input — " args)) "")
           (dom/li #js {:className "test-result"} (str "Output — " result))
           (dom/li #js {:className "test-should-be"} (str "Expected Output — " should-be))))))))

(defn tests-view [nsp-tests owner]
  "tests-view renders a vector of test maps.  Each should have a :nsp string
   and :tests vector."
  (reify
    om/IRender (render [_]
      (let [{:keys [nsp tests]} nsp-tests]
        (dom/div #js {:id (str "tests-" nsp) :className "ns-view"}
          (dom/h2 #js {:className "ns-title"} (str nsp " Tests"))
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
