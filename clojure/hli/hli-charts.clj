(in-ns 'hli-charts)
(clojure/refer 'clojure)

(import '(javax.swing JFrame JPanel)
	'(org.jfree.chart ChartFactory
			  ChartFrame
			  JFreeChart
			  ChartPanel)
	'(org.jfree.data.general DefaultValueDataset
				       ValueDataset)
	'(java.awt BorderLayout
		   Color
		   Dimension
		   Font
		   GradientPaint
		   Point
		   GridLayout)
	'(org.jfree.chart.plot.dial DialPointer$Pin 
				    DialPlot
				    DialValueIndicator
				    StandardDialScale
				    DialCap
				    StandardDialFrame
				    DialBackground))

;; usage (create-dial-chart -40 60 120 300 10 4 "blah")
(defn create-dial-chart
  [lower-bound upper-bound start-angle extent major-tick-increment minor-tick-count chart-title]
  (let [plot (new DialPlot)
	chart (new JFreeChart chart-title plot)
	dataset (new DefaultValueDataset 0)
	dvi (new DialValueIndicator 0)
	scale (new StandardDialScale lower-bound upper-bound start-angle extent major-tick-increment minor-tick-count)
	cap (new DialCap)]
    
    (doto scale
      (setMajorTickIncrement 10)
      (setMinorTickCount 4)
      (setTickRadius 0.88)
      (setTickLabelOffset 0.15)
      (setTickLabelFont (new Font "Dialog" (. Font PLAIN) 14)))
    
    (doto plot
      (setDataset dataset)
      (setDialFrame (new StandardDialFrame))
      (setBackground (new DialBackground))
      (addLayer dvi)
      (addScale 0 scale)
      (addPointer (new DialPointer$Pin))
      (setCap cap))
    {:plot plot
     :chart chart
     :dvi dvi
     :scale scale
     :cap cap
     :dataset dataset
     :values [0]
     :thread "Thread?"}))