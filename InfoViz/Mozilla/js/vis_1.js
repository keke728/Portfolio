var format = d3.format(",");

// Set tooltips
var tip = d3.tip()
  .attr('class', 'd3-tip')
  .offset([-10, 0])
  .html(function(d) {
    var tech_ratio = "NA";
    if (d.techratio !== null && d.techratio !== undefined) {
      tech_ratio = format(d.techratio.toPrecision(3) * 10) + "%";
    }

    return "<strong>Country: </strong><span class='details'>" + d.properties.name + "<br></span>" + "<strong>Tech Ratio: </strong><span class='details'>" + tech_ratio + "</span>";
  });

var margin = {
    top: 0,
    right: 0,
    bottom: 0,
    left: 0
  },
  width = 960 - margin.left - margin.right,
  height = 500 - margin.top - margin.bottom;


var world_color = d3.scaleThreshold()
  .domain(d3.range(0, 10))
  .range(d3.schemeOranges[9]);

var v1_path = d3.geoPath();

var v1_svg = d3.select("#vis_1")
  .append("svg")
  .attr("width", width)
  .attr("height", height)
  .append('g')
  .attr('class', 'map');

var projection = d3.geoMercator()
  .scale(130)
  .translate([width / 2, height / 1.5]);

var v1_path = d3.geoPath().projection(projection);

v1_svg.call(tip);

queue()
  .defer(d3.json, "data/world_countries.json")
  .defer(d3.csv, "data/vis_1.csv")
  .await(ready);

function ready(error, world, tech) {
  if (error) throw error;

  var ct = document.getElementById("v1_data_select");
  var selectVal = ct.options[ct.selectedIndex].value;
  var selectSub = ct.options[ct.selectedIndex].text.replace(/\s+/g, '');

  var techRatioById = {};
  var techTotal = {};
  var techSub = {};

  tech.forEach(function(d) {
    techRatioById[d.id] = +d[selectVal] * 10;
    techSub[d.id] = +d[selectSub];
    techTotal[d.id] = +d.Q1total;
  });

  world.features.forEach(function(d) {
    d.techratio = techRatioById[d.id];
    d.techsub = techSub[d.id];
    d.techtotal = techTotal[d.id];
  });

  v1_svg.append("g")
    .attr("class", "v1_world")
    .selectAll("path")
    .data(world.features)
    .enter().append("path")
    .attr("d", v1_path)
    .style("fill", function(d) {
      var rate = 0;
      if (techRatioById[d.id] !== null && techRatioById[d.id] !== undefined) {
        rate = techRatioById[d.id];
      }
      return world_color(rate);
    })
    .style('stroke', 'white')
    .style('stroke-width', 1.5)
    .style("opacity", 0.8)
    .style("cursor", "pointer")
    // tooltips
    .style("stroke", "white")
    .style('stroke-width', 0.3)
    .on('mouseover', function(d) {
      tip.show(d);
      if (d.techtotal !== null && d.techtotal !== undefined) {
        dountTip(d.techsub, d.techtotal);
      }

      d3.select(this)
        .style("opacity", 1)
        .style("stroke", "white")
        .style("stroke-width", 3);
    })
    .on('mouseout', function(d) {
      tip.hide(d);

      d3.select(this)
        .style("opacity", 0.8)
        .style("stroke", "white")
        .style("stroke-width", 0.3);
    });

  v1_svg.append("path")
    .datum(topojson.mesh(world.features, function(a, b) {
      return a.id !== b.id;
    }))
    .attr("class", "names")
    .attr("d", v1_path);
}

function v1DataChange() {
  queue()
    .defer(d3.json, "data/world_countries.json")
    .defer(d3.csv, "data/vis_1.csv")
    .await(ready);
}

function dountTip(sub, total) {
  d3.select('#vis_1_donut')
    .html("");

  var donut = donutChart()
    .width(250)
    .height(250)
    .cornerRadius(3) // sets how rounded the corners are on each slice
    .padAngle(0.015) // effectively dictates the gap between slices
    .variable_sub(sub)
    .variable_total(total);

  d3.select('#vis_1_donut')
    .call(donut);
}

//draw the donut donut_chart
function donutChart() {
  var width,
    height,
    margin = {
      top: 10,
      right: 10,
      bottom: 10,
      left: 10
    },
    colour = d3.scaleOrdinal(d3.schemeCategory20c), // colour scheme
    variable, // value in data that will dictate proportions on donut_chart
    variable_sub,
    variable_total,
    category, // compare data by
    padAngle, // effectively dictates the gap between slices
    floatFormat = d3.format('.4r'),
    cornerRadius, // sets how rounded the corners are on each slice
    percentFormat = d3.format(',.2%');

  function donut_chart() {
    var dataset = [{
        vsub: "s1",
        value: variable_sub
      },
      {
        vsub: "s2",
        value: (variable_total - variable_sub)
      },
    ];

    var radius = Math.min(width, height) / 2;

    var v1d_color = d3.scaleOrdinal(d3.schemeCategory20c);

    var vd_svg = d3.select('#vis_1_donut')
      .append('svg')
      .attr('width', width)
      .attr('height', height)
      .append('g')
      .attr('transform', 'translate(' + (width / 2) +
        ',' + (height / 2) + ')');

    var donutWidth = 55;

    var arc = d3.arc()
      .innerRadius(radius - donutWidth)
      .outerRadius(radius);

    var pie = d3.pie()
      .value(function(d) {
        return d.value;
      })
      .sort(null);

    var vd_path = vd_svg.selectAll('path')
      .data(pie(dataset))
      .enter()
      .append('path')
      .attr('d', arc)
      .attr('fill', function(d, i) {
        return v1d_color(d.data.vsub);
      });

    var text = vd_svg
      .append("text")
      .attr("text-anchor", "middle")
      .attr('font-size', '1.5em')
      .attr('y', 12)
      .text(variable_sub + "/" + variable_total);
  }

  // getter and setter functions.
  donut_chart.width = function(value) {
    if (!arguments.length) return width;
    width = value;
    return donut_chart;
  };

  donut_chart.height = function(value) {
    if (!arguments.length) return height;
    height = value;
    return donut_chart;
  };

  donut_chart.margin = function(value) {
    if (!arguments.length) return margin;
    margin = value;
    return donut_chart;
  };

  donut_chart.radius = function(value) {
    if (!arguments.length) return radius;
    radius = value;
    return donut_chart;
  };

  donut_chart.padAngle = function(value) {
    if (!arguments.length) return padAngle;
    padAngle = value;
    return donut_chart;
  };

  donut_chart.cornerRadius = function(value) {
    if (!arguments.length) return cornerRadius;
    cornerRadius = value;
    return donut_chart;
  };

  donut_chart.colour = function(value) {
    if (!arguments.length) return colour;
    colour = value;
    return donut_chart;
  };

  donut_chart.variable = function(value) {
    if (!arguments.length) return variable;
    variable = value;
    return donut_chart;
  };
  donut_chart.variable_sub = function(value) {
    if (!arguments.length) return variable_sub;
    variable_sub = value;
    return donut_chart;
  };
  donut_chart.variable_total = function(value) {
    if (!arguments.length) return variable_total;
    variable_total = value;
    return donut_chart;
  };

  donut_chart.category = function(value) {
    if (!arguments.length) return category;
    category = value;
    return donut_chart;
  };

  return donut_chart;
}