(ns hx-frame-routing.db
  (:require [cljs.spec.alpha :as s]))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; State
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def initial-state
  {:hx-frame-router {:route nil
                     :route-params nil
                     :route-query nil
                     :initialized false}})

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Getters / Setters
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-route-key [db] (get-in db [:hx-frame-router :route]))
(defn get-route-params [db] (get-in db [:hx-frame-router :route-params]))
(defn get-route-query [db] (get-in db [:hx-frame-router :route-query]))
(defn get-initialized [db] (get-in db [:hx-frame-router :initialized]))

;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Specs
;; ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(s/def :router/initialized boolean?)
(s/def :router/route (s/nilable keyword?))
(s/def :router/route-params (s/nilable map?))
(s/def :router/route-query (s/nilable map?))
(s/def ::router (s/keys :req-un [:router/route
                                 :router/route-params
                                 :router/route-query
                                 :router/initialized]))
