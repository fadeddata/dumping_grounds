(in-ns 'hli-parse)
(clojure/refer 'clojure)
(clojure/refer 'binary-structure)

(def-binary-primitive :u1
  {:Reader
   (. #^java.io.InputStream (:stream args) read)
   :Writer 
   (print "Not implemented")})

(def-binary-primitive :u2
  {:Reader
   (bit-or (bit-shift-left (. #^java.io.InputStream (:stream args) read) 8) 
			     (. #^java.io.InputStream (:stream args) read))
   :Writer 
   (print "Not implemented")})

(def-binary-primitive :payload
  {:Reader
   (loop [x (:length args) l nil]
     (if (zero? x)
       l
       (recur (dec x) (cons (. #^java.io.InputStream (:stream args) read) l))))
   :Writer 
   (print "Not implemented")})


(def-binary-structure :hli-packet
  ((length :u2)
   (box-id :u1)
   (sensor-id :u1)
   (packet-type :u1)
   (payload {:payload packet-type} {:length length})
   (checksum :u1)))

(def-binary-structure {:payload 0}
  ((value :u2)))

(comment ;; Examples
(def-binary-structure {:payload 1}
  ((payload :payload {:length (:length args)})))
  
(def-binary-structure {:payload 2}
  ((payload :payload {:length (:length args)})))
)

(defmethod read-value :default [x]
  (print (str "No method for " x \newline)))

(defmethod write-value :default [x]
  (print (str "No method for " x \newline)))

(defn checksum [lst]
	(bit-and (- 0 (apply + lst)) 255))

(defmulti checksum? :packet-type)

(defmethod checksum? 0 [packet]
  (let [length-1 (bit-shift-right (:length packet) 8)
	length-2 (- (:length packet) (bit-shift-left length-1 8))
	box-id (:box-id packet)
	sensor-id (:sensor-id packet)
	packet-type 0
	payload-value (:value (:payload packet))
	payload-1 (bit-shift-right payload-value 8)
	payload-2 (- payload-value (bit-shift-left payload-1 8))
	checksum (checksum (list length-1
				 length-2
				 box-id 
				 sensor-id 
				 packet-type
				 payload-1
				 payload-2))]
    (= checksum (:checksum packet))))

(defmulti packet-value :packet-type)

(defmethod packet-value 0 [packet]
  (:value (:payload packet)))