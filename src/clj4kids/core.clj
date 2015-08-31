(ns clj4kids.core
  (:require [clj4kids.graphics :as gfx]))

(def ^:private side-length 40)
(def ^:private pixel-size 16)

(def ^:private clear-color :black)
(def ^:private default-color :white)

(def ^:private clear-screen (vec (repeat side-length (vec (repeat side-length clear-color)))))

(defonce ^:private raster (atom clear-screen))
(defonce ^:private current-color (atom :white))

(def colors
  {:black       [0 0 0]
   :red         [157 9 102]
   :dark-blue   [42 42 229]
   :purple      [199 52 255]
   :dark-green  [0 118 26]
   :dark-gray   [128 128 128]
   :medium-blue [13 161 255]
   :light-blue  [170 170 255]
   :brown       [85 85 0]
   :orange      [242 94 0]
   :light-gray  [192 192 192]
   :pink        [255 137 229]
   :light-green [56 203 0]
   :yellow      [213 213 26]
   :aqua        [98 246 153]
   :white       [255 255 255]})

(defn- make-panel []
  (gfx/make-panel colors raster side-length pixel-size))

(defonce ^:private canvas
  (delay (make-panel)))

(defn color [c]
  (reset! current-color c))

(defn clear []
  (force canvas)
  (reset! raster clear-screen)
  nil)

(defn point [x y]
  (force canvas)
  (swap! raster assoc-in [x y] @current-color)
  nil)

(defn h-line [y x1 x2]
  (doseq [x (range x1 (inc x2))]
    (point x y))
  nil)

(defn v-line [x y1 y2]
  (doseq [y (range y1 (inc y2))]
    (point x y))
  nil)

(defn box [x1 y1 x2 y2]
  (doseq [y (range y1 y2)]
    (h-line y x1 x2)))
