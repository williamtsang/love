define(function(require, exports, module){
    var $ = require('$'),
        util = require('util'),
        tpl = require('tpl'),
        FastClick = require('fastclick'),
        app = window.app = {},
        appCache = window.applicationCache,
        location = window.location,
        history = window.history,
        CLICK = window.CLICK = 'click';

    module.exports = app;

    $.extend(app, {
        index: 'index',
        useTransition: true,   //是否使用页面切换动画
        history: [],
        session: util.session,
        storage: util.storage,
        init: init,
        update: update,    //更新缓存
        hash: null,
        pages: {},          //所有页面
        page: {},           //当前页面
        lastPage: {},       //上一个页面
        post: post,         //post请求
        go: go,             //地址导航
        refresh: refresh,   //刷新当前页
        showMsg: showMsg,   //显示消息
        hideMsg: hideMsg,   //隐藏消息
        setTitle: setTitle  //修改页面标题
    });

    // Events
    var events = app.events = {};
    $.extend(app, {
        on: function(type, handler) {
            (events[type] || (events[type] = [])).push(handler);
            return this;
        },
        off: function(type, handler) {
            if (!(type || handler)) {
                events = {};
                return this;
            }
            var list = events[type];
            if (list) {
                if (handler) {
                  for (var i = list.length - 1; i >= 0; i--) {
                    if (list[i] === handler) {
                      list.splice(i, 1);
                    }
                  }
                }
                else {
                  delete events[type];
                }
            }
            return this;
        },
        emit: function(type, data) {
            var list = events[type], fn;

            if (list) {
                list = list.slice();
                while ((fn = list.shift())) {
                  fn.call(this, data);
                }
            }
            return this;
        }
    });

    function update(callback, swap) {
        app.checking = callback;
        appCache.isSwap = swap;
        appCache.update();
    }


    // app initialization
    function init(options) {
        if (init.hasInit) return;
        init.hasInit = true;

        if (options) {
            $.extend(app, options);
        }

        app.params = util.unparam(location.search) || {};
        app.$activeXHR = [];

        var lastid = util.session.get('app_lastid');
        if (lastid) app.lastid = lastid;

        // add className to html element
        var cls = ['os-' + G.env];
        if (/Chrome\//.test(navigator.userAgent)) {
            $.os.chrome = true;
        }
        $.each($.os, function(k, v){
            if (k === 'version') return;
            if (v) cls.push('os-' + k);
        });
        $('html').addClass(cls.join(' '));

        // global ajax settings
        $.ajaxSettings.headers = {
            'X-Mobile': true
        };

        var xhrnum = 0;
        $(document)
        .on('ajaxStart', function() {
            xhrnum++;
            app.showMsg("加载中", true);
        })
        .on('ajaxStop', function() {
            --xhrnum;
            if (app.errorMsg) {
                app.showMsg(app.errorMsg);
                app.errorMsg = null;
            } else {
                if (app.showMsg.isLoading) app.hideMsg();
            }
        })
        .on('ajaxBeforeSend', function(e, xhr, options) {
            app.$activeXHR.unshift(xhr);
            var token = resetToken();
            if (token) {
                xhr.setRequestHeader('X-CSRF-Token', util.md5(token));
            }
        });

        function resetToken() {
            var XTOKEN = 'csrftoken', token = util.cookie.get(XTOKEN);
            if (token) {
                util.cookie.remove(XTOKEN);
                util.storage.set(XTOKEN, token);
            } else {
                token = util.storage.get(XTOKEN) || util.md5(new Date().getTime());
            }
            return token;
        }

        // navigation
        $(document.body)
        .on(CLICK, 'a', function(e){
            var href = this.getAttribute('href');
            callHandler(this);
            if (!href || href.charAt(0) === '#') {
                e.preventDefault();
                app.go(href);
            }
        })
        .on(CLICK, 'li', function(e){
            if (e.target.tagName === 'A') return;
            var href = this.getAttribute('data-href');
            callHandler(this);
            if (href) {
                app.go(href);
            }
        });
        function callHandler(el) {
            var page, fn = el.getAttribute('data-fn');
            if (fn) {
                page = app.page;
                if (page && page[fn]) page[fn].call(page, el);
            }
        }

        // init FastClick
        FastClick.attach(document.body);

        // applicationCache
        ['checking', 'noupdate', 'downloading', 'updateready', 'cached'].forEach(function(type){
            appCache.addEventListener(type, function(e){
                if ($.isFunction(app.checking)) {
                    app.checking(appCache.status);
                }
                if (e.type === 'updateready' && appCache.isSwap !== false) {
                    // 替换缓存
                    appCache.swapCache();
                    // 如果是手动触发（检查更新）
                    if ($.isFunction(app.checking)) {
                        console.log('app: begin update ...');
                        setTimeout(function(){
                            location.reload();
                        }, 1000);
                    } else {
                        // 使更新后的缓存立即生效
                        location.reload();
                    }
                }
                if (appCache.status === 1) {
                    app.checking = null;
                }
            });
        });

        // online && offline
        window.addEventListener('online', function(e){
            console.log('app:online');
            $('.app-msg-offline').remove();
            $('body').removeClass('app-offline');
            app.emit('online');
        });
        window.addEventListener('offline', function(){
            console.log('app:offline');
            $('body').addClass('app-offline').append('<div class="app-msg-offline"><i></i>网络连接不可用</div>');
            app.emit('offline');
        });

        // forward & back & click a link
        /*var timeStamp = 0;
        window.addEventListener('popstate', function(e){
            var arr = resolve(location.hash);
            var url = arr[0] === 'login' ? app.index : location.hash;
            timeStamp = e.timeStamp;
            app.go(url, null);
        });*/
        window.addEventListener('hashchange', function(e){
            /*// 如果已经执行了popstate，就忽略hashchange
            if ( Math.abs(e.timeStamp - timeStamp) < 120 ) {
                return;
            } else {
                timeStamp = e.timeStamp;
            }*/
            var arr = resolve(location.hash);
            var url = arr[0] === 'login' ? (util.session.get('app_lastid') || app.index) : location.hash;
            app.go(url, null, true);
        });

        // handle refresh page (F5)
        window.onbeforeunload = function(){
            util.session.set('app_page_params', app.page.params);
        };

        // init page
        app.on('initpage', function(e) {
            if (e.page.init) {
                e.page.init.call(e.page, e.data);
            }
            if (app.page.id === app.lastid || app.page === app.lastPage) return;
            destroy(app.lastPage);
        });

        if (app.useTransition) initTransition();

        return app;
    }


    // 应用通用请求接口
    
    //var isPageXHR;
    function post(url, data, callback, fallback) {
        if ($.isFunction(data)) {
            callback = data;
            fallback = callback;
            data = null;
        }

        if (location.protocol==='file:' || app.redirectid && app.pages[app.redirectid] && app.pages[app.redirectid].local)
            url = 'json/' + url.replace(/[\/\\\?]/g, '');

        return $.ajax({
            url: url,
            data: data,
            type: "post",
            dataType: 'json',
            timeout: 60*1000,
            /*beforeSend: function(xhr) {
                if (xhrnum++===0) {
                    app.showMsg("加载中", true);
                }
                xhr.activeid = xhrnum;
                app.activeXHR = xhr;
            },
            complete: function(xhr) {
                if (!--xhrnum) {
                    if (app.errorMsg) {
                        app.showMsg(app.errorMsg);
                        app.errorMsg = null;
                    } else if (app.showMsg.isLoading) {
                        app.hideMsg();
                    }
                }
            },*/
            success: function(d) {
                if (!d) return;
                if(d.status==200 || d.status==250) {
                    if ($.isFunction(callback)) {
                        callback.apply(this, arguments);
                    }
                }
                // 登录超时
                else if (d.status==6002 || d.status===80010) {
                    app.emit('logout');
                    if (app.redirectid) {
                        var page = app.pages[app.redirectid];
                        var redirect = encodeURIComponent(app.redirectid + '/' + (page && page._search ? page._search : ''));
                        //util.session.set('redirect', redirect);
                        app.go('login?redirect='+ redirect );
                    } else {
                        app.go('login');
                    }
                }
                else {
                    app.errorMsg = d.msg;
                    if ($.isFunction(fallback)) {
                        fallback.apply(this, arguments);
                    }
                }
            },
            error: function(xhr, errorType, error) {
                // 请求异常，暂时通过这种方式自动退出
                if (errorType === 'parsererror') {
                    app.emit('logout');
                    app.go("login");
                }
                else if (errorType==='timeout') {
                    app.errorMsg = '请求超时！';
                }
                if ($.isFunction(fallback)) {
                    fallback.apply(this, arguments);
                }
            }
        });
    }

    // parse URL
    function resolve(url) {
        var arr = url.split('#');
        url = arr.length === 1 ? arr[0] : arr[1];
        arr = url.replace(/^([!\/]*)?/, '').split('?');
        if (arr) arr[0] = arr[0].replace(/\/$/, '');
        return arr;
    }

    // navigator
    function go(url, params, isOrigin) {
        if (!url || url === '#') return;
        var id, search;
        if (typeof url === 'object') {
            id = url.id;
            search = url._search;
        } else {
            var arr = resolve(url);
            id = arr[0];
            search = arr[1] ? '?' + arr[1] : '';
        }
        var temp;
        switch (id) {
            case 'exit':
                return app.emit('exit');
            case '-1':
                temp = app.history[1];
                if (temp && 'login' !== temp.id) {
                    go(temp);
                } else {
                    go(app.index);
                }
                return;
        }
        if (id !== 'login') app.redirectid = id;
        var _params = $.extend({}, params, util.unparam(search));

        // 销毁旧页面的xhr
        var i = app.$activeXHR.length;
        while(i--) {
            app.$activeXHR[i].abort();
        }
        app.$activeXHR.length = 0;

        app.emit('beforegetpage', {params: _params});

        getPage(id, function(page){
            page.isOrigin = isOrigin;
            page.params = _params;
            page._search = search || '';
            app.hash = '#' + id + page._search;

            getTemplate(page.id, function(compiler){
                /*if (isPageXHR) {
                    app.activeXHR.abort();
                    isPageXHR = false;
                }*/
                if (page.data && $.isFunction(page.data)) {
                    if (page.data.length) {
                        //isPageXHR = true;
                        page.data.call(page, function(d){
                            //isPageXHR = false;
                            if (!d) return;
                            render(page.id, compiler, d);
                        });
                    } else {
                        render(page.id, compiler, page.data());
                    }
                    
                } 
                else if ($.type(page.data) === 'object') {
                    render(page.id, compiler, page.data);
                }
                else {
                    render(page.id, compiler);
                }
            });
        });
    }

    function getPage(id, callback) {
        var page = app.pages[id],
            url;

        if (!page) {
        	url = 'dist/'+ id;
            require.async(url, function(p){
                if (p) {
                    p.id = id;
                    app.pages[id] = p;
                    callback(p);
                } else {
                    console.log('Module "'+ id +'" has not defined!');
                }
            });   
        } else {
            callback(page);
        }
    }

    // 获取模板
    function getTemplate(id, callback) {
        var page = app.pages[id];
        // 从内存中取
        if (page.compiler) {
            callback(page.compiler);
        } else {
            if ('template' in page) {
                if (!page.template) {
                    page.compiler = tpl('');
                    callback(page.compiler);
                    return;
                } else if (page.template.charAt(0)==='<') {
                    page.compiler = tpl(page.template);
                    callback(page.compiler);
                    return;
                }
            }
            // 从指定路径或者pages目录请求模板
            $.get( page.template || 'pages/'+ id + '.html', function(res){
                if (res.charAt(0) === '{') {
                    var data = JSON.parse(res);
                    if (data && data.status === 6002) {
                        app.go('login?redirect=' + id);
                    }
                } else {
                    page.compiler = tpl(res);
                    callback(page.compiler);
                }
            });
        }
    }

    // 记录访问路径，并且保证记录不会无限，不会重复
    function logHistory(page) {
        if (page.id === app.lastid || page.id === 'login') return;
        var i = app.history.indexOf(page);
        if (-1 === i) {
            app.history.unshift(page);
            app.direction = 'next';
            if (page.id === app.index) app.history.length = 1;
        } else {
            app.history.splice(0, i);
            app.direction = 'prev';
        }
    }

    function initTransition() {
        app.on('beforepagein', function(e){
            if (!app.lastPage.$wrapper) {
                app.emit('initpage', {page:e.page, data:e.data});
                return;
            }
            // 如果只是刷新当前页
            if (app.page.id === app.lastid || app.page === app.lastPage) return;

            document.body.className = 'app-direction-' + app.direction;
            // 当前
            app.page.$wrapper
            .one('webkitAnimationEnd animationend', function(){
                $(this).removeClass('app-animated');
                app.emit('initpage', {page:e.page, data:e.data});
            })
            .addClass('app-page-active app-animated');
            
            // 上一页
            app.lastPage.$wrapper
            .one('webkitAnimationEnd animationend', function(){
                //app.page.$wrapper.removeClass('app-animated');
                $(this).removeClass('app-animated').hide();
            })
            .removeClass('app-page-active')
            .addClass('app-animated');
        });
    }

    function render(id, compiler, data) {
        var page = app.pages[id];

        // 处理数据
        if ($.type(data) !== 'object') data = {};
        data = $.extend({}, data);
        // 挂载变量，方便模板调用
        data._id = page.id = id;      //当前页面id
        data._util = util;            //工具方法
        data._params = page.params;   //页面参数
        page._data = data;

        app.lastid = app.page.id;
        app.lastPage = app.page;
        app.page = page;

        if (app.lastPage.id) {
            app.lastPage.$el.off();
            app.lastPage.$wrapper.off();
            if (app.lastPage.destroy) app.lastPage.destroy.call(app.lastPage);
        }
        

        // 记录访问轨迹
        logHistory(page);

        if ($.isFunction(page.enter)) page.enter.call(page);

        // 页面载入之前
        app.emit('beforeloadpage', {id: id, url: location.pathname + location.hash, page: page});

        var html = getHeader(page) + '<div class="app-content app-scroller">' + compiler(data) + '</div>' + getFooter(page);
        var elem = document.createElement('section');
        elem.className = 'app-page page-' + id.replace(/_|\//g, '-');
        elem.innerHTML = html;
        //page.$wrapper = ;

        
        if (app.$wrapper) {
            app.$wrapper.before($(elem));
        }
        else {
            document.body.appendChild(elem);
        }

        if (!app.useTransition || app.lastid === id) {
            destroy(app.lastPage);
        }

        app.$wrapper = page.$wrapper = $(elem);
        app.$el = page.$el = page.$wrapper.children('.app-content');
        
        // 页面进入之前
        app.emit('beforepagein', {page:page, data:data});

        if (!app.useTransition || app.lastid === id) {
            // 执行页面的init方法
            app.emit('initpage', {page:page, data:data});
        }

        if (!page.isOrigin && id !== app.lastid) {
            // 刷新页面的时候使用replaceState
            history[page.replaceState ? 'replaceState' : 'pushState'](null, null, page.stateURL || app.hash);
        }
        // 页面加载完成
        app.emit('loadpage', {id: id, url: location.pathname + location.hash, page: page});
    }


    var header_template = '\
        <div class="app-header">\
            <h1 class="app-title"><#=title#></h1>\
            <#for(var i=0;i<btn.length;i++){#>\
            <a class="app-button<#echo(btn[i].href==="#-1"?" app-backbutton":"")#>" href="<#=normalURL(btn[i].href)#>"><i class="<#=btn[i].icon#>"></i><#=btn[i].text#></a>\
            <#}#>\
        </div>';
    var header_compiler = tpl(header_template);

    function getHeader(page) {
        page = page || {};
        var header = page.header,
            html = '';

        if (header && header.length) {
            var title = header[0], data = {};

            document.title = page.title = data.title = title;
            app.emit('settitle', {title:title});
            // 微信环境bug
            if (G.env === 'weixin' && $.os.ios) {
                $('<iframe src="offline.html"></iframe>').on('load', function() {
                    var iframe = this;
                    setTimeout(function() {
                        $(iframe).off('load').remove();
                    }, 0);
                }).appendTo('body');
            };

            data.btn = header.slice(1);
            data.normalURL = normalURL;
            html = header_compiler(data);
        }
        return html;
    }

    var footer_template = ' \
        <div class="app-footer">\
            <#for(var i=0;i<btn.length;i++){#>\
            <a class="<#=btn[i].cls || "app-button"#><#=(pageTitle===btn[i].text||btn[i].href && btn[i].href.indexOf(pageId)!==-1 ? " current":"")#>" href="<#=normalURL(btn[i].href)#>">\
            <i class="<#=btn[i].icon#>"></i><#=btn[i].text#></a>\
            <#}#>\
        </div>';
    var footer_compiler = tpl(footer_template);

    function getFooter(page) {
        page = page || {};
        var footer = page.footer,
            html = '';

        if (footer && footer.length) {
            var data = {};
            data.btn = footer.slice(0);
            data.normalURL = normalURL;
            data.pageTitle = page.title;
            data.pageId = page.id;
            html = footer_compiler(data);
        }
        return html;
    }

    // 转换 p/profit 路径为：#!/p/profit/
    function normalURL(str) {
        if (!str) return str;
        var url = str.replace(/(#!?)?([^#!?]+)(.*)/, '#!/$2/$3').replace(/\/\//g, '/');
        var isSpecial = str.indexOf('{') !== -1;
        if (isSpecial) {
            var params = app.page.params ? app.page.params : util.session.get('app_page_params');
            if (params) {
                url = url.replace(/([^?=&]+=){([^&#]*)}/g, function(m, m1, m2){
                    return m1 + (params[m2] || '');
                });
            }
        }
        return url;
    }


    // 刷新页面
    function refresh() {
        app.go(app.page.id + app.page._search, app.page.params, true);
    }

    // 销毁页面
    function destroy(page) {
        if (!page.id) return;
        //app.hideMsg();
        page.$wrapper.remove();
        $('.app-page:hidden').remove();
    }

    // showMsg('加载中...', true) => loading
    // showMsg('xxxxxxxxx', 2000) => auto hide message
    function showMsg(msg, isLoading, callback) {
        var timer, html;
        if (typeof isLoading === 'number') {
            timer = isLoading;
            isLoading = false;
        } else if (typeof isLoading === 'function') {
            callback = isLoading;
            isLoading = false;
        }
        if (isLoading && showMsg.isLoading) return;
        showMsg.isLoading = isLoading;
        var $el = $('#app_msg');
        if (!$el.length) {
            $el = $('<div id="app_msg"></div>').appendTo('body');
        }
        $el[0].className = 'app-msg' + ( isLoading ? ' app-loading' : '' );
        html = '<div class="app-msg-box">';
        if (isLoading) {
            html += '<div class="loader">';
            for (var i=1; i<=3; i++) {
                html += '<i class="i'+ i +'"></i>';
            }
            html += '</div>'
        }
        html += msg + '</div>';
        $el[0].innerHTML = html;
        if (!isLoading) {
            if (showMsg.timer) clearTimeout(showMsg.timer);
            showMsg.timer = setTimeout(function(){
                hideMsg();
                if (typeof callback === 'function') callback();
            }, timer || 3000);
        }
    }

    function hideMsg() {
        $('#app_msg').removeClass().html('');
        showMsg.isLoading = null;
    }

    function setTitle(title) {
        app.page.$wrapper.find('h1.app-title').html(title);
        document.title = title;
        app.emit('settitle', {title:title});
    }
});


/*! 
 * JavaScript MD5 1.0.1
 * https://github.com/blueimp/JavaScript-MD5
 *
 * Copyright 2011, Sebastian Tschan
 * Licensed under the MIT license
 */
!function(a){"use strict";function b(a,b){var c=(65535&a)+(65535&b),d=(a>>16)+(b>>16)+(c>>16);return d<<16|65535&c}function c(a,b){return a<<b|a>>>32-b}function d(a,d,e,f,g,h){return b(c(b(b(d,a),b(f,h)),g),e)}function e(a,b,c,e,f,g,h){return d(b&c|~b&e,a,b,f,g,h)}function f(a,b,c,e,f,g,h){return d(b&e|c&~e,a,b,f,g,h)}function g(a,b,c,e,f,g,h){return d(b^c^e,a,b,f,g,h)}function h(a,b,c,e,f,g,h){return d(c^(b|~e),a,b,f,g,h)}function i(a,c){a[c>>5]|=128<<c%32,a[(c+64>>>9<<4)+14]=c;var d,i,j,k,l,m=1732584193,n=-271733879,o=-1732584194,p=271733878;for(d=0;d<a.length;d+=16)i=m,j=n,k=o,l=p,m=e(m,n,o,p,a[d],7,-680876936),p=e(p,m,n,o,a[d+1],12,-389564586),o=e(o,p,m,n,a[d+2],17,606105819),n=e(n,o,p,m,a[d+3],22,-1044525330),m=e(m,n,o,p,a[d+4],7,-176418897),p=e(p,m,n,o,a[d+5],12,1200080426),o=e(o,p,m,n,a[d+6],17,-1473231341),n=e(n,o,p,m,a[d+7],22,-45705983),m=e(m,n,o,p,a[d+8],7,1770035416),p=e(p,m,n,o,a[d+9],12,-1958414417),o=e(o,p,m,n,a[d+10],17,-42063),n=e(n,o,p,m,a[d+11],22,-1990404162),m=e(m,n,o,p,a[d+12],7,1804603682),p=e(p,m,n,o,a[d+13],12,-40341101),o=e(o,p,m,n,a[d+14],17,-1502002290),n=e(n,o,p,m,a[d+15],22,1236535329),m=f(m,n,o,p,a[d+1],5,-165796510),p=f(p,m,n,o,a[d+6],9,-1069501632),o=f(o,p,m,n,a[d+11],14,643717713),n=f(n,o,p,m,a[d],20,-373897302),m=f(m,n,o,p,a[d+5],5,-701558691),p=f(p,m,n,o,a[d+10],9,38016083),o=f(o,p,m,n,a[d+15],14,-660478335),n=f(n,o,p,m,a[d+4],20,-405537848),m=f(m,n,o,p,a[d+9],5,568446438),p=f(p,m,n,o,a[d+14],9,-1019803690),o=f(o,p,m,n,a[d+3],14,-187363961),n=f(n,o,p,m,a[d+8],20,1163531501),m=f(m,n,o,p,a[d+13],5,-1444681467),p=f(p,m,n,o,a[d+2],9,-51403784),o=f(o,p,m,n,a[d+7],14,1735328473),n=f(n,o,p,m,a[d+12],20,-1926607734),m=g(m,n,o,p,a[d+5],4,-378558),p=g(p,m,n,o,a[d+8],11,-2022574463),o=g(o,p,m,n,a[d+11],16,1839030562),n=g(n,o,p,m,a[d+14],23,-35309556),m=g(m,n,o,p,a[d+1],4,-1530992060),p=g(p,m,n,o,a[d+4],11,1272893353),o=g(o,p,m,n,a[d+7],16,-155497632),n=g(n,o,p,m,a[d+10],23,-1094730640),m=g(m,n,o,p,a[d+13],4,681279174),p=g(p,m,n,o,a[d],11,-358537222),o=g(o,p,m,n,a[d+3],16,-722521979),n=g(n,o,p,m,a[d+6],23,76029189),m=g(m,n,o,p,a[d+9],4,-640364487),p=g(p,m,n,o,a[d+12],11,-421815835),o=g(o,p,m,n,a[d+15],16,530742520),n=g(n,o,p,m,a[d+2],23,-995338651),m=h(m,n,o,p,a[d],6,-198630844),p=h(p,m,n,o,a[d+7],10,1126891415),o=h(o,p,m,n,a[d+14],15,-1416354905),n=h(n,o,p,m,a[d+5],21,-57434055),m=h(m,n,o,p,a[d+12],6,1700485571),p=h(p,m,n,o,a[d+3],10,-1894986606),o=h(o,p,m,n,a[d+10],15,-1051523),n=h(n,o,p,m,a[d+1],21,-2054922799),m=h(m,n,o,p,a[d+8],6,1873313359),p=h(p,m,n,o,a[d+15],10,-30611744),o=h(o,p,m,n,a[d+6],15,-1560198380),n=h(n,o,p,m,a[d+13],21,1309151649),m=h(m,n,o,p,a[d+4],6,-145523070),p=h(p,m,n,o,a[d+11],10,-1120210379),o=h(o,p,m,n,a[d+2],15,718787259),n=h(n,o,p,m,a[d+9],21,-343485551),m=b(m,i),n=b(n,j),o=b(o,k),p=b(p,l);return[m,n,o,p]}function j(a){var b,c="";for(b=0;b<32*a.length;b+=8)c+=String.fromCharCode(a[b>>5]>>>b%32&255);return c}function k(a){var b,c=[];for(c[(a.length>>2)-1]=void 0,b=0;b<c.length;b+=1)c[b]=0;for(b=0;b<8*a.length;b+=8)c[b>>5]|=(255&a.charCodeAt(b/8))<<b%32;return c}function l(a){return j(i(k(a),8*a.length))}function m(a,b){var c,d,e=k(a),f=[],g=[];for(f[15]=g[15]=void 0,e.length>16&&(e=i(e,8*a.length)),c=0;16>c;c+=1)f[c]=909522486^e[c],g[c]=1549556828^e[c];return d=i(f.concat(k(b)),512+8*b.length),j(i(g.concat(d),640))}function n(a){var b,c,d="0123456789abcdef",e="";for(c=0;c<a.length;c+=1)b=a.charCodeAt(c),e+=d.charAt(b>>>4&15)+d.charAt(15&b);return e}function o(a){return unescape(encodeURIComponent(a))}function p(a){return l(o(a))}function q(a){return n(p(a))}function r(a,b){return m(o(a),o(b))}function s(a,b){return n(r(a,b))}function t(a,b,c){return b?c?r(b,a):s(b,a):c?p(a):q(a)}"function"==typeof define?define('md5',[],function(){return t}):a.md5=t}(this);



/************
 * 工具方法
 ************/
define("util", ['md5'], function(require){
    return {
        // params to object
        unparam: function(str, params) {
            if (!str) return;
            params = params || {};
            str.replace(/\/$/, '').replace(/([^?=&]+)=([^&#]*)/g, function (m, key, value) {
                params[key] = value;
            });
            return params;
        },
        /* eg. 
            util.formatDate('2014-03-24', '-1m')  => 2014-02-24
            util.formatDate('2014-03-24', '-2d')  => 2014-03-22
            util.formatDate('2014-03-24', '/')  => 2014/03/22
            util.formatDate(null, '/')  => 2014/10/15
            util.formatDate(null, '')  => 20141015
        */
        formatDate: function(date, str) {
            if (!date) {
                date = new Date();
            } else if (typeof date === 'string') {
                date = new Date( /(\d{4})\D?(\d{2})\D?(\d{2})/.exec(date).splice(1).join('/') );
            }
            var y, m, d, div = '/';

            if (typeof str === 'string') {
                str = str.toLowerCase();
                if (/\d(y|m|d)$/.test(str)) {
                    var n = str.substr(0, str.length-1);
                    if (str.substr(-1)==='m') {
                        date.setMonth(date.getMonth() + (+n));
                    } else {
                        date.setDate(date.getDate() + (+n));
                    }
                } else {
                    div = str;
                }
            }
            y = date.getFullYear(),
            m = date.getMonth() + 1,
            d = date.getDate();
            
            return y + div + (m>9 ? m : '0'+m) + div + (d>9 ? d : '0'+d);
        },

        formatMoney: function(s, unit){
            if (s===undefined || s===null) s = 0;
            s = String(s);
            if(/[^0-9\.]/.test(s)) return "";
            s=s.replace(/^(\d*)$/,"$1.");
            s=(s+"00").replace(/(\d*\.\d\d)\d*/,"$1");
            s=s.replace(".",",");
            var re=/(\d)(\d{3},)/;
            while(re.test(s))
                    s=s.replace(re,"$1,$2");
            s=s.replace(/,(\d\d)$/,".$1");
            return s.replace(/^\./,"0.") + (unit ? ' '+unit : '');
        },
        formatFloat: function (num, length) {
            if (typeof length === 'undefined') length = 2;
            var n = Math.pow(10, length);
            return (num * n / n).toFixed(length);
        },

        formatName: function(str) {
            return str.replace(/^(\d{3})\d{4}(\d{4})$/, '$1****$2').replace(/^(.{3})[^@]*(@.+)$/, '$1****$2');
        },

        session: {
            set: function(key, value) {
                key = key.replace(/\//g, '_');
                if (value===undefined) return;
                sessionStorage.setItem(key, JSON.stringify(value));
            },
            get: function(key) {
                key = key.replace(/\//g, '_');
                return JSON.parse(sessionStorage.getItem(key));
            },
            remove: function(key) {
                key = key.replace(/\//g, '_');
                sessionStorage.removeItem(key);
            },
            clear: function() {
                sessionStorage.clear();
            }
        },

        storage: {
            set: function(key, value) {
                key = key.replace(/\//g, '_');
                if (value===undefined) return;
                localStorage.setItem(key, JSON.stringify(value));
            },
            get: function(key) {
                key = key.replace(/\//g, '_');
                return JSON.parse(localStorage.getItem(key));
            },
            remove: function(key) {
                key = key.replace(/\//g, '_');
                localStorage.removeItem(key);
            },
            clear: function() {
                localStorage.clear();
            }
        },

        cookie: {
            set:function(key, value, expires, path, domain, secure) {
                if (value===null) value = "";
                if(typeof expires === "number") {
                    expires = new Date( +new Date() + 86400000*expires );
                }
                document.cookie=key+"="+escape(value)
                    + ((expires)?"; expires="+expires.toUTCString():"")
                    + ((path)?"; path="+path:"; path=/")
                    + ((domain)?"; domain="+domain:"")
                    + ((secure)?"; secure":"");
            },
            get:function(key) {
                var arr=document.cookie.match(new RegExp("(^| )"+key+"=([^;]*)(;|$)"));
                return arr!=null ? unescape(arr[2]) : null;
            },
            remove:function(key, path, domain) {
                if(this.get(key)){
                    this.set(key, "", -365);
                }
            },
            clear:function(key, path, domain) {
                if(this.get(key)){
                    document.cookie=key+"="+((path)?"; path="+path:"; path=/")+((domain)?"; domain="+domain:"")+";expires=Fri, 02-Jan-1970 00:00:00 GMT";
                }
            }
        },

        md5: require('md5'),

        base64: {
            encode: function(str) {
                var s = typeof str !== 'string' ? '' : str;
                return window.btoa(encodeURIComponent( escape( s )));
            },
            decode: function(str) {
                var s = typeof str !== 'string' ? '' : str;
                return unescape(decodeURIComponent(window.atob( s )));
            }
        }
    };
});



/************
 * 模板引擎
 ************/
define('tpl', [], function(){

    function tpl(html, data) {
        var fn = compiler(html);
        return data ? fn(data) : fn;
    }
    tpl.begin = '<#';
    tpl.end = '#>';

    function compiler(html) {
        html = html || '';
        if (/^#\w+$/.test(html)) html = document.getElementById(html.substring(1)).innerHTML;
        var begin = tpl.begin,
            end = tpl.end,
            v = tpl.variable,
            arg1 = v || "$",
            trim = function(str) {
                return str.trim ? str.trim() : str.replace(/^\s*|\s*$/g, '');
            },
            ecp = function(str){
                return str.replace(/('|\\|\r?\n)/g, '\\$1');
            },
            str = "var "+ arg1 +"="+ arg1 +"||this,__='',___,echo=function(s){__+=s},include=function(t,d){__+=tpl(t).call(d||"+ arg1 +")};"+ (v?"":"with($||{}){"),
            blen = begin.length,
            elen = end.length,
            b = html.indexOf(begin),
            e,
            skip,
            tmp;
            
        while(b != -1) {
            e = skip ? b + blen : html.indexOf(end);
            if(e < b) break; //出错后不再编译

            str += "__+='" + ecp(html.substring(0, b)) + "';";

            if (skip) {
                html = html.substring(blen+elen+1);
                skip--;
            } else {
                tmp = trim(html.substring(b+blen, e));
                if ('#'===tmp) {
                    skip = 1;
                }
                else if( tmp.indexOf('=') === 0 ) { //模板变量
                    tmp = tmp.substring(1);
                    str += "___=" + tmp + ";typeof ___!=='undefined'&&(__+=___);";
                } else { //js代码
                    str += "\n" + tmp + "\n";
                }
            }

            html = html.substring(e + elen);
            b = html.indexOf( begin + (skip ? '#'+end : '') );
        }
        str += "__+='" + ecp(html) + "'"+ (v?";":"}") +"return __";
        return new Function(arg1, str);
    }
    //Browser global
    return window.tpl = tpl;
});
