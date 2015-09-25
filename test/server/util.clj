(ns server.util
  (:require [cognitect.transit :as transit])
  (:import [java.io ByteArrayOutputStream]))

(defn write-out [data]
  (let [out (ByteArrayOutputStream. 4096)
        writer (transit/writer out :json)]
    (do
      (transit/write writer data)
      (.toString out))))
