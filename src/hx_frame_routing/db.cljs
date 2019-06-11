(ns hx-frame-routing.db
  (:require [cljs.spec.alpha :as s]))

(def initial-state
  {:hx-frame-router {:route nil
                     :route-params nil
                     :route-query nil
                     :initialized false}})

(s/def :router/initialized boolean?)
(s/def :router/route (s/nilable keyword?))
(s/def :router/route-params (s/nilable map?))
(s/def :router/route-query (s/nilable map?))
(s/def ::router (s/keys :req-un [:router/route
                                 :router/route-params
                                 :router/route-query
                                 :router/initialized]))
