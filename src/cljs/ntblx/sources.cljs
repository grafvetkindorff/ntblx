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

'(defn source-item []
   (let [editing (r/atom false)]
     (fn [{:keys [id done title]}]
       [:li {:class (str (if done "completed ")
                         (if @editing "editing"))}
        [:div.view
         [:input.toggle {:type "checkbox" :checked done
                         :on-change #(toggle id)}]
         [:label {:on-double-click #(reset! editing true)} title]
         [:button.destroy {:on-click #(delete id)}]]
        (when @editing
          [todo-edit {:class "edit" :title title
                      :on-save #(save id %)
                      :on-stop #(reset! editing false)}])])))

(defn source-item []
  (let [editing (r/atom false)]
    (fn [source]
      (let [name (get source "name" "")
            email (get source "email" "")
            tel (get source "phone" "")
            notes (get source "notes" "")]
        [:tr
         [:td [:div {:class "box"} [:content [:p [:strong name]] [:p [:small "email: " email " tel: " tel]]]]]
         [:td [:div {:class "box"} notes]]])
      )))

(defn sources-table [sources]
  [:section#table
   [:table {:class "table"}
    [:thead [:tr [:th "Info"] [:th "Notes"]]]
    [:tbody
     (for [source (filter not-empty (get sources "entries" {}))]
       [source-item source])]]])