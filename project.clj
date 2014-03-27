(defproject bing-results "0.1.0-SNAPSHOT"
  :description "Download number of bing results for all queries supplied in a file on the commandline"
  :url "https://github.com/xmonkee/bing-results"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.9.1"]
                 [org.clojure/data.json "0.2.4"]]
  :main bing-results.core)
