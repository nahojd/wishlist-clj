(ns wish-api.handler
	(:use cheshire.core)
	(:use ring.util.response)
	(:require [compojure.core :refer :all]
		[compojure.route :as route]
		[compojure.handler :as handler]
		[ring.middleware.defaults :refer :all]
		[ring.middleware.json :as middleware]))

(defroutes app-routes
	(GET "/" [] (response {:hello "world"}))

	(route/not-found "Not Found"))

(def app
	(-> app-routes
		(middleware/wrap-json-body)
		(middleware/wrap-json-response)
		(wrap-defaults api-defaults)))
