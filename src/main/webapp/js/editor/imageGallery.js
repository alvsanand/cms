/**
 @preserve CLEditor Advanced Table Plugin v1.0.0
 http://premiumsoftware.net/cleditor
 requires CLEditor v1.2.2 or later
 
 Copyright 2010, Sergio Drago
 Dual licensed under the MIT or GPL Version 2 licenses.

 Based on Chris Landowski's Table Plugin v1.0.2
*/

(function($) {  
  $.cleditor.buttons.imageGallery = {
    name: "imageGallery",
    css: {
        backgroundImage: "URL('" + context + "/js/editor/imageGallery.png')"
    },
    title: "Insert image from repository",
    command: "inserthtml",
    popupName: "imageGalleryFloatRight",
    popupClass: "ui-editor-prompt",
    popupContent:
        "<table cellpadding=0 cellspacing=0>" +
        "<tr>" +
        "<td style=\"padding-right:6px;\"><input name='float' type='radio' value='right'/>Float right</td>" +
        "</tr>" +
        "<tr>" +
        "<td style=\"padding-right:6px;\"><input name='float' type='radio' value='left'/>Float left</td>" +
        "</tr>" +
        "</table>" +
        "<input type=button value='Select image'/>",
    buttonClick: imageGalleryButtonClick
  };
  
  function imageGalleryButtonClick(e, data) {
	  // Wire up the submit button click event handler
	  $(data.popup).children(":button")
	      .unbind("click")
	      .bind("click", function(e) {
	    	  var editor = data.editor;
	    	  var button = data.button;
	    	  var popup = data.popup;
	    	    
			  var $text = $(popup).find(":checked");
			  
			  if($text.val()=='right'){
				  css = "float: right; margin: 5px;";  
			  }
			  
			  if($text.val()=='left'){
				  css = "float: left; margin: 5px;";  
			  }			  
			  
			  dataExt = data;
			  showImageGallery();
		      
			  editor.hidePopups();
			  
			  return false;
	  });
  }

  $.cleditor.defaultOptions.controls = $.cleditor.defaultOptions.controls
    .replace("link ", " link imageGallery ");
})(jQuery);

var css;
	
var dataExt;
function selectUrl(url) {
	  imageDialog.hide();
	  dataExt.editor.focus();
	  
	  var image = '<img src="' + url + '"' + ((css!="")?(' style="' + css +  '"'):'') + '/>';    
	  
	  dataExt.editor.execCommand(dataExt.command, image, null, dataExt.button);
}