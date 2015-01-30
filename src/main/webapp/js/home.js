var pageCompany;
var pageProject;
var pageGPRS;
var pageAmmeter;

$(document).ready(function(){
		
	//setMenu(1);
	
	function setMenu(type) {
		if (type == 1) {
			$('li[id^=esbMenu]').children().find('li').each(function(){
				if (!$(this).hasClass('dropdown-toggle')) {
					$(this).removeAttr('href');
					$(this).addClass('ui-state-disabled');
				}
			});
		}
		
		$(".ui-state-disabled").each(function(){
			$(this).removeAttr('href');
		});
	}
	
	$(".ui-state-disabled1").click(function(){
			$(this).removeAttr('href');
			$(this)[0].removeAttribute("href");
		});
	
	/**
	
	$('li').click(function(){
		if ($(this).hasClass('ui-state-disabled')) {
			alert('xx');
		}
	});
	*/
});