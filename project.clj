(defproject duct/logger "0.1.1"
  :description "Logging library for the Duct framework"
  :url "https://github.com/duct-framework/logger"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha19"]]
  :plugins [[lein-codox "0.10.3"]]
  :codox {:output-path "codox"
          :metadata {:doc/format :markdown}
          :source-uri
          "https://github.com/duct-framework/logger/blob/{version}/{filepath}#L{line}"})
