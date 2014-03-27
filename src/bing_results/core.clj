(ns bing-results.core
  (:require [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.data.json :as json])
  (:gen-class :main true))


;; Configuration atom, getter and setter:
(def patom (atom 0))
(defn params [k]
  ((deref patom) k))
(defn set-params! [params]
  (reset! patom params))


(defn single-quote [s]
  (str "'" s "'"))

(defn gen-query [s]
  (str
   (params :url)
   (client/generate-query-string {"Sources" (single-quote (params :sources))
                                  "Query" (single-quote s)
                                  "$format" (params :format)})))

(defn get-page [url]
   (client/get url {:basic-auth (params :key)}))

(defn get-num-results [page]
  (-> page
      :body
      json/read-str
      (get-in (params :path))))

;generate url, query-string, num-of-results and print result
(defn do-all [names]
  (doseq [n names]
    (-> n
        (gen-query)
        (get-page)
        (get-num-results)
        (println n))))

(defn -main
  ([] (-main "names.txt" "params.edn"))
  ([nfile] (-main nfile "params.edn"))
  ([nfile pfile]
   (set-params! (binding [*read-eval* false] (read-string (slurp pfile))))
   (-> nfile
       slurp
       (string/split #"\n")
       #_(#(take 5 %)) ; uncomment for debugging
       do-all)))
