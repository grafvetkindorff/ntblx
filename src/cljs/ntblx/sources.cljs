(ns ntblx.sources
  (:require
   [day8.re-frame.http-fx]
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [re-frame.core :as rf]
   [goog.events :as events]
   [goog.history.EventType :as HistoryEventType]
   [markdown.core :refer [md->html]]
   [ntblx.ajax :as ajax]
   [ntblx.events]
   [reitit.core :as reitit]
   [reitit.frontend.easy :as rfe]
   [clojure.string :as string])
  (:import goog.History))

(defn source-item-person-cell [source]
  (let [selected     (r/atom false)
        name         (get source "name" "")
        position     (get source "position" "")
        email        (get source "email" "")
        tel          (get source "phone" "")
        social_media (get source "social_media" "")]
    (fn []
      [:div {:class "tile is-child box"
             :style {:background-color (if @selected "#A07178" "#E6CCBE")}
             :on-click #(swap! selected not)
             :on-double-click #(println "Box Clicked!")}
       [:p {:class "title"} [:strong name]]
       [:p {:class "subtitle"} [:em position]]
       [:p [:small "email: " email]]
       [:p [:small "tel: " tel]]
       [:p [:small "social media: " social_media]]])))

(defn source-item-media-cell [source]
  (let [selected     (r/atom false)
        media        (get source "media" "")
        type         (get source "type" "")
        page         (get source "site" "")]
    (fn []
      [:div {:class "tile is-child is-3 box"
             :style {:background-color (if @selected "#A07178" "#C8CC92")}
             :on-click #(swap! selected not)
             :on-double-click #(println "Box Clicked!")}
       [:p {:class "title"} media]
       [:p {:class "subtitle"} type]
       [:p {:class "subtitle"} page]])))

(defn source-item-notes-cell [source]
  (let [selected     (r/atom false)
        notes        (get source "notes" "")]
    (fn []
      [:div {:class "tile is-child box"
             :style {:background-color (if @selected "#A07178" "#E6CCBE")}
             :on-click #(swap! selected not)
             :on-double-click #(println "Box Clicked!")}
       [:content notes]])))

(defn source-item-row []
  (let [editing (r/atom false)]
    (fn [source]
      [:div {:class "tile is-parent" :style {:padding "10px"}}
       [source-item-media-cell source]  [:div {:style {:padding "20px"}}]
       [source-item-person-cell source] [:div {:style {:padding "5px"}}]
       [source-item-notes-cell source]
       ])))

(defn sources-table [sources]
  [:section#table
   [:div {:class "tile is-ancestor"}]
    (for [source (filter not-empty (get sources "entries" {}))]
      [source-item-row source])])