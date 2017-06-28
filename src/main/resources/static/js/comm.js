/**
 * 工具类
 */
var comm = (function () {
    var basePath = "../";
    //执行成功代码
    var SUCCESS = 10000;
    //执行失败代码
    var ERROR = 99999;

    var fuc = {
        getBasePath: function(){
            return basePath;
        },
        //获得页面参数
        getParam: function (name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
            var r = window.location.search.substr(1).match(reg);
            if (r != null) return decodeURI(r[2]);
            return null;
        },
        //判断是否为空
        isNull: function (val) {
            if (val == null || val.length <= 0 || val == '' || val == "null" || val == undefined || val == "undefined") {
                if(typeof(val) != 'number'){
                    return true;
                }
            }
            return false;
        },

        //去除null,可设默认值
        notNull: function (val, defaultVal) {
            if (this.isNull(val)) {
                if (!this.isNull(defaultVal)) {
                    return defaultVal;
                } else {
                    return "";
                }
            } else {
                return val;
            }
        },

        /**
         * 手势滑动事件
         * @param contentId 滑动范围
         * @param leftEvent 左滑动事件
         * @param rightEvent 右滑动事件
         */
        touchEvent: function (contentId, leftEvent, rightEvent, touchstartEvent, touchmoveEvent) {
            var startPosition, endPosition, deltaX, deltaY, moveLength;
            $(contentId).bind('touchstart', function (e) {
                var touch = e.touches[0];
                startPosition = {
                    x: touch.pageX,
                    y: touch.pageY
                }
                if ($.isFunction(touchstartEvent)) {
                    touchstartEvent();
                }

            }).bind('touchmove', function (e) {
                var touch = e.touches[0];
                endPosition = {
                    x: touch.pageX,
                    y: touch.pageY
                };

                deltaX = endPosition.x - startPosition.x;
                deltaY = endPosition.y - startPosition.y;
                moveLength = Math.sqrt(Math.pow(Math.abs(deltaX), 2) + Math.pow(Math.abs(deltaY), 2));
                if ($.isFunction(touchmoveEvent)) {
                    touchmoveEvent();
                }
            }).bind('touchend', function (e) {
                if (deltaX < 0) { // 向左划动
                    if ($.isFunction(leftEvent)) {
                        leftEvent();
                    }
                } else if (deltaX > 0) { // 向右划动
                    if ($.isFunction(rightEvent)) {
                        rightEvent();
                    }
                }
            });
        },

        //时间格式化
        getFormatDate: function (date, pattern) {
            if (date == undefined) {
                date = new Date();
            }
            if (pattern == undefined) {
                pattern = "yyyy-MM-dd hh:mm:ss";
            }
            return date.Format(pattern);
        },

        //格式化userID
        getUserId: function (myrunnersfid) {
            return 20150331 ^ parseInt(myrunnersfid);
        },

        /**
         * ajax公共方法
         * @param options
         * @param successFuc
         * @param errorFuc
         * @param resultOtherFuc 当rusult != 成功的时候,如果含有此方法,那么执行,没有就打印错误信息
         */
        ajax: function (options, successFuc, resultOtherFuc, errorFuc) {
            if (comm.isNull(options)) {
                return;
            }
            var defaults = {
                type: 'post',
                dataType: 'json',
                success: function (rt) {
                    if (rt && rt != null) {
                        var rt = JSON.parse(rt);
                        if (rt.result == SUCCESS) {
                            var data = rt.data;
                            // if (data != null) {
                            if ($.isFunction(successFuc)) {
                                successFuc(data);
                            }
                            // }
                        } else {
                            if ($.isFunction(resultOtherFuc)) {
                                resultOtherFuc(rt);
                            } else {
                                //$.toast(rt.result_err_msg, 2000, "toastCss");
                                $.toast(rt.result_err_msg);
                            }
                        }
                    } else {
                        $.toast("操作失败");
                       // $.toast("操作失败", 2000, "toastCss");
                    }
                },
                error: function (xhr, type) {
                    $.toast("操作失败");
                  //  $.toast("操作失败", 2000, "toastCss");
                    if ($.isFunction(errorFuc)) {
                        errorFuc(xhr, type);
                    }
                }
            }
            var _options = $.extend(defaults,options);
            $.ajax(_options);
        },
        //分享公共方法
        shareH5: function (userId) {
            $.ajax({
                type: 'post',
                url: comm.getBasePath() + 'common/domain',
                data: {},
                async: true,
                dataType: 'json',
                success: function (rt) {
                    if (!comm.isNull(rt)) {
                        setDefaultShareConfig({
                            icon: rt + '/activity/h5/img/runeveryday.png',
                            url: rt + '/activity/h5/activity/2017/daily/share.html?user_id=' + userId,
                            title: 'runeveryday，一路有你”每天跑步1.61公里～',
                            content: '我在咪咕善跑参加了“runeveryday，一路有你”已坚持跑步x天，排名x名，为我点赞加油吧～',
                        });
                    }
                }
            })
        }
    }
    return fuc;
})();

//Date函数拓展 fmt为格式 例如 yyyy-MM-dd ,yyyy-MM,yyyy
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

