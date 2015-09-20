(ns clj4kids.core
  (:require [clj4kids.graphics :as gfx]
            [clojure.string :as string]))

(def ^:private side-length 40)
(def ^:private pixel-size 16)

(def ^:private clear-color :black)
(def ^:private default-color :white)

(def ^:private clear-screen (vec (repeat side-length (vec (repeat side-length clear-color)))))

(defonce ^:private raster (atom clear-screen))
(defonce ^:private current-color (atom :white))

(def colors
  {:black       [0 0 0]
   :red         [255 0 0]
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

(defn color
  "Use the color that you name. To see all the colors you can use, just
  enter: (color)."
  ([] (println (string/join "\n" (keys colors))))
  ([name]
   (reset! current-color name)))

(defn point
  "Draw a point at coordinates (x, y). Ask your math teacher or parents to explain."
  [x y]
  (force canvas)
  (swap! raster assoc-in [x y] @current-color)
  :ok)

(defn clear
  "Clears the whole screen. If you want to just clear one point (maybe you made a
  mistake?), then call clear with x and y."
  ([]
   (force canvas)
   (reset! raster clear-screen)
   :ok)

  ([x y]
   (force canvas)
   (let [c @current-color]
     (color clear-color)
     (point x y)
     (color c))
   :ok))

(defn h-line
  "Draws a horizontal line from (x1, y) to (x2, y)."
  [y x1 x2]
  (doseq [x (range x1 (inc x2))]
    (point x y))
  :ok)

(defn v-line
  "Draws a vertical line from (x, y1) to (x, y2)."
  [x y1 y2]
  (doseq [y (range y1 (inc y2))]
    (point x y))
  :ok)

(defn box
  "Draws a box with an upper left-hand corner at (x1, y1) and a lower right-hand
  corner at (x2, y2)."
  [x1 y1 x2 y2]
  (doseq [y (range y1 y2)]
    (h-line y x1 x2))
  :ok)

(defn move
  "Moves the entire drawing x pixels right and y pixels down. To move left or up,
  use a negative number for x and/or y."
  [x y]
  (let [abs #(if (pos? %) % (* % -1))
        transform (fn [n c] (if (neg? n)
                             (drop (abs n) c)
                             (take (- side-length (abs n)) c)))
        fill-rows (take (abs x) clear-screen)
        fill-pixels (repeat (abs y) clear-color)]
    (->> (concat (when (pos? x) fill-rows)
                 (transform x @raster)
                 (when-not (pos? x) fill-rows))
         (map (fn [col] (vec (concat (when (pos? y) fill-pixels)
                                    (transform  y col)
                                    (when-not (pos? y) fill-pixels)))))
         vec
         (reset! raster)))
  :ok)

(defn- drawing-filename [name]
  (format "drawing-%s.clj" name))

(defn save
  "Saves the drawing with a name."
  [name]
  (spit (drawing-filename name)
        (pr-str @raster))
  :ok)

(defn load
  "Loads the drawing with the name."
  [name]
  (force canvas)
  (->> (drawing-filename name)
       slurp
       read-string
       (reset! raster))
  :ok)

(defn help
  "Display all commands and explain how to get more help."
  []
  (->> ["clj4kids lets you make simple drawings.\n"
        "The commands you can use are:\n"
        "  clear"
        "  color"
        "  point"
        "  box"
        "  h-line"
        "  v-line"
        "  move"
        "  save"
        "  load"
        "\n"
        "To learn more about each command, use the (doc) command, like this:\n"
        "  (doc color)"]
       (string/join "\n")
       println))
