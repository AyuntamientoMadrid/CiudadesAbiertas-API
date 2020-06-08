
$( document ).ready(function(){
    $( "#datepicker" ).datepicker( $.datepicker.regional[ "es" ] );
    $( "#datepicker" ).datepicker();

    $( ".calendar" ).datepicker({
        showOn: "button",
        buttonImage: "images/calendar.png",
        buttonImageOnly: true
    });
    $( ".from" ).datepicker({
        defaultDate: "+1w",
        showOn: "button",
        buttonImage: "images/calendar.png",
        buttonImageOnly: true,  
        onClose: function( selectedDate ) {
            $( "#to" ).datepicker( "option", "minDate", selectedDate );
        }
    });
    $( ".to" ).datepicker({
        defaultDate: "+1w",
        showOn: "button",
        buttonImage: "images/calendar.png",
        buttonImageOnly: true,
        onClose: function( selectedDate ) {
            $( "#from" ).datepicker( "option", "maxDate", selectedDate );
        }
    });

    $(".calendario_festivos .ui-datepicker-next").click(function(event){
        event.preventDefault();
        var selected_container = $(this).closest( ".container" );
        selected_container.removeClass("selected");
        selected_container.next(".container").addClass("selected");
    });
    $(".calendario_festivos .ui-datepicker-prev").click(function(event){
        event.preventDefault();
        var selected_container = $(this).closest( ".container" );
        selected_container.removeClass("selected");
        selected_container.prev(".container").addClass("selected");
    });

});
