(import '(org.jfree.chart ChartFactory
			  ChartFrame
			  JFreeChart)
	'(org.jfree.data.general DefaultPieDataset))

(def data (new DefaultPieDataset))

(doto data
      (setValue "Category 1" 43.2)
      (setValue "Category 2" 27.9)
      (setValue "Category 3" 79.5))

(defn go []
  (let [chart (. ChartFactory createPieChart "Sample Pie Chart" data true true false)
	frame (new ChartFrame "First" chart)]

    (doto frame
      (pack)
      (setVisible true))))
    
    

   