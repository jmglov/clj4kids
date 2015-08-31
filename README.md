# clj4kids

Low-res graphics sandbox for kids.

clj4kids is intended to be used as an aid for teaching Clojure to kids. It is based on Mike Fikes's [Bocko](https://github.com/mfikes/bocko) library, and adds more geometric primitives.

Stuff I hope to add soon:

* Non-filled boxes
* Circles
* Diagonal lines
* Movement

## Usage

Fire up a REPL with `lein repl` and start playing:

```clj
(require '[clj4kids.core :refer :all])
(color :medium-blue)
(h-line 18 1 5)
(v-line 1 18 23)
(h-line 23 1 5)
(v-line 8 14 23)
(v-line 12 18 27)
(h-line 27 10 12)
(point 12 14)
(v-line 15 14 18)
(h-line 18 15 18)
(v-line 18 14 23)
(v-line 21 14 23)
(point 22 20)
(point 23 19)
(point 24 18)
(point 22 21)
(point 23 22)
(point 24 23)
(v-line 27 18 23)
(point 27 14)
(v-line 33 14 23)
(h-line 18 30 33)
(v-line 30 18 23)
(h-line 23 30 33)
(h-line 18 35 38)
(v-line 35 18 20)
(h-line 20 35 38)
(v-line 38 20 23)
(h-line 23 35 38)
```

## License

Copyright © 2015 Josh Glover <jmglov@gmail.com>

Distributed under the MIT License.
