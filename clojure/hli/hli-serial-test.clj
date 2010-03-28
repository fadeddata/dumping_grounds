(in-ns 'hli-serial-test)
(clojure/refer 'clojure)
(clojure/refer 'hli-parse)
(clojure/refer 'binary-structure)
(clojure/refer 'hli-callback)
(import '(java.util Random))

(def *packets* (ref ()))

(defn random-packet []
  (let [random (new Random)
	packet-start-1 85
	packet-start-2 245
	payload-size-1 0
	payload-size-2 2
	box-id 0
	sensor-id (. random nextInt 4)
	packet-type 0
	value-1 (. random nextInt 255)
	value-2 (. random nextInt 255)
	checksum (checksum (list payload-size-1 payload-size-2 box-id sensor-id packet-type value-1 value-2))]
    (list packet-start-1
	  packet-start-2
	  payload-size-1
	  payload-size-2
	  box-id
	  sensor-id
	  packet-type
	  value-1
	  value-2
	  checksum)))

(def serial-stream 
  (let [c (ref (cycle (concat (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet)
		      (random-packet))))] 
     (proxy [java.io.InputStream] [] 
         (read [] 
           (dosync 
            (let [ret (first @c)] 
              (alter c rest) 
              (int ret)))))))

(add-callback {:sensor-id 0 :box-id 0 :packet-type 0} 
	      (fn [x] (.. System out (println 
				      (str 
				       "Box " (:box-id x) 
				       " Sensor " (:sensor-id x) 
				       " Type " (:packet-type x) 
				       " Recieved packet with value: " (packet-value x))))))

(add-callback {:sensor-id 1 :box-id 0 :packet-type 0} 
	      (fn [x] (.. System out (println 
				      (str 
				       "Box " (:box-id x) 
				       " Sensor " (:sensor-id x) 
				       " Type " (:packet-type x) 
				       " Recieved packet with value: " (packet-value x))))))

(add-callback {:sensor-id 2 :box-id 0 :packet-type 0} 
	      (fn [x] (.. System out (println 
				      (str 
				       "Box " (:box-id x) 
				       " Sensor " (:sensor-id x) 
				       " Type " (:packet-type x) 
				       " Recieved packet with value: " (packet-value x))))))

(add-callback {:sensor-id 3 :box-id 0 :packet-type 0} 
	      (fn [x] (.. System out (println 
				      (str 
				       "Box " (:box-id x) 
				       " Sensor " (:sensor-id x) 
				       " Type " (:packet-type x) 
				       " Recieved packet with value: " (packet-value x))))))

(add-callback {:sensor-id 4 :box-id 0 :packet-type 0} 
	      (fn [x] (.. System out (println 
				      (str 
				       "Box " (:box-id x) 
				       " Sensor " (:sensor-id x) 
				       " Type " (:packet-type x) 
				       " Recieved packet with value: " (packet-value x))))))

(comment 
  (dotimes x 10 (when (= (. serial-stream read) 85) 
 		  (let [packet (read-value {:Type :hli-packet :stream serial-stream})
 			callback-id (select-keys packet '(:sensor-id :box-id :packet-type))]
		    (when (checksum? packet)
		      (callback callback-id packet))))))
		    

