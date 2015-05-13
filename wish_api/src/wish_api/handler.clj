(ns wish-api.handler
	(:use cheshire.core)
	(:use ring.util.response)
	(:require [compojure.core :refer :all]
		[compojure.route :as route]
		[compojure.handler :as handler]
		[ring.middleware.defaults :refer :all]
		[ring.middleware.json :as middleware]
		[wish-api.queries :as queries]
		[wish-api.commands :as cmd]))

(defn status-response [code body]
  {:status code
   :headers {"Content-Type" "text/plain"}
   :body body})

(defn get-all-users []
	(response (queries/all-users)))

(defn get-user [userid] 
	(let [user (queries/get-user userid)] 
			(if (nil? user) 
				(ring.util.response/not-found (str "User '" userid "' not found."))
				(response user))))

(defn create-new-user [user]
	(let [userid (user "name")]
		(let [existing-user (queries/get-user userid)]
			(if (nil? existing-user)
				((cmd/create-user userid (user "email"))
					(response (queries/get-user userid)))
				(status-response 409 "User already exists.")))))
	

(defn get-all-wishes [userid]
	(response []))

(defn create-new-wish [userid wish]
	(response wish))

(defn get-wish [wishid]
	(response {:title "Lorem ipsum" :description "Lorem ipsum dolor sit amet." :url "http://example.com" :userid "johndoe"}))

(defn aptest [apa]
	(println apa)
	(response apa))

(defroutes app-routes
	(POST "/test" {body :body} (aptest body))
	(context "/users" [] (defroutes users-routes
		(GET "/" [] (get-all-users))
		(POST "/" {body :body} (create-new-user body))
		(context "/:userid" [userid] (defroutes user-routes
			(GET 	"/" [] (get-user userid))
			(context "/wishes" [] (defroutes wishes-routes
				(GET	"/" [] (get-all-wishes userid))
				(POST	"/" {body :body} (create-new-wish userid body))
				(context "/:wishid" [wishid] (defroutes wish-routes
					(GET 	"/" [] (get-wish wishid))))))))))
	
	(route/not-found "Not Found"))

(def app
	(-> app-routes
		(middleware/wrap-json-body)
		(middleware/wrap-json-response)
		(wrap-defaults api-defaults)))
