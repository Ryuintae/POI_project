import Map from 'static/js/ol/Map.js';
import View from 'static/js/ol/View.js';
import {Draw, Modify, Select, Snap} from 'static/js/ol/interaction.js';
import {OSM, Vector as VectorSource} from 'static/js/ol/Source.js';
import {Tile as TileLayer, Vector as VectorLayer} from 'static/js/ol/Layer.js';

var mousePositionCtrl = new ol.control.MousePosition({
    coordinateFormat: ol.coordinate.createStringXY(4),
    projection: 'EPSG:4326',
    className: 'custom-mouse-position',
    target: document.getElementById('mouse-position'),
    undefinedHTML: '&nbsp;'
});

var map = new ol.Map({
    controls: ol.control.defaults().extend([mousePositionCtrl]),
    layers: [
        new ol.layer.Tile({
            source: new ol.source.OSM()
        })
    ],
    target: 'map',
    view: new ol.View({
        center: [0, 0],
        zoom: 2
    })
});


const raster = new TileLayer({
    source: new OSM(),
});

const vector = new VectorLayer({
    source: new VectorSource(),
    style: {
        'fill-color': 'rgba(255, 255, 255, 0.2)',
        'stroke-color': '#ffcc33',
        'stroke-width': 2,
        'circle-radius': 7,
        'circle-fill-color': '#ffcc33',
    },
});

const map = new Map({
    layers: [raster, vector],
    target: 'map',
    view: new View({
        center: [-11000000, 4600000],
        zoom: 4,
    }),
});

const ExampleModify = {
    init: function () {
        this.select = new Select();
        map.addInteraction(this.select);

        this.modify = new Modify({
            features: this.select.getFeatures(),
        });
        map.addInteraction(this.modify);

        this.setEvents();
    },
    setEvents: function () {
        const selectedFeatures = this.select.getFeatures();

        this.select.on('change:active', function () {
            selectedFeatures.forEach(function (each) {
                selectedFeatures.remove(each);
            });
        });
    },
    setActive: function (active) {
        this.select.setActive(active);
        this.modify.setActive(active);
    },
};
ExampleModify.init();

const optionsForm = document.getElementById('options-form');

const ExampleDraw = {
    init: function () {
        map.addInteraction(this.Point);
        this.Point.setActive(false);
        map.addInteraction(this.LineString);
        this.LineString.setActive(false);
        map.addInteraction(this.Polygon);
        this.Polygon.setActive(false);
        map.addInteraction(this.Circle);
        this.Circle.setActive(false);
    },
    Point: new Draw({
        source: vector.getSource(),
        type: 'Point',
    }),
    LineString: new Draw({
        source: vector.getSource(),
        type: 'LineString',
    }),
    Polygon: new Draw({
        source: vector.getSource(),
        type: 'Polygon',
    }),
    Circle: new Draw({
        source: vector.getSource(),
        type: 'Circle',
    }),
    activeDraw: null,
    setActive: function (active) {
        if (this.activeDraw) {
            this.activeDraw.setActive(false);
            this.activeDraw = null;
        }
        if (active) {
            const type = optionsForm.elements['draw-type'].value;
            this.activeDraw = this[type];
            this.activeDraw.setActive(true);
        }
    },
};
ExampleDraw.init();

/**
 * Let user change the geometry type.
 * @param {Event} e Change event.
 */
optionsForm.onchange = function (e) {
    const type = e.target.getAttribute('name');
    if (type == 'draw-type') {
        ExampleModify.setActive(false);
        ExampleDraw.setActive(true);
        optionsForm.elements['interaction'].value = 'draw';
    } else if (type == 'interaction') {
        const interactionType = e.target.value;
        if (interactionType == 'modify') {
            ExampleDraw.setActive(false);
            ExampleModify.setActive(true);
        } else if (interactionType == 'draw') {
            ExampleDraw.setActive(true);
            ExampleModify.setActive(false);
        }
        j
    }
};

ExampleDraw.setActive(true);
ExampleModify.setActive(false);

// The snap interaction must be added after the Modify and Draw interactions
// in order for its map browser event handlers to be fired first. Its handlers
const snap = new Snap({
    source: vector.getSource(),
});
map.addInteraction(snap);