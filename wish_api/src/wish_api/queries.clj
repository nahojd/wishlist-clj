(ns wish-api.queries
	(:require 
		[datomic.api :as d]
		[wish-api.db :as db]))

(defn user-result-to-map [user]
	{:name (nth user 0) :email (nth user 1)})

(defn all-users []
	(map
		user-result-to-map
		(db/q '[:find ?name ?email
					  :where 
					  [?u :user/name ?name]
					  [?u :user/email ?email]])))
		
(defn get-user [userid]
	(first 
		(filter 
			(fn [user] (= (get user :name) userid))
			(all-users))))