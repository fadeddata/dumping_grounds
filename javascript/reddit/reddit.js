$(document).ready(function(){
	$.getJSON('http://www.reddit.com/.json?jsoncallback=?', function(data){ 
		$.each(data.data.children, function(i, item){
			$("<p/>").text(item.data.title).appendTo("#reddit");
		    }); 
	    });
    });

