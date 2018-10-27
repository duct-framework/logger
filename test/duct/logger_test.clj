(ns duct.logger-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [duct.logger :as logger]))

(deftest nil-test
  (is (str/blank? (with-out-str (logger/log nil :info ::test1))))
  (is (str/blank? (with-out-str (logger/log nil :warn ::test2 {:foo "bar"})))))

(defrecord AtomLogger [a ids]
  logger/Logger
  (-log [logger level ns-str file line id event data]
    (swap! a conj [level ns-str file line event data])
    (swap! ids conj (force id))))

(deftest log-test
  (let [logs   (atom [])
        ids    (atom [])
        logger (->AtomLogger logs ids)]
    (logger/log logger :info ::t1)
    (logger/log logger :warn ::t2 {:x "y"})
    (is (= @logs
           [[:info "duct.logger-test" "duct/logger_test.clj" 20 ::t1 nil]
            [:warn "duct.logger-test" "duct/logger_test.clj" 21 ::t2 {:x "y"}]]))
    (is (= (count @ids) (count (distinct @ids))))
    (is (every? (partial instance? java.util.UUID) @ids))))

(deftest sugar-test
  (let [logs   (atom [])
        ids    (atom [])
        logger (->AtomLogger logs ids)]
    (logger/report logger ::t1)
    (logger/fatal logger ::t2)
    (logger/error logger ::t3)
    (logger/warn logger ::t4)
    (logger/info logger ::t5)
    (logger/debug logger ::t6)
    (is (= @logs
           [[:report "duct.logger-test" "duct/logger_test.clj" 32 ::t1 nil]
            [:fatal  "duct.logger-test" "duct/logger_test.clj" 33 ::t2 nil]
            [:error  "duct.logger-test" "duct/logger_test.clj" 34 ::t3 nil]
            [:warn   "duct.logger-test" "duct/logger_test.clj" 35 ::t4 nil]
            [:info   "duct.logger-test" "duct/logger_test.clj" 36 ::t5 nil]
            [:debug  "duct.logger-test" "duct/logger_test.clj" 37 ::t6 nil]]))))
