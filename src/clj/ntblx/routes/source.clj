(ns ntblx.routes.source
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [insert-entry what-return]]
   [jsonista.core :refer [write-value-as-string]]
   [ring.util.http-response :as response]))

(defn source-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/source"
    {:get
     {:handler
      (fn [request]
        (let [entries (what-return (:query-params request) "sources")]
          {:status 200 :body (write-value-as-string {:entries entries})}))}

     :post
     {:handler
      (fn [request]
        (do (insert-entry (:body-params request) "sources")
            {:status 200 :body (str (:body-params request))}))}}]])
