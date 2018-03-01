jQuery(document).ready(function(){
	$('.date').datepicker({
		autoclose: true,
		format: 'yyyy/mm/dd'
		});
		
	$('#wsdl').popover({		
	    trigger: 'hover',	 
	    content: 'Click here to get WSDL file'
	});
});
