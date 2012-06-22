(ns kaprekar.draw
  (:require [analemma.svg :refer :all]))

(defn as-html [output-file results-vec]
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

(defn as-svg [output-file results-vec]
  (->>
   "foo"
   (spit output-file)))

(def using {"svg" as-svg
            "html" html})

(defn usage [& args] (println (format "usage: choose among %s" (keys using))))
