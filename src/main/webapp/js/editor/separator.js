/**
 @preserve CLEditor Advanced Table Plugin v1.0.0
 http://premiumsoftware.net/cleditor
 requires CLEditor v1.2.2 or later
 
 Copyright 2010, Sergio Drago
 Dual licensed under the MIT or GPL Version 2 licenses.

 Based on Chris Landowski's Table Plugin v1.0.2
*/

(function($) {
  
  // Define the table button
  $.cleditor.buttons.separator = {
    name: "separator",
    css: {
        backgroundImage: "URL('" + context + "/js/editor/separator.png')"
    },
    title: "Insert article Separator",
    command: "inserthtml",
    popupName: "",
    popupContent: "",
    buttonClick: separatorButtonClick
  };

  // Add the button to the default controls
  $.cleditor.defaultOptions.controls = $.cleditor.defaultOptions.controls
    .replace("rule ", "rule separator ");
  
  // Table button click event handler
  function separatorButtonClick(e, data) {
    // Get the editor
    var editor = data.editor;
    var button = data.button;

    // Build the html
    var html = '<hr id="separator" noshade="true" style="height: 10px; border-style: dashed;"/>';    	    
	  
	var separator = editor.doc.getElementById("separator");
	if(separator!=null){
		editor.showMessage("The summary separator has already added", button);
		return false;
	}
	if(editor.$frame[0].contentWindow.getSelection().getRangeAt(0)['startContainer'].nodeName!='BODY'){
		editor.showMessage("The summary separator has to be added in the root of the article", button);
		return false;
	}
	  
    editor.execCommand(data.command, html, null, button);
	
    editor.hidePopups();
    editor.focus();
    
    return false;
  }
})(jQuery);