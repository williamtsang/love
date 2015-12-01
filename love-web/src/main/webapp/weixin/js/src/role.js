define(function(require, exports, module){
    var $ = require("$"),
        app = require("app"),
        util = require('util'),
        dragend = require('dragend');

    // 更多 ///////////////////////////////////////////////////////////////////////

    module.exports = {
        init: function(){
            var me = this;

            $('#dragend')
            .css({height: me.$el.css('height')})
            .dragend({
                direction: 'vertical',
                onSwipeStart: function(container, el, index) {
                    $('.dragend-page').eq(index).siblings().css('visibility', 'hidden');
                },
                onSwipeEnd: function(container, el, index) {
                    me.initAnimate($(el), index);
                }
            });
            me.initAnimate($('.dragend-page:first-child'));
        },
        initAnimate: function($page, index) {
            $page.find('[data-animate]').each(function(){
                animate(this, this.getAttribute('data-animate'));
            }).end().css('visibility', 'visible');
        },
        markView: function(a) {
            util.storage.set('app_viewstart', 1);
            util.storage.set('app_type', util.unparam(a.getAttribute('href')));
        }
    };

    function animate(el, x) {
        $(el).addClass(x + ' animated').one('webkitAnimationEnd MSAnimationEnd animationend', function(){
            $(this).removeClass(x + ' animated');
        });
    }

});