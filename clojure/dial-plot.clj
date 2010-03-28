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

(defn create-dial-chart
  [lower-bound upper-bound start-angle extent major-tick-increment minor-tick-count chart-title dataset-value]
  (let [plot (new DialPlot)
	chart (new JFreeChart chart-title plot)
	dataset (new DefaultValueDataset dataset-value)
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
     :dataset dataset}))

(comment

(def dial (create-dial-chart -40 60 120 300 10 4 "blah" 10))

(def frame (new ChartFrame "blah" (:chart dial)))

(doto frame
  (pack)
  (setVisible true))





(def dataset (new DefaultValueDataset 10))

(def dial (create-dial-chart -40 60 120 300 10 4 "blah" dataset))

(def chart-panel (new ChartPanel dial))

(def frame (new JFrame "blah"))

(def panel (new JPanel (new GridLayout 1 2)))

(. panel add chart-panel)

(doto frame
 	(add panel)
 	(pack)
 	(show))

(def dial-2 (create-dial-chart -40 60 120 300 10 4 "blah" dataset))

(def chart-panel-2 (new ChartPanel dial-2))

(. panel add chart-panel-2)

(. frame pack)

(. dataset setValue 40)
)