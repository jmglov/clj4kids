(ns clj4kids.core
  (:require [clj4kids.graphics :as gfx]))

(def side-length 40)
(def pixel-size 16)

(def clear-colour :black)
(def default-colour :white)

(def ^:private clear-screen (vec (repeat side-length (vec (repeat side-length clear-colour)))))

(defonce ^:private raster (atom clear-screen))

(def colours
  {:black [0 0 0]
   :white [255 255 255]})

(defn make-panel []
  (gfx/make-panel colours raster side-length pixel-size))

(defonce ^:private canvas
  (delay (make-panel)))

(defn clear []
  (force canvas)
  (reset! raster clear-screen))

(defn dot [x y]
  (force canvas)
  (swap! raster assoc-in [x y] :white)
  nil)
