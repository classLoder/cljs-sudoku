(ns cljs-sudoku.app
  (:require ["react"]
            [cljs-sudoku.events]
            [cljs-sudoku.subs]
            [reagent.core :as reagent :refer [atom render]]
            [re-frame.core :as rf :include-macros true]
            [cljs-sudoku.components :refer [sudoku-component pagination navbar]]
            [cljs-sudoku.views.past-sudokus :refer [past-sudokus-view]]
            [cljs-sudoku.views.regular :refer [regular-view]]
            [cljs-sudoku.views.play :refer [play-view]]
            [clojure.set :as sets :refer [difference union]]
            [cljs-sudoku.sudoku :as s :refer [random-sudoku neighbor-positions]]
            [cljs.core.async :as async :refer [<! >! chan put! go go-loop]
             :include-macros true]
            [stylefy.core :as stylefy]))
;; (rf/dispatch-sync [:initialize])

      

(defn app []
  [:div.app
   [navbar]
   (cond
     (= @(rf/subscribe [:current-view])
        :regular) [regular-view]
     (= @(rf/subscribe [:current-view])
        :history) [past-sudokus-view]
     (= @(rf/subscribe [:current-view])
        :play) [play-view])])

(defn mount-components! []
 (render
   [app]
   (.getElementById js/document "root")))

(defn init! []
  (stylefy/init)
  (rf/dispatch-sync [:initialize])
  (rf/dispatch [:init-past-sudokus-view])
  (mount-components!))

