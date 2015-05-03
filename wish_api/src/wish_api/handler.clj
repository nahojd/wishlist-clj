(ns wish-api.handler
	(:use cheshire.core)
	(:use ring.util.response)
	(:require [compojure.core :refer :all]
		[compojure.route :as route]
		[compojure.handler :as handler]
		[ring.middleware.defaults :refer :all]
		[ring.middleware.json :as middleware]))

(defn get-all-wishes [userid]
	(response []))

(defn create-new-wish [userid wish]
	(response wish))

(defn get-wish [wishid]
	(response {:title "Lorem ipsum" :description "Lorem ipsum dolor sit amet." :url "http://example.com" :userid "johndoe"}))

(defroutes app-routes
	(GET "/" [] (response {:hello "world"}))

	(context "/:userid" [userid] (defroutes user-routes
		(context "/wishes" [] (defroutes wishes-routes
			(GET	"/" [] (get-all-wishes userid))
			(POST	"/" {body :body} (create-new-wish userid body))
			(context "/:wishid" [wishid] (defroutes wish-routes
				(GET 	"/" [] (get-wish wishid))))))))

	(route/not-found "Not Found"))

(def app
	(-> app-routes
		(middleware/wrap-json-body)
		(middleware/wrap-json-response)
		(wrap-defaults api-defaults)))
