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

function initialize2() {
	var myOptions = {
		center : homeLocation,
		zoom : homeZoom,
		mapTypeId : google.maps.MapTypeId.TERRAIN
	};
	var map = new google.maps.Map(document.getElementById("map_canvas"), myOptions);



	var homeControlDiv = document.createElement('div');
	ResetControl(homeControlDiv, map);

	homeControlDiv.index = 1;
	map.controls[google.maps.ControlPosition.TOP_RIGHT].push(homeControlDiv);

}