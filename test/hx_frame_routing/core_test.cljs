(ns hx-frame-routing.core-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]

   [hx-frame.registrar :as hx-frame-registrar]
   [hx-frame-routing.core :as c]))

(def hx-frame-routing-events #{:router/set-route
                               :router/nav-to
                               :router/initialized})

(def hx-frame-routing-effects #{:router/initialize})

(def hx-frame-routing-subscriptions #{:router/initialized
                                      :router/route
                                      :router/route-params
                                      :router/route-query})

(deftest register-events
  (c/register-events {})

  (testing "Registers hx-frame-routing events with hx-frame's registry"
    (is (->> hx-frame-routing-events
             (map (partial hx-frame-registrar/get-handler :event))
             (remove some?)
             (empty?))))

  (testing "Registers hx-frame-routing effects with hx-frame's registry"
    (is (->> hx-frame-routing-effects
             (map (partial hx-frame-registrar/get-handler :effect))
             (remove some?)
             (empty?)))))

(deftest register-subscriptions
  (c/register-subscriptions)

  (testing "Registers hx-frame-routing subscriptions with hx-frame's registry"
    (is (->> hx-frame-routing-subscriptions
             (map (partial hx-frame-registrar/get-handler :subscription))
             (remove some?)
             (empty?)))))

(deftest register-all
  (c/register-all {})

  (testing "Registers hx-frame-routing events with hx-frame's registry"
    (is (->> hx-frame-routing-events
             (map (partial hx-frame-registrar/get-handler :event))
             (remove some?)
             (empty?))))

  (testing "Registers hx-frame-routing effects with hx-frame's registry"
    (is (->> hx-frame-routing-effects
             (map (partial hx-frame-registrar/get-handler :effect))
             (remove some?)
             (empty?))))

  (testing "Registers hx-frame-routing subscriptions with hx-frame's registry"
    (is (->> hx-frame-routing-subscriptions
             (map (partial hx-frame-registrar/get-handler :subscription))
             (remove some?)
             (empty?)))))
