(refer 'binary-structure)
(refer 'hli-parse)

(import '(gnu.io CommPort CommPortIdentifier SerialPort)
	'(javax.swing JFrame JLabel JTextField JButton)
        '(java.awt.event ActionListener)
        '(java.awt GridLayout))

(def portIdentifier (. CommPortIdentifier getPortIdentifier "/dev/cu.KeySerial1"))

(def port (. portIdentifier open "" 2000))

(. port setSerialPortParams 115200 (. SerialPort DATABITS_8) (. SerialPort STOPBITS_1) (. SerialPort PARITY_NONE))

(. port setFlowControlMode (. SerialPort FLOWCONTROL_NONE))

(def comm-in-stream (. port getInputStream))

(def *packets* (ref ()))

(def frame (new JFrame "Celsius Converter"))
(def length-label (new JLabel "Length"))
(def box-id-label (new JLabel "Box ID"))
(def sensor-id-label (new JLabel "Sensor ID"))
(def checksum-label (new JLabel "Checksum"))
(def payload-value-label (new JLabel "Value"))
(def packet-type-label (new JLabel "Packet Type"))

(def length-textfield (new JLabel "length value"))
(def box-id-textfield (new JLabel "box id value"))
(def sensor-id-textfield (new JLabel "sensor id value"))
(def checksum-textfield (new JLabel "checksum value"))
(def payload-value-textfield (new JLabel "payload value"))
(def packet-type-textfield (new JLabel "packet type value"))

(doto frame 
  (setLayout (new GridLayout 2 2 3 3))
  (add length-label)
  (add length-textfield)
  (add box-id-label)
  (add box-id-textfield)
  (add sensor-id-label)
  (add sensor-id-textfield)
  (add checksum-label)
  (add checksum-textfield)
  (add payload-value-label)
  (add payload-value-textfield)
  (add packet-type-label)
  (add packet-type-textfield)
  (setSize 300 80)
  (setVisible true))

(defn set-display [current-byte]
  (if (= current-byte 85)  ;; 55
    (set-display (. comm-in-stream read))
    (when (= current-byte 245) ;; F5
      (let [packet (read-value {:Type :hli-packet :stream comm-in-stream})]
	(. length-textfield (setText (str (:length packet))))
	(. box-id-textfield (setText (str (:box-id packet))))
	(. sensor-id-textfield (setText (str (:sensor-id packet))))
	(. checksum-textfield (setText (str (:checksum packet))))
	(. payload-value-textfield (setText (str (:value (:payload packet)))))
	(. packet-type-textfield (setText (str (:packet-type packet))))))))


(defn get-packet [current-byte]
  (if (= current-byte 85)  ;; 55
    (get-packet (. comm-in-stream read))
    (when (= current-byte 245) ;; F5
      (dosync 
       (ref-set *packets* (conj @*packets* (read-value {:Type :hli-packet :stream comm-in-stream})))))))

(comment
  (dotimes x 20
    (get-packet (. comm-in-stream read)))
  
  (dotimes x 20
    (set-display (. comm-in-stream read)))
  
  (dotimes x 10 (print (. Integer toHexString (. comm-in-stream read))))
  
  (defn print-packets []
    (dotimes x 20
      (when (= (. comm-in-stream read) 85)
	(println "start-byte-1: 0x55")
	(when (= (. comm-in-stream read) 245)
	  (println "start-byte-2: 0xF5")
	  (let [length (bit-or (bit-shift-left (. comm-in-stream read) 8) 
			       (. comm-in-stream read))
		box-id (. comm-in-stream read)
		sensor-id (. comm-in-stream read)
		packet-type (. comm-in-stream read)]
	    (println (str "box-id: 0x" (. Integer toHexString box-id)))
	    (println (str "sensor-id: 0x" (. Integer toHexString sensor-id)))
	    (println (str "packet-type: 0x" (. Integer toHexString packet-type)))
	    (print "payload: ")
	    (dotimes x length
	      (print (str " 0x" (. Integer toHexString (. comm-in-stream read)))))
	    (println "")
	    (println (str "checksum: 0x" (. Integer toHexString (. comm-in-stream read)))))))))
)
  



