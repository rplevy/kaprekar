(ns kaprekar.draw
  (:require [analemma
             [svg :refer :all]
             [xml :refer [emit]]]))

(def color
  (shuffle ["#000000" "#FF0000" "#00FF00" "#0000FF" "#FFFF00"
            "#00FFFF" "#FF00FF" "#C0C0C0" "#FFFFFF"]))

(defn as-html [output-file results-vec]
  (let [format-val (fn [str-out value]
                     (format (str "%s<span style=\"background-color:%s\"><nobr>"
                                  "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</nobr></span>")
                             str-out (if (not (.equals value Double/NaN))
                                       (color value) "")))
        format-row (fn [str-out row]
                     (format "%s%s\n" str-out (reduce format-val "" row)))]
    (spit output-file
          (reduce format-row "" (partition 100 results-vec)))))

(defn as-svg [output-file results-vec]
  (let [plane (vec (map vec (partition 100 results-vec)))
        coord #(get-in plane [%1 %2])]
    (spit output-file
          (emit
           (svg
            (apply
             group
             (for [x (range 100)
                   y (range 100)]
               (when-not (.equals (coord x y) Double/NaN)
                 (rect (* 50 x) (* 50 y) 50 50 :fill (color (coord x y)))))))))))


(def using {"svg" as-svg
            "html" as-html})

(defn usage [& args] (println (format "usage: choose among %s" (keys using))))
