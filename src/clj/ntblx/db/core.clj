(ns ntblx.db.core
  (:require
    [monger.core :as mg]
    [monger.collection :as mc]
    [monger.operators :refer :all]
    [mount.core :refer [defstate]]
    [ntblx.config :refer [env]])
  (:import org.bson.types.ObjectId))

(defstate db*
  :start (-> env :database-url mg/connect-via-uri)
  :stop (-> db* :conn mg/disconnect))

(defstate db
  :start (:db db*))

(defn create-user [user]
  (mc/insert db "users" (merge user {:_id (org.bson.types.ObjectId.)})))

(defn get-user [user]
  (mc/find-one-as-map db "users" user))

(defn create-source [source]
  (mc/insert db "sources" (merge source {:_id (org.bson.types.ObjectId.)})))

(defn get-source [source]
  (mc/find-one-as-map db "sources" source))
