(ns duct.logger-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [duct.logger :as logger]))

(deftest nil-test
  (is (str/blank? (with-out-str (logger/log nil :info ::test1))))
  (is (str/blank? (with-out-str (logger/log nil :warn ::test2 {:foo "bar"})))))

(defrecord AtomLogger [a]
  logger/Logger
  (-log [logger level ns-str file line event data]
    (swap! a conj [level ns-str file line event data])))

(deftest log-test
  (let [logs   (atom [])
        logger (->AtomLogger logs)]
    (logger/log logger :info ::t1)
    (logger/log logger :warn ::t2 {:x "y"})
    (is (= @logs
           [[:info "duct.logger-test" "duct/logger_test.clj" 18 ::t1 nil]
            [:warn "duct.logger-test" "duct/logger_test.clj" 19 ::t2 {:x "y"}]]))))
