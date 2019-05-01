(ns klipse-notebook.core)

(def directory (clojure.java.io/file "resources/content"))

(def files (seq (.list directory)))

(def content-list (sort (for [x files] (Integer/parseInt x))))

(defn header [title]
    (str "<!DOCTYPE html>
          <html>
          <head>
          <meta charset=\"utf-8\">
          <title>" title "</title>
          <link rel=\"stylesheet\" type=\"text/css\" href=\"https://storage.googleapis.com/app.klipse.tech/css/codemirror.css\">
    <script>
    window.klipse_settings = {
        selector: '.language-klipse',
    };
</script>
<script type=\"text/x-mathjax-config\">
  MathJax.Hub.Config({tex2jax: {inlineMath: [['$','$'], ['\\(','\\)']]}});
</script>
<script type=\"text/javascript\" async
  src=\"https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/latest.js?config=TeX-MML-AM_CHTML\">
</script>
</head>
<body>
<h1>
<center>" title "</center>
</h1>"))

(def content
    (for [x content-list]
      (str "<pre><code class=\"language-klipse\">"
           (slurp (str "resources/content/" x))
           "</code></pre>")))

(def footer
"<script src=\"https://storage.googleapis.com/app.klipse.tech/plugin/js/klipse_plugin.js\"></script>
</body>
</html>
")

(defn notebook [title]
  (spit "notebook.html"
        (str (header title) (apply str content) footer)))


; create or update notebook.html
; (notebook "Page Title")

; create or update snippet
(defn snippet [n s]
  (spit (str "resources/content/" n) s))

; pass snippet number as integer and content as string
; (snippet 1 "(+ 6 5 7)")

; quotes must be escaped
; (snippet 1 "\"here is a string\"")