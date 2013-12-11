
jQuery(document).ready(function() {

	$('#emailbutton').on('click',function() {
        alert("sending email");
        var feature=$('#feature').val();
        var project=$('#projectName').val();
	    $.ajax({
                url: "/test/email/"+feature+"/"+project+"/",
                }).done(function(data) {
                    alert("success");
                           });

});
});
