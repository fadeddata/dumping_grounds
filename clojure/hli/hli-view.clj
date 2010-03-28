(in-ns 'hli-view)
(clojure/refer 'hli-charts)
(clojure/refer 'clojure)

(def *charts* (ref {"fake" {:values [0]}
		    "fake-2" {:values [10]}}))

(defn add-chart [chart chart-id]
     (dosync (commute *charts* assoc chart-id chart)))

(defn update-chart-value [chart-id value value-num]
  (let [chart (@*charts* chart-id)
	values (:values chart)]
    (dosync (ref-set *charts* (assoc @*charts* chart-id (assoc chart :values (assoc values value-num value)))))))

