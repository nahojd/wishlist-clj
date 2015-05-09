(ns wish-api.db
	(:require [datomic.api :as d]))

;; A better way of doing this is probably http://www.rkn.io/2014/12/16/datomic-antipatterns-eager-conn/

(def uri "datomic:mem://wishlist")

;;Create the database
(d/create-database uri)

(defn conn []
	(d/connect uri))



;; Define the schema
(def schema-tx (read-string (slurp "resources/schema.edn")))
@(d/transact (conn) schema-tx)

;; Insert some initial data
@(d/transact (conn) [{:db/id #db/id[:db.part/user] :user/name "johan" :user/email "johan@driessen.se"}
					{:db/id #db/id[:db.part/user] :user/name "ylva" :user/email "ylva@stenervall.se"}
					{:db/id #db/id[:db.part/user] :user/name "elin" :user/email ""}])

(defn db []
  (d/db (conn)))

;; '[:find ?name :where [?name :user/name]]
(def q 
	#(d/q % (db)))


