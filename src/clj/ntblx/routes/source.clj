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
          (-> (response/ok (write-value-as-string {:entries entries}))
              (response/header "Content-Type" "application/json"))))}

     :post
     {:handler
      (fn [request]
        (do
          (let [params (:body-params request)]
            (if (empty? params)
              (println "Empty params")
              (insert-entry params "sources")))
          (-> (response/ok))))}}]])
