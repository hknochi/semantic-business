
<!doctype html>
<html>
  <head>
    <meta charset="UTF-8">
    <title>Spark simple map example</title>
    <script type="text/javascript"
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
    <script type="text/javascript"
            src="http://km.aifb.kit.edu/sites/spark/src/jquery.spark.js"></script>
  
    <!-- it would be good to make this automatic in spark.gmapv3.js -->
    <script type="text/javascript"
            src="http://maps.google.com/maps/api/js?sensor=false" ></script>

    <!-- the following is required by Chrome when running on file: URIs-->
    <script type="text/javascript"
            src="/resources/js/spark.gmapv3.js" ></script>


  </head>
  <body>
    <div class="container">
      <div class="span-24 last prepend-top">
        <h1>Spark map example</h1>
        <p>
          The following map is a spark showing the result of a
          query to <a href="http://dbpedia.org/">DBpedia</a>,
          asking for the coordinates of the european capital cities.

          <div class="spark" id="map_canvas" style="height: 400px"
            data-spark-endpoint="http://localhost:8890/sparql"
            data-spark-format="resources/js/spark.gmapv3.js"
            data-spark-param-zoom="15"
            data-spark-param-center="Leipzig, Germany"
            data-spark-query='PREFIX schema:<http://schema.org/>
                SELECT ?name ?lat ?long ?m
				WHERE
				{
				    ?m schema:geo ?geo .
				    ?m rdfs:label ?name.
				    ?geo schema:latitude ?lat.
				    ?geo schema:longitude ?long.    
				    FILTER (bif:st_intersects ( bif:st_point (xsd:double(?long), xsd:double(?lat)), bif:st_point (12.37475,51.340333), 100))
				}
            '
            data-spark-param-latitude="lat"
            data-spark-param-longitude="long"
            data-spark-param-label="name"
            data-spark-param-uri="m"
          >

            <em>loading&hellip;</em>
          </div><br />

          <div class="spark"
            data-spark-endpoint="http://localhost:8890/sparql"
            data-spark-format="http://km.aifb.kit.edu/sites/spark/src/jquery.spark.simpletable.js"
            data-spark-query=' PREFIX schema:<http://schema.org/>
                SELECT ?name ?lat ?long
				WHERE
				{
				    ?m schema:geo ?geo .
				    ?m rdfs:label ?name.
				    ?geo schema:latitude ?lat.
				    ?geo schema:longitude ?long.    
				    FILTER (bif:st_intersects ( bif:st_point (xsd:double(?long), xsd:double(?lat)), bif:st_point (12.37475,51.340333), 100))
				}
              '>

            <em>loading&hellip;</em>
          </div>
        </p>
      </div>
    </div>

  </body>
</html>
