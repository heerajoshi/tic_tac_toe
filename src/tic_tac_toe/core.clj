(ns tic-tac-toe.core)
(require '[clojure.set])

(def win-sets
  #{[0 1 2], [3 4 5], [6 7 8], [0 3 6], [1 4 7], [2 5 8], [0 4 8], [2 4 6]})

(def board (vec (repeat 9 " ")))

(defn draw-board [board] (->> board
                              (partition 3)
                              (map #(clojure.string/join (str " " "|" " ") %))
                              (clojure.string/join (str "\n" "**********" "\n"))println))

(defn get-player-detail [player-num, symbol]
  (println (str "Enter player " player-num " name"))
  {:name (read-line) :symbol symbol :moves #{}})

(defn has-won? [moves] (some #(clojure.set/subset? %1 moves) win-sets))

(defn has-draw? [board] (not (some #(= %1 " ") board)))

(defn update-board [board current-move symbol]
  (assoc board current-move symbol))


(defn play-game [board current-player next-player]
  (do
    (draw-board board)
    (println (str (current-player :name) " your turn \n"))
    (let [current-move (dec (read-string (read-line)))
          updated-board (update-board board current-move (current-player :symbol))]
      (def update-player-move (conj (current-player :moves) current-move))
      (draw-board updated-board)
      (cond
        (has-won? update-player-move) (println (str "you won " (current-player :name)))
        (has-draw? updated-board) (str "match draw")
        :else (recur updated-board next-player (assoc current-player :moves  update-player-move))))))

(defn start-game []
  (play-game board (get-player-detail 1 "X") (get-player-detail 2 "O") ))

(start-game)