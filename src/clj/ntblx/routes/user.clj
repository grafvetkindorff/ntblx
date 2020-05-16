(ns ntblx.routes.user
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [insert-entry what-return]]
   [jsonista.core :refer [write-value-as-string]]
   [ring.util.http-response :as response]))

(defn user-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/user"
    {:get
     {:handler
      (fn [request]
        (let [entries (what-return (:query-params request) "users")]
          (-> (response/ok (write-value-as-string {:entries entries}))
              (response/header "Content-Type" "application/json"))))}

     :post
     {:handler
      (fn [request]
        (do (insert-entry (:body-params request) "users")
            (-> (response/ok))))}}]])
