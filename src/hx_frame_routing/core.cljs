(ns hx-frame-routing.core
  (:require
   [hx.react :as hx :refer [defnc]]
   [hx-frame.core :as hx-frame]
   [hx-frame-routing.events :as events]
   [hx-frame-routing.subscriptions :as subscriptions]))

(defn register-events
  "Registers hx-frame-routing events"
  [opts]
  (events/register-events opts))

(defn register-subscriptions
  "Registers hx-frame-routing subscriptions"
  []
  (subscriptions/register-subscriptions))

(defn register-all
  "Registers both hx-frame-routing events & subscriptions"
  [{:keys [event-options]}]
  (register-events event-options)
  (register-subscriptions))

;; TODO Support Async Rendering (It's possible that some middleware could
;;      be dependent on networking, localStorage, etc...
(defnc Middleware
  [{:keys [container middleware]}]
  (let [route-params (hx-frame/subscribe [:router/route-params])
        route-query (hx-frame/subscribe [:router/route-query])
        route (hx-frame/subscribe [:router/route])
        RenderedContainer (if (seq middleware)
                            (:container (reduce #(%2 %1)
                                                {:container container}
                                                middleware))
                            container)]

    [RenderedContainer {:route-params route-params
                        :route-query route-query
                        :route route}]))

(defn create-route-middleware
  "Route middleware is an itterator that reduces over a sequence of
  middleware functions to ensure all required route logic has been
  met before mounting the containing view.

  Use cases: Protecting routes
             Redirecting routes
             Bootstraping data"
  []
  (fn [container middleware]
    (hx/f [Middleware {:container container
                       :middleware middleware}])))
