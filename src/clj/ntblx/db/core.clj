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

(defn insert-entry [entry collection]
  (mc/insert db collection (merge entry {:_id (org.bson.types.ObjectId.)})))

(defn get-entry [entry collection]
  (mc/find-one-as-map db collection entry))

(defn get-all [collection] (mc/find-maps db collection))