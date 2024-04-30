(function(document, $) {
	"use strict";
	
$(document).on('dialog-ready', function(e) {

	// Updating the title of the collapsible header
	$(".pricingMatrixSegments").each(function(index) { 
		var numberOfLines = $(this).find('.coral3-NumberInput-input').val();
		$(this).find('.coral-Collapsible-title').each(function(){
			var a = $(this).html();
			$(this).html(a + " upto " + numberOfLines + " lines");
		});
	});
	

	$(".pricingMatrixLightboxCheck").each(function( index ) {
		if($(this)[0].hasAttribute("checked")){
			$(this)[0].parentElement.nextElementSibling.style.display="none";
			$(this)[0].parentElement.nextElementSibling.nextElementSibling.style.display="block";
		}else{
			$(this)[0].parentElement.nextElementSibling.style.display="block";
			$(this)[0].parentElement.nextElementSibling.nextElementSibling.style.display="none";
		}
		
	});
	
	$(".pricingMatrixLightboxCheck").click(function(e) {
        showHide(e);
    });

});



function showHide(e){

    if(e.target.parentElement.hasAttribute("checked")){
    	e.target.parentElement.parentElement.nextElementSibling.style.display="block";
		e.target.parentElement.parentElement.nextElementSibling.nextElementSibling.style.display="none";
	}else{
		e.target.parentElement.parentElement.nextElementSibling.style.display="none";
    	e.target.parentElement.parentElement.nextElementSibling.nextElementSibling.style.display="block";
	}

    }


})(document,Granite.$);