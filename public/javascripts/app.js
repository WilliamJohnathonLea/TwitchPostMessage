$(document).ready(function(){
    var form = $('form');
    var submitBtn = $('#btn-submit');

    var downloadSoundFile = function(name) {
        var soundSrc = '/messages/' + name;
        var sound = new Howl({
            src: [ soundSrc ]
        });
        sound.play();
    };

    submitBtn.on('click', function(){
        submitBtn.attr('disabled', 'disabled');
        submitBtn.html('Processing...');
        form.submit();
    });

//    setInterval(function(){
//        $.ajax({
//            type: 'GET',
//            url: '/messages',
//            success: function(data) {
//                if(typeof(data) !== 'undefined' && data !== null){
//                    downloadSoundFile(data.message)
//                }
//            }
//        });
//    }, 5000);

});
