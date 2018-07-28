$(document).ready(function(){
    var form = $('form');
    var submitBtn = $('#btn-submit');

    submitBtn.on('click', function(){
        submitBtn.attr('disabled', 'disabled');
        submitBtn.html('Processing...');
        form.submit();
    });
});
