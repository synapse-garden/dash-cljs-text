(ns server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]
            [server.data :as data]
            [server.util :as util]))

(defroutes test-app
<<<<<<< HEAD
  (GET "/test/get-data" [] (util/write-out data/basic-data))
  (GET "/test/get-basic" [req] (do (println (str req)) (util/write-out "Hello"))))
=======
  (GET "/test/test-data" [] (generate-string test-data)))
;(route/not-found "<h1>Page not found</h1>")
>>>>>>> WIP http GET testing
