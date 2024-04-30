(function(document, $) {
	"use strict";
	
$(document).on('dialog-ready', function(e) {
	if($(".hideSlider").prop("checked")){
		$(".inlineSelector").show();
	}else{
		$(".inlineSelector").prop("checked", false)
		$(".inlineSelector").hide();
	}
	$(".hideSlider").change(function() {
		if(this.checked) {
			$(".inlineSelector").show();
		}else{
			$(".inlineSelector").prop("checked", false)
			$(".inlineSelector").hide();
		}
	});
});
})(document,Granite.$);