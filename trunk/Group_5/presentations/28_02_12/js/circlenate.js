function circlenate(){
	var targets = $('.step');
	var stepdeg = 360/targets.length;
	var r = 100+(50*targets.length)+targets.outerWidth();
	targets.each(function(i,v){
		var target = $(v);
		var tardeg = convert("degs", -stepdeg*i);
		var x =  r * Math.sin(tardeg);
		var z = r * Math.cos(tardeg);
		target.attr("data-x", z);
		target.attr("data-z", x);
		target.attr("data-rotate-y", stepdeg*i);
	});
}

function convert($type, $num) {
    if ($type == "rads") {
          $result = $num*180/Math.PI();
        }

    if ($type == "degs") {
          $result = $num*Math.PI/180;
        }

    return $result;
  }