    $(document).ready(function() { 
 
        $('#test').click(function() { 
        	$.blockUI({ message: $('#question'), css: { width: '275px' } }); 
        }); 
 
        
 
        $('#no').click(function() { 
            $.unblockUI(); 
            return false; 
        }); 
 
    }); 


function blockMessage() {
	$.blockUI({ message: "<h2>Remote call in progress...</h2>" });
}

function unblock() {
	$.unblockUI();
}