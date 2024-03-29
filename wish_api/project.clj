(defproject wish_api "0.1.0-SNAPSHOT"
  :description "API for Wishlist"
  :url "http://wish.driessen.se/api"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.3.2"]
                 [ring/ring-defaults "0.1.5"]
                 [ring/ring-json "0.3.1"]
                 [cheshire "5.4.0"]
                 [com.datomic/datomic-free "0.9.5153"]]
  :plugins [[lein-ring "0.8.13"]]
  :ring {:handler wish-api.handler/app}
  :profiles
    {:dev 
      {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
        
      
