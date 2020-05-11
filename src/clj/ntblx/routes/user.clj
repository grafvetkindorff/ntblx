(ns ntblx.routes.user
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [insert-entry get-entry get-all]]
   [cheshire.core :refer [generate-string]]
   [ring.util.http-response :as response]))

(defn what-return [request]
  (let [params (:query-params request)]
    (if (empty? params)
      (map #(dissoc % :_id) (get-all "users"))
      (dissoc (get-entry "users" params) :_id))))

(defn user-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/user"
    {:get {:summary "Get information about user"
           :query-params {:firstname string?}
           :responses {200 {:body ""}}

           :handler
           (fn [request]
             (let [user_info (generate-string (what-return request))]
               {:status 200 :body (str user_info)}))}

     :post {:summary "Add a new user to the users collection"
            :query-params {:firstname string?, :lastname string?, :email string?, :phone string?}
            :responses {200 {:body ""}}

            :handler
            (fn [request]
              (do (insert-entry (:query-params request) "users")
                  {:status 200 :body (str (:query-params request))}))}}]])
