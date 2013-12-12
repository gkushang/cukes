jQuery(document).ready($(function() {
    // when you click the link w/ a class of 'linkage'
    $('.scenario2').click(function() {
		 var cl = $(this).attr('class');
		 var id = '#'+cl;
		 // alert(id)
        // scroll to the DIV w/ an ID of 'three'
        // $.scrollTo( '#scenario10', 800, {easing:'swing'} );
 //        // highlight the DIV using jQuery UI effect
 //        $('#scenario10').effect('highlight', {}, 3000);
    $('html, body').animate({
                        scrollTop: $("#scenario10").offset().top
                    }, 2000);
    });
}));