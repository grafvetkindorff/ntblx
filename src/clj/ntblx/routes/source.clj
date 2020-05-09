(ns ntblx.routes.source
  (:require
   [ntblx.middleware :as middleware]
   [ntblx.db.core :refer [create-source get-source]]
   [ring.util.http-response :as response]))

(defn source-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/source"
    {:get {:summary "Get source"
           :query-params {:firstname string?}
           :responses {200 {:body ""}}
           :handler
           (fn [request]
             (do (get-source (:query-params request))
                 {:status 200 :body (str (:query-params request))}))}
     :post {:summary "Add a new source to the sources collection"
            :query-params {:media string?, :site string?, :type string?, :department string?, :position string?
                           :name string?, :phone string?, :email string?, :social_media string? :notes string?}
            :responses {200 {:body ""}}
            :handler
            (fn [request]
              (do (create-source (:query-params request))
                  {:status 200 :body (str (:query-params request))}))}}]])
