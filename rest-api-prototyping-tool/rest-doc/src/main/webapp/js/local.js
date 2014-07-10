$(document).ready(function() {
	$('.representation a.responseLink').bind('click', function(e) {
		e.preventDefault();
		
		var preTag = $(this.parentNode.getElementsByTagName("pre")[0]);
		preTag.slideToggle("fast", function() {
			if(preTag._loaded) {
				return;
			}
			
			var anchor = $(e.target);
			var url = anchor.attr('href');
			var clazz = anchor.attr("class");
			var postData = null;
			
			if (preTag.hasClass('POST')) {
				postData = {
					dummy : 'This is needed to force POST rather than get'
				};
			}
			
			preTag.text("Loading...");
			preTag.load(url, postData, function(_, _, xhr) {
				this._loaded = true;

				var str = xhr.responseText;
				str = str.trim();

				if(xhr.getResponseHeader("Content-Type") == "application/xml") {
					str = vkbeautify.xml(str);
				} else {
					str = vkbeautify.json(str);
				}
				
				preTag.text(str);
				preTag.addClass("prettyprint");
				prettyPrint();
				preTag.removeClass("prettyprint");
				
				// expanding the pre nodes affects document height so update scrollspy to make sure
				// table of contents on the left has the right item selected...
				$('[data-spy="scroll"]').each(function () {  
					$(this).scrollspy('refresh')  
				});
			});
		});
	});

	prettyPrint();
	$("pre").each(function() {
		var me = $(this);
		
		if (me.hasClass("prettyprint")) {
			me.removeClass("prettyprint");
		}
	});
});