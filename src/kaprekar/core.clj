(ns kaprekar.core)
  
(def kaprekar-number
  (memoize
   (fn [i]
     (let [digits (map #(Integer/parseInt (str %)) (format "%04d" i))
           digits->int (fn [ds] (Integer/parseInt (apply str ds)))
           sorted-int (fn [ds sort-fn] (digits->int (sort sort-fn ds)))]
       (cond (apply = digits)     Double/NaN
             (= [6 1 7 4] digits) 0
             :else                (inc (kaprekar-number
                                        (- (sorted-int digits >)
                                           (sorted-int digits <)))))))))

(defn print-colors [output-file results-vec]
  (let [color (shuffle ["#000000" "#FF0000" "#00FF00" "#0000FF" "#FFFF00"
                        "#00FFFF" "#FF00FF" "#C0C0C0" "#FFFFFF"])
        format-val (fn [str-out value]
                     (format (str "%s<span style=\"background-color:%s\"><nobr>"
                                  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</nobr></span>")
                             str-out (if (not (.equals value Double/NaN))
                                       (color value) "")))
        format-row (fn [str-out row]
                     (format "%s%s\n" str-out (reduce format-val "" row)))]
    (spit output-file
          (reduce format-row "" (partition 100 results-vec)))))

(defn -main [& [output-file]]
  (print-colors output-file (vec (map kaprekar-number (range 10000)))))