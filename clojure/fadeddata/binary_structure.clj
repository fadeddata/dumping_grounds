(ns fadeddata.binary-structure
  (:use clojure.main))

;; generic multi methods dispatching on hashes masquerading as classes
(defmulti read-value :Type)

(defmulti write-value :Type)

;; builds a defmethod for the specified "type" for both reader and writer
(defmacro def-binary-primitive 
  [type rw-hash]
  (list 'do
	(list 'defmethod 'read-value type '[args]
	      (:Reader rw-hash))
	(list 'defmethod 'write-value type '[args] 
	      (:Writer rw-hash))))

;; takes slot spec and creates a clojure let binding calling the appropriate read method
(defn slot->binding [slot-spec]
  (let [name (first slot-spec)
	type (second slot-spec)
	hash-arg (nth slot-spec 2)]
    (vector name (list 'read-value (merge (hash-map :Type type :stream 'stream) hash-arg)))))

;; should build up a hash using the associated binary-primitives/binary-structures
(defmacro def-binary-structure 
  [type slots]
  (list 'defmethod 'read-value type (vector 'args)
	(let [mapped-slots (map slot->binding slots)]
	  (list 'let '[stream (:stream args)]
		(list 'let (loop [x (count mapped-slots) l []]
			     (if (zero? x)
			       l
			       (recur (dec x) (into (nth mapped-slots (- x 1)) l))))
		      (loop [x (count slots) l {}]
			(if (zero? x)
			  l
			  (recur (dec x) (merge 
					  (hash-map (keyword (name (first (nth slots (- x 1))))) 
						    (first (nth slots (- x 1)))) l)))))))))
