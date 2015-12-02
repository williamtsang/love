// 全局名称空间
(function(G){
    // 运行环境
    var ua = navigator.userAgent;
    // Base path
    G.base = location.href.match(/[^?#]*\//)[0];
    // winxin
    G.weixin = /MicroMessenger/.test(ua);
    // APP Environment
    G.env = !G.weixin ? 'other' : 'weixin';
    G.bridge = G.env!=='other';
    // 是否调试
    G.debug = true;//!!location.port || location.hostname === 'localhost' || ~location.hostname.indexOf('172.20');
    // 全局参数
    G.params = {};
    // 微信默认分享配置
    G.wxData = {
        title: '天天天蓝',
        desc: '每一天，都是蓝天，上帝保佑你！',
        link: G.base,
        imgUrl: G.base + 'img/help.jpg'
    };
})(window.G = {});


// 加载样式
(function(){
    var MIN = G.debug ? '' : '.min';
    getCSS('css/base'+ MIN + '.css');
    getCSS('css/pages'+ MIN + '.css');
    function getCSS(url) {
        var node = document.getElementById('seajsnode'),
            el = document.createElement('link');
        el.rel = 'stylesheet';
        el.href = url;
        node.parentNode.insertBefore(el, node);
    }
})();


// seajs配置
seajs.config({
    //base: './',
    debug: G.debug,
    paths: {
        'dist': G.debug ? 'src' : 'dist'
    },
    map: G.debug ? [['.min.js', '.js']] : null,
    alias: {
        '$': 'common/zepto/1.1.6/zepto.min.js',
        'fastclick': 'common/fastclick/1.0.6/fastclick.min.js',
        'dragend': 'common/dragend/0.2.0/dragend.min.js',
        'swipe': 'common/swipe/2.0.0/swipe.min.js',
        'chart': 'common/chartjs/1.0.2/Chart.min.js',
        'app': 'dist/app'
    }
});

// 启动应用
seajs.use(
    ['app',({
        weixin:'http://res.wx.qq.com/open/js/jweixin-1.0.0.js'
    })[G.env] || ''],
function(app, wx){


    // 微信和云之家下隐藏标题栏
    if (G.env === 'weixin') {
        $('html').addClass('hide-title');

	    // Weixin share
	    // 在 page 中定义：wxData 和 wxCallback
        var jsApiList = ["onMenuShareAppMessage","onMenuShareQQ","onMenuShareTimeline","onMenuShareWeibo"],
            wxConfig = {
                debug: false,
                jsApiList: jsApiList.slice(0)
            };
        $.get('/api/v1/web/signature?url=' + encodeURIComponent(location.href.split('#')[0]), function(d){
            var data;
            if (d.status === 200) {
                data = d.data;
                app.session.set('wxConfig', data);
            } else {
                data = app.session.get('wxConfig');
            }
            if (!data) return;

            wx.config( $.extend(wxConfig, data) );
        });
        wx.ready(function(){
            jsApiList.forEach(function(item){
                wx[item]({
                    trigger: function (res) {
                        //用户点击发送给朋友;
                        $.extend(this, G.wxData, app.page.wxData);
                        if (item==='onMenuShareTimeline') {
                            this.title = this.desc;
                        }
                    },
                    success: function (res) {
                        //已分享;
                        if (typeof app.page.wxCallback === 'function') app.page.wxCallback(res);
                    }/*,
                    cancel: function (res) {
                        //已取消;
                    },
                    fail: function (res) {
                        //alert(JSON.stringify(res));
                    },
                    complete: function() {
                        
                    }*/
                });
            });
        });
    }

    // 标记应用类型
    app.storage.set('app_type', G.params.app_type = app.storage.get('app_type') || 'p');

    // 应用初始化
    app.init({
        index: 'index',
        useTransition: true
    })
    // 退出登陆
    .on('logout', function(){
        var pageParams = app.session.get('app_page_params');
        app.session.clear();
        app.session.set('app_page_params', pageParams);
    })
    // 获取页面之前
    .on('beforegetpage', function(e){
        var params = e.params;
        if (params.app_type) {
            app.storage.set('app_type', (G.params.app_type = params.app_type) );
        }
        if (params.u && params.u.length%4===0) {
            app.params.u = params.u;
            // 携带推荐人信息
            G.wxData.link = G.base + '?u=' + params.u;
            app.session.set('username', unescape(decodeURIComponent(window.atob( params.u ))));
        }
    })
    // 加载页面
    .on('loadpage', function(e){
        //_hmt.push(['_trackPageview', e.url]);
    });

    // go page
    app.go("role");
});