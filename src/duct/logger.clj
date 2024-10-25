(ns duct.logger
  "Protocol and macro for logging events in Duct.")

(defprotocol Logger
  "Protocol for abstracting logging. Used by the log macro."
  (-log [logger level ns-str file line id event data]))

(extend-protocol Logger
  nil
  (-log [_ _ _ _ _ _ _ _])
  java.util.Collection
  (-log [loggers level ns-str file line id event data]
    (run! #(-log % level ns-str file line id event data) loggers)))

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

(doseq [level '(report fatal error warn info debug)]
  (eval
   `(defmacro ~level
      ~(format "Log an event with %s logging level. See [[log]]." level)
      (~'[logger event]
       (log-form ~'logger ~(keyword level) ~'event nil ~'&form))
      (~'[logger event data]
       (log-form ~'logger ~(keyword level) ~'event ~'data ~'&form)))))
