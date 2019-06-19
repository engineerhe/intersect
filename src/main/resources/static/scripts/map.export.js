//添加矢量图层
var vectorSource = new ol.source.Vector({
    features: []
});
var map = new ol.Map({
    target: 'map',
    controls:[new  ol.control.MousePosition({
        coordinateFormat: ol.coordinate.createStringXY(4),
        projection: "EPSG:4519",
        className: 'custom-mouse-position',
        target: document.getElementById('mouse-position'),
        undefinedHTML: '&nbsp;'
    })],
    layers: [
        new ol.layer.Tile({
            extent: config.range,
            source: new ol.source.TileArcGISRest({
                url: config.mapService,
            })
        }),
        new ol.layer.Vector({
            source: vectorSource
        })
    ],
    view: new ol.View({
        projection:new ol.proj.Projection({
            code:"EPSG:4519",
            units: 'ft'
        }),
        center: [(config.range[0]+config.range[2])/2,(config.range[1]+config.range[3])/2],
        zoom: 17
    })
});
if(pointsArr != null){
    var rings = [];
    rings.push(pointsArr);
    var polygonFeature = new ol.Feature({
        geometry: new ol.geom.Polygon(rings)
    });
    vectorSource.addFeature(polygonFeature);
    map.getView().setCenter(rings[0][0]);
}