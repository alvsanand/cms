var $j = jQuery.noConflict();


scroll = function(id) {
	$j(this).scrollTop($j('#' + id + '').position().top);
};
  
doAfter = function(time, f) {
	setTimeout(f, time);
};