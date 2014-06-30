/**
 * This is a SPARK [1] formatter that uses Google Maps API V3 [2] to display
 * geo-localised data.
 * 
 * [1] http://km.aifb.kit.edu/sites/spark/
 * [2] http://code.google.com/apis/maps/documentation/javascript/
 * 
 * Copyright 2011, Pierre-Antoine Champin (champin.net)
 * This file is distributed under the GNU Lesser General Public License v3;
 * see file LICENSE.txt .
 *
 * REQUIREMENTS
 *  * the google maps API must be included in the page with the following html:
 *      <script type="text/javascript"
 *              src="http://maps.google.com/maps/api/js?sensor=false"></script>
 *  * in Chrome, it seems that this script must be loaded with a <script>
 *    element in addition to being refered by the data-spark-format attribute.
 *  * the SPARQL query must have the following variables:
 *     o latitude: the latitude of the point
 *     o longitude: the longitude of the point
 *     o label: the label of the point
 *    or parameters data-spark-param-X can be used to override their name, e.g.
 *    data-spark-param-latitude="lat".
 * 
 * TODO:
 *  * Allow to customize shape and color of the marker
 *  * Allow to customize the InfoWindow
 *  * Provide a mean to interact with the markers from outside the widget
 *    (e.g. making the marker bounce...)
 *  * Find a way to automatically import the Google Maps API
 *  * Find a way to automatically center and zoom the map
 */

(function($){

    $.spark.format.gmapv3 = function(element, result, reduced, params) {
        
        // force element height of at least 100 pixels
        if (element.height() < 100) {
            element.height(100);
        }
        
        // create map widget and center it
        var map = new google.maps.Map(element.get(0), {
                zoom: 0,
                center: (new google.maps.LatLng(0,0)),                
                mapTypeId: google.maps.MapTypeId.ROADMAP
            });

        var zoomLevel = parseInt(params.param.zoom) || 7;
        var centerName = params.param.center || "Lyon, France";
        var geocoder = new google.maps.Geocoder();
        geocoder.geocode({ address: centerName },
                         function (results, status) {
                             if (status == google.maps.GeocoderStatus.OK) {
                                 var position = results[0].geometry.location;
                                 map.setCenter(position);
                                 map.setZoom(zoomLevel);
                             } else {
                                 alert("geocoder status: " + status);
                             }

                         });

         // add marker for every entry
        var latKey = params.param.latitude || "latitude";
        var lonKey = params.param.longitude || "longitude";
        var labelKey = params.param.label || "label";
        var labelUri = params.param.uri || "uri";
        $.each(result.results.bindings, function(item, values) {
                var latitude = parseFloat(values[latKey].value);
                var longitude = parseFloat(values[lonKey].value);
                var label = values[labelKey].value;
                var uri = values[labelUri].value;
                var position = new google.maps.LatLng(latitude, longitude);
                var marker = new google.maps.Marker({
                        position: position,
                        map: map,
                        title: label
                    });
                var listener = function(event) {
                    var info = new google.maps.InfoWindow({
                            "position": position,
                            "content":  "<a target=\"_blank\" href=\""+uri+"\" >"+label+"</a>" 
                        });
                    info.open(map);
                }
                google.maps.event.addListener(marker, 'click', listener);
            });
   }
})(jQuery);
