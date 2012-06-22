(ns kaprekar.core
  (:require [kaprekar
             [compute :as compute]
             [draw :as draw]]))

(defn -main [& [output-file draw-using]]
  ((or (draw/using draw-using) draw/usage)
   output-file (map compute/kaprekar-number (range 10000))))