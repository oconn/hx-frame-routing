(ns hx-frame-routing.utils
  (:require [bidi.bidi :as bidi]
            [goog.object :as gobj]))

(defonce app-routes (atom nil))

(defn path-for
  [route & args]
  (apply (partial bidi/path-for @app-routes route) args))

(defn get-active-route
  []
  (bidi/match-route @app-routes
                    (gobj/getValueByKeys js/window "location" "pathname")))
