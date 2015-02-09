(defproject wish_api "0.1.0-SNAPSHOT"
  :description "API for Wishlist"
  :url "http://wish.driessen.se/api"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]]
  :main ^:skip-aot wish-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
