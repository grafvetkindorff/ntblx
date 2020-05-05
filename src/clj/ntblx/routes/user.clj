(ns ntblx.routes.auth
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [create-user]]
   [ring.util.http-response :as response]))

(defn user-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/user" {:post {:summary "Add a new user to the users collection"
                    :query-params {:firstname string?, :lastname string?, :email string?, :phone string?}
                    :responses {200 {:body ""}}}
                    :handler (fn [request] {:status 200 :body (str request)})}]])
