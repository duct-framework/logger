(ns duct.logger-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [duct.logger :as logger]))

(deftest nil-test
  (is (str/blank? (with-out-str (logger/log nil :info ::testing))))
  (is (str/blank? (with-out-str (logger/log nil :info ::testing {:foo "bar"})))))
