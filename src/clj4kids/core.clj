(ns clj4kids.core
  (:require [clj4kids.graphics :as gfx]))

(def width 40)
(def height 40)
(def pixel-size 16)

(def clear-colour :black)
(def default-colour :white)

(def ^:private clear-screen (vec (repeat height (vec (repeat width clear-colour)))))

(defonce ^:private raster (atom clear-screen))

(def colours
  {:black [0 0 0]
   :white [255 255 255]})

(defonce ^:private canvas
  (delay (gfx/make-panel colours raster width height pixel-size pixel-size)))

(defn dot [x y]
  (force canvas)
  (swap! raster assoc-in [x y] :white)
  nil)
