;; Reading a 24 bit value from a stream
(read-24 
 [s]
 (bit-or (bit-shift-left (read-byte s) 16) (bit-or (bit-shift-left (read-byte s) 8) (read-byte s))))