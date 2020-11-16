var _window = $(window);

_window.on('load',function(){
    //.blockのそれぞれの要素を操作する(以下、.blockはeの変数に入る)
    $('.block').each(function(i, e){
        //.nameの高さと.contentの高さを足し合わせる
        var hei = $(e).children('.name').height() + $(e).children('.content').height() + 20;
        
        //合計値が50px以上であれば高さを変更する
        if (50 < hei) {
            $(e).css('height', hei + 'px');
        }
        else {
            $(e).css('height', 'auto');
        }
    })
});

_window.on('resize',function(){
    //.blockのそれぞれの要素を操作する(以下、.blockはeの変数に入る)
    $('.block').each(function(i, e){
        //.nameの高さと.contentの高さを足し合わせる
        var hei = $(e).children('.name').height() + $(e).children('.content').height() + 20;
        
        //合計値が50px以上であれば高さを変更する
        if (50 < hei) {
            $(e).css('height', hei + 'px');
        }
        else {
            $(e).css('height', 'auto');
        }
    })
});