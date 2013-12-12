
jQuery(document).ready(function() {

	$("input[id^='emailbutton']").on('click',function() {

        var me = $(this);
        var id = me.attr('id');
        var feature_id = id.replace('emailbutton','');

        var project=$('#projectName').val();

	    $.ajax({
                url: "/test/email/"+feature_id+"/"+project+"/",
                }).done(function(data) {
                    alert("Successfully Sent");
                           });

});
});
