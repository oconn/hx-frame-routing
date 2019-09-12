(ns hx-frame-routing.events
  (:require [bidi.bidi :as bidi]
            [cemerick.url :as cemerick-url]
            [pushy.core :as pushy]
            [hx-frame.core :as hx-frame]

            [hx-frame-routing.utils :as utils]))

(defn- url->query
  "Returns the query params from a url"
  [href] (-> href cemerick-url/url :query))

(defn get-route-query
  "Pulls query params from the window"
  []
  (-> js/window
      .-location
      .-href
      (url->query)))

(defn set-route
  "Sets updated route state"
  [db [_ {:keys [handler route-params]}]]
  (update db :hx-frame-router merge {:route handler
                                     :route-params route-params
                                     :route-query (get-route-query)}))

(defn nav-to
  "Triggers the nav-to side-effect"
  [{:keys [db]} [_ url]]
  {:db db :router/nav-to url})

(defn initialized
  "Called once pushy has started and router is initialized"
  [db _]
  (assoc-in db [:hx-frame-router :initialized] true))

(defn listen-for-navigation!
  "Wraps the history object and begins listening for history events"
  [history]
  (hx-frame/register-effect
   :router/nav-to
   (fn [route]
     (pushy/set-token! history route))))

(defn initialize
  "Initializes hx-frame-routing"
  [routes]
  (fn [_]
    (let [history
          (pushy/pushy #(hx-frame/dispatch [:router/set-route %])
                       #(bidi/match-route routes %))]

      (listen-for-navigation! history)

      (pushy/start! history)

      (hx-frame/dispatch [:router/initialized]))))

;; Public functions

(defn register-events
  "Registers hx-frame-routing events in hx-frame's registrar"
  [{:keys [set-route-interceptors
           nav-to-interceptors
           initialized-interceptors
           router-interceptors
           routes]
    :or {set-route-interceptors []
         nav-to-interceptors []
         initialized-interceptors []
         router-interceptors []}}]

  (reset! utils/app-routes routes)

  (hx-frame/register-event-db
   :router/set-route
   (into router-interceptors set-route-interceptors)
   set-route)

  (hx-frame/register-event-fx
   :router/nav-to
   (into router-interceptors nav-to-interceptors)
   nav-to)

  (hx-frame/register-event-db
   :router/initialized
   (into router-interceptors initialized-interceptors)
   initialized)

  (hx-frame/register-effect
   :router/initialize
   (initialize routes)))
