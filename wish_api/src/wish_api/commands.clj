(ns wish-api.commands
	(:require 
		[datomic.api :as d]
		[wish-api.db :as db]))

(defn create-user [username email]
	@(d/transact (db/conn) [{:db/id #db/id[:db.part/user] 
		:user/name username 
		:user/email email}]))