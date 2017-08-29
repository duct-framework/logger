(ns duct.logger
  "Protocol and macro for logging events in Duct.")

(defprotocol Logger
  "Protocol for abstracting logging. Used by the log macro."
  (-log [logger level ns-str file line id event data]))

(extend-protocol Logger
  nil
  (-log [_ _ _ _ _ _ _ _]))

(defn- log-form [logger level event data form]
  `(-log ~logger
         ~level
         ~(str *ns*) ~*file* ~(:line (meta form))
         (delay (java.util.UUID/randomUUID))
         ~event ~data))

(defmacro log
  "Log an event and optional data structure at the supplied severity level
  using a logger that implements the Logger protocol."
  ([logger level event]      (log-form logger level event nil &form))
  ([logger level event data] (log-form logger level event data &form)))
