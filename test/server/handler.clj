(ns server.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [cheshire.core :refer :all]))

(def test-data
  {:lists
      [{:list-name "Soon"
        :tasks [{:title "Get this working" :completed true :date "2015 / 01 / 12" :timedue "6pm"}
                {:title "Eat dinner" :completed false :date "Everyday" :timedue "8pm"}
                {:title "Bros Grumpout" :completed true :date "Everyday" :timedue "All the time"}]}

       {:list-name "Later"
        :tasks [{:title "Wake up" :completed false :date "Everyday" :timedue "8am-ISH"}
                {:title "Eat breakfast" :completed false :date "2015 / 01 / 12" :timedue "6pm"}
                {:title "Render some cool stuff" :completed false :date "Any day" :timedue "Any time"}
                {:title "Play some games" :completed false :date "Any day" :timedue "After work"}]}

       {:list-name "Future"
        :tasks [{:title "Be successful as fuck" :completed false :date "Someday" :timedue "n/a"}
                {:title "Complete Mindfork" :completed false :date "Someday" :timedue "n/a"}
                {:title "Complete Phoenix Engine" :completed false :date "Someday" :timedue "n/a"}
                {:title "Complete Dwarf Game" :completed false :date "Someday" :timedue "n/a"}
                {:title "Die happy" :completed false :date "On your last day" :timedue "At the end of your time"}]}]
    :user
      "Mind Forker"})

(defroutes test-app
  (GET "/" [] (generate-string test-data)))
;(route/not-found "<h1>Page not found</h1>")
