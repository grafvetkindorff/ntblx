(ns ntblx.env
  (:require
    [selmer.parser :as parser]
    [clojure.tools.logging :as log]
    [ntblx.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[ntblx started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[ntblx has shut down successfully]=-"))
   :middleware wrap-dev})
