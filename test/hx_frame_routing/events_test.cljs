(ns hx-frame-routing.events-test
  (:require
   [cljs.test :refer-macros [deftest is testing]]

   [hx-frame-routing.core :as hx-frame-routing]
   [hx-frame-routing.events :as e]))

(def app-context {:db (merge {}
                             hx-frame-routing/initial-state)})

(deftest url->query
  (testing "Returns the query parameter of a route as a map."
    (is (= {"a" "123" "b" "456"}
           (#'e/url->query "https://www.site.com?a=123&b=456")))))

(deftest initialized
  (testing "Sets initialized flag to true"
    (is (-> (:db app-context)
            (e/initialized [:router/initialized])
            (get-in [:hx-frame-router :initialized])
            (true?)))))

(deftest set-route
  (testing "Properly sets route state when dispatched"
    (is (= {:hx-frame-router {:route :blog-post
                              :route-params {:blog-id 12345}
                              :route-query {"q" "foo"}
                              :initialized false}}
           (with-redefs [e/get-route-query (constantly {"q" "foo"})]
             (e/set-route (:db app-context)
                          [:router/set-route
                           {:handler :blog-post
                            :route-params {:blog-id 12345}}]))))))

(deftest nav-to
  (testing "Dispatches `nav-to` side-effect"
    (is (contains?
         (e/nav-to app-context [:nav-to "https://site.com"])
         :router/nav-to))))
