(ns ntblx.routes.user
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [create-user get-user]]
   [ring.util.http-response :as response]))

(defn user-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/user"
    {:get {:summary "Get information about user"
           :query-params {:firstname string?}
           :responses {200 {:body ""}}
           :handler
           (fn [request]
             (let [user_info (dissoc (get-user (:query-params request)) :_id)]
               {:status 200 :body (str user_info)}))}
     :post {:summary "Add a new user to the users collection"
            :query-params {:firstname string?, :lastname string?, :email string?, :phone string?}
            :responses {200 {:body ""}}
            :handler
            (fn [request]
              (do (create-user (:query-params request))
                  {:status 200 :body (str (:query-params request))}))}}]])
