(ns ntblx.routes.auth
  (:require
   [ntblx.middleware :as middleware]
   [ring.util.http-response :as response]))

(defn auth-routes []
  [""
   {:middleware [middleware/wrap-formats]}
   ["/auth" {:get (fn [_]
            (-> (response/ok "Some text")
                (response/header "Content-Type" "text/plain; charset=utf-8")))}]])
