(ns hx-frame-routing.subscriptions-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]

   [hx-frame-routing.subscriptions :as s]))

(def sample-app-state
  {:hx-frame-router {:route :home
                     :route-params {:page 1}
                     :route-query {"q" "user@test.com"}
                     :initialized true}})

(deftest initialized
  (testing "Pulls `:hx-frame-router/initialized` prop from app-state"
    (is (true? (s/initialized sample-app-state nil)))))

(deftest route
  (testing "Pulls `:hx-frame-router/route` prop from app-state"
    (is (= :home (s/route sample-app-state nil)))))

(deftest route-params
  (testing "Pulls `:hx-frame-router/route-params` prop from app-state"
    (is (= {:page 1} (s/route-params sample-app-state nil)))))

(deftest route-query
  (testing "Pulls `:hx-frame-router/route-query` prop from app-state"
    (is (= {"q" "user@test.com"} (s/route-query sample-app-state nil)))))
