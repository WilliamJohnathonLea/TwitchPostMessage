$(document).ready(function(){
    var form = $('form');
    var submitBtn = $('#btn-submit');

    submitBtn.on('click', function(){
        submitBtn.attr('disabled', 'disabled');
        submitBtn.html('Processing...');
        form.submit();
    });

    setInterval(function(){
        $.ajax({
            type: 'GET',
            url: '/messages',
            success: function(data) {
                console.log('success', data)
            }
        });
    }, 5000);

});
