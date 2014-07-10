// Configuration variables, change these if required
var homeLatitude = 0;   // the latitude centre of the map in its initial state
var homeLongitude = 50; // the longitude centre of the map in its initial state
var homeZoom = 2;       // the zoom level of the map in its initial state
var countryCsvUrl = "countries.csv";  // the URL of the CSV file for country data
var producerCsvUrl = "producers.csv"; // the URL of the CSV file for producer data

// state variables, don't change these
var homeLocation = new google.maps.LatLng(homeLatitude, homeLongitude);
var selectedPolygon = null;
var currentLocation = homeLocation;
var producerPopup = new google.maps.InfoWindow();

function ResetControl(controlDiv, map) {

	// Set CSS styles for the DIV containing the control
	// Setting padding to 5 px will offset the control
	// from the edge of the map
	controlDiv.style.padding = '5px';

	// Set CSS for the control border
	var controlUI = document.createElement('div');
	controlUI.style.backgroundColor = 'white';
	controlUI.style.borderStyle = 'solid';
	controlUI.style.borderWidth = '1px';
	controlUI.style.cursor = 'pointer';
	controlUI.style.textAlign = 'center';
	controlUI.title = 'Click to reset the map';
	controlDiv.appendChild(controlUI);

	// Set CSS for the control interior
	var controlText = document.createElement('div');
	controlText.style.fontFamily = 'Arial,sans-serif';
	controlText.style.fontSize = '12px';
	controlText.style.paddingLeft = '6px';
	controlText.style.paddingRight = '6px';
	controlText.style.paddingTop = '2px';
	controlText.style.paddingBottom = '2px';
	controlText.innerHTML = '<b>Reset</b>';
	controlUI.appendChild(controlText);

	google.maps.event.addDomListener(controlUI, 'click', function() {
		map.setCenter(homeLocation);
		map.setZoom(homeZoom);
		if (selectedPolygon != null) {
			selectedPolygon.setVisible(true);
			selectedPolygon = null;
		}
		currentLocation = homeLocation;
	});

}

function initialize() {
	var myOptions = {
		center : homeLocation,
		zoom : homeZoom,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	};
	var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);

	$.ajax({
	  	url: countryCsvUrl
	}).done(function( csv ) {
		var countries = $.csv2Dictionary(csv);
		for ( var cCount = 0; cCount < countries.length; cCount += 1) {
			var c = countries[cCount];
			if (c.name == null) {
				break;
			}

			c.zoomLat = parseFloat(c.zoomLat);
			c.zoomLong = parseFloat(c.zoomLong);
			c.zoomLevel = parseFloat(c.zoomLevel);

			var outlines = [c.outline1, c.outline2, c.outline3, c.outline4, c.outline5];
			var polygons = [];
			for ( var j = 0; j < outlines.length; j += 1) {
				if (outlines[j] == null) {
					break;
				}
				var points = [];
				var outline = outlines[j].split(",");
				for ( var i = 0; i < outline.length; i += 2) {
					points.push(new google.maps.LatLng(parseFloat(outline[i + 1]), parseFloat(outline[i])));
				}
				polygons.push(points);
			}

			var polygon = new google.maps.Polygon({
				paths : polygons,
				strokeColor : c.colour,
				strokeOpacity : 0.8,
				strokeWeight : 2,
				fillColor : c.colour,
				fillOpacity : 0.35,
				country : c
			});
			polygon.setMap(map);
			google.maps.event.addListener(polygon, 'click', function() {
				var newLocation = new google.maps.LatLng(this.country.zoomLat, this.country.zoomLong);
				map.setCenter(newLocation);
				map.setZoom(this.country.zoomLevel);
				if (selectedPolygon != null) {
					selectedPolygon.setVisible(true);
				}
				selectedPolygon = this;
				currentLocation = newLocation;
				this.setVisible(false);
			});

		}
	});


	$.ajax({
		  	url: producerCsvUrl
		}).done(function( csv ) {
			producers = $.csv2Dictionary(csv);
			for ( var i = 0; i < producers.length; i += 1) {
				var p = producers[i];
				if (p.name == null) {
					break;
				}

				var marker = new google.maps.Marker({
					position : new google.maps.LatLng(parseFloat(p.latitude), parseFloat(p.longitude)),
					map : map,
					animation : google.maps.Animation.DROP,
					title : p.name,
					description : p.name + "<br/>" + p.description + "<br/> <a href='" + p.url + "' target='_blank'>Web site</a>"
				});

				new google.maps.event.addListener(marker, "click", function() {
					producerPopup.setContent(this.description);
					producerPopup.open(map, this);
					map.panTo(this.position);
				});

			}
		});



	new google.maps.event.addListener(producerPopup, "closeclick", function() {
		map.panTo(currentLocation);
	});

	// for the reset control

	var homeControlDiv = document.createElement('div');
	ResetControl(homeControlDiv, map);

	homeControlDiv.index = 1;
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);

}