(ns hx-frame-routing.subscriptions
  (:require [hx-frame.core :as hx-frame]))

(defn initialized
  [{:keys [hx-frame-router]} _] (:initialized hx-frame-router))

(defn route
  [{:keys [hx-frame-router]} _] (:route hx-frame-router))

(defn route-params
  [{:keys [hx-frame-router]} _] (:route-params hx-frame-router))

(defn route-query
  [{:keys [hx-frame-router]} _] (:route-query hx-frame-router))

(defn register-subscriptions
  "Register hx-frame-routing subscriptions"
  []
  (hx-frame/register-subscription :router/initialized initialized)
  (hx-frame/register-subscription :router/route route)
  (hx-frame/register-subscription :router/route-params route-params)
  (hx-frame/register-subscription :router/route-query route-query))
