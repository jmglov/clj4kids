(ns clj4kids.graphics
  (:import (java.awt BorderLayout Color Dimension GridLayout)
           (javax.swing JFrame JLabel JPanel)))

(defn- rgb->color
  [[r g b]]
  (Color. r g b))

(defn make-container [side-length]
  (let [increment (/ side-length 10)
        container (JPanel. (BorderLayout.))
        x-axis (JPanel. (GridLayout. 1 10))
        y-axis (JPanel. (GridLayout. 10 1))]
    (doseq [offset (range 10)]
      (.add x-axis (JLabel. (str (* offset increment)) JLabel/CENTER))
      (.add y-axis (doto (JLabel. (str (* offset increment)) JLabel/CENTER)
                     (.setVerticalAlignment JLabel/CENTER))))
    (doto container
      (.add x-axis BorderLayout/PAGE_START)
      (.add y-axis BorderLayout/LINE_START))))

(defn make-panel
  [color-map raster side-length pixel-size]
  (let [frame (JFrame. "clj4kids")
        rgb->color (memoize rgb->color)
        paint-point (fn [x y c g]
                      (.setColor g (rgb->color (c color-map)))
                      (.fillRect g
                                 (* x pixel-size) (* y pixel-size)
                                 pixel-size pixel-size))
        panel (proxy [JPanel] []
                (paintComponent [g]
                  (proxy-super paintComponent g)
                  (let [r @raster]
                    (doseq [x (range side-length)
                            y (range side-length)]
                      (paint-point x y (get-in r [x y]) g))))
                (getPreferredSize []
                  (Dimension.
                   (* side-length pixel-size)
                   (* side-length pixel-size))))]
    (doto frame
      (.add (doto (make-container side-length)
              (.add panel BorderLayout/CENTER)))
      (.pack)
      (.setVisible true))
    (add-watch raster :monitor
               (fn [_ _ o n]
                 (when (not= o n)
                   (.repaint panel))))
    panel))
