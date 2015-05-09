(ns wish-api.handler
	(:use cheshire.core)
	(:use ring.util.response)
	(:require [compojure.core :refer :all]
		[compojure.route :as route]
		[compojure.handler :as handler]
		[ring.middleware.defaults :refer :all]
		[ring.middleware.json :as middleware]
		[datomic.api :as d]
		[wish-api.db :as db]))

(defn get-all-users []
	(response (db/q '[:find ?name ?email
					  :where 
					  [?u :user/name ?name]
					  [?u :user/email ?email]])))

(defn get-user [userid]
	(response {:name userid :email "test@example.com"}))

(defn get-all-wishes [userid]
	(response []))

(defn create-new-wish [userid wish]
	(response wish))

(defn get-wish [wishid]
	(response {:title "Lorem ipsum" :description "Lorem ipsum dolor sit amet." :url "http://example.com" :userid "johndoe"}))

(defroutes app-routes
	(GET "/" [] (get-all-users))
	(context "/:userid" [userid] (defroutes user-routes
		(GET 	"/" [] (get-user userid))
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
