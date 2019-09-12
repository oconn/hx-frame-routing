(ns hx-frame-routing.utils
  (:require [bidi.bidi :as bidi]))

(defonce app-routes (atom nil))

(defn path-for
  [route & args]
  (apply (partial bidi/path-for @app-routes route) args))
