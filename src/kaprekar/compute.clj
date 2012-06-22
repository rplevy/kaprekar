(ns kaprekar.compute)

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
