# Duct Logger [![Build Status](https://github.com/duct-framework/logger/actions/workflows/test.yml/badge.svg)](https://github.com/duct-framework/logger/actions/workflows/test.yml)

A logging library for the [Duct][] framework.

[duct]: https://github.com/duct-framework/duct

## Installation

Add the following dependency to your deps.edn file:

    org.duct-framework/logger {:mvn/version "0.3.0"}

Or to your Leiningen project file:

    [org.duct-framework/logger "0.3.0"]


## Usage

To use this library, you'll need an implementation of the
`duct.logger/Logger` protocol. You can implement this yourself, or
use a pre-written one, such as [logger.timbre][].

[logger.timbre]: https://github.com/duct-framework/logger.timbre

Once you have a logger, you can use the `duct.logger/log`
macro. Logging in Duct is data-driven, and has three parts:

1. The logging level
2. A namespaced keyword that represents the event occurring
3. A map of relevant event data

For example, instead of writing something like this:

```clojure
(info "Starting server on port" port)
```

We instead write something like:

```clojure
(log logger :info ::starting-server {:port port})
```

The use of keywords and data structures produces logs that can be
queried more efficiently and consistantly than logs written in
English.

## Documentation

* [API Docs](https://duct-framework.github.io/logger/duct.logger.html)

## License

Copyright © 2024 James Reeves

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
