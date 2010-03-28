(in-ns 'hli-callback)
(clojure/refer 'clojure)
;; (clojure/refer 'binary-structure)

(def *callbacks* (ref {})) 

(def *sensors-to-charts* (ref {}))

(defn callback [id data] 
  #^{:doc "looks up function(s) in *callbacks* by id and calls them 
        with data as argument"} 
  (dorun (map #(% data) (@*callbacks* id)))) 

(defn add-callback [id fn] 
  #^{:doc "adds call back functions to *callbacks* map"} 
  (dosync (commute *callbacks* assoc id (conj (@*callbacks* id) fn)))) 

(add-callback "print" (fn [x] (.. System out (println (str x))))) 

(defmacro connect [sensor-id chart-name]
  "builds a callback fn for a sensor to update a chart"
  `(add-callback ~sensor-id (fn [x])))
  