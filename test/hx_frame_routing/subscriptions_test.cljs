(ns hx-frame-routing.subscriptions-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]

   [hx-frame-routing.subscriptions :as s]))

(def sample-app-state
  {:router/route :home
   :router/route-params {:page 1}
   :router/route-query {"q" "user@test.com"}
   :router/initialized true})

(deftest initialized
  (testing "Pulls `:router/initialized` prop from app-state"
    (is (true? (s/initialized sample-app-state nil)))))

(deftest route
  (testing "Pulls `:router/route` prop from app-state"
    (is (= :home (s/route sample-app-state nil)))))

(deftest route-params
  (testing "Pulls `:router/route-params` prop from app-state"
    (is (= {:page 1} (s/route-params sample-app-state nil)))))

(deftest route-query
  (testing "Pulls `:router/route-query` prop from app-state"
    (is (= {"q" "user@test.com"} (s/route-query sample-app-state nil)))))
