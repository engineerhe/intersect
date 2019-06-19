//添加矢量图层
var vectorSource = new ol.source.Vector({
    features: []
});
var selectedSource = new ol.source.Vector({
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
        }),
        new ol.layer.Vector({
            source: selectedSource,
            opacity:0.6,
            style: new ol.style.Style({
                fill: new ol.style.Fill({
                    color: 'blue'
                }),
                stroke: new ol.style.Stroke({
                    color: 'red',
                    width: 2
                })
            })
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
if(features != null){
    var data = [];
    for(var i=0;i<features.length;i++){
        var rings = features[i].geometry.rings;
        var polygonFeature = new ol.Feature({
            geometry: new ol.geom.Polygon(rings)
        });
        vectorSource.addFeature(polygonFeature);
        features[i].attributes["RATIO"]=(features[i].attributes["Shape_Area"]/features[i].attributes["TBMJ"]*100).toFixed(3);
        features[i].attributes["rings"]=rings;
        data.push(features[i].attributes);
    }
    layui.use(['table'], function(){
        var table = layui.table;
        table.render({
            elem: '#analysis-res-table'
            ,data:data
            ,height:250
            ,cols: [[
                {field:'TBBH_1', width:'7%', title: '图斑编号'}
                ,{field:'QSDWMC', width:'10%',title: '权属单位名称', }
                ,{field:'DLBM', width:'7%',title: '地类编码'}
                ,{field:'DLMC',width:'9%',title: '地类名称',}
                ,{field:'QSXZ', width:'8%',title: '权属性质'}
                ,{field:'TBMJ', width:'11%',title: '图斑面积'}
                ,{field:'TBDLMJ', width:'12%',title: '图斑地类面积'}
                ,{field:'XZDWMJ', width:'12%',title: '线状地物面积'}
                ,{field:'Shape_Area', width:'12%',title: '压占面积'}
                ,{field:'RATIO', width:'12%', title: '面积压占比(%)', sort: true}
            ]]
        });
        //监听行单击事件
        table.on('row(test)', function(obj){
            var polygonFeature = new ol.Feature({
                geometry: new ol.geom.Polygon(obj.data.rings)
            });
            selectedSource.clear();
            selectedSource.addFeature(polygonFeature);
        });
    });
}