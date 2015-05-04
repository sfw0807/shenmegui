function onPageLoad() {
    if ($pay_summary = $("#paySummary"), suggestions_distance = $("#suggestions").offset(), header_height = $(".parallax-product").outerHeight(), pay_height = $pay_summary.outerHeight(), product_price = parseInt($("#product_price").val()), $product_coupon = $("#coupon"), $(".post-comment-area").blur(function () {
        "" == $(".post-comment-area").val().trim() && cancelPostCommentArea()
    }), notice_message = $("#notif-message").html(), 0 != jQuery.trim(notice_message).length) {
        var e = $("#notif-message").attr("notice-type");
        console.log(e), showNotification(e, notice_message)
    }
    $('[rel="tooltip"]').tooltip(), window.rangy.initialized = !1, $(".wysihtml5").each(function (e, t) {
        $(t).wysihtml5({image: !1})
    });
    var t = $(window).height() - 60;
    $("#livePreviewFrame").css("height", t), $('[rel="tooltip"]').tooltip(), $(document).width() > 768 && $(window).on("scroll", scrollCheckForPayment), $(".choice").click(function () {
        $input = $(this).find("input"), $input_name = $input.attr("name"), $license = $(this).data("license"), $('input[name="' + $input_name + '"]').each(function () {
            $(this).parent(".choice").removeClass("active"), $(this).prop("checked", !1)
        }), $(this).addClass("active"), $input.prop("checked", !0), void 0 != $license && (license_type = $license, 1 == license_type ? (price_html = product_price - 1, price_photoshop = product_price - 1, price_bundle = Math.ceil(1.75 * product_price - 1)) : 2 == license_type && (price_html = 4 * product_price - 1, price_photoshop = 4 * product_price - 1, price_bundle = Math.ceil(7.5 * product_price - 1)), $("#htmlCss").attr("data-price", "$" + price_html), $("#htmlCss").find("span").html("$" + price_html), $("#photoshop").attr("data-price", "$" + price_photoshop), $("#photoshop").find("span").html("$" + price_photoshop), $("#bundle").attr("data-price", "$" + price_bundle), $("#bundle").find("span").html("$" + price_bundle), console.log("full:" + full_product_price), 0 != $product_coupon.length && (1 == license_type ? (d_price_html = 39, d_price_photoshop = 39, d_price_bundle = 69) : 2 == license_type && (d_price_html = 159, d_price_photoshop = 159, d_price_bundle = 299), $("#htmlCss").attr("data-full-price", "$" + d_price_html), $("#photoshop").attr("data-full-price", "$" + d_price_photoshop), $("#bundle").attr("data-full-price", "$" + d_price_bundle))), $('input[name="archive_type"]').each(function () {
            $(this).is(":checked") && (product_price_view = $(this).parent(".choice").attr("data-price"), full_product_price = $(this).parent(".choice").attr("data-full-price"), $(".product-price").html(0 != $product_coupon.length ? "Total: <strike>" + full_product_price + '</strike> <span class="text-danger">' + product_price_view + "</span>" : "Total: " + product_price_view))
        })
    }), $(".makeLoading").click(function () {
        $(this).html('<i class="fa fa-circle-o-notch fa-spin"></i> Loading...').addClass("disabled")
    })
}
function showLicenseArea(e) {
    payment_visible ? validatePayment(e) : ($pay_summary.addClass("purchase-flow").css("top", 0), $(this).scrollTop() <= header_height && $pay_summary.addClass("is_top"), $("#licenseCollapse").collapse("show"), $(".pay-summary .btn-live-preview").fadeOut(), $(".pay-summary .small-info").hide(), $("hr.separator").hide(), $("#productPrice").hide(), $price = $(".product-price").html(), $(".product-price").html("Total: " + $price), payment_visible = !0, $(".already-purchased").hide(), setTimeout(function () {
        pay_height = $pay_summary.height()
    }, 500))
}
function validatePayment(e) {
    return $(".pay-summary .step").removeClass("input-error"), license = 0, archive = 0, payment = 0, $('input[name="license_type"]').each(function () {
        $(this).is(":checked") && (license = 1, license_type = $(this).val())
    }), 0 == license ? void $("#step-license").addClass("input-error") : ($('input[name="archive_type"]').each(function () {
        $(this).is(":checked") && (archive = 1, archive_type = $(this).val())
    }), 0 == archive ? void $("#step-package").addClass("input-error") : ($('input[name="payment_type"]').each(function () {
        $(this).is(":checked") && (payment = 1, payment_type = $(this).val())
    }), 0 == payment ? void $("#step-payment").addClass("input-error") : ("card" == payment_type ? (link = 1 == archive_type ? $("#gumroad_html_url").val() : 2 == archive_type ? $("#gumroad_psd_url").val() : 3 == archive_type ? $("#gumroad_bundle_url").val() : $("#gumroad_html_url").val(), window.location.href = link) : $("#payForm").submit(), void $(e).html('<i class="fa fa-circle-o-notch fa-spin"></i> Loading...').addClass("disabled"))))
}
function showRegisterForm() {
    $(".loginBox").fadeOut("fast", function () {
        $(".registerBox").fadeIn("fast"), $(".login-footer").fadeOut("fast", function () {
            $(".register-footer").fadeIn("fast")
        }), $(".modal-title").html("Register with")
    }), $(".error").removeClass("alert alert-danger").html("")
}
function showLoginForm() {
    $("#loginModal .registerBox").fadeOut("fast", function () {
        $(".loginBox").fadeIn("fast"), $(".register-footer").fadeOut("fast", function () {
            $(".login-footer").fadeIn("fast")
        }), $(".modal-title").html("Login with")
    }), $(".error").removeClass("alert alert-danger").html("")
}
function openLoginModal() {
    showLoginForm(), setTimeout(function () {
        $("#loginModal").modal("show")
    }, 230)
}
function openClientMode() {
    alert("没有开通客户模式！")
}
function openRegisterModal() {
    showRegisterForm(), setTimeout(function () {
        $("#loginModal").modal("show")
    }, 230)
}
function showSearchForm(e) {
    0 == click_rate ? ($('form[role="search"]').fadeIn(), $(".search").focus(), $(e).addClass("active"), $(e).children("p").html("Close"), click_rate = 1) : ($('form[role="search"]').fadeOut(), $(e).removeClass("active"), $(e).children("p").html("Search"), click_rate = 0)
}
function showMoreComments(e) {
    $(e).html('<i class="fa fa-circle-o-notch fa-spin"></i> Loading...'), setTimeout(function () {
        $(".comment.hidden").css("display", "block").fadeIn("fast").removeClass("hidden"), $(e).hide()
    }, 1e3)
}
function cancelPostCommentArea() {
    $(".post-comment-area").val("")
}
function showNotification(e, t) {
    void 0 == e && (e = "success"), $("#notif-message").html(t).addClass(e).fadeIn("slow"), setTimeout(function () {
        $("#notif-message").fadeOut("slow"), setTimeout(function () {
            $("#notif-message").removeClass(e)
        }, 1e3)
    }, 5e3)
}
function getMonthDownloads(e) {
    currentDate = $("#currentDate"), month = currentDate.attr("data-month"), year = currentDate.attr("data-year"), $.get("/dashboard/number-of-downloads", {month: month, year: year, period: e}, function (e) {
        downloads = e.downloads, nextMonth = e.nextMonth, previousMonth = e.previousMonth, currentMonth = e.currentMonth, year = e.year, today = e.today, $("#nextMonth b").html(nextMonth.string), $("#previousMonth b").html(previousMonth.string), $("#currentDate").attr("data-month", currentMonth.number), $("#currentDate").attr("data-year", year), $("#currentDate").html(currentMonth.string + " " + year), $("#downloadsFromMonth").html(e.totalDownloads);
        var t = [
            ["Date", "Downloads", {role: "style"}]
        ];
        downloads.length ? (downloads.forEach(function (e) {
            string = [e.day, e.downloads, "color : #00aaff"], t.push(string)
        }), $(".info-text").hide()) : (string = [today, 0, "color: #00aaff"], t.push(string)), t = google.visualization.arrayToDataTable(t), _downloadsChart.draw(t, _chartOptions)
    })
}
function getMonthUsers(e) {
    currentDate = $("#currentDate"), month = currentDate.attr("data-month"), year = currentDate.attr("data-year"), $.get("/admin/number-of-users", {month: month, year: year, period: e}, function (e) {
        users = e.users, nextMonth = e.nextMonth, previousMonth = e.previousMonth, currentMonth = e.currentMonth, year = e.year, today = e.today, $("#nextMonth b").html(nextMonth.string), $("#previousMonth b").html(previousMonth.string), $("#currentDate").attr("data-month", currentMonth.number), $("#currentDate").attr("data-year", year), $("#currentDate").html(currentMonth.string + " " + year), $("#usersFromMonth").html(e.totalUsers);
        var t = [
            ["Date", "Users", {role: "style"}]
        ];
        users.length ? (users.forEach(function (e) {
            string = [e.day, e.users, "color : #00aaff"], t.push(string)
        }), $(".info-text").hide()) : (string = [today, 0, "color: #00aaff"], t.push(string)), t = google.visualization.arrayToDataTable(t), _usersChart.draw(t, _chartOptions)
    })
}
function debounce(e, t, n) {
    var o;
    return function () {
        var i = this, r = arguments;
        clearTimeout(o), o = setTimeout(function () {
            o = null, n || e.apply(i, r)
        }, t), n && !o && e.apply(i, r)
    }
}
!function (e, t) {
    "object" == typeof module && "object" == typeof module.exports ? module.exports = e.document ? t(e, !0) : function (e) {
        if (!e.document)throw new Error("jQuery requires a window with a document");
        return t(e)
    } : t(e)
}("undefined" != typeof window ? window : this, function (e, t) {
    function n(e) {
        var t = e.length, n = it.type(e);
        return"function" === n || it.isWindow(e) ? !1 : 1 === e.nodeType && t ? !0 : "array" === n || 0 === t || "number" == typeof t && t > 0 && t - 1 in e
    }

    function o(e, t, n) {
        if (it.isFunction(t))return it.grep(e, function (e, o) {
            return!!t.call(e, o, e) !== n
        });
        if (t.nodeType)return it.grep(e, function (e) {
            return e === t !== n
        });
        if ("string" == typeof t) {
            if (ht.test(t))return it.filter(t, e, n);
            t = it.filter(t, e)
        }
        return it.grep(e, function (e) {
            return it.inArray(e, t) >= 0 !== n
        })
    }

    function i(e, t) {
        do e = e[t]; while (e && 1 !== e.nodeType);
        return e
    }

    function r(e) {
        var t = wt[e] = {};
        return it.each(e.match(bt) || [], function (e, n) {
            t[n] = !0
        }), t
    }

    function a() {
        pt.addEventListener ? (pt.removeEventListener("DOMContentLoaded", s, !1), e.removeEventListener("load", s, !1)) : (pt.detachEvent("onreadystatechange", s), e.detachEvent("onload", s))
    }

    function s() {
        (pt.addEventListener || "load" === event.type || "complete" === pt.readyState) && (a(), it.ready())
    }

    function l(e, t, n) {
        if (void 0 === n && 1 === e.nodeType) {
            var o = "data-" + t.replace(Nt, "-$1").toLowerCase();
            if (n = e.getAttribute(o), "string" == typeof n) {
                try {
                    n = "true" === n ? !0 : "false" === n ? !1 : "null" === n ? null : +n + "" === n ? +n : Tt.test(n) ? it.parseJSON(n) : n
                } catch (i) {
                }
                it.data(e, t, n)
            } else n = void 0
        }
        return n
    }

    function c(e) {
        var t;
        for (t in e)if (("data" !== t || !it.isEmptyObject(e[t])) && "toJSON" !== t)return!1;
        return!0
    }

    function u(e, t, n, o) {
        if (it.acceptData(e)) {
            var i, r, a = it.expando, s = e.nodeType, l = s ? it.cache : e, c = s ? e[a] : e[a] && a;
            if (c && l[c] && (o || l[c].data) || void 0 !== n || "string" != typeof t)return c || (c = s ? e[a] = G.pop() || it.guid++ : a), l[c] || (l[c] = s ? {} : {toJSON: it.noop}), ("object" == typeof t || "function" == typeof t) && (o ? l[c] = it.extend(l[c], t) : l[c].data = it.extend(l[c].data, t)), r = l[c], o || (r.data || (r.data = {}), r = r.data), void 0 !== n && (r[it.camelCase(t)] = n), "string" == typeof t ? (i = r[t], null == i && (i = r[it.camelCase(t)])) : i = r, i
        }
    }

    function d(e, t, n) {
        if (it.acceptData(e)) {
            var o, i, r = e.nodeType, a = r ? it.cache : e, s = r ? e[it.expando] : it.expando;
            if (a[s]) {
                if (t && (o = n ? a[s] : a[s].data)) {
                    it.isArray(t) ? t = t.concat(it.map(t, it.camelCase)) : t in o ? t = [t] : (t = it.camelCase(t), t = t in o ? [t] : t.split(" ")), i = t.length;
                    for (; i--;)delete o[t[i]];
                    if (n ? !c(o) : !it.isEmptyObject(o))return
                }
                (n || (delete a[s].data, c(a[s]))) && (r ? it.cleanData([e], !0) : nt.deleteExpando || a != a.window ? delete a[s] : a[s] = null)
            }
        }
    }

    function h() {
        return!0
    }

    function f() {
        return!1
    }

    function p() {
        try {
            return pt.activeElement
        } catch (e) {
        }
    }

    function m(e) {
        var t = Mt.split("|"), n = e.createDocumentFragment();
        if (n.createElement)for (; t.length;)n.createElement(t.pop());
        return n
    }

    function g(e, t) {
        var n, o, i = 0, r = typeof e.getElementsByTagName !== Et ? e.getElementsByTagName(t || "*") : typeof e.querySelectorAll !== Et ? e.querySelectorAll(t || "*") : void 0;
        if (!r)for (r = [], n = e.childNodes || e; null != (o = n[i]); i++)!t || it.nodeName(o, t) ? r.push(o) : it.merge(r, g(o, t));
        return void 0 === t || t && it.nodeName(e, t) ? it.merge([e], r) : r
    }

    function y(e) {
        kt.test(e.type) && (e.defaultChecked = e.checked)
    }

    function v(e, t) {
        return it.nodeName(e, "table") && it.nodeName(11 !== t.nodeType ? t : t.firstChild, "tr") ? e.getElementsByTagName("tbody")[0] || e.appendChild(e.ownerDocument.createElement("tbody")) : e
    }

    function b(e) {
        return e.type = (null !== it.find.attr(e, "type")) + "/" + e.type, e
    }

    function w(e) {
        var t = Xt.exec(e.type);
        return t ? e.type = t[1] : e.removeAttribute("type"), e
    }

    function C(e, t) {
        for (var n, o = 0; null != (n = e[o]); o++)it._data(n, "globalEval", !t || it._data(t[o], "globalEval"))
    }

    function x(e, t) {
        if (1 === t.nodeType && it.hasData(e)) {
            var n, o, i, r = it._data(e), a = it._data(t, r), s = r.events;
            if (s) {
                delete a.handle, a.events = {};
                for (n in s)for (o = 0, i = s[n].length; i > o; o++)it.event.add(t, n, s[n][o])
            }
            a.data && (a.data = it.extend({}, a.data))
        }
    }

    function E(e, t) {
        var n, o, i;
        if (1 === t.nodeType) {
            if (n = t.nodeName.toLowerCase(), !nt.noCloneEvent && t[it.expando]) {
                i = it._data(t);
                for (o in i.events)it.removeEvent(t, o, i.handle);
                t.removeAttribute(it.expando)
            }
            "script" === n && t.text !== e.text ? (b(t).text = e.text, w(t)) : "object" === n ? (t.parentNode && (t.outerHTML = e.outerHTML), nt.html5Clone && e.innerHTML && !it.trim(t.innerHTML) && (t.innerHTML = e.innerHTML)) : "input" === n && kt.test(e.type) ? (t.defaultChecked = t.checked = e.checked, t.value !== e.value && (t.value = e.value)) : "option" === n ? t.defaultSelected = t.selected = e.defaultSelected : ("input" === n || "textarea" === n) && (t.defaultValue = e.defaultValue)
        }
    }

    function T(t, n) {
        var o, i = it(n.createElement(t)).appendTo(n.body), r = e.getDefaultComputedStyle && (o = e.getDefaultComputedStyle(i[0])) ? o.display : it.css(i[0], "display");
        return i.detach(), r
    }

    function N(e) {
        var t = pt, n = Zt[e];
        return n || (n = T(e, t), "none" !== n && n || (Jt = (Jt || it("<iframe frameborder='0' width='0' height='0'/>")).appendTo(t.documentElement), t = (Jt[0].contentWindow || Jt[0].contentDocument).document, t.write(), t.close(), n = T(e, t), Jt.detach()), Zt[e] = n), n
    }

    function S(e, t) {
        return{get: function () {
            var n = e();
            if (null != n)return n ? void delete this.get : (this.get = t).apply(this, arguments)
        }}
    }

    function _(e, t) {
        if (t in e)return t;
        for (var n = t.charAt(0).toUpperCase() + t.slice(1), o = t, i = fn.length; i--;)if (t = fn[i] + n, t in e)return t;
        return o
    }

    function R(e, t) {
        for (var n, o, i, r = [], a = 0, s = e.length; s > a; a++)o = e[a], o.style && (r[a] = it._data(o, "olddisplay"), n = o.style.display, t ? (r[a] || "none" !== n || (o.style.display = ""), "" === o.style.display && Rt(o) && (r[a] = it._data(o, "olddisplay", N(o.nodeName)))) : (i = Rt(o), (n && "none" !== n || !i) && it._data(o, "olddisplay", i ? n : it.css(o, "display"))));
        for (a = 0; s > a; a++)o = e[a], o.style && (t && "none" !== o.style.display && "" !== o.style.display || (o.style.display = t ? r[a] || "" : "none"));
        return e
    }

    function A(e, t, n) {
        var o = cn.exec(t);
        return o ? Math.max(0, o[1] - (n || 0)) + (o[2] || "px") : t
    }

    function k(e, t, n, o, i) {
        for (var r = n === (o ? "border" : "content") ? 4 : "width" === t ? 1 : 0, a = 0; 4 > r; r += 2)"margin" === n && (a += it.css(e, n + _t[r], !0, i)), o ? ("content" === n && (a -= it.css(e, "padding" + _t[r], !0, i)), "margin" !== n && (a -= it.css(e, "border" + _t[r] + "Width", !0, i))) : (a += it.css(e, "padding" + _t[r], !0, i), "padding" !== n && (a += it.css(e, "border" + _t[r] + "Width", !0, i)));
        return a
    }

    function D(e, t, n) {
        var o = !0, i = "width" === t ? e.offsetWidth : e.offsetHeight, r = en(e), a = nt.boxSizing && "border-box" === it.css(e, "boxSizing", !1, r);
        if (0 >= i || null == i) {
            if (i = tn(e, t, r), (0 > i || null == i) && (i = e.style[t]), on.test(i))return i;
            o = a && (nt.boxSizingReliable() || i === e.style[t]), i = parseFloat(i) || 0
        }
        return i + k(e, t, n || (a ? "border" : "content"), o, r) + "px"
    }

    function L(e, t, n, o, i) {
        return new L.prototype.init(e, t, n, o, i)
    }

    function O() {
        return setTimeout(function () {
            pn = void 0
        }), pn = it.now()
    }

    function I(e, t) {
        var n, o = {height: e}, i = 0;
        for (t = t ? 1 : 0; 4 > i; i += 2 - t)n = _t[i], o["margin" + n] = o["padding" + n] = e;
        return t && (o.opacity = o.width = e), o
    }

    function $(e, t, n) {
        for (var o, i = (wn[t] || []).concat(wn["*"]), r = 0, a = i.length; a > r; r++)if (o = i[r].call(n, t, e))return o
    }

    function M(e, t, n) {
        var o, i, r, a, s, l, c, u, d = this, h = {}, f = e.style, p = e.nodeType && Rt(e), m = it._data(e, "fxshow");
        n.queue || (s = it._queueHooks(e, "fx"), null == s.unqueued && (s.unqueued = 0, l = s.empty.fire, s.empty.fire = function () {
            s.unqueued || l()
        }), s.unqueued++, d.always(function () {
            d.always(function () {
                s.unqueued--, it.queue(e, "fx").length || s.empty.fire()
            })
        })), 1 === e.nodeType && ("height"in t || "width"in t) && (n.overflow = [f.overflow, f.overflowX, f.overflowY], c = it.css(e, "display"), u = "none" === c ? it._data(e, "olddisplay") || N(e.nodeName) : c, "inline" === u && "none" === it.css(e, "float") && (nt.inlineBlockNeedsLayout && "inline" !== N(e.nodeName) ? f.zoom = 1 : f.display = "inline-block")), n.overflow && (f.overflow = "hidden", nt.shrinkWrapBlocks() || d.always(function () {
            f.overflow = n.overflow[0], f.overflowX = n.overflow[1], f.overflowY = n.overflow[2]
        }));
        for (o in t)if (i = t[o], gn.exec(i)) {
            if (delete t[o], r = r || "toggle" === i, i === (p ? "hide" : "show")) {
                if ("show" !== i || !m || void 0 === m[o])continue;
                p = !0
            }
            h[o] = m && m[o] || it.style(e, o)
        } else c = void 0;
        if (it.isEmptyObject(h))"inline" === ("none" === c ? N(e.nodeName) : c) && (f.display = c); else {
            m ? "hidden"in m && (p = m.hidden) : m = it._data(e, "fxshow", {}), r && (m.hidden = !p), p ? it(e).show() : d.done(function () {
                it(e).hide()
            }), d.done(function () {
                var t;
                it._removeData(e, "fxshow");
                for (t in h)it.style(e, t, h[t])
            });
            for (o in h)a = $(p ? m[o] : 0, o, d), o in m || (m[o] = a.start, p && (a.end = a.start, a.start = "width" === o || "height" === o ? 1 : 0))
        }
    }

    function H(e, t) {
        var n, o, i, r, a;
        for (n in e)if (o = it.camelCase(n), i = t[o], r = e[n], it.isArray(r) && (i = r[1], r = e[n] = r[0]), n !== o && (e[o] = r, delete e[n]), a = it.cssHooks[o], a && "expand"in a) {
            r = a.expand(r), delete e[o];
            for (n in r)n in e || (e[n] = r[n], t[n] = i)
        } else t[o] = i
    }

    function P(e, t, n) {
        var o, i, r = 0, a = bn.length, s = it.Deferred().always(function () {
            delete l.elem
        }), l = function () {
            if (i)return!1;
            for (var t = pn || O(), n = Math.max(0, c.startTime + c.duration - t), o = n / c.duration || 0, r = 1 - o, a = 0, l = c.tweens.length; l > a; a++)c.tweens[a].run(r);
            return s.notifyWith(e, [c, r, n]), 1 > r && l ? n : (s.resolveWith(e, [c]), !1)
        }, c = s.promise({elem: e, props: it.extend({}, t), opts: it.extend(!0, {specialEasing: {}}, n), originalProperties: t, originalOptions: n, startTime: pn || O(), duration: n.duration, tweens: [], createTween: function (t, n) {
            var o = it.Tween(e, c.opts, t, n, c.opts.specialEasing[t] || c.opts.easing);
            return c.tweens.push(o), o
        }, stop: function (t) {
            var n = 0, o = t ? c.tweens.length : 0;
            if (i)return this;
            for (i = !0; o > n; n++)c.tweens[n].run(1);
            return t ? s.resolveWith(e, [c, t]) : s.rejectWith(e, [c, t]), this
        }}), u = c.props;
        for (H(u, c.opts.specialEasing); a > r; r++)if (o = bn[r].call(c, e, u, c.opts))return o;
        return it.map(u, $, c), it.isFunction(c.opts.start) && c.opts.start.call(e, c), it.fx.timer(it.extend(l, {elem: e, anim: c, queue: c.opts.queue})), c.progress(c.opts.progress).done(c.opts.done, c.opts.complete).fail(c.opts.fail).always(c.opts.always)
    }

    function B(e) {
        return function (t, n) {
            "string" != typeof t && (n = t, t = "*");
            var o, i = 0, r = t.toLowerCase().match(bt) || [];
            if (it.isFunction(n))for (; o = r[i++];)"+" === o.charAt(0) ? (o = o.slice(1) || "*", (e[o] = e[o] || []).unshift(n)) : (e[o] = e[o] || []).push(n)
        }
    }

    function j(e, t, n, o) {
        function i(s) {
            var l;
            return r[s] = !0, it.each(e[s] || [], function (e, s) {
                var c = s(t, n, o);
                return"string" != typeof c || a || r[c] ? a ? !(l = c) : void 0 : (t.dataTypes.unshift(c), i(c), !1)
            }), l
        }

        var r = {}, a = e === zn;
        return i(t.dataTypes[0]) || !r["*"] && i("*")
    }

    function F(e, t) {
        var n, o, i = it.ajaxSettings.flatOptions || {};
        for (o in t)void 0 !== t[o] && ((i[o] ? e : n || (n = {}))[o] = t[o]);
        return n && it.extend(!0, e, n), e
    }

    function q(e, t, n) {
        for (var o, i, r, a, s = e.contents, l = e.dataTypes; "*" === l[0];)l.shift(), void 0 === i && (i = e.mimeType || t.getResponseHeader("Content-Type"));
        if (i)for (a in s)if (s[a] && s[a].test(i)) {
            l.unshift(a);
            break
        }
        if (l[0]in n)r = l[0]; else {
            for (a in n) {
                if (!l[0] || e.converters[a + " " + l[0]]) {
                    r = a;
                    break
                }
                o || (o = a)
            }
            r = r || o
        }
        return r ? (r !== l[0] && l.unshift(r), n[r]) : void 0
    }

    function W(e, t, n, o) {
        var i, r, a, s, l, c = {}, u = e.dataTypes.slice();
        if (u[1])for (a in e.converters)c[a.toLowerCase()] = e.converters[a];
        for (r = u.shift(); r;)if (e.responseFields[r] && (n[e.responseFields[r]] = t), !l && o && e.dataFilter && (t = e.dataFilter(t, e.dataType)), l = r, r = u.shift())if ("*" === r)r = l; else if ("*" !== l && l !== r) {
            if (a = c[l + " " + r] || c["* " + r], !a)for (i in c)if (s = i.split(" "), s[1] === r && (a = c[l + " " + s[0]] || c["* " + s[0]])) {
                a === !0 ? a = c[i] : c[i] !== !0 && (r = s[0], u.unshift(s[1]));
                break
            }
            if (a !== !0)if (a && e["throws"])t = a(t); else try {
                t = a(t)
            } catch (d) {
                return{state: "parsererror", error: a ? d : "No conversion from " + l + " to " + r}
            }
        }
        return{state: "success", data: t}
    }

    function z(e, t, n, o) {
        var i;
        if (it.isArray(t))it.each(t, function (t, i) {
            n || Gn.test(e) ? o(e, i) : z(e + "[" + ("object" == typeof i ? t : "") + "]", i, n, o)
        }); else if (n || "object" !== it.type(t))o(e, t); else for (i in t)z(e + "[" + i + "]", t[i], n, o)
    }

    function U() {
        try {
            return new e.XMLHttpRequest
        } catch (t) {
        }
    }

    function V() {
        try {
            return new e.ActiveXObject("Microsoft.XMLHTTP")
        } catch (t) {
        }
    }

    function X(e) {
        return it.isWindow(e) ? e : 9 === e.nodeType ? e.defaultView || e.parentWindow : !1
    }

    var G = [], K = G.slice, Y = G.concat, Q = G.push, J = G.indexOf, Z = {}, et = Z.toString, tt = Z.hasOwnProperty, nt = {}, ot = "1.11.1", it = function (e, t) {
        return new it.fn.init(e, t)
    }, rt = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, at = /^-ms-/, st = /-([\da-z])/gi, lt = function (e, t) {
        return t.toUpperCase()
    };
    it.fn = it.prototype = {jquery: ot, constructor: it, selector: "", length: 0, toArray: function () {
        return K.call(this)
    }, get: function (e) {
        return null != e ? 0 > e ? this[e + this.length] : this[e] : K.call(this)
    }, pushStack: function (e) {
        var t = it.merge(this.constructor(), e);
        return t.prevObject = this, t.context = this.context, t
    }, each: function (e, t) {
        return it.each(this, e, t)
    }, map: function (e) {
        return this.pushStack(it.map(this, function (t, n) {
            return e.call(t, n, t)
        }))
    }, slice: function () {
        return this.pushStack(K.apply(this, arguments))
    }, first: function () {
        return this.eq(0)
    }, last: function () {
        return this.eq(-1)
    }, eq: function (e) {
        var t = this.length, n = +e + (0 > e ? t : 0);
        return this.pushStack(n >= 0 && t > n ? [this[n]] : [])
    }, end: function () {
        return this.prevObject || this.constructor(null)
    }, push: Q, sort: G.sort, splice: G.splice}, it.extend = it.fn.extend = function () {
        var e, t, n, o, i, r, a = arguments[0] || {}, s = 1, l = arguments.length, c = !1;
        for ("boolean" == typeof a && (c = a, a = arguments[s] || {}, s++), "object" == typeof a || it.isFunction(a) || (a = {}), s === l && (a = this, s--); l > s; s++)if (null != (i = arguments[s]))for (o in i)e = a[o], n = i[o], a !== n && (c && n && (it.isPlainObject(n) || (t = it.isArray(n))) ? (t ? (t = !1, r = e && it.isArray(e) ? e : []) : r = e && it.isPlainObject(e) ? e : {}, a[o] = it.extend(c, r, n)) : void 0 !== n && (a[o] = n));
        return a
    }, it.extend({expando: "jQuery" + (ot + Math.random()).replace(/\D/g, ""), isReady: !0, error: function (e) {
        throw new Error(e)
    }, noop: function () {
    }, isFunction: function (e) {
        return"function" === it.type(e)
    }, isArray: Array.isArray || function (e) {
        return"array" === it.type(e)
    }, isWindow: function (e) {
        return null != e && e == e.window
    }, isNumeric: function (e) {
        return!it.isArray(e) && e - parseFloat(e) >= 0
    }, isEmptyObject: function (e) {
        var t;
        for (t in e)return!1;
        return!0
    }, isPlainObject: function (e) {
        var t;
        if (!e || "object" !== it.type(e) || e.nodeType || it.isWindow(e))return!1;
        try {
            if (e.constructor && !tt.call(e, "constructor") && !tt.call(e.constructor.prototype, "isPrototypeOf"))return!1
        } catch (n) {
            return!1
        }
        if (nt.ownLast)for (t in e)return tt.call(e, t);
        for (t in e);
        return void 0 === t || tt.call(e, t)
    }, type: function (e) {
        return null == e ? e + "" : "object" == typeof e || "function" == typeof e ? Z[et.call(e)] || "object" : typeof e
    }, globalEval: function (t) {
        t && it.trim(t) && (e.execScript || function (t) {
            e.eval.call(e, t)
        })(t)
    }, camelCase: function (e) {
        return e.replace(at, "ms-").replace(st, lt)
    }, nodeName: function (e, t) {
        return e.nodeName && e.nodeName.toLowerCase() === t.toLowerCase()
    }, each: function (e, t, o) {
        var i, r = 0, a = e.length, s = n(e);
        if (o) {
            if (s)for (; a > r && (i = t.apply(e[r], o), i !== !1); r++); else for (r in e)if (i = t.apply(e[r], o), i === !1)break
        } else if (s)for (; a > r && (i = t.call(e[r], r, e[r]), i !== !1); r++); else for (r in e)if (i = t.call(e[r], r, e[r]), i === !1)break;
        return e
    }, trim: function (e) {
        return null == e ? "" : (e + "").replace(rt, "")
    }, makeArray: function (e, t) {
        var o = t || [];
        return null != e && (n(Object(e)) ? it.merge(o, "string" == typeof e ? [e] : e) : Q.call(o, e)), o
    }, inArray: function (e, t, n) {
        var o;
        if (t) {
            if (J)return J.call(t, e, n);
            for (o = t.length, n = n ? 0 > n ? Math.max(0, o + n) : n : 0; o > n; n++)if (n in t && t[n] === e)return n
        }
        return-1
    }, merge: function (e, t) {
        for (var n = +t.length, o = 0, i = e.length; n > o;)e[i++] = t[o++];
        if (n !== n)for (; void 0 !== t[o];)e[i++] = t[o++];
        return e.length = i, e
    }, grep: function (e, t, n) {
        for (var o, i = [], r = 0, a = e.length, s = !n; a > r; r++)o = !t(e[r], r), o !== s && i.push(e[r]);
        return i
    }, map: function (e, t, o) {
        var i, r = 0, a = e.length, s = n(e), l = [];
        if (s)for (; a > r; r++)i = t(e[r], r, o), null != i && l.push(i); else for (r in e)i = t(e[r], r, o), null != i && l.push(i);
        return Y.apply([], l)
    }, guid: 1, proxy: function (e, t) {
        var n, o, i;
        return"string" == typeof t && (i = e[t], t = e, e = i), it.isFunction(e) ? (n = K.call(arguments, 2), o = function () {
            return e.apply(t || this, n.concat(K.call(arguments)))
        }, o.guid = e.guid = e.guid || it.guid++, o) : void 0
    }, now: function () {
        return+new Date
    }, support: nt}), it.each("Boolean Number String Function Array Date RegExp Object Error".split(" "), function (e, t) {
        Z["[object " + t + "]"] = t.toLowerCase()
    });
    var ct = function (e) {
        function t(e, t, n, o) {
            var i, r, a, s, l, c, d, f, p, m;
            if ((t ? t.ownerDocument || t : j) !== L && D(t), t = t || L, n = n || [], !e || "string" != typeof e)return n;
            if (1 !== (s = t.nodeType) && 9 !== s)return[];
            if (I && !o) {
                if (i = vt.exec(e))if (a = i[1]) {
                    if (9 === s) {
                        if (r = t.getElementById(a), !r || !r.parentNode)return n;
                        if (r.id === a)return n.push(r), n
                    } else if (t.ownerDocument && (r = t.ownerDocument.getElementById(a)) && P(t, r) && r.id === a)return n.push(r), n
                } else {
                    if (i[2])return Z.apply(n, t.getElementsByTagName(e)), n;
                    if ((a = i[3]) && C.getElementsByClassName && t.getElementsByClassName)return Z.apply(n, t.getElementsByClassName(a)), n
                }
                if (C.qsa && (!$ || !$.test(e))) {
                    if (f = d = B, p = t, m = 9 === s && e, 1 === s && "object" !== t.nodeName.toLowerCase()) {
                        for (c = N(e), (d = t.getAttribute("id")) ? f = d.replace(wt, "\\$&") : t.setAttribute("id", f), f = "[id='" + f + "'] ", l = c.length; l--;)c[l] = f + h(c[l]);
                        p = bt.test(e) && u(t.parentNode) || t, m = c.join(",")
                    }
                    if (m)try {
                        return Z.apply(n, p.querySelectorAll(m)), n
                    } catch (g) {
                    } finally {
                        d || t.removeAttribute("id")
                    }
                }
            }
            return _(e.replace(lt, "$1"), t, n, o)
        }

        function n() {
            function e(n, o) {
                return t.push(n + " ") > x.cacheLength && delete e[t.shift()], e[n + " "] = o
            }

            var t = [];
            return e
        }

        function o(e) {
            return e[B] = !0, e
        }

        function i(e) {
            var t = L.createElement("div");
            try {
                return!!e(t)
            } catch (n) {
                return!1
            } finally {
                t.parentNode && t.parentNode.removeChild(t), t = null
            }
        }

        function r(e, t) {
            for (var n = e.split("|"), o = e.length; o--;)x.attrHandle[n[o]] = t
        }

        function a(e, t) {
            var n = t && e, o = n && 1 === e.nodeType && 1 === t.nodeType && (~t.sourceIndex || G) - (~e.sourceIndex || G);
            if (o)return o;
            if (n)for (; n = n.nextSibling;)if (n === t)return-1;
            return e ? 1 : -1
        }

        function s(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return"input" === n && t.type === e
            }
        }

        function l(e) {
            return function (t) {
                var n = t.nodeName.toLowerCase();
                return("input" === n || "button" === n) && t.type === e
            }
        }

        function c(e) {
            return o(function (t) {
                return t = +t, o(function (n, o) {
                    for (var i, r = e([], n.length, t), a = r.length; a--;)n[i = r[a]] && (n[i] = !(o[i] = n[i]))
                })
            })
        }

        function u(e) {
            return e && typeof e.getElementsByTagName !== X && e
        }

        function d() {
        }

        function h(e) {
            for (var t = 0, n = e.length, o = ""; n > t; t++)o += e[t].value;
            return o
        }

        function f(e, t, n) {
            var o = t.dir, i = n && "parentNode" === o, r = q++;
            return t.first ? function (t, n, r) {
                for (; t = t[o];)if (1 === t.nodeType || i)return e(t, n, r)
            } : function (t, n, a) {
                var s, l, c = [F, r];
                if (a) {
                    for (; t = t[o];)if ((1 === t.nodeType || i) && e(t, n, a))return!0
                } else for (; t = t[o];)if (1 === t.nodeType || i) {
                    if (l = t[B] || (t[B] = {}), (s = l[o]) && s[0] === F && s[1] === r)return c[2] = s[2];
                    if (l[o] = c, c[2] = e(t, n, a))return!0
                }
            }
        }

        function p(e) {
            return e.length > 1 ? function (t, n, o) {
                for (var i = e.length; i--;)if (!e[i](t, n, o))return!1;
                return!0
            } : e[0]
        }

        function m(e, n, o) {
            for (var i = 0, r = n.length; r > i; i++)t(e, n[i], o);
            return o
        }

        function g(e, t, n, o, i) {
            for (var r, a = [], s = 0, l = e.length, c = null != t; l > s; s++)(r = e[s]) && (!n || n(r, o, i)) && (a.push(r), c && t.push(s));
            return a
        }

        function y(e, t, n, i, r, a) {
            return i && !i[B] && (i = y(i)), r && !r[B] && (r = y(r, a)), o(function (o, a, s, l) {
                var c, u, d, h = [], f = [], p = a.length, y = o || m(t || "*", s.nodeType ? [s] : s, []), v = !e || !o && t ? y : g(y, h, e, s, l), b = n ? r || (o ? e : p || i) ? [] : a : v;
                if (n && n(v, b, s, l), i)for (c = g(b, f), i(c, [], s, l), u = c.length; u--;)(d = c[u]) && (b[f[u]] = !(v[f[u]] = d));
                if (o) {
                    if (r || e) {
                        if (r) {
                            for (c = [], u = b.length; u--;)(d = b[u]) && c.push(v[u] = d);
                            r(null, b = [], c, l)
                        }
                        for (u = b.length; u--;)(d = b[u]) && (c = r ? tt.call(o, d) : h[u]) > -1 && (o[c] = !(a[c] = d))
                    }
                } else b = g(b === a ? b.splice(p, b.length) : b), r ? r(null, a, b, l) : Z.apply(a, b)
            })
        }

        function v(e) {
            for (var t, n, o, i = e.length, r = x.relative[e[0].type], a = r || x.relative[" "], s = r ? 1 : 0, l = f(function (e) {
                return e === t
            }, a, !0), c = f(function (e) {
                return tt.call(t, e) > -1
            }, a, !0), u = [function (e, n, o) {
                return!r && (o || n !== R) || ((t = n).nodeType ? l(e, n, o) : c(e, n, o))
            }]; i > s; s++)if (n = x.relative[e[s].type])u = [f(p(u), n)]; else {
                if (n = x.filter[e[s].type].apply(null, e[s].matches), n[B]) {
                    for (o = ++s; i > o && !x.relative[e[o].type]; o++);
                    return y(s > 1 && p(u), s > 1 && h(e.slice(0, s - 1).concat({value: " " === e[s - 2].type ? "*" : ""})).replace(lt, "$1"), n, o > s && v(e.slice(s, o)), i > o && v(e = e.slice(o)), i > o && h(e))
                }
                u.push(n)
            }
            return p(u)
        }

        function b(e, n) {
            var i = n.length > 0, r = e.length > 0, a = function (o, a, s, l, c) {
                var u, d, h, f = 0, p = "0", m = o && [], y = [], v = R, b = o || r && x.find.TAG("*", c), w = F += null == v ? 1 : Math.random() || .1, C = b.length;
                for (c && (R = a !== L && a); p !== C && null != (u = b[p]); p++) {
                    if (r && u) {
                        for (d = 0; h = e[d++];)if (h(u, a, s)) {
                            l.push(u);
                            break
                        }
                        c && (F = w)
                    }
                    i && ((u = !h && u) && f--, o && m.push(u))
                }
                if (f += p, i && p !== f) {
                    for (d = 0; h = n[d++];)h(m, y, a, s);
                    if (o) {
                        if (f > 0)for (; p--;)m[p] || y[p] || (y[p] = Q.call(l));
                        y = g(y)
                    }
                    Z.apply(l, y), c && !o && y.length > 0 && f + n.length > 1 && t.uniqueSort(l)
                }
                return c && (F = w, R = v), m
            };
            return i ? o(a) : a
        }

        var w, C, x, E, T, N, S, _, R, A, k, D, L, O, I, $, M, H, P, B = "sizzle" + -new Date, j = e.document, F = 0, q = 0, W = n(), z = n(), U = n(), V = function (e, t) {
            return e === t && (k = !0), 0
        }, X = "undefined", G = 1 << 31, K = {}.hasOwnProperty, Y = [], Q = Y.pop, J = Y.push, Z = Y.push, et = Y.slice, tt = Y.indexOf || function (e) {
            for (var t = 0, n = this.length; n > t; t++)if (this[t] === e)return t;
            return-1
        }, nt = "checked|selected|async|autofocus|autoplay|controls|defer|disabled|hidden|ismap|loop|multiple|open|readonly|required|scoped", ot = "[\\x20\\t\\r\\n\\f]", it = "(?:\\\\.|[\\w-]|[^\\x00-\\xa0])+", rt = it.replace("w", "w#"), at = "\\[" + ot + "*(" + it + ")(?:" + ot + "*([*^$|!~]?=)" + ot + "*(?:'((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\"|(" + rt + "))|)" + ot + "*\\]", st = ":(" + it + ")(?:\\((('((?:\\\\.|[^\\\\'])*)'|\"((?:\\\\.|[^\\\\\"])*)\")|((?:\\\\.|[^\\\\()[\\]]|" + at + ")*)|.*)\\)|)", lt = new RegExp("^" + ot + "+|((?:^|[^\\\\])(?:\\\\.)*)" + ot + "+$", "g"), ct = new RegExp("^" + ot + "*," + ot + "*"), ut = new RegExp("^" + ot + "*([>+~]|" + ot + ")" + ot + "*"), dt = new RegExp("=" + ot + "*([^\\]'\"]*?)" + ot + "*\\]", "g"), ht = new RegExp(st), ft = new RegExp("^" + rt + "$"), pt = {ID: new RegExp("^#(" + it + ")"), CLASS: new RegExp("^\\.(" + it + ")"), TAG: new RegExp("^(" + it.replace("w", "w*") + ")"), ATTR: new RegExp("^" + at), PSEUDO: new RegExp("^" + st), CHILD: new RegExp("^:(only|first|last|nth|nth-last)-(child|of-type)(?:\\(" + ot + "*(even|odd|(([+-]|)(\\d*)n|)" + ot + "*(?:([+-]|)" + ot + "*(\\d+)|))" + ot + "*\\)|)", "i"), bool: new RegExp("^(?:" + nt + ")$", "i"), needsContext: new RegExp("^" + ot + "*[>+~]|:(even|odd|eq|gt|lt|nth|first|last)(?:\\(" + ot + "*((?:-\\d)?\\d*)" + ot + "*\\)|)(?=[^-]|$)", "i")}, mt = /^(?:input|select|textarea|button)$/i, gt = /^h\d$/i, yt = /^[^{]+\{\s*\[native \w/, vt = /^(?:#([\w-]+)|(\w+)|\.([\w-]+))$/, bt = /[+~]/, wt = /'|\\/g, Ct = new RegExp("\\\\([\\da-f]{1,6}" + ot + "?|(" + ot + ")|.)", "ig"), xt = function (e, t, n) {
            var o = "0x" + t - 65536;
            return o !== o || n ? t : 0 > o ? String.fromCharCode(o + 65536) : String.fromCharCode(o >> 10 | 55296, 1023 & o | 56320)
        };
        try {
            Z.apply(Y = et.call(j.childNodes), j.childNodes), Y[j.childNodes.length].nodeType
        } catch (Et) {
            Z = {apply: Y.length ? function (e, t) {
                J.apply(e, et.call(t))
            } : function (e, t) {
                for (var n = e.length, o = 0; e[n++] = t[o++];);
                e.length = n - 1
            }}
        }
        C = t.support = {}, T = t.isXML = function (e) {
            var t = e && (e.ownerDocument || e).documentElement;
            return t ? "HTML" !== t.nodeName : !1
        }, D = t.setDocument = function (e) {
            var t, n = e ? e.ownerDocument || e : j, o = n.defaultView;
            return n !== L && 9 === n.nodeType && n.documentElement ? (L = n, O = n.documentElement, I = !T(n), o && o !== o.top && (o.addEventListener ? o.addEventListener("unload", function () {
                D()
            }, !1) : o.attachEvent && o.attachEvent("onunload", function () {
                D()
            })), C.attributes = i(function (e) {
                return e.className = "i", !e.getAttribute("className")
            }), C.getElementsByTagName = i(function (e) {
                return e.appendChild(n.createComment("")), !e.getElementsByTagName("*").length
            }), C.getElementsByClassName = yt.test(n.getElementsByClassName) && i(function (e) {
                return e.innerHTML = "<div class='a'></div><div class='a i'></div>", e.firstChild.className = "i", 2 === e.getElementsByClassName("i").length
            }), C.getById = i(function (e) {
                return O.appendChild(e).id = B, !n.getElementsByName || !n.getElementsByName(B).length
            }), C.getById ? (x.find.ID = function (e, t) {
                if (typeof t.getElementById !== X && I) {
                    var n = t.getElementById(e);
                    return n && n.parentNode ? [n] : []
                }
            }, x.filter.ID = function (e) {
                var t = e.replace(Ct, xt);
                return function (e) {
                    return e.getAttribute("id") === t
                }
            }) : (delete x.find.ID, x.filter.ID = function (e) {
                var t = e.replace(Ct, xt);
                return function (e) {
                    var n = typeof e.getAttributeNode !== X && e.getAttributeNode("id");
                    return n && n.value === t
                }
            }), x.find.TAG = C.getElementsByTagName ? function (e, t) {
                return typeof t.getElementsByTagName !== X ? t.getElementsByTagName(e) : void 0
            } : function (e, t) {
                var n, o = [], i = 0, r = t.getElementsByTagName(e);
                if ("*" === e) {
                    for (; n = r[i++];)1 === n.nodeType && o.push(n);
                    return o
                }
                return r
            }, x.find.CLASS = C.getElementsByClassName && function (e, t) {
                return typeof t.getElementsByClassName !== X && I ? t.getElementsByClassName(e) : void 0
            }, M = [], $ = [], (C.qsa = yt.test(n.querySelectorAll)) && (i(function (e) {
                e.innerHTML = "<select msallowclip=''><option selected=''></option></select>", e.querySelectorAll("[msallowclip^='']").length && $.push("[*^$]=" + ot + "*(?:''|\"\")"), e.querySelectorAll("[selected]").length || $.push("\\[" + ot + "*(?:value|" + nt + ")"), e.querySelectorAll(":checked").length || $.push(":checked")
            }), i(function (e) {
                var t = n.createElement("input");
                t.setAttribute("type", "hidden"), e.appendChild(t).setAttribute("name", "D"), e.querySelectorAll("[name=d]").length && $.push("name" + ot + "*[*^$|!~]?="), e.querySelectorAll(":enabled").length || $.push(":enabled", ":disabled"), e.querySelectorAll("*,:x"), $.push(",.*:")
            })), (C.matchesSelector = yt.test(H = O.matches || O.webkitMatchesSelector || O.mozMatchesSelector || O.oMatchesSelector || O.msMatchesSelector)) && i(function (e) {
                C.disconnectedMatch = H.call(e, "div"), H.call(e, "[s!='']:x"), M.push("!=", st)
            }), $ = $.length && new RegExp($.join("|")), M = M.length && new RegExp(M.join("|")), t = yt.test(O.compareDocumentPosition), P = t || yt.test(O.contains) ? function (e, t) {
                var n = 9 === e.nodeType ? e.documentElement : e, o = t && t.parentNode;
                return e === o || !(!o || 1 !== o.nodeType || !(n.contains ? n.contains(o) : e.compareDocumentPosition && 16 & e.compareDocumentPosition(o)))
            } : function (e, t) {
                if (t)for (; t = t.parentNode;)if (t === e)return!0;
                return!1
            }, V = t ? function (e, t) {
                if (e === t)return k = !0, 0;
                var o = !e.compareDocumentPosition - !t.compareDocumentPosition;
                return o ? o : (o = (e.ownerDocument || e) === (t.ownerDocument || t) ? e.compareDocumentPosition(t) : 1, 1 & o || !C.sortDetached && t.compareDocumentPosition(e) === o ? e === n || e.ownerDocument === j && P(j, e) ? -1 : t === n || t.ownerDocument === j && P(j, t) ? 1 : A ? tt.call(A, e) - tt.call(A, t) : 0 : 4 & o ? -1 : 1)
            } : function (e, t) {
                if (e === t)return k = !0, 0;
                var o, i = 0, r = e.parentNode, s = t.parentNode, l = [e], c = [t];
                if (!r || !s)return e === n ? -1 : t === n ? 1 : r ? -1 : s ? 1 : A ? tt.call(A, e) - tt.call(A, t) : 0;
                if (r === s)return a(e, t);
                for (o = e; o = o.parentNode;)l.unshift(o);
                for (o = t; o = o.parentNode;)c.unshift(o);
                for (; l[i] === c[i];)i++;
                return i ? a(l[i], c[i]) : l[i] === j ? -1 : c[i] === j ? 1 : 0
            }, n) : L
        }, t.matches = function (e, n) {
            return t(e, null, null, n)
        }, t.matchesSelector = function (e, n) {
            if ((e.ownerDocument || e) !== L && D(e), n = n.replace(dt, "='$1']"), !(!C.matchesSelector || !I || M && M.test(n) || $ && $.test(n)))try {
                var o = H.call(e, n);
                if (o || C.disconnectedMatch || e.document && 11 !== e.document.nodeType)return o
            } catch (i) {
            }
            return t(n, L, null, [e]).length > 0
        }, t.contains = function (e, t) {
            return(e.ownerDocument || e) !== L && D(e), P(e, t)
        }, t.attr = function (e, t) {
            (e.ownerDocument || e) !== L && D(e);
            var n = x.attrHandle[t.toLowerCase()], o = n && K.call(x.attrHandle, t.toLowerCase()) ? n(e, t, !I) : void 0;
            return void 0 !== o ? o : C.attributes || !I ? e.getAttribute(t) : (o = e.getAttributeNode(t)) && o.specified ? o.value : null
        }, t.error = function (e) {
            throw new Error("Syntax error, unrecognized expression: " + e)
        }, t.uniqueSort = function (e) {
            var t, n = [], o = 0, i = 0;
            if (k = !C.detectDuplicates, A = !C.sortStable && e.slice(0), e.sort(V), k) {
                for (; t = e[i++];)t === e[i] && (o = n.push(i));
                for (; o--;)e.splice(n[o], 1)
            }
            return A = null, e
        }, E = t.getText = function (e) {
            var t, n = "", o = 0, i = e.nodeType;
            if (i) {
                if (1 === i || 9 === i || 11 === i) {
                    if ("string" == typeof e.textContent)return e.textContent;
                    for (e = e.firstChild; e; e = e.nextSibling)n += E(e)
                } else if (3 === i || 4 === i)return e.nodeValue
            } else for (; t = e[o++];)n += E(t);
            return n
        }, x = t.selectors = {cacheLength: 50, createPseudo: o, match: pt, attrHandle: {}, find: {}, relative: {">": {dir: "parentNode", first: !0}, " ": {dir: "parentNode"}, "+": {dir: "previousSibling", first: !0}, "~": {dir: "previousSibling"}}, preFilter: {ATTR: function (e) {
            return e[1] = e[1].replace(Ct, xt), e[3] = (e[3] || e[4] || e[5] || "").replace(Ct, xt), "~=" === e[2] && (e[3] = " " + e[3] + " "), e.slice(0, 4)
        }, CHILD: function (e) {
            return e[1] = e[1].toLowerCase(), "nth" === e[1].slice(0, 3) ? (e[3] || t.error(e[0]), e[4] = +(e[4] ? e[5] + (e[6] || 1) : 2 * ("even" === e[3] || "odd" === e[3])), e[5] = +(e[7] + e[8] || "odd" === e[3])) : e[3] && t.error(e[0]), e
        }, PSEUDO: function (e) {
            var t, n = !e[6] && e[2];
            return pt.CHILD.test(e[0]) ? null : (e[3] ? e[2] = e[4] || e[5] || "" : n && ht.test(n) && (t = N(n, !0)) && (t = n.indexOf(")", n.length - t) - n.length) && (e[0] = e[0].slice(0, t), e[2] = n.slice(0, t)), e.slice(0, 3))
        }}, filter: {TAG: function (e) {
            var t = e.replace(Ct, xt).toLowerCase();
            return"*" === e ? function () {
                return!0
            } : function (e) {
                return e.nodeName && e.nodeName.toLowerCase() === t
            }
        }, CLASS: function (e) {
            var t = W[e + " "];
            return t || (t = new RegExp("(^|" + ot + ")" + e + "(" + ot + "|$)")) && W(e, function (e) {
                return t.test("string" == typeof e.className && e.className || typeof e.getAttribute !== X && e.getAttribute("class") || "")
            })
        }, ATTR: function (e, n, o) {
            return function (i) {
                var r = t.attr(i, e);
                return null == r ? "!=" === n : n ? (r += "", "=" === n ? r === o : "!=" === n ? r !== o : "^=" === n ? o && 0 === r.indexOf(o) : "*=" === n ? o && r.indexOf(o) > -1 : "$=" === n ? o && r.slice(-o.length) === o : "~=" === n ? (" " + r + " ").indexOf(o) > -1 : "|=" === n ? r === o || r.slice(0, o.length + 1) === o + "-" : !1) : !0
            }
        }, CHILD: function (e, t, n, o, i) {
            var r = "nth" !== e.slice(0, 3), a = "last" !== e.slice(-4), s = "of-type" === t;
            return 1 === o && 0 === i ? function (e) {
                return!!e.parentNode
            } : function (t, n, l) {
                var c, u, d, h, f, p, m = r !== a ? "nextSibling" : "previousSibling", g = t.parentNode, y = s && t.nodeName.toLowerCase(), v = !l && !s;
                if (g) {
                    if (r) {
                        for (; m;) {
                            for (d = t; d = d[m];)if (s ? d.nodeName.toLowerCase() === y : 1 === d.nodeType)return!1;
                            p = m = "only" === e && !p && "nextSibling"
                        }
                        return!0
                    }
                    if (p = [a ? g.firstChild : g.lastChild], a && v) {
                        for (u = g[B] || (g[B] = {}), c = u[e] || [], f = c[0] === F && c[1], h = c[0] === F && c[2], d = f && g.childNodes[f]; d = ++f && d && d[m] || (h = f = 0) || p.pop();)if (1 === d.nodeType && ++h && d === t) {
                            u[e] = [F, f, h];
                            break
                        }
                    } else if (v && (c = (t[B] || (t[B] = {}))[e]) && c[0] === F)h = c[1]; else for (; (d = ++f && d && d[m] || (h = f = 0) || p.pop()) && ((s ? d.nodeName.toLowerCase() !== y : 1 !== d.nodeType) || !++h || (v && ((d[B] || (d[B] = {}))[e] = [F, h]), d !== t)););
                    return h -= i, h === o || h % o === 0 && h / o >= 0
                }
            }
        }, PSEUDO: function (e, n) {
            var i, r = x.pseudos[e] || x.setFilters[e.toLowerCase()] || t.error("unsupported pseudo: " + e);
            return r[B] ? r(n) : r.length > 1 ? (i = [e, e, "", n], x.setFilters.hasOwnProperty(e.toLowerCase()) ? o(function (e, t) {
                for (var o, i = r(e, n), a = i.length; a--;)o = tt.call(e, i[a]), e[o] = !(t[o] = i[a])
            }) : function (e) {
                return r(e, 0, i)
            }) : r
        }}, pseudos: {not: o(function (e) {
            var t = [], n = [], i = S(e.replace(lt, "$1"));
            return i[B] ? o(function (e, t, n, o) {
                for (var r, a = i(e, null, o, []), s = e.length; s--;)(r = a[s]) && (e[s] = !(t[s] = r))
            }) : function (e, o, r) {
                return t[0] = e, i(t, null, r, n), !n.pop()
            }
        }), has: o(function (e) {
            return function (n) {
                return t(e, n).length > 0
            }
        }), contains: o(function (e) {
            return function (t) {
                return(t.textContent || t.innerText || E(t)).indexOf(e) > -1
            }
        }), lang: o(function (e) {
            return ft.test(e || "") || t.error("unsupported lang: " + e), e = e.replace(Ct, xt).toLowerCase(), function (t) {
                var n;
                do if (n = I ? t.lang : t.getAttribute("xml:lang") || t.getAttribute("lang"))return n = n.toLowerCase(), n === e || 0 === n.indexOf(e + "-"); while ((t = t.parentNode) && 1 === t.nodeType);
                return!1
            }
        }), target: function (t) {
            var n = e.location && e.location.hash;
            return n && n.slice(1) === t.id
        }, root: function (e) {
            return e === O
        }, focus: function (e) {
            return e === L.activeElement && (!L.hasFocus || L.hasFocus()) && !!(e.type || e.href || ~e.tabIndex)
        }, enabled: function (e) {
            return e.disabled === !1
        }, disabled: function (e) {
            return e.disabled === !0
        }, checked: function (e) {
            var t = e.nodeName.toLowerCase();
            return"input" === t && !!e.checked || "option" === t && !!e.selected
        }, selected: function (e) {
            return e.parentNode && e.parentNode.selectedIndex, e.selected === !0
        }, empty: function (e) {
            for (e = e.firstChild; e; e = e.nextSibling)if (e.nodeType < 6)return!1;
            return!0
        }, parent: function (e) {
            return!x.pseudos.empty(e)
        }, header: function (e) {
            return gt.test(e.nodeName)
        }, input: function (e) {
            return mt.test(e.nodeName)
        }, button: function (e) {
            var t = e.nodeName.toLowerCase();
            return"input" === t && "button" === e.type || "button" === t
        }, text: function (e) {
            var t;
            return"input" === e.nodeName.toLowerCase() && "text" === e.type && (null == (t = e.getAttribute("type")) || "text" === t.toLowerCase())
        }, first: c(function () {
            return[0]
        }), last: c(function (e, t) {
            return[t - 1]
        }), eq: c(function (e, t, n) {
            return[0 > n ? n + t : n]
        }), even: c(function (e, t) {
            for (var n = 0; t > n; n += 2)e.push(n);
            return e
        }), odd: c(function (e, t) {
            for (var n = 1; t > n; n += 2)e.push(n);
            return e
        }), lt: c(function (e, t, n) {
            for (var o = 0 > n ? n + t : n; --o >= 0;)e.push(o);
            return e
        }), gt: c(function (e, t, n) {
            for (var o = 0 > n ? n + t : n; ++o < t;)e.push(o);
            return e
        })}}, x.pseudos.nth = x.pseudos.eq;
        for (w in{radio: !0, checkbox: !0, file: !0, password: !0, image: !0})x.pseudos[w] = s(w);
        for (w in{submit: !0, reset: !0})x.pseudos[w] = l(w);
        return d.prototype = x.filters = x.pseudos, x.setFilters = new d, N = t.tokenize = function (e, n) {
            var o, i, r, a, s, l, c, u = z[e + " "];
            if (u)return n ? 0 : u.slice(0);
            for (s = e, l = [], c = x.preFilter; s;) {
                (!o || (i = ct.exec(s))) && (i && (s = s.slice(i[0].length) || s), l.push(r = [])), o = !1, (i = ut.exec(s)) && (o = i.shift(), r.push({value: o, type: i[0].replace(lt, " ")}), s = s.slice(o.length));
                for (a in x.filter)!(i = pt[a].exec(s)) || c[a] && !(i = c[a](i)) || (o = i.shift(), r.push({value: o, type: a, matches: i}), s = s.slice(o.length));
                if (!o)break
            }
            return n ? s.length : s ? t.error(e) : z(e, l).slice(0)
        }, S = t.compile = function (e, t) {
            var n, o = [], i = [], r = U[e + " "];
            if (!r) {
                for (t || (t = N(e)), n = t.length; n--;)r = v(t[n]), r[B] ? o.push(r) : i.push(r);
                r = U(e, b(i, o)), r.selector = e
            }
            return r
        }, _ = t.select = function (e, t, n, o) {
            var i, r, a, s, l, c = "function" == typeof e && e, d = !o && N(e = c.selector || e);
            if (n = n || [], 1 === d.length) {
                if (r = d[0] = d[0].slice(0), r.length > 2 && "ID" === (a = r[0]).type && C.getById && 9 === t.nodeType && I && x.relative[r[1].type]) {
                    if (t = (x.find.ID(a.matches[0].replace(Ct, xt), t) || [])[0], !t)return n;
                    c && (t = t.parentNode), e = e.slice(r.shift().value.length)
                }
                for (i = pt.needsContext.test(e) ? 0 : r.length; i-- && (a = r[i], !x.relative[s = a.type]);)if ((l = x.find[s]) && (o = l(a.matches[0].replace(Ct, xt), bt.test(r[0].type) && u(t.parentNode) || t))) {
                    if (r.splice(i, 1), e = o.length && h(r), !e)return Z.apply(n, o), n;
                    break
                }
            }
            return(c || S(e, d))(o, t, !I, n, bt.test(e) && u(t.parentNode) || t), n
        }, C.sortStable = B.split("").sort(V).join("") === B, C.detectDuplicates = !!k, D(), C.sortDetached = i(function (e) {
            return 1 & e.compareDocumentPosition(L.createElement("div"))
        }), i(function (e) {
            return e.innerHTML = "<a href='#'></a>", "#" === e.firstChild.getAttribute("href")
        }) || r("type|href|height|width", function (e, t, n) {
            return n ? void 0 : e.getAttribute(t, "type" === t.toLowerCase() ? 1 : 2)
        }), C.attributes && i(function (e) {
            return e.innerHTML = "<input/>", e.firstChild.setAttribute("value", ""), "" === e.firstChild.getAttribute("value")
        }) || r("value", function (e, t, n) {
            return n || "input" !== e.nodeName.toLowerCase() ? void 0 : e.defaultValue
        }), i(function (e) {
            return null == e.getAttribute("disabled")
        }) || r(nt, function (e, t, n) {
            var o;
            return n ? void 0 : e[t] === !0 ? t.toLowerCase() : (o = e.getAttributeNode(t)) && o.specified ? o.value : null
        }), t
    }(e);
    it.find = ct, it.expr = ct.selectors, it.expr[":"] = it.expr.pseudos, it.unique = ct.uniqueSort, it.text = ct.getText, it.isXMLDoc = ct.isXML, it.contains = ct.contains;
    var ut = it.expr.match.needsContext, dt = /^<(\w+)\s*\/?>(?:<\/\1>|)$/, ht = /^.[^:#\[\.,]*$/;
    it.filter = function (e, t, n) {
        var o = t[0];
        return n && (e = ":not(" + e + ")"), 1 === t.length && 1 === o.nodeType ? it.find.matchesSelector(o, e) ? [o] : [] : it.find.matches(e, it.grep(t, function (e) {
            return 1 === e.nodeType
        }))
    }, it.fn.extend({find: function (e) {
        var t, n = [], o = this, i = o.length;
        if ("string" != typeof e)return this.pushStack(it(e).filter(function () {
            for (t = 0; i > t; t++)if (it.contains(o[t], this))return!0
        }));
        for (t = 0; i > t; t++)it.find(e, o[t], n);
        return n = this.pushStack(i > 1 ? it.unique(n) : n), n.selector = this.selector ? this.selector + " " + e : e, n
    }, filter: function (e) {
        return this.pushStack(o(this, e || [], !1))
    }, not: function (e) {
        return this.pushStack(o(this, e || [], !0))
    }, is: function (e) {
        return!!o(this, "string" == typeof e && ut.test(e) ? it(e) : e || [], !1).length
    }});
    var ft, pt = e.document, mt = /^(?:\s*(<[\w\W]+>)[^>]*|#([\w-]*))$/, gt = it.fn.init = function (e, t) {
        var n, o;
        if (!e)return this;
        if ("string" == typeof e) {
            if (n = "<" === e.charAt(0) && ">" === e.charAt(e.length - 1) && e.length >= 3 ? [null, e, null] : mt.exec(e), !n || !n[1] && t)return!t || t.jquery ? (t || ft).find(e) : this.constructor(t).find(e);
            if (n[1]) {
                if (t = t instanceof it ? t[0] : t, it.merge(this, it.parseHTML(n[1], t && t.nodeType ? t.ownerDocument || t : pt, !0)), dt.test(n[1]) && it.isPlainObject(t))for (n in t)it.isFunction(this[n]) ? this[n](t[n]) : this.attr(n, t[n]);
                return this
            }
            if (o = pt.getElementById(n[2]), o && o.parentNode) {
                if (o.id !== n[2])return ft.find(e);
                this.length = 1, this[0] = o
            }
            return this.context = pt, this.selector = e, this
        }
        return e.nodeType ? (this.context = this[0] = e, this.length = 1, this) : it.isFunction(e) ? "undefined" != typeof ft.ready ? ft.ready(e) : e(it) : (void 0 !== e.selector && (this.selector = e.selector, this.context = e.context), it.makeArray(e, this))
    };
    gt.prototype = it.fn, ft = it(pt);
    var yt = /^(?:parents|prev(?:Until|All))/, vt = {children: !0, contents: !0, next: !0, prev: !0};
    it.extend({dir: function (e, t, n) {
        for (var o = [], i = e[t]; i && 9 !== i.nodeType && (void 0 === n || 1 !== i.nodeType || !it(i).is(n));)1 === i.nodeType && o.push(i), i = i[t];
        return o
    }, sibling: function (e, t) {
        for (var n = []; e; e = e.nextSibling)1 === e.nodeType && e !== t && n.push(e);
        return n
    }}), it.fn.extend({has: function (e) {
        var t, n = it(e, this), o = n.length;
        return this.filter(function () {
            for (t = 0; o > t; t++)if (it.contains(this, n[t]))return!0
        })
    }, closest: function (e, t) {
        for (var n, o = 0, i = this.length, r = [], a = ut.test(e) || "string" != typeof e ? it(e, t || this.context) : 0; i > o; o++)for (n = this[o]; n && n !== t; n = n.parentNode)if (n.nodeType < 11 && (a ? a.index(n) > -1 : 1 === n.nodeType && it.find.matchesSelector(n, e))) {
            r.push(n);
            break
        }
        return this.pushStack(r.length > 1 ? it.unique(r) : r)
    }, index: function (e) {
        return e ? "string" == typeof e ? it.inArray(this[0], it(e)) : it.inArray(e.jquery ? e[0] : e, this) : this[0] && this[0].parentNode ? this.first().prevAll().length : -1
    }, add: function (e, t) {
        return this.pushStack(it.unique(it.merge(this.get(), it(e, t))))
    }, addBack: function (e) {
        return this.add(null == e ? this.prevObject : this.prevObject.filter(e))
    }}), it.each({parent: function (e) {
        var t = e.parentNode;
        return t && 11 !== t.nodeType ? t : null
    }, parents: function (e) {
        return it.dir(e, "parentNode")
    }, parentsUntil: function (e, t, n) {
        return it.dir(e, "parentNode", n)
    }, next: function (e) {
        return i(e, "nextSibling")
    }, prev: function (e) {
        return i(e, "previousSibling")
    }, nextAll: function (e) {
        return it.dir(e, "nextSibling")
    }, prevAll: function (e) {
        return it.dir(e, "previousSibling")
    }, nextUntil: function (e, t, n) {
        return it.dir(e, "nextSibling", n)
    }, prevUntil: function (e, t, n) {
        return it.dir(e, "previousSibling", n)
    }, siblings: function (e) {
        return it.sibling((e.parentNode || {}).firstChild, e)
    }, children: function (e) {
        return it.sibling(e.firstChild)
    }, contents: function (e) {
        return it.nodeName(e, "iframe") ? e.contentDocument || e.contentWindow.document : it.merge([], e.childNodes)
    }}, function (e, t) {
        it.fn[e] = function (n, o) {
            var i = it.map(this, t, n);
            return"Until" !== e.slice(-5) && (o = n), o && "string" == typeof o && (i = it.filter(o, i)), this.length > 1 && (vt[e] || (i = it.unique(i)), yt.test(e) && (i = i.reverse())), this.pushStack(i)
        }
    });
    var bt = /\S+/g, wt = {};
    it.Callbacks = function (e) {
        e = "string" == typeof e ? wt[e] || r(e) : it.extend({}, e);
        var t, n, o, i, a, s, l = [], c = !e.once && [], u = function (r) {
            for (n = e.memory && r, o = !0, a = s || 0, s = 0, i = l.length, t = !0; l && i > a; a++)if (l[a].apply(r[0], r[1]) === !1 && e.stopOnFalse) {
                n = !1;
                break
            }
            t = !1, l && (c ? c.length && u(c.shift()) : n ? l = [] : d.disable())
        }, d = {add: function () {
            if (l) {
                var o = l.length;
                !function r(t) {
                    it.each(t, function (t, n) {
                        var o = it.type(n);
                        "function" === o ? e.unique && d.has(n) || l.push(n) : n && n.length && "string" !== o && r(n)
                    })
                }(arguments), t ? i = l.length : n && (s = o, u(n))
            }
            return this
        }, remove: function () {
            return l && it.each(arguments, function (e, n) {
                for (var o; (o = it.inArray(n, l, o)) > -1;)l.splice(o, 1), t && (i >= o && i--, a >= o && a--)
            }), this
        }, has: function (e) {
            return e ? it.inArray(e, l) > -1 : !(!l || !l.length)
        }, empty: function () {
            return l = [], i = 0, this
        }, disable: function () {
            return l = c = n = void 0, this
        }, disabled: function () {
            return!l
        }, lock: function () {
            return c = void 0, n || d.disable(), this
        }, locked: function () {
            return!c
        }, fireWith: function (e, n) {
            return!l || o && !c || (n = n || [], n = [e, n.slice ? n.slice() : n], t ? c.push(n) : u(n)), this
        }, fire: function () {
            return d.fireWith(this, arguments), this
        }, fired: function () {
            return!!o
        }};
        return d
    }, it.extend({Deferred: function (e) {
        var t = [
            ["resolve", "done", it.Callbacks("once memory"), "resolved"],
            ["reject", "fail", it.Callbacks("once memory"), "rejected"],
            ["notify", "progress", it.Callbacks("memory")]
        ], n = "pending", o = {state: function () {
            return n
        }, always: function () {
            return i.done(arguments).fail(arguments), this
        }, then: function () {
            var e = arguments;
            return it.Deferred(function (n) {
                it.each(t, function (t, r) {
                    var a = it.isFunction(e[t]) && e[t];
                    i[r[1]](function () {
                        var e = a && a.apply(this, arguments);
                        e && it.isFunction(e.promise) ? e.promise().done(n.resolve).fail(n.reject).progress(n.notify) : n[r[0] + "With"](this === o ? n.promise() : this, a ? [e] : arguments)
                    })
                }), e = null
            }).promise()
        }, promise: function (e) {
            return null != e ? it.extend(e, o) : o
        }}, i = {};
        return o.pipe = o.then, it.each(t, function (e, r) {
            var a = r[2], s = r[3];
            o[r[1]] = a.add, s && a.add(function () {
                n = s
            }, t[1 ^ e][2].disable, t[2][2].lock), i[r[0]] = function () {
                return i[r[0] + "With"](this === i ? o : this, arguments), this
            }, i[r[0] + "With"] = a.fireWith
        }), o.promise(i), e && e.call(i, i), i
    }, when: function (e) {
        var t, n, o, i = 0, r = K.call(arguments), a = r.length, s = 1 !== a || e && it.isFunction(e.promise) ? a : 0, l = 1 === s ? e : it.Deferred(), c = function (e, n, o) {
            return function (i) {
                n[e] = this, o[e] = arguments.length > 1 ? K.call(arguments) : i, o === t ? l.notifyWith(n, o) : --s || l.resolveWith(n, o)
            }
        };
        if (a > 1)for (t = new Array(a), n = new Array(a), o = new Array(a); a > i; i++)r[i] && it.isFunction(r[i].promise) ? r[i].promise().done(c(i, o, r)).fail(l.reject).progress(c(i, n, t)) : --s;
        return s || l.resolveWith(o, r), l.promise()
    }});
    var Ct;
    it.fn.ready = function (e) {
        return it.ready.promise().done(e), this
    }, it.extend({isReady: !1, readyWait: 1, holdReady: function (e) {
        e ? it.readyWait++ : it.ready(!0)
    }, ready: function (e) {
        if (e === !0 ? !--it.readyWait : !it.isReady) {
            if (!pt.body)return setTimeout(it.ready);
            it.isReady = !0, e !== !0 && --it.readyWait > 0 || (Ct.resolveWith(pt, [it]), it.fn.triggerHandler && (it(pt).triggerHandler("ready"), it(pt).off("ready")))
        }
    }}), it.ready.promise = function (t) {
        if (!Ct)if (Ct = it.Deferred(), "complete" === pt.readyState)setTimeout(it.ready); else if (pt.addEventListener)pt.addEventListener("DOMContentLoaded", s, !1), e.addEventListener("load", s, !1); else {
            pt.attachEvent("onreadystatechange", s), e.attachEvent("onload", s);
            var n = !1;
            try {
                n = null == e.frameElement && pt.documentElement
            } catch (o) {
            }
            n && n.doScroll && !function i() {
                if (!it.isReady) {
                    try {
                        n.doScroll("left")
                    } catch (e) {
                        return setTimeout(i, 50)
                    }
                    a(), it.ready()
                }
            }()
        }
        return Ct.promise(t)
    };
    var xt, Et = "undefined";
    for (xt in it(nt))break;
    nt.ownLast = "0" !== xt, nt.inlineBlockNeedsLayout = !1, it(function () {
        var e, t, n, o;
        n = pt.getElementsByTagName("body")[0], n && n.style && (t = pt.createElement("div"), o = pt.createElement("div"), o.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(o).appendChild(t), typeof t.style.zoom !== Et && (t.style.cssText = "display:inline;margin:0;border:0;padding:1px;width:1px;zoom:1", nt.inlineBlockNeedsLayout = e = 3 === t.offsetWidth, e && (n.style.zoom = 1)), n.removeChild(o))
    }), function () {
        var e = pt.createElement("div");
        if (null == nt.deleteExpando) {
            nt.deleteExpando = !0;
            try {
                delete e.test
            } catch (t) {
                nt.deleteExpando = !1
            }
        }
        e = null
    }(), it.acceptData = function (e) {
        var t = it.noData[(e.nodeName + " ").toLowerCase()], n = +e.nodeType || 1;
        return 1 !== n && 9 !== n ? !1 : !t || t !== !0 && e.getAttribute("classid") === t
    };
    var Tt = /^(?:\{[\w\W]*\}|\[[\w\W]*\])$/, Nt = /([A-Z])/g;
    it.extend({cache: {}, noData: {"applet ": !0, "embed ": !0, "object ": "clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"}, hasData: function (e) {
        return e = e.nodeType ? it.cache[e[it.expando]] : e[it.expando], !!e && !c(e)
    }, data: function (e, t, n) {
        return u(e, t, n)
    }, removeData: function (e, t) {
        return d(e, t)
    }, _data: function (e, t, n) {
        return u(e, t, n, !0)
    }, _removeData: function (e, t) {
        return d(e, t, !0)
    }}), it.fn.extend({data: function (e, t) {
        var n, o, i, r = this[0], a = r && r.attributes;
        if (void 0 === e) {
            if (this.length && (i = it.data(r), 1 === r.nodeType && !it._data(r, "parsedAttrs"))) {
                for (n = a.length; n--;)a[n] && (o = a[n].name, 0 === o.indexOf("data-") && (o = it.camelCase(o.slice(5)), l(r, o, i[o])));
                it._data(r, "parsedAttrs", !0)
            }
            return i
        }
        return"object" == typeof e ? this.each(function () {
            it.data(this, e)
        }) : arguments.length > 1 ? this.each(function () {
            it.data(this, e, t)
        }) : r ? l(r, e, it.data(r, e)) : void 0
    }, removeData: function (e) {
        return this.each(function () {
            it.removeData(this, e)
        })
    }}), it.extend({queue: function (e, t, n) {
        var o;
        return e ? (t = (t || "fx") + "queue", o = it._data(e, t), n && (!o || it.isArray(n) ? o = it._data(e, t, it.makeArray(n)) : o.push(n)), o || []) : void 0
    }, dequeue: function (e, t) {
        t = t || "fx";
        var n = it.queue(e, t), o = n.length, i = n.shift(), r = it._queueHooks(e, t), a = function () {
            it.dequeue(e, t)
        };
        "inprogress" === i && (i = n.shift(), o--), i && ("fx" === t && n.unshift("inprogress"), delete r.stop, i.call(e, a, r)), !o && r && r.empty.fire()
    }, _queueHooks: function (e, t) {
        var n = t + "queueHooks";
        return it._data(e, n) || it._data(e, n, {empty: it.Callbacks("once memory").add(function () {
            it._removeData(e, t + "queue"), it._removeData(e, n)
        })})
    }}), it.fn.extend({queue: function (e, t) {
        var n = 2;
        return"string" != typeof e && (t = e, e = "fx", n--), arguments.length < n ? it.queue(this[0], e) : void 0 === t ? this : this.each(function () {
            var n = it.queue(this, e, t);
            it._queueHooks(this, e), "fx" === e && "inprogress" !== n[0] && it.dequeue(this, e)
        })
    }, dequeue: function (e) {
        return this.each(function () {
            it.dequeue(this, e)
        })
    }, clearQueue: function (e) {
        return this.queue(e || "fx", [])
    }, promise: function (e, t) {
        var n, o = 1, i = it.Deferred(), r = this, a = this.length, s = function () {
            --o || i.resolveWith(r, [r])
        };
        for ("string" != typeof e && (t = e, e = void 0), e = e || "fx"; a--;)n = it._data(r[a], e + "queueHooks"), n && n.empty && (o++, n.empty.add(s));
        return s(), i.promise(t)
    }});
    var St = /[+-]?(?:\d*\.|)\d+(?:[eE][+-]?\d+|)/.source, _t = ["Top", "Right", "Bottom", "Left"], Rt = function (e, t) {
        return e = t || e, "none" === it.css(e, "display") || !it.contains(e.ownerDocument, e)
    }, At = it.access = function (e, t, n, o, i, r, a) {
        var s = 0, l = e.length, c = null == n;
        if ("object" === it.type(n)) {
            i = !0;
            for (s in n)it.access(e, t, s, n[s], !0, r, a)
        } else if (void 0 !== o && (i = !0, it.isFunction(o) || (a = !0), c && (a ? (t.call(e, o), t = null) : (c = t, t = function (e, t, n) {
            return c.call(it(e), n)
        })), t))for (; l > s; s++)t(e[s], n, a ? o : o.call(e[s], s, t(e[s], n)));
        return i ? e : c ? t.call(e) : l ? t(e[0], n) : r
    }, kt = /^(?:checkbox|radio)$/i;
    !function () {
        var e = pt.createElement("input"), t = pt.createElement("div"), n = pt.createDocumentFragment();
        if (t.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", nt.leadingWhitespace = 3 === t.firstChild.nodeType, nt.tbody = !t.getElementsByTagName("tbody").length, nt.htmlSerialize = !!t.getElementsByTagName("link").length, nt.html5Clone = "<:nav></:nav>" !== pt.createElement("nav").cloneNode(!0).outerHTML, e.type = "checkbox", e.checked = !0, n.appendChild(e), nt.appendChecked = e.checked, t.innerHTML = "<textarea>x</textarea>", nt.noCloneChecked = !!t.cloneNode(!0).lastChild.defaultValue, n.appendChild(t), t.innerHTML = "<input type='radio' checked='checked' name='t'/>", nt.checkClone = t.cloneNode(!0).cloneNode(!0).lastChild.checked, nt.noCloneEvent = !0, t.attachEvent && (t.attachEvent("onclick", function () {
            nt.noCloneEvent = !1
        }), t.cloneNode(!0).click()), null == nt.deleteExpando) {
            nt.deleteExpando = !0;
            try {
                delete t.test
            } catch (o) {
                nt.deleteExpando = !1
            }
        }
    }(), function () {
        var t, n, o = pt.createElement("div");
        for (t in{submit: !0, change: !0, focusin: !0})n = "on" + t, (nt[t + "Bubbles"] = n in e) || (o.setAttribute(n, "t"), nt[t + "Bubbles"] = o.attributes[n].expando === !1);
        o = null
    }();
    var Dt = /^(?:input|select|textarea)$/i, Lt = /^key/, Ot = /^(?:mouse|pointer|contextmenu)|click/, It = /^(?:focusinfocus|focusoutblur)$/, $t = /^([^.]*)(?:\.(.+)|)$/;
    it.event = {global: {}, add: function (e, t, n, o, i) {
        var r, a, s, l, c, u, d, h, f, p, m, g = it._data(e);
        if (g) {
            for (n.handler && (l = n, n = l.handler, i = l.selector), n.guid || (n.guid = it.guid++), (a = g.events) || (a = g.events = {}), (u = g.handle) || (u = g.handle = function (e) {
                return typeof it === Et || e && it.event.triggered === e.type ? void 0 : it.event.dispatch.apply(u.elem, arguments)
            }, u.elem = e), t = (t || "").match(bt) || [""], s = t.length; s--;)r = $t.exec(t[s]) || [], f = m = r[1], p = (r[2] || "").split(".").sort(), f && (c = it.event.special[f] || {}, f = (i ? c.delegateType : c.bindType) || f, c = it.event.special[f] || {}, d = it.extend({type: f, origType: m, data: o, handler: n, guid: n.guid, selector: i, needsContext: i && it.expr.match.needsContext.test(i), namespace: p.join(".")}, l), (h = a[f]) || (h = a[f] = [], h.delegateCount = 0, c.setup && c.setup.call(e, o, p, u) !== !1 || (e.addEventListener ? e.addEventListener(f, u, !1) : e.attachEvent && e.attachEvent("on" + f, u))), c.add && (c.add.call(e, d), d.handler.guid || (d.handler.guid = n.guid)), i ? h.splice(h.delegateCount++, 0, d) : h.push(d), it.event.global[f] = !0);
            e = null
        }
    }, remove: function (e, t, n, o, i) {
        var r, a, s, l, c, u, d, h, f, p, m, g = it.hasData(e) && it._data(e);
        if (g && (u = g.events)) {
            for (t = (t || "").match(bt) || [""], c = t.length; c--;)if (s = $t.exec(t[c]) || [], f = m = s[1], p = (s[2] || "").split(".").sort(), f) {
                for (d = it.event.special[f] || {}, f = (o ? d.delegateType : d.bindType) || f, h = u[f] || [], s = s[2] && new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)"), l = r = h.length; r--;)a = h[r], !i && m !== a.origType || n && n.guid !== a.guid || s && !s.test(a.namespace) || o && o !== a.selector && ("**" !== o || !a.selector) || (h.splice(r, 1), a.selector && h.delegateCount--, d.remove && d.remove.call(e, a));
                l && !h.length && (d.teardown && d.teardown.call(e, p, g.handle) !== !1 || it.removeEvent(e, f, g.handle), delete u[f])
            } else for (f in u)it.event.remove(e, f + t[c], n, o, !0);
            it.isEmptyObject(u) && (delete g.handle, it._removeData(e, "events"))
        }
    }, trigger: function (t, n, o, i) {
        var r, a, s, l, c, u, d, h = [o || pt], f = tt.call(t, "type") ? t.type : t, p = tt.call(t, "namespace") ? t.namespace.split(".") : [];
        if (s = u = o = o || pt, 3 !== o.nodeType && 8 !== o.nodeType && !It.test(f + it.event.triggered) && (f.indexOf(".") >= 0 && (p = f.split("."), f = p.shift(), p.sort()), a = f.indexOf(":") < 0 && "on" + f, t = t[it.expando] ? t : new it.Event(f, "object" == typeof t && t), t.isTrigger = i ? 2 : 3, t.namespace = p.join("."), t.namespace_re = t.namespace ? new RegExp("(^|\\.)" + p.join("\\.(?:.*\\.|)") + "(\\.|$)") : null, t.result = void 0, t.target || (t.target = o), n = null == n ? [t] : it.makeArray(n, [t]), c = it.event.special[f] || {}, i || !c.trigger || c.trigger.apply(o, n) !== !1)) {
            if (!i && !c.noBubble && !it.isWindow(o)) {
                for (l = c.delegateType || f, It.test(l + f) || (s = s.parentNode); s; s = s.parentNode)h.push(s), u = s;
                u === (o.ownerDocument || pt) && h.push(u.defaultView || u.parentWindow || e)
            }
            for (d = 0; (s = h[d++]) && !t.isPropagationStopped();)t.type = d > 1 ? l : c.bindType || f, r = (it._data(s, "events") || {})[t.type] && it._data(s, "handle"), r && r.apply(s, n), r = a && s[a], r && r.apply && it.acceptData(s) && (t.result = r.apply(s, n), t.result === !1 && t.preventDefault());
            if (t.type = f, !i && !t.isDefaultPrevented() && (!c._default || c._default.apply(h.pop(), n) === !1) && it.acceptData(o) && a && o[f] && !it.isWindow(o)) {
                u = o[a], u && (o[a] = null), it.event.triggered = f;
                try {
                    o[f]()
                } catch (m) {
                }
                it.event.triggered = void 0, u && (o[a] = u)
            }
            return t.result
        }
    }, dispatch: function (e) {
        e = it.event.fix(e);
        var t, n, o, i, r, a = [], s = K.call(arguments), l = (it._data(this, "events") || {})[e.type] || [], c = it.event.special[e.type] || {};
        if (s[0] = e, e.delegateTarget = this, !c.preDispatch || c.preDispatch.call(this, e) !== !1) {
            for (a = it.event.handlers.call(this, e, l), t = 0; (i = a[t++]) && !e.isPropagationStopped();)for (e.currentTarget = i.elem, r = 0; (o = i.handlers[r++]) && !e.isImmediatePropagationStopped();)(!e.namespace_re || e.namespace_re.test(o.namespace)) && (e.handleObj = o, e.data = o.data, n = ((it.event.special[o.origType] || {}).handle || o.handler).apply(i.elem, s), void 0 !== n && (e.result = n) === !1 && (e.preventDefault(), e.stopPropagation()));
            return c.postDispatch && c.postDispatch.call(this, e), e.result
        }
    }, handlers: function (e, t) {
        var n, o, i, r, a = [], s = t.delegateCount, l = e.target;
        if (s && l.nodeType && (!e.button || "click" !== e.type))for (; l != this; l = l.parentNode || this)if (1 === l.nodeType && (l.disabled !== !0 || "click" !== e.type)) {
            for (i = [], r = 0; s > r; r++)o = t[r], n = o.selector + " ", void 0 === i[n] && (i[n] = o.needsContext ? it(n, this).index(l) >= 0 : it.find(n, this, null, [l]).length), i[n] && i.push(o);
            i.length && a.push({elem: l, handlers: i})
        }
        return s < t.length && a.push({elem: this, handlers: t.slice(s)}), a
    }, fix: function (e) {
        if (e[it.expando])return e;
        var t, n, o, i = e.type, r = e, a = this.fixHooks[i];
        for (a || (this.fixHooks[i] = a = Ot.test(i) ? this.mouseHooks : Lt.test(i) ? this.keyHooks : {}), o = a.props ? this.props.concat(a.props) : this.props, e = new it.Event(r), t = o.length; t--;)n = o[t], e[n] = r[n];
        return e.target || (e.target = r.srcElement || pt), 3 === e.target.nodeType && (e.target = e.target.parentNode), e.metaKey = !!e.metaKey, a.filter ? a.filter(e, r) : e
    }, props: "altKey bubbles cancelable ctrlKey currentTarget eventPhase metaKey relatedTarget shiftKey target timeStamp view which".split(" "), fixHooks: {}, keyHooks: {props: "char charCode key keyCode".split(" "), filter: function (e, t) {
        return null == e.which && (e.which = null != t.charCode ? t.charCode : t.keyCode), e
    }}, mouseHooks: {props: "button buttons clientX clientY fromElement offsetX offsetY pageX pageY screenX screenY toElement".split(" "), filter: function (e, t) {
        var n, o, i, r = t.button, a = t.fromElement;
        return null == e.pageX && null != t.clientX && (o = e.target.ownerDocument || pt, i = o.documentElement, n = o.body, e.pageX = t.clientX + (i && i.scrollLeft || n && n.scrollLeft || 0) - (i && i.clientLeft || n && n.clientLeft || 0), e.pageY = t.clientY + (i && i.scrollTop || n && n.scrollTop || 0) - (i && i.clientTop || n && n.clientTop || 0)), !e.relatedTarget && a && (e.relatedTarget = a === e.target ? t.toElement : a), e.which || void 0 === r || (e.which = 1 & r ? 1 : 2 & r ? 3 : 4 & r ? 2 : 0), e
    }}, special: {load: {noBubble: !0}, focus: {trigger: function () {
        if (this !== p() && this.focus)try {
            return this.focus(), !1
        } catch (e) {
        }
    }, delegateType: "focusin"}, blur: {trigger: function () {
        return this === p() && this.blur ? (this.blur(), !1) : void 0
    }, delegateType: "focusout"}, click: {trigger: function () {
        return it.nodeName(this, "input") && "checkbox" === this.type && this.click ? (this.click(), !1) : void 0
    }, _default: function (e) {
        return it.nodeName(e.target, "a")
    }}, beforeunload: {postDispatch: function (e) {
        void 0 !== e.result && e.originalEvent && (e.originalEvent.returnValue = e.result)
    }}}, simulate: function (e, t, n, o) {
        var i = it.extend(new it.Event, n, {type: e, isSimulated: !0, originalEvent: {}});
        o ? it.event.trigger(i, null, t) : it.event.dispatch.call(t, i), i.isDefaultPrevented() && n.preventDefault()
    }}, it.removeEvent = pt.removeEventListener ? function (e, t, n) {
        e.removeEventListener && e.removeEventListener(t, n, !1)
    } : function (e, t, n) {
        var o = "on" + t;
        e.detachEvent && (typeof e[o] === Et && (e[o] = null), e.detachEvent(o, n))
    }, it.Event = function (e, t) {
        return this instanceof it.Event ? (e && e.type ? (this.originalEvent = e, this.type = e.type, this.isDefaultPrevented = e.defaultPrevented || void 0 === e.defaultPrevented && e.returnValue === !1 ? h : f) : this.type = e, t && it.extend(this, t), this.timeStamp = e && e.timeStamp || it.now(), void(this[it.expando] = !0)) : new it.Event(e, t)
    }, it.Event.prototype = {isDefaultPrevented: f, isPropagationStopped: f, isImmediatePropagationStopped: f, preventDefault: function () {
        var e = this.originalEvent;
        this.isDefaultPrevented = h, e && (e.preventDefault ? e.preventDefault() : e.returnValue = !1)
    }, stopPropagation: function () {
        var e = this.originalEvent;
        this.isPropagationStopped = h, e && (e.stopPropagation && e.stopPropagation(), e.cancelBubble = !0)
    }, stopImmediatePropagation: function () {
        var e = this.originalEvent;
        this.isImmediatePropagationStopped = h, e && e.stopImmediatePropagation && e.stopImmediatePropagation(), this.stopPropagation()
    }}, it.each({mouseenter: "mouseover", mouseleave: "mouseout", pointerenter: "pointerover", pointerleave: "pointerout"}, function (e, t) {
        it.event.special[e] = {delegateType: t, bindType: t, handle: function (e) {
            var n, o = this, i = e.relatedTarget, r = e.handleObj;
            return(!i || i !== o && !it.contains(o, i)) && (e.type = r.origType, n = r.handler.apply(this, arguments), e.type = t), n
        }}
    }), nt.submitBubbles || (it.event.special.submit = {setup: function () {
        return it.nodeName(this, "form") ? !1 : void it.event.add(this, "click._submit keypress._submit", function (e) {
            var t = e.target, n = it.nodeName(t, "input") || it.nodeName(t, "button") ? t.form : void 0;
            n && !it._data(n, "submitBubbles") && (it.event.add(n, "submit._submit", function (e) {
                e._submit_bubble = !0
            }), it._data(n, "submitBubbles", !0))
        })
    }, postDispatch: function (e) {
        e._submit_bubble && (delete e._submit_bubble, this.parentNode && !e.isTrigger && it.event.simulate("submit", this.parentNode, e, !0))
    }, teardown: function () {
        return it.nodeName(this, "form") ? !1 : void it.event.remove(this, "._submit")
    }}), nt.changeBubbles || (it.event.special.change = {setup: function () {
        return Dt.test(this.nodeName) ? (("checkbox" === this.type || "radio" === this.type) && (it.event.add(this, "propertychange._change", function (e) {
            "checked" === e.originalEvent.propertyName && (this._just_changed = !0)
        }), it.event.add(this, "click._change", function (e) {
            this._just_changed && !e.isTrigger && (this._just_changed = !1), it.event.simulate("change", this, e, !0)
        })), !1) : void it.event.add(this, "beforeactivate._change", function (e) {
            var t = e.target;
            Dt.test(t.nodeName) && !it._data(t, "changeBubbles") && (it.event.add(t, "change._change", function (e) {
                !this.parentNode || e.isSimulated || e.isTrigger || it.event.simulate("change", this.parentNode, e, !0)
            }), it._data(t, "changeBubbles", !0))
        })
    }, handle: function (e) {
        var t = e.target;
        return this !== t || e.isSimulated || e.isTrigger || "radio" !== t.type && "checkbox" !== t.type ? e.handleObj.handler.apply(this, arguments) : void 0
    }, teardown: function () {
        return it.event.remove(this, "._change"), !Dt.test(this.nodeName)
    }}), nt.focusinBubbles || it.each({focus: "focusin", blur: "focusout"}, function (e, t) {
        var n = function (e) {
            it.event.simulate(t, e.target, it.event.fix(e), !0)
        };
        it.event.special[t] = {setup: function () {
            var o = this.ownerDocument || this, i = it._data(o, t);
            i || o.addEventListener(e, n, !0), it._data(o, t, (i || 0) + 1)
        }, teardown: function () {
            var o = this.ownerDocument || this, i = it._data(o, t) - 1;
            i ? it._data(o, t, i) : (o.removeEventListener(e, n, !0), it._removeData(o, t))
        }}
    }), it.fn.extend({on: function (e, t, n, o, i) {
        var r, a;
        if ("object" == typeof e) {
            "string" != typeof t && (n = n || t, t = void 0);
            for (r in e)this.on(r, t, n, e[r], i);
            return this
        }
        if (null == n && null == o ? (o = t, n = t = void 0) : null == o && ("string" == typeof t ? (o = n, n = void 0) : (o = n, n = t, t = void 0)), o === !1)o = f; else if (!o)return this;
        return 1 === i && (a = o, o = function (e) {
            return it().off(e), a.apply(this, arguments)
        }, o.guid = a.guid || (a.guid = it.guid++)), this.each(function () {
            it.event.add(this, e, o, n, t)
        })
    }, one: function (e, t, n, o) {
        return this.on(e, t, n, o, 1)
    }, off: function (e, t, n) {
        var o, i;
        if (e && e.preventDefault && e.handleObj)return o = e.handleObj, it(e.delegateTarget).off(o.namespace ? o.origType + "." + o.namespace : o.origType, o.selector, o.handler), this;
        if ("object" == typeof e) {
            for (i in e)this.off(i, t, e[i]);
            return this
        }
        return(t === !1 || "function" == typeof t) && (n = t, t = void 0), n === !1 && (n = f), this.each(function () {
            it.event.remove(this, e, n, t)
        })
    }, trigger: function (e, t) {
        return this.each(function () {
            it.event.trigger(e, t, this)
        })
    }, triggerHandler: function (e, t) {
        var n = this[0];
        return n ? it.event.trigger(e, t, n, !0) : void 0
    }});
    var Mt = "abbr|article|aside|audio|bdi|canvas|data|datalist|details|figcaption|figure|footer|header|hgroup|mark|meter|nav|output|progress|section|summary|time|video", Ht = / jQuery\d+="(?:null|\d+)"/g, Pt = new RegExp("<(?:" + Mt + ")[\\s/>]", "i"), Bt = /^\s+/, jt = /<(?!area|br|col|embed|hr|img|input|link|meta|param)(([\w:]+)[^>]*)\/>/gi, Ft = /<([\w:]+)/, qt = /<tbody/i, Wt = /<|&#?\w+;/, zt = /<(?:script|style|link)/i, Ut = /checked\s*(?:[^=]|=\s*.checked.)/i, Vt = /^$|\/(?:java|ecma)script/i, Xt = /^true\/(.*)/, Gt = /^\s*<!(?:\[CDATA\[|--)|(?:\]\]|--)>\s*$/g, Kt = {option: [1, "<select multiple='multiple'>", "</select>"], legend: [1, "<fieldset>", "</fieldset>"], area: [1, "<map>", "</map>"], param: [1, "<object>", "</object>"], thead: [1, "<table>", "</table>"], tr: [2, "<table><tbody>", "</tbody></table>"], col: [2, "<table><tbody></tbody><colgroup>", "</colgroup></table>"], td: [3, "<table><tbody><tr>", "</tr></tbody></table>"], _default: nt.htmlSerialize ? [0, "", ""] : [1, "X<div>", "</div>"]}, Yt = m(pt), Qt = Yt.appendChild(pt.createElement("div"));
    Kt.optgroup = Kt.option, Kt.tbody = Kt.tfoot = Kt.colgroup = Kt.caption = Kt.thead, Kt.th = Kt.td, it.extend({clone: function (e, t, n) {
        var o, i, r, a, s, l = it.contains(e.ownerDocument, e);
        if (nt.html5Clone || it.isXMLDoc(e) || !Pt.test("<" + e.nodeName + ">") ? r = e.cloneNode(!0) : (Qt.innerHTML = e.outerHTML, Qt.removeChild(r = Qt.firstChild)), !(nt.noCloneEvent && nt.noCloneChecked || 1 !== e.nodeType && 11 !== e.nodeType || it.isXMLDoc(e)))for (o = g(r), s = g(e), a = 0; null != (i = s[a]); ++a)o[a] && E(i, o[a]);
        if (t)if (n)for (s = s || g(e), o = o || g(r), a = 0; null != (i = s[a]); a++)x(i, o[a]); else x(e, r);
        return o = g(r, "script"), o.length > 0 && C(o, !l && g(e, "script")), o = s = i = null, r
    }, buildFragment: function (e, t, n, o) {
        for (var i, r, a, s, l, c, u, d = e.length, h = m(t), f = [], p = 0; d > p; p++)if (r = e[p], r || 0 === r)if ("object" === it.type(r))it.merge(f, r.nodeType ? [r] : r); else if (Wt.test(r)) {
            for (s = s || h.appendChild(t.createElement("div")), l = (Ft.exec(r) || ["", ""])[1].toLowerCase(), u = Kt[l] || Kt._default, s.innerHTML = u[1] + r.replace(jt, "<$1></$2>") + u[2], i = u[0]; i--;)s = s.lastChild;
            if (!nt.leadingWhitespace && Bt.test(r) && f.push(t.createTextNode(Bt.exec(r)[0])), !nt.tbody)for (r = "table" !== l || qt.test(r) ? "<table>" !== u[1] || qt.test(r) ? 0 : s : s.firstChild, i = r && r.childNodes.length; i--;)it.nodeName(c = r.childNodes[i], "tbody") && !c.childNodes.length && r.removeChild(c);
            for (it.merge(f, s.childNodes), s.textContent = ""; s.firstChild;)s.removeChild(s.firstChild);
            s = h.lastChild
        } else f.push(t.createTextNode(r));
        for (s && h.removeChild(s), nt.appendChecked || it.grep(g(f, "input"), y), p = 0; r = f[p++];)if ((!o || -1 === it.inArray(r, o)) && (a = it.contains(r.ownerDocument, r), s = g(h.appendChild(r), "script"), a && C(s), n))for (i = 0; r = s[i++];)Vt.test(r.type || "") && n.push(r);
        return s = null, h
    }, cleanData: function (e, t) {
        for (var n, o, i, r, a = 0, s = it.expando, l = it.cache, c = nt.deleteExpando, u = it.event.special; null != (n = e[a]); a++)if ((t || it.acceptData(n)) && (i = n[s], r = i && l[i])) {
            if (r.events)for (o in r.events)u[o] ? it.event.remove(n, o) : it.removeEvent(n, o, r.handle);
            l[i] && (delete l[i], c ? delete n[s] : typeof n.removeAttribute !== Et ? n.removeAttribute(s) : n[s] = null, G.push(i))
        }
    }}), it.fn.extend({text: function (e) {
        return At(this, function (e) {
            return void 0 === e ? it.text(this) : this.empty().append((this[0] && this[0].ownerDocument || pt).createTextNode(e))
        }, null, e, arguments.length)
    }, append: function () {
        return this.domManip(arguments, function (e) {
            if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                var t = v(this, e);
                t.appendChild(e)
            }
        })
    }, prepend: function () {
        return this.domManip(arguments, function (e) {
            if (1 === this.nodeType || 11 === this.nodeType || 9 === this.nodeType) {
                var t = v(this, e);
                t.insertBefore(e, t.firstChild)
            }
        })
    }, before: function () {
        return this.domManip(arguments, function (e) {
            this.parentNode && this.parentNode.insertBefore(e, this)
        })
    }, after: function () {
        return this.domManip(arguments, function (e) {
            this.parentNode && this.parentNode.insertBefore(e, this.nextSibling)
        })
    }, remove: function (e, t) {
        for (var n, o = e ? it.filter(e, this) : this, i = 0; null != (n = o[i]); i++)t || 1 !== n.nodeType || it.cleanData(g(n)), n.parentNode && (t && it.contains(n.ownerDocument, n) && C(g(n, "script")), n.parentNode.removeChild(n));
        return this
    }, empty: function () {
        for (var e, t = 0; null != (e = this[t]); t++) {
            for (1 === e.nodeType && it.cleanData(g(e, !1)); e.firstChild;)e.removeChild(e.firstChild);
            e.options && it.nodeName(e, "select") && (e.options.length = 0)
        }
        return this
    }, clone: function (e, t) {
        return e = null == e ? !1 : e, t = null == t ? e : t, this.map(function () {
            return it.clone(this, e, t)
        })
    }, html: function (e) {
        return At(this, function (e) {
            var t = this[0] || {}, n = 0, o = this.length;
            if (void 0 === e)return 1 === t.nodeType ? t.innerHTML.replace(Ht, "") : void 0;
            if (!("string" != typeof e || zt.test(e) || !nt.htmlSerialize && Pt.test(e) || !nt.leadingWhitespace && Bt.test(e) || Kt[(Ft.exec(e) || ["", ""])[1].toLowerCase()])) {
                e = e.replace(jt, "<$1></$2>");
                try {
                    for (; o > n; n++)t = this[n] || {}, 1 === t.nodeType && (it.cleanData(g(t, !1)), t.innerHTML = e);
                    t = 0
                } catch (i) {
                }
            }
            t && this.empty().append(e)
        }, null, e, arguments.length)
    }, replaceWith: function () {
        var e = arguments[0];
        return this.domManip(arguments, function (t) {
            e = this.parentNode, it.cleanData(g(this)), e && e.replaceChild(t, this)
        }), e && (e.length || e.nodeType) ? this : this.remove()
    }, detach: function (e) {
        return this.remove(e, !0)
    }, domManip: function (e, t) {
        e = Y.apply([], e);
        var n, o, i, r, a, s, l = 0, c = this.length, u = this, d = c - 1, h = e[0], f = it.isFunction(h);
        if (f || c > 1 && "string" == typeof h && !nt.checkClone && Ut.test(h))return this.each(function (n) {
            var o = u.eq(n);
            f && (e[0] = h.call(this, n, o.html())), o.domManip(e, t)
        });
        if (c && (s = it.buildFragment(e, this[0].ownerDocument, !1, this), n = s.firstChild, 1 === s.childNodes.length && (s = n), n)) {
            for (r = it.map(g(s, "script"), b), i = r.length; c > l; l++)o = s, l !== d && (o = it.clone(o, !0, !0), i && it.merge(r, g(o, "script"))), t.call(this[l], o, l);
            if (i)for (a = r[r.length - 1].ownerDocument, it.map(r, w), l = 0; i > l; l++)o = r[l], Vt.test(o.type || "") && !it._data(o, "globalEval") && it.contains(a, o) && (o.src ? it._evalUrl && it._evalUrl(o.src) : it.globalEval((o.text || o.textContent || o.innerHTML || "").replace(Gt, "")));
            s = n = null
        }
        return this
    }}), it.each({appendTo: "append", prependTo: "prepend", insertBefore: "before", insertAfter: "after", replaceAll: "replaceWith"}, function (e, t) {
        it.fn[e] = function (e) {
            for (var n, o = 0, i = [], r = it(e), a = r.length - 1; a >= o; o++)n = o === a ? this : this.clone(!0), it(r[o])[t](n), Q.apply(i, n.get());
            return this.pushStack(i)
        }
    });
    var Jt, Zt = {};
    !function () {
        var e;
        nt.shrinkWrapBlocks = function () {
            if (null != e)return e;
            e = !1;
            var t, n, o;
            return n = pt.getElementsByTagName("body")[0], n && n.style ? (t = pt.createElement("div"), o = pt.createElement("div"), o.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(o).appendChild(t), typeof t.style.zoom !== Et && (t.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:1px;width:1px;zoom:1", t.appendChild(pt.createElement("div")).style.width = "5px", e = 3 !== t.offsetWidth), n.removeChild(o), e) : void 0
        }
    }();
    var en, tn, nn = /^margin/, on = new RegExp("^(" + St + ")(?!px)[a-z%]+$", "i"), rn = /^(top|right|bottom|left)$/;
    e.getComputedStyle ? (en = function (e) {
        return e.ownerDocument.defaultView.getComputedStyle(e, null)
    }, tn = function (e, t, n) {
        var o, i, r, a, s = e.style;
        return n = n || en(e), a = n ? n.getPropertyValue(t) || n[t] : void 0, n && ("" !== a || it.contains(e.ownerDocument, e) || (a = it.style(e, t)), on.test(a) && nn.test(t) && (o = s.width, i = s.minWidth, r = s.maxWidth, s.minWidth = s.maxWidth = s.width = a, a = n.width, s.width = o, s.minWidth = i, s.maxWidth = r)), void 0 === a ? a : a + ""
    }) : pt.documentElement.currentStyle && (en = function (e) {
        return e.currentStyle
    }, tn = function (e, t, n) {
        var o, i, r, a, s = e.style;
        return n = n || en(e), a = n ? n[t] : void 0, null == a && s && s[t] && (a = s[t]), on.test(a) && !rn.test(t) && (o = s.left, i = e.runtimeStyle, r = i && i.left, r && (i.left = e.currentStyle.left), s.left = "fontSize" === t ? "1em" : a, a = s.pixelLeft + "px", s.left = o, r && (i.left = r)), void 0 === a ? a : a + "" || "auto"
    }), function () {
        function t() {
            var t, n, o, i;
            n = pt.getElementsByTagName("body")[0], n && n.style && (t = pt.createElement("div"), o = pt.createElement("div"), o.style.cssText = "position:absolute;border:0;width:0;height:0;top:0;left:-9999px", n.appendChild(o).appendChild(t), t.style.cssText = "-webkit-box-sizing:border-box;-moz-box-sizing:border-box;box-sizing:border-box;display:block;margin-top:1%;top:1%;border:1px;padding:1px;width:4px;position:absolute", r = a = !1, l = !0, e.getComputedStyle && (r = "1%" !== (e.getComputedStyle(t, null) || {}).top, a = "4px" === (e.getComputedStyle(t, null) || {width: "4px"}).width, i = t.appendChild(pt.createElement("div")), i.style.cssText = t.style.cssText = "-webkit-box-sizing:content-box;-moz-box-sizing:content-box;box-sizing:content-box;display:block;margin:0;border:0;padding:0", i.style.marginRight = i.style.width = "0", t.style.width = "1px", l = !parseFloat((e.getComputedStyle(i, null) || {}).marginRight)), t.innerHTML = "<table><tr><td></td><td>t</td></tr></table>", i = t.getElementsByTagName("td"), i[0].style.cssText = "margin:0;border:0;padding:0;display:none", s = 0 === i[0].offsetHeight, s && (i[0].style.display = "", i[1].style.display = "none", s = 0 === i[0].offsetHeight), n.removeChild(o))
        }

        var n, o, i, r, a, s, l;
        n = pt.createElement("div"), n.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", i = n.getElementsByTagName("a")[0], o = i && i.style, o && (o.cssText = "float:left;opacity:.5", nt.opacity = "0.5" === o.opacity, nt.cssFloat = !!o.cssFloat, n.style.backgroundClip = "content-box", n.cloneNode(!0).style.backgroundClip = "", nt.clearCloneStyle = "content-box" === n.style.backgroundClip, nt.boxSizing = "" === o.boxSizing || "" === o.MozBoxSizing || "" === o.WebkitBoxSizing, it.extend(nt, {reliableHiddenOffsets: function () {
            return null == s && t(), s
        }, boxSizingReliable: function () {
            return null == a && t(), a
        }, pixelPosition: function () {
            return null == r && t(), r
        }, reliableMarginRight: function () {
            return null == l && t(), l
        }}))
    }(), it.swap = function (e, t, n, o) {
        var i, r, a = {};
        for (r in t)a[r] = e.style[r], e.style[r] = t[r];
        i = n.apply(e, o || []);
        for (r in t)e.style[r] = a[r];
        return i
    };
    var an = /alpha\([^)]*\)/i, sn = /opacity\s*=\s*([^)]*)/, ln = /^(none|table(?!-c[ea]).+)/, cn = new RegExp("^(" + St + ")(.*)$", "i"), un = new RegExp("^([+-])=(" + St + ")", "i"), dn = {position: "absolute", visibility: "hidden", display: "block"}, hn = {letterSpacing: "0", fontWeight: "400"}, fn = ["Webkit", "O", "Moz", "ms"];
    it.extend({cssHooks: {opacity: {get: function (e, t) {
        if (t) {
            var n = tn(e, "opacity");
            return"" === n ? "1" : n
        }
    }}}, cssNumber: {columnCount: !0, fillOpacity: !0, flexGrow: !0, flexShrink: !0, fontWeight: !0, lineHeight: !0, opacity: !0, order: !0, orphans: !0, widows: !0, zIndex: !0, zoom: !0}, cssProps: {"float": nt.cssFloat ? "cssFloat" : "styleFloat"}, style: function (e, t, n, o) {
        if (e && 3 !== e.nodeType && 8 !== e.nodeType && e.style) {
            var i, r, a, s = it.camelCase(t), l = e.style;
            if (t = it.cssProps[s] || (it.cssProps[s] = _(l, s)), a = it.cssHooks[t] || it.cssHooks[s], void 0 === n)return a && "get"in a && void 0 !== (i = a.get(e, !1, o)) ? i : l[t];
            if (r = typeof n, "string" === r && (i = un.exec(n)) && (n = (i[1] + 1) * i[2] + parseFloat(it.css(e, t)), r = "number"), null != n && n === n && ("number" !== r || it.cssNumber[s] || (n += "px"), nt.clearCloneStyle || "" !== n || 0 !== t.indexOf("background") || (l[t] = "inherit"), !(a && "set"in a && void 0 === (n = a.set(e, n, o)))))try {
                l[t] = n
            } catch (c) {
            }
        }
    }, css: function (e, t, n, o) {
        var i, r, a, s = it.camelCase(t);
        return t = it.cssProps[s] || (it.cssProps[s] = _(e.style, s)), a = it.cssHooks[t] || it.cssHooks[s], a && "get"in a && (r = a.get(e, !0, n)), void 0 === r && (r = tn(e, t, o)), "normal" === r && t in hn && (r = hn[t]), "" === n || n ? (i = parseFloat(r), n === !0 || it.isNumeric(i) ? i || 0 : r) : r
    }}), it.each(["height", "width"], function (e, t) {
        it.cssHooks[t] = {get: function (e, n, o) {
            return n ? ln.test(it.css(e, "display")) && 0 === e.offsetWidth ? it.swap(e, dn, function () {
                return D(e, t, o)
            }) : D(e, t, o) : void 0
        }, set: function (e, n, o) {
            var i = o && en(e);
            return A(e, n, o ? k(e, t, o, nt.boxSizing && "border-box" === it.css(e, "boxSizing", !1, i), i) : 0)
        }}
    }), nt.opacity || (it.cssHooks.opacity = {get: function (e, t) {
        return sn.test((t && e.currentStyle ? e.currentStyle.filter : e.style.filter) || "") ? .01 * parseFloat(RegExp.$1) + "" : t ? "1" : ""
    }, set: function (e, t) {
        var n = e.style, o = e.currentStyle, i = it.isNumeric(t) ? "alpha(opacity=" + 100 * t + ")" : "", r = o && o.filter || n.filter || "";
        n.zoom = 1, (t >= 1 || "" === t) && "" === it.trim(r.replace(an, "")) && n.removeAttribute && (n.removeAttribute("filter"), "" === t || o && !o.filter) || (n.filter = an.test(r) ? r.replace(an, i) : r + " " + i)
    }}), it.cssHooks.marginRight = S(nt.reliableMarginRight, function (e, t) {
        return t ? it.swap(e, {display: "inline-block"}, tn, [e, "marginRight"]) : void 0
    }), it.each({margin: "", padding: "", border: "Width"}, function (e, t) {
        it.cssHooks[e + t] = {expand: function (n) {
            for (var o = 0, i = {}, r = "string" == typeof n ? n.split(" ") : [n]; 4 > o; o++)i[e + _t[o] + t] = r[o] || r[o - 2] || r[0];
            return i
        }}, nn.test(e) || (it.cssHooks[e + t].set = A)
    }), it.fn.extend({css: function (e, t) {
        return At(this, function (e, t, n) {
            var o, i, r = {}, a = 0;
            if (it.isArray(t)) {
                for (o = en(e), i = t.length; i > a; a++)r[t[a]] = it.css(e, t[a], !1, o);
                return r
            }
            return void 0 !== n ? it.style(e, t, n) : it.css(e, t)
        }, e, t, arguments.length > 1)
    }, show: function () {
        return R(this, !0)
    }, hide: function () {
        return R(this)
    }, toggle: function (e) {
        return"boolean" == typeof e ? e ? this.show() : this.hide() : this.each(function () {
            Rt(this) ? it(this).show() : it(this).hide()
        })
    }}), it.Tween = L, L.prototype = {constructor: L, init: function (e, t, n, o, i, r) {
        this.elem = e, this.prop = n, this.easing = i || "swing", this.options = t, this.start = this.now = this.cur(), this.end = o, this.unit = r || (it.cssNumber[n] ? "" : "px")
    }, cur: function () {
        var e = L.propHooks[this.prop];
        return e && e.get ? e.get(this) : L.propHooks._default.get(this)
    }, run: function (e) {
        var t, n = L.propHooks[this.prop];
        return this.pos = t = this.options.duration ? it.easing[this.easing](e, this.options.duration * e, 0, 1, this.options.duration) : e, this.now = (this.end - this.start) * t + this.start, this.options.step && this.options.step.call(this.elem, this.now, this), n && n.set ? n.set(this) : L.propHooks._default.set(this), this
    }}, L.prototype.init.prototype = L.prototype, L.propHooks = {_default: {get: function (e) {
        var t;
        return null == e.elem[e.prop] || e.elem.style && null != e.elem.style[e.prop] ? (t = it.css(e.elem, e.prop, ""), t && "auto" !== t ? t : 0) : e.elem[e.prop]
    }, set: function (e) {
        it.fx.step[e.prop] ? it.fx.step[e.prop](e) : e.elem.style && (null != e.elem.style[it.cssProps[e.prop]] || it.cssHooks[e.prop]) ? it.style(e.elem, e.prop, e.now + e.unit) : e.elem[e.prop] = e.now
    }}}, L.propHooks.scrollTop = L.propHooks.scrollLeft = {set: function (e) {
        e.elem.nodeType && e.elem.parentNode && (e.elem[e.prop] = e.now)
    }}, it.easing = {linear: function (e) {
        return e
    }, swing: function (e) {
        return.5 - Math.cos(e * Math.PI) / 2
    }}, it.fx = L.prototype.init, it.fx.step = {};
    var pn, mn, gn = /^(?:toggle|show|hide)$/, yn = new RegExp("^(?:([+-])=|)(" + St + ")([a-z%]*)$", "i"), vn = /queueHooks$/, bn = [M], wn = {"*": [function (e, t) {
        var n = this.createTween(e, t), o = n.cur(), i = yn.exec(t), r = i && i[3] || (it.cssNumber[e] ? "" : "px"), a = (it.cssNumber[e] || "px" !== r && +o) && yn.exec(it.css(n.elem, e)), s = 1, l = 20;
        if (a && a[3] !== r) {
            r = r || a[3], i = i || [], a = +o || 1;
            do s = s || ".5", a /= s, it.style(n.elem, e, a + r); while (s !== (s = n.cur() / o) && 1 !== s && --l)
        }
        return i && (a = n.start = +a || +o || 0, n.unit = r, n.end = i[1] ? a + (i[1] + 1) * i[2] : +i[2]), n
    }]};
    it.Animation = it.extend(P, {tweener: function (e, t) {
        it.isFunction(e) ? (t = e, e = ["*"]) : e = e.split(" ");
        for (var n, o = 0, i = e.length; i > o; o++)n = e[o], wn[n] = wn[n] || [], wn[n].unshift(t)
    }, prefilter: function (e, t) {
        t ? bn.unshift(e) : bn.push(e)
    }}), it.speed = function (e, t, n) {
        var o = e && "object" == typeof e ? it.extend({}, e) : {complete: n || !n && t || it.isFunction(e) && e, duration: e, easing: n && t || t && !it.isFunction(t) && t};
        return o.duration = it.fx.off ? 0 : "number" == typeof o.duration ? o.duration : o.duration in it.fx.speeds ? it.fx.speeds[o.duration] : it.fx.speeds._default, (null == o.queue || o.queue === !0) && (o.queue = "fx"), o.old = o.complete, o.complete = function () {
            it.isFunction(o.old) && o.old.call(this), o.queue && it.dequeue(this, o.queue)
        }, o
    }, it.fn.extend({fadeTo: function (e, t, n, o) {
        return this.filter(Rt).css("opacity", 0).show().end().animate({opacity: t}, e, n, o)
    }, animate: function (e, t, n, o) {
        var i = it.isEmptyObject(e), r = it.speed(t, n, o), a = function () {
            var t = P(this, it.extend({}, e), r);
            (i || it._data(this, "finish")) && t.stop(!0)
        };
        return a.finish = a, i || r.queue === !1 ? this.each(a) : this.queue(r.queue, a)
    }, stop: function (e, t, n) {
        var o = function (e) {
            var t = e.stop;
            delete e.stop, t(n)
        };
        return"string" != typeof e && (n = t, t = e, e = void 0), t && e !== !1 && this.queue(e || "fx", []), this.each(function () {
            var t = !0, i = null != e && e + "queueHooks", r = it.timers, a = it._data(this);
            if (i)a[i] && a[i].stop && o(a[i]); else for (i in a)a[i] && a[i].stop && vn.test(i) && o(a[i]);
            for (i = r.length; i--;)r[i].elem !== this || null != e && r[i].queue !== e || (r[i].anim.stop(n), t = !1, r.splice(i, 1));
            (t || !n) && it.dequeue(this, e)
        })
    }, finish: function (e) {
        return e !== !1 && (e = e || "fx"), this.each(function () {
            var t, n = it._data(this), o = n[e + "queue"], i = n[e + "queueHooks"], r = it.timers, a = o ? o.length : 0;
            for (n.finish = !0, it.queue(this, e, []), i && i.stop && i.stop.call(this, !0), t = r.length; t--;)r[t].elem === this && r[t].queue === e && (r[t].anim.stop(!0), r.splice(t, 1));
            for (t = 0; a > t; t++)o[t] && o[t].finish && o[t].finish.call(this);
            delete n.finish
        })
    }}), it.each(["toggle", "show", "hide"], function (e, t) {
        var n = it.fn[t];
        it.fn[t] = function (e, o, i) {
            return null == e || "boolean" == typeof e ? n.apply(this, arguments) : this.animate(I(t, !0), e, o, i)
        }
    }), it.each({slideDown: I("show"), slideUp: I("hide"), slideToggle: I("toggle"), fadeIn: {opacity: "show"}, fadeOut: {opacity: "hide"}, fadeToggle: {opacity: "toggle"}}, function (e, t) {
        it.fn[e] = function (e, n, o) {
            return this.animate(t, e, n, o)
        }
    }), it.timers = [], it.fx.tick = function () {
        var e, t = it.timers, n = 0;
        for (pn = it.now(); n < t.length; n++)e = t[n], e() || t[n] !== e || t.splice(n--, 1);
        t.length || it.fx.stop(), pn = void 0
    }, it.fx.timer = function (e) {
        it.timers.push(e), e() ? it.fx.start() : it.timers.pop()
    }, it.fx.interval = 13, it.fx.start = function () {
        mn || (mn = setInterval(it.fx.tick, it.fx.interval))
    }, it.fx.stop = function () {
        clearInterval(mn), mn = null
    }, it.fx.speeds = {slow: 600, fast: 200, _default: 400}, it.fn.delay = function (e, t) {
        return e = it.fx ? it.fx.speeds[e] || e : e, t = t || "fx", this.queue(t, function (t, n) {
            var o = setTimeout(t, e);
            n.stop = function () {
                clearTimeout(o)
            }
        })
    }, function () {
        var e, t, n, o, i;
        t = pt.createElement("div"), t.setAttribute("className", "t"), t.innerHTML = "  <link/><table></table><a href='/a'>a</a><input type='checkbox'/>", o = t.getElementsByTagName("a")[0], n = pt.createElement("select"), i = n.appendChild(pt.createElement("option")), e = t.getElementsByTagName("input")[0], o.style.cssText = "top:1px", nt.getSetAttribute = "t" !== t.className, nt.style = /top/.test(o.getAttribute("style")), nt.hrefNormalized = "/a" === o.getAttribute("href"), nt.checkOn = !!e.value, nt.optSelected = i.selected, nt.enctype = !!pt.createElement("form").enctype, n.disabled = !0, nt.optDisabled = !i.disabled, e = pt.createElement("input"), e.setAttribute("value", ""), nt.input = "" === e.getAttribute("value"), e.value = "t", e.setAttribute("type", "radio"), nt.radioValue = "t" === e.value
    }();
    var Cn = /\r/g;
    it.fn.extend({val: function (e) {
        var t, n, o, i = this[0];
        {
            if (arguments.length)return o = it.isFunction(e), this.each(function (n) {
                var i;
                1 === this.nodeType && (i = o ? e.call(this, n, it(this).val()) : e, null == i ? i = "" : "number" == typeof i ? i += "" : it.isArray(i) && (i = it.map(i, function (e) {
                    return null == e ? "" : e + ""
                })), t = it.valHooks[this.type] || it.valHooks[this.nodeName.toLowerCase()], t && "set"in t && void 0 !== t.set(this, i, "value") || (this.value = i))
            });
            if (i)return t = it.valHooks[i.type] || it.valHooks[i.nodeName.toLowerCase()], t && "get"in t && void 0 !== (n = t.get(i, "value")) ? n : (n = i.value, "string" == typeof n ? n.replace(Cn, "") : null == n ? "" : n)
        }
    }}), it.extend({valHooks: {option: {get: function (e) {
        var t = it.find.attr(e, "value");
        return null != t ? t : it.trim(it.text(e))
    }}, select: {get: function (e) {
        for (var t, n, o = e.options, i = e.selectedIndex, r = "select-one" === e.type || 0 > i, a = r ? null : [], s = r ? i + 1 : o.length, l = 0 > i ? s : r ? i : 0; s > l; l++)if (n = o[l], !(!n.selected && l !== i || (nt.optDisabled ? n.disabled : null !== n.getAttribute("disabled")) || n.parentNode.disabled && it.nodeName(n.parentNode, "optgroup"))) {
            if (t = it(n).val(), r)return t;
            a.push(t)
        }
        return a
    }, set: function (e, t) {
        for (var n, o, i = e.options, r = it.makeArray(t), a = i.length; a--;)if (o = i[a], it.inArray(it.valHooks.option.get(o), r) >= 0)try {
            o.selected = n = !0
        } catch (s) {
            o.scrollHeight
        } else o.selected = !1;
        return n || (e.selectedIndex = -1), i
    }}}}), it.each(["radio", "checkbox"], function () {
        it.valHooks[this] = {set: function (e, t) {
            return it.isArray(t) ? e.checked = it.inArray(it(e).val(), t) >= 0 : void 0
        }}, nt.checkOn || (it.valHooks[this].get = function (e) {
            return null === e.getAttribute("value") ? "on" : e.value
        })
    });
    var xn, En, Tn = it.expr.attrHandle, Nn = /^(?:checked|selected)$/i, Sn = nt.getSetAttribute, _n = nt.input;
    it.fn.extend({attr: function (e, t) {
        return At(this, it.attr, e, t, arguments.length > 1)
    }, removeAttr: function (e) {
        return this.each(function () {
            it.removeAttr(this, e)
        })
    }}), it.extend({attr: function (e, t, n) {
        var o, i, r = e.nodeType;
        if (e && 3 !== r && 8 !== r && 2 !== r)return typeof e.getAttribute === Et ? it.prop(e, t, n) : (1 === r && it.isXMLDoc(e) || (t = t.toLowerCase(), o = it.attrHooks[t] || (it.expr.match.bool.test(t) ? En : xn)), void 0 === n ? o && "get"in o && null !== (i = o.get(e, t)) ? i : (i = it.find.attr(e, t), null == i ? void 0 : i) : null !== n ? o && "set"in o && void 0 !== (i = o.set(e, n, t)) ? i : (e.setAttribute(t, n + ""), n) : void it.removeAttr(e, t))
    }, removeAttr: function (e, t) {
        var n, o, i = 0, r = t && t.match(bt);
        if (r && 1 === e.nodeType)for (; n = r[i++];)o = it.propFix[n] || n, it.expr.match.bool.test(n) ? _n && Sn || !Nn.test(n) ? e[o] = !1 : e[it.camelCase("default-" + n)] = e[o] = !1 : it.attr(e, n, ""), e.removeAttribute(Sn ? n : o)
    }, attrHooks: {type: {set: function (e, t) {
        if (!nt.radioValue && "radio" === t && it.nodeName(e, "input")) {
            var n = e.value;
            return e.setAttribute("type", t), n && (e.value = n), t
        }
    }}}}), En = {set: function (e, t, n) {
        return t === !1 ? it.removeAttr(e, n) : _n && Sn || !Nn.test(n) ? e.setAttribute(!Sn && it.propFix[n] || n, n) : e[it.camelCase("default-" + n)] = e[n] = !0, n
    }}, it.each(it.expr.match.bool.source.match(/\w+/g), function (e, t) {
        var n = Tn[t] || it.find.attr;
        Tn[t] = _n && Sn || !Nn.test(t) ? function (e, t, o) {
            var i, r;
            return o || (r = Tn[t], Tn[t] = i, i = null != n(e, t, o) ? t.toLowerCase() : null, Tn[t] = r), i
        } : function (e, t, n) {
            return n ? void 0 : e[it.camelCase("default-" + t)] ? t.toLowerCase() : null
        }
    }), _n && Sn || (it.attrHooks.value = {set: function (e, t, n) {
        return it.nodeName(e, "input") ? void(e.defaultValue = t) : xn && xn.set(e, t, n)
    }}), Sn || (xn = {set: function (e, t, n) {
        var o = e.getAttributeNode(n);
        return o || e.setAttributeNode(o = e.ownerDocument.createAttribute(n)), o.value = t += "", "value" === n || t === e.getAttribute(n) ? t : void 0
    }}, Tn.id = Tn.name = Tn.coords = function (e, t, n) {
        var o;
        return n ? void 0 : (o = e.getAttributeNode(t)) && "" !== o.value ? o.value : null
    }, it.valHooks.button = {get: function (e, t) {
        var n = e.getAttributeNode(t);
        return n && n.specified ? n.value : void 0
    }, set: xn.set}, it.attrHooks.contenteditable = {set: function (e, t, n) {
        xn.set(e, "" === t ? !1 : t, n)
    }}, it.each(["width", "height"], function (e, t) {
        it.attrHooks[t] = {set: function (e, n) {
            return"" === n ? (e.setAttribute(t, "auto"), n) : void 0
        }}
    })), nt.style || (it.attrHooks.style = {get: function (e) {
        return e.style.cssText || void 0
    }, set: function (e, t) {
        return e.style.cssText = t + ""
    }});
    var Rn = /^(?:input|select|textarea|button|object)$/i, An = /^(?:a|area)$/i;
    it.fn.extend({prop: function (e, t) {
        return At(this, it.prop, e, t, arguments.length > 1)
    }, removeProp: function (e) {
        return e = it.propFix[e] || e, this.each(function () {
            try {
                this[e] = void 0, delete this[e]
            } catch (t) {
            }
        })
    }}), it.extend({propFix: {"for": "htmlFor", "class": "className"}, prop: function (e, t, n) {
        var o, i, r, a = e.nodeType;
        if (e && 3 !== a && 8 !== a && 2 !== a)return r = 1 !== a || !it.isXMLDoc(e), r && (t = it.propFix[t] || t, i = it.propHooks[t]), void 0 !== n ? i && "set"in i && void 0 !== (o = i.set(e, n, t)) ? o : e[t] = n : i && "get"in i && null !== (o = i.get(e, t)) ? o : e[t]
    }, propHooks: {tabIndex: {get: function (e) {
        var t = it.find.attr(e, "tabindex");
        return t ? parseInt(t, 10) : Rn.test(e.nodeName) || An.test(e.nodeName) && e.href ? 0 : -1
    }}}}), nt.hrefNormalized || it.each(["href", "src"], function (e, t) {
        it.propHooks[t] = {get: function (e) {
            return e.getAttribute(t, 4)
        }}
    }), nt.optSelected || (it.propHooks.selected = {get: function (e) {
        var t = e.parentNode;
        return t && (t.selectedIndex, t.parentNode && t.parentNode.selectedIndex), null
    }}), it.each(["tabIndex", "readOnly", "maxLength", "cellSpacing", "cellPadding", "rowSpan", "colSpan", "useMap", "frameBorder", "contentEditable"], function () {
        it.propFix[this.toLowerCase()] = this
    }), nt.enctype || (it.propFix.enctype = "encoding");
    var kn = /[\t\r\n\f]/g;
    it.fn.extend({addClass: function (e) {
        var t, n, o, i, r, a, s = 0, l = this.length, c = "string" == typeof e && e;
        if (it.isFunction(e))return this.each(function (t) {
            it(this).addClass(e.call(this, t, this.className))
        });
        if (c)for (t = (e || "").match(bt) || []; l > s; s++)if (n = this[s], o = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(kn, " ") : " ")) {
            for (r = 0; i = t[r++];)o.indexOf(" " + i + " ") < 0 && (o += i + " ");
            a = it.trim(o), n.className !== a && (n.className = a)
        }
        return this
    }, removeClass: function (e) {
        var t, n, o, i, r, a, s = 0, l = this.length, c = 0 === arguments.length || "string" == typeof e && e;
        if (it.isFunction(e))return this.each(function (t) {
            it(this).removeClass(e.call(this, t, this.className))
        });
        if (c)for (t = (e || "").match(bt) || []; l > s; s++)if (n = this[s], o = 1 === n.nodeType && (n.className ? (" " + n.className + " ").replace(kn, " ") : "")) {
            for (r = 0; i = t[r++];)for (; o.indexOf(" " + i + " ") >= 0;)o = o.replace(" " + i + " ", " ");
            a = e ? it.trim(o) : "", n.className !== a && (n.className = a)
        }
        return this
    }, toggleClass: function (e, t) {
        var n = typeof e;
        return"boolean" == typeof t && "string" === n ? t ? this.addClass(e) : this.removeClass(e) : this.each(it.isFunction(e) ? function (n) {
            it(this).toggleClass(e.call(this, n, this.className, t), t)
        } : function () {
            if ("string" === n)for (var t, o = 0, i = it(this), r = e.match(bt) || []; t = r[o++];)i.hasClass(t) ? i.removeClass(t) : i.addClass(t); else(n === Et || "boolean" === n) && (this.className && it._data(this, "__className__", this.className), this.className = this.className || e === !1 ? "" : it._data(this, "__className__") || "")
        })
    }, hasClass: function (e) {
        for (var t = " " + e + " ", n = 0, o = this.length; o > n; n++)if (1 === this[n].nodeType && (" " + this[n].className + " ").replace(kn, " ").indexOf(t) >= 0)return!0;
        return!1
    }}), it.each("blur focus focusin focusout load resize scroll unload click dblclick mousedown mouseup mousemove mouseover mouseout mouseenter mouseleave change select submit keydown keypress keyup error contextmenu".split(" "), function (e, t) {
        it.fn[t] = function (e, n) {
            return arguments.length > 0 ? this.on(t, null, e, n) : this.trigger(t)
        }
    }), it.fn.extend({hover: function (e, t) {
        return this.mouseenter(e).mouseleave(t || e)
    }, bind: function (e, t, n) {
        return this.on(e, null, t, n)
    }, unbind: function (e, t) {
        return this.off(e, null, t)
    }, delegate: function (e, t, n, o) {
        return this.on(t, e, n, o)
    }, undelegate: function (e, t, n) {
        return 1 === arguments.length ? this.off(e, "**") : this.off(t, e || "**", n)
    }});
    var Dn = it.now(), Ln = /\?/, On = /(,)|(\[|{)|(}|])|"(?:[^"\\\r\n]|\\["\\\/bfnrt]|\\u[\da-fA-F]{4})*"\s*:?|true|false|null|-?(?!0\d)\d+(?:\.\d+|)(?:[eE][+-]?\d+|)/g;
    it.parseJSON = function (t) {
        if (e.JSON && e.JSON.parse)return e.JSON.parse(t + "");
        var n, o = null, i = it.trim(t + "");
        return i && !it.trim(i.replace(On, function (e, t, i, r) {
            return n && t && (o = 0), 0 === o ? e : (n = i || t, o += !r - !i, "")
        })) ? Function("return " + i)() : it.error("Invalid JSON: " + t)
    }, it.parseXML = function (t) {
        var n, o;
        if (!t || "string" != typeof t)return null;
        try {
            e.DOMParser ? (o = new DOMParser, n = o.parseFromString(t, "text/xml")) : (n = new ActiveXObject("Microsoft.XMLDOM"), n.async = "false", n.loadXML(t))
        } catch (i) {
            n = void 0
        }
        return n && n.documentElement && !n.getElementsByTagName("parsererror").length || it.error("Invalid XML: " + t), n
    };
    var In, $n, Mn = /#.*$/, Hn = /([?&])_=[^&]*/, Pn = /^(.*?):[ \t]*([^\r\n]*)\r?$/gm, Bn = /^(?:about|app|app-storage|.+-extension|file|res|widget):$/, jn = /^(?:GET|HEAD)$/, Fn = /^\/\//, qn = /^([\w.+-]+:)(?:\/\/(?:[^\/?#]*@|)([^\/?#:]*)(?::(\d+)|)|)/, Wn = {}, zn = {}, Un = "*/".concat("*");
    try {
        $n = location.href
    } catch (Vn) {
        $n = pt.createElement("a"), $n.href = "", $n = $n.href
    }
    In = qn.exec($n.toLowerCase()) || [], it.extend({active: 0, lastModified: {}, etag: {}, ajaxSettings: {url: $n, type: "GET", isLocal: Bn.test(In[1]), global: !0, processData: !0, async: !0, contentType: "application/x-www-form-urlencoded; charset=UTF-8", accepts: {"*": Un, text: "text/plain", html: "text/html", xml: "application/xml, text/xml", json: "application/json, text/javascript"}, contents: {xml: /xml/, html: /html/, json: /json/}, responseFields: {xml: "responseXML", text: "responseText", json: "responseJSON"}, converters: {"* text": String, "text html": !0, "text json": it.parseJSON, "text xml": it.parseXML}, flatOptions: {url: !0, context: !0}}, ajaxSetup: function (e, t) {
        return t ? F(F(e, it.ajaxSettings), t) : F(it.ajaxSettings, e)
    }, ajaxPrefilter: B(Wn), ajaxTransport: B(zn), ajax: function (e, t) {
        function n(e, t, n, o) {
            var i, u, y, v, w, x = t;
            2 !== b && (b = 2, s && clearTimeout(s), c = void 0, a = o || "", C.readyState = e > 0 ? 4 : 0, i = e >= 200 && 300 > e || 304 === e, n && (v = q(d, C, n)), v = W(d, v, C, i), i ? (d.ifModified && (w = C.getResponseHeader("Last-Modified"), w && (it.lastModified[r] = w), w = C.getResponseHeader("etag"), w && (it.etag[r] = w)), 204 === e || "HEAD" === d.type ? x = "nocontent" : 304 === e ? x = "notmodified" : (x = v.state, u = v.data, y = v.error, i = !y)) : (y = x, (e || !x) && (x = "error", 0 > e && (e = 0))), C.status = e, C.statusText = (t || x) + "", i ? p.resolveWith(h, [u, x, C]) : p.rejectWith(h, [C, x, y]), C.statusCode(g), g = void 0, l && f.trigger(i ? "ajaxSuccess" : "ajaxError", [C, d, i ? u : y]), m.fireWith(h, [C, x]), l && (f.trigger("ajaxComplete", [C, d]), --it.active || it.event.trigger("ajaxStop")))
        }

        "object" == typeof e && (t = e, e = void 0), t = t || {};
        var o, i, r, a, s, l, c, u, d = it.ajaxSetup({}, t), h = d.context || d, f = d.context && (h.nodeType || h.jquery) ? it(h) : it.event, p = it.Deferred(), m = it.Callbacks("once memory"), g = d.statusCode || {}, y = {}, v = {}, b = 0, w = "canceled", C = {readyState: 0, getResponseHeader: function (e) {
            var t;
            if (2 === b) {
                if (!u)for (u = {}; t = Pn.exec(a);)u[t[1].toLowerCase()] = t[2];
                t = u[e.toLowerCase()]
            }
            return null == t ? null : t
        }, getAllResponseHeaders: function () {
            return 2 === b ? a : null
        }, setRequestHeader: function (e, t) {
            var n = e.toLowerCase();
            return b || (e = v[n] = v[n] || e, y[e] = t), this
        }, overrideMimeType: function (e) {
            return b || (d.mimeType = e), this
        }, statusCode: function (e) {
            var t;
            if (e)if (2 > b)for (t in e)g[t] = [g[t], e[t]]; else C.always(e[C.status]);
            return this
        }, abort: function (e) {
            var t = e || w;
            return c && c.abort(t), n(0, t), this
        }};
        if (p.promise(C).complete = m.add, C.success = C.done, C.error = C.fail, d.url = ((e || d.url || $n) + "").replace(Mn, "").replace(Fn, In[1] + "//"), d.type = t.method || t.type || d.method || d.type, d.dataTypes = it.trim(d.dataType || "*").toLowerCase().match(bt) || [""], null == d.crossDomain && (o = qn.exec(d.url.toLowerCase()), d.crossDomain = !(!o || o[1] === In[1] && o[2] === In[2] && (o[3] || ("http:" === o[1] ? "80" : "443")) === (In[3] || ("http:" === In[1] ? "80" : "443")))), d.data && d.processData && "string" != typeof d.data && (d.data = it.param(d.data, d.traditional)), j(Wn, d, t, C), 2 === b)return C;
        l = d.global, l && 0 === it.active++ && it.event.trigger("ajaxStart"), d.type = d.type.toUpperCase(), d.hasContent = !jn.test(d.type), r = d.url, d.hasContent || (d.data && (r = d.url += (Ln.test(r) ? "&" : "?") + d.data, delete d.data), d.cache === !1 && (d.url = Hn.test(r) ? r.replace(Hn, "$1_=" + Dn++) : r + (Ln.test(r) ? "&" : "?") + "_=" + Dn++)), d.ifModified && (it.lastModified[r] && C.setRequestHeader("If-Modified-Since", it.lastModified[r]), it.etag[r] && C.setRequestHeader("If-None-Match", it.etag[r])), (d.data && d.hasContent && d.contentType !== !1 || t.contentType) && C.setRequestHeader("Content-Type", d.contentType), C.setRequestHeader("Accept", d.dataTypes[0] && d.accepts[d.dataTypes[0]] ? d.accepts[d.dataTypes[0]] + ("*" !== d.dataTypes[0] ? ", " + Un + "; q=0.01" : "") : d.accepts["*"]);
        for (i in d.headers)C.setRequestHeader(i, d.headers[i]);
        if (d.beforeSend && (d.beforeSend.call(h, C, d) === !1 || 2 === b))return C.abort();
        w = "abort";
        for (i in{success: 1, error: 1, complete: 1})C[i](d[i]);
        if (c = j(zn, d, t, C)) {
            C.readyState = 1, l && f.trigger("ajaxSend", [C, d]), d.async && d.timeout > 0 && (s = setTimeout(function () {
                C.abort("timeout")
            }, d.timeout));
            try {
                b = 1, c.send(y, n)
            } catch (x) {
                if (!(2 > b))throw x;
                n(-1, x)
            }
        } else n(-1, "No Transport");
        return C
    }, getJSON: function (e, t, n) {
        return it.get(e, t, n, "json")
    }, getScript: function (e, t) {
        return it.get(e, void 0, t, "script")
    }}), it.each(["get", "post"], function (e, t) {
        it[t] = function (e, n, o, i) {
            return it.isFunction(n) && (i = i || o, o = n, n = void 0), it.ajax({url: e, type: t, dataType: i, data: n, success: o})
        }
    }), it.each(["ajaxStart", "ajaxStop", "ajaxComplete", "ajaxError", "ajaxSuccess", "ajaxSend"], function (e, t) {
        it.fn[t] = function (e) {
            return this.on(t, e)
        }
    }), it._evalUrl = function (e) {
        return it.ajax({url: e, type: "GET", dataType: "script", async: !1, global: !1, "throws": !0})
    }, it.fn.extend({wrapAll: function (e) {
        if (it.isFunction(e))return this.each(function (t) {
            it(this).wrapAll(e.call(this, t))
        });
        if (this[0]) {
            var t = it(e, this[0].ownerDocument).eq(0).clone(!0);
            this[0].parentNode && t.insertBefore(this[0]), t.map(function () {
                for (var e = this; e.firstChild && 1 === e.firstChild.nodeType;)e = e.firstChild;
                return e
            }).append(this)
        }
        return this
    }, wrapInner: function (e) {
        return this.each(it.isFunction(e) ? function (t) {
            it(this).wrapInner(e.call(this, t))
        } : function () {
            var t = it(this), n = t.contents();
            n.length ? n.wrapAll(e) : t.append(e)
        })
    }, wrap: function (e) {
        var t = it.isFunction(e);
        return this.each(function (n) {
            it(this).wrapAll(t ? e.call(this, n) : e)
        })
    }, unwrap: function () {
        return this.parent().each(function () {
            it.nodeName(this, "body") || it(this).replaceWith(this.childNodes)
        }).end()
    }}), it.expr.filters.hidden = function (e) {
        return e.offsetWidth <= 0 && e.offsetHeight <= 0 || !nt.reliableHiddenOffsets() && "none" === (e.style && e.style.display || it.css(e, "display"))
    }, it.expr.filters.visible = function (e) {
        return!it.expr.filters.hidden(e)
    };
    var Xn = /%20/g, Gn = /\[\]$/, Kn = /\r?\n/g, Yn = /^(?:submit|button|image|reset|file)$/i, Qn = /^(?:input|select|textarea|keygen)/i;
    it.param = function (e, t) {
        var n, o = [], i = function (e, t) {
            t = it.isFunction(t) ? t() : null == t ? "" : t, o[o.length] = encodeURIComponent(e) + "=" + encodeURIComponent(t)
        };
        if (void 0 === t && (t = it.ajaxSettings && it.ajaxSettings.traditional), it.isArray(e) || e.jquery && !it.isPlainObject(e))it.each(e, function () {
            i(this.name, this.value)
        }); else for (n in e)z(n, e[n], t, i);
        return o.join("&").replace(Xn, "+")
    }, it.fn.extend({serialize: function () {
        return it.param(this.serializeArray())
    }, serializeArray: function () {
        return this.map(function () {
            var e = it.prop(this, "elements");
            return e ? it.makeArray(e) : this
        }).filter(function () {
            var e = this.type;
            return this.name && !it(this).is(":disabled") && Qn.test(this.nodeName) && !Yn.test(e) && (this.checked || !kt.test(e))
        }).map(function (e, t) {
            var n = it(this).val();
            return null == n ? null : it.isArray(n) ? it.map(n, function (e) {
                return{name: t.name, value: e.replace(Kn, "\r\n")}
            }) : {name: t.name, value: n.replace(Kn, "\r\n")}
        }).get()
    }}), it.ajaxSettings.xhr = void 0 !== e.ActiveXObject ? function () {
        return!this.isLocal && /^(get|post|head|put|delete|options)$/i.test(this.type) && U() || V()
    } : U;
    var Jn = 0, Zn = {}, eo = it.ajaxSettings.xhr();
    e.ActiveXObject && it(e).on("unload", function () {
        for (var e in Zn)Zn[e](void 0, !0)
    }), nt.cors = !!eo && "withCredentials"in eo, eo = nt.ajax = !!eo, eo && it.ajaxTransport(function (e) {
        if (!e.crossDomain || nt.cors) {
            var t;
            return{send: function (n, o) {
                var i, r = e.xhr(), a = ++Jn;
                if (r.open(e.type, e.url, e.async, e.username, e.password), e.xhrFields)for (i in e.xhrFields)r[i] = e.xhrFields[i];
                e.mimeType && r.overrideMimeType && r.overrideMimeType(e.mimeType), e.crossDomain || n["X-Requested-With"] || (n["X-Requested-With"] = "XMLHttpRequest");
                for (i in n)void 0 !== n[i] && r.setRequestHeader(i, n[i] + "");
                r.send(e.hasContent && e.data || null), t = function (n, i) {
                    var s, l, c;
                    if (t && (i || 4 === r.readyState))if (delete Zn[a], t = void 0, r.onreadystatechange = it.noop, i)4 !== r.readyState && r.abort();
                    else {
                        c = {}, s = r.status, "string" == typeof r.responseText && (c.text = r.responseText);
                        try {
                            l = r.statusText
                        } catch (u) {
                            l = ""
                        }
                        s || !e.isLocal || e.crossDomain ? 1223 === s && (s = 204) : s = c.text ? 200 : 404
                    }
                    c && o(s, l, c, r.getAllResponseHeaders())
                }, e.async ? 4 === r.readyState ? setTimeout(t) : r.onreadystatechange = Zn[a] = t : t()
            }, abort: function () {
                t && t(void 0, !0)
            }}
        }
    }), it.ajaxSetup({accepts: {script: "text/javascript, application/javascript, application/ecmascript, application/x-ecmascript"}, contents: {script: /(?:java|ecma)script/}, converters: {"text script": function (e) {
        return it.globalEval(e), e
    }}}), it.ajaxPrefilter("script", function (e) {
        void 0 === e.cache && (e.cache = !1), e.crossDomain && (e.type = "GET", e.global = !1)
    }), it.ajaxTransport("script", function (e) {
        if (e.crossDomain) {
            var t, n = pt.head || it("head")[0] || pt.documentElement;
            return{send: function (o, i) {
                t = pt.createElement("script"), t.async = !0, e.scriptCharset && (t.charset = e.scriptCharset), t.src = e.url, t.onload = t.onreadystatechange = function (e, n) {
                    (n || !t.readyState || /loaded|complete/.test(t.readyState)) && (t.onload = t.onreadystatechange = null, t.parentNode && t.parentNode.removeChild(t), t = null, n || i(200, "success"))
                }, n.insertBefore(t, n.firstChild)
            }, abort: function () {
                t && t.onload(void 0, !0)
            }}
        }
    });
    var to = [], no = /(=)\?(?=&|$)|\?\?/;
    it.ajaxSetup({jsonp: "callback", jsonpCallback: function () {
        var e = to.pop() || it.expando + "_" + Dn++;
        return this[e] = !0, e
    }}), it.ajaxPrefilter("json jsonp", function (t, n, o) {
        var i, r, a, s = t.jsonp !== !1 && (no.test(t.url) ? "url" : "string" == typeof t.data && !(t.contentType || "").indexOf("application/x-www-form-urlencoded") && no.test(t.data) && "data");
        return s || "jsonp" === t.dataTypes[0] ? (i = t.jsonpCallback = it.isFunction(t.jsonpCallback) ? t.jsonpCallback() : t.jsonpCallback, s ? t[s] = t[s].replace(no, "$1" + i) : t.jsonp !== !1 && (t.url += (Ln.test(t.url) ? "&" : "?") + t.jsonp + "=" + i), t.converters["script json"] = function () {
            return a || it.error(i + " was not called"), a[0]
        }, t.dataTypes[0] = "json", r = e[i], e[i] = function () {
            a = arguments
        }, o.always(function () {
            e[i] = r, t[i] && (t.jsonpCallback = n.jsonpCallback, to.push(i)), a && it.isFunction(r) && r(a[0]), a = r = void 0
        }), "script") : void 0
    }), it.parseHTML = function (e, t, n) {
        if (!e || "string" != typeof e)return null;
        "boolean" == typeof t && (n = t, t = !1), t = t || pt;
        var o = dt.exec(e), i = !n && [];
        return o ? [t.createElement(o[1])] : (o = it.buildFragment([e], t, i), i && i.length && it(i).remove(), it.merge([], o.childNodes))
    };
    var oo = it.fn.load;
    it.fn.load = function (e, t, n) {
        if ("string" != typeof e && oo)return oo.apply(this, arguments);
        var o, i, r, a = this, s = e.indexOf(" ");
        return s >= 0 && (o = it.trim(e.slice(s, e.length)), e = e.slice(0, s)), it.isFunction(t) ? (n = t, t = void 0) : t && "object" == typeof t && (r = "POST"), a.length > 0 && it.ajax({url: e, type: r, dataType: "html", data: t}).done(function (e) {
            i = arguments, a.html(o ? it("<div>").append(it.parseHTML(e)).find(o) : e)
        }).complete(n && function (e, t) {
            a.each(n, i || [e.responseText, t, e])
        }), this
    }, it.expr.filters.animated = function (e) {
        return it.grep(it.timers, function (t) {
            return e === t.elem
        }).length
    };
    var io = e.document.documentElement;
    it.offset = {setOffset: function (e, t, n) {
        var o, i, r, a, s, l, c, u = it.css(e, "position"), d = it(e), h = {};
        "static" === u && (e.style.position = "relative"), s = d.offset(), r = it.css(e, "top"), l = it.css(e, "left"), c = ("absolute" === u || "fixed" === u) && it.inArray("auto", [r, l]) > -1, c ? (o = d.position(), a = o.top, i = o.left) : (a = parseFloat(r) || 0, i = parseFloat(l) || 0), it.isFunction(t) && (t = t.call(e, n, s)), null != t.top && (h.top = t.top - s.top + a), null != t.left && (h.left = t.left - s.left + i), "using"in t ? t.using.call(e, h) : d.css(h)
    }}, it.fn.extend({offset: function (e) {
        if (arguments.length)return void 0 === e ? this : this.each(function (t) {
            it.offset.setOffset(this, e, t)
        });
        var t, n, o = {top: 0, left: 0}, i = this[0], r = i && i.ownerDocument;
        if (r)return t = r.documentElement, it.contains(t, i) ? (typeof i.getBoundingClientRect !== Et && (o = i.getBoundingClientRect()), n = X(r), {top: o.top + (n.pageYOffset || t.scrollTop) - (t.clientTop || 0), left: o.left + (n.pageXOffset || t.scrollLeft) - (t.clientLeft || 0)}) : o
    }, position: function () {
        if (this[0]) {
            var e, t, n = {top: 0, left: 0}, o = this[0];
            return"fixed" === it.css(o, "position") ? t = o.getBoundingClientRect() : (e = this.offsetParent(), t = this.offset(), it.nodeName(e[0], "html") || (n = e.offset()), n.top += it.css(e[0], "borderTopWidth", !0), n.left += it.css(e[0], "borderLeftWidth", !0)), {top: t.top - n.top - it.css(o, "marginTop", !0), left: t.left - n.left - it.css(o, "marginLeft", !0)}
        }
    }, offsetParent: function () {
        return this.map(function () {
            for (var e = this.offsetParent || io; e && !it.nodeName(e, "html") && "static" === it.css(e, "position");)e = e.offsetParent;
            return e || io
        })
    }}), it.each({scrollLeft: "pageXOffset", scrollTop: "pageYOffset"}, function (e, t) {
        var n = /Y/.test(t);
        it.fn[e] = function (o) {
            return At(this, function (e, o, i) {
                var r = X(e);
                return void 0 === i ? r ? t in r ? r[t] : r.document.documentElement[o] : e[o] : void(r ? r.scrollTo(n ? it(r).scrollLeft() : i, n ? i : it(r).scrollTop()) : e[o] = i)
            }, e, o, arguments.length, null)
        }
    }), it.each(["top", "left"], function (e, t) {
        it.cssHooks[t] = S(nt.pixelPosition, function (e, n) {
            return n ? (n = tn(e, t), on.test(n) ? it(e).position()[t] + "px" : n) : void 0
        })
    }), it.each({Height: "height", Width: "width"}, function (e, t) {
        it.each({padding: "inner" + e, content: t, "": "outer" + e}, function (n, o) {
            it.fn[o] = function (o, i) {
                var r = arguments.length && (n || "boolean" != typeof o), a = n || (o === !0 || i === !0 ? "margin" : "border");
                return At(this, function (t, n, o) {
                    var i;
                    return it.isWindow(t) ? t.document.documentElement["client" + e] : 9 === t.nodeType ? (i = t.documentElement, Math.max(t.body["scroll" + e], i["scroll" + e], t.body["offset" + e], i["offset" + e], i["client" + e])) : void 0 === o ? it.css(t, n, a) : it.style(t, n, o, a)
                }, t, r ? o : void 0, r, null)
            }
        })
    }), it.fn.size = function () {
        return this.length
    }, it.fn.andSelf = it.fn.addBack, "function" == typeof define && define.amd && define("jquery", [], function () {
        return it
    });
    var ro = e.jQuery, ao = e.$;
    return it.noConflict = function (t) {
        return e.$ === it && (e.$ = ao), t && e.jQuery === it && (e.jQuery = ro), it
    }, typeof t === Et && (e.jQuery = e.$ = it), it
}), function (e, t) {
    e.rails !== t && e.error("jquery-ujs has already been loaded!");
    var n, o = e(document);
    e.rails = n = {linkClickSelector: "a[data-confirm], a[data-method], a[data-remote], a[data-disable-with], a[data-disable]", buttonClickSelector: "button[data-remote]:not(form button), button[data-confirm]:not(form button)", inputChangeSelector: "select[data-remote], input[data-remote], textarea[data-remote]", formSubmitSelector: "form", formInputClickSelector: "form input[type=submit], form input[type=image], form button[type=submit], form button:not([type]), input[type=submit][form], input[type=image][form], button[type=submit][form], button[form]:not([type])", disableSelector: "input[data-disable-with]:enabled, button[data-disable-with]:enabled, textarea[data-disable-with]:enabled, input[data-disable]:enabled, button[data-disable]:enabled, textarea[data-disable]:enabled", enableSelector: "input[data-disable-with]:disabled, button[data-disable-with]:disabled, textarea[data-disable-with]:disabled, input[data-disable]:disabled, button[data-disable]:disabled, textarea[data-disable]:disabled", requiredInputSelector: "input[name][required]:not([disabled]),textarea[name][required]:not([disabled])", fileInputSelector: "input[type=file]", linkDisableSelector: "a[data-disable-with], a[data-disable]", buttonDisableSelector: "button[data-remote][data-disable-with], button[data-remote][data-disable]", CSRFProtection: function (t) {
        var n = e('meta[name="csrf-token"]').attr("content");
        n && t.setRequestHeader("X-CSRF-Token", n)
    }, refreshCSRFTokens: function () {
        var t = e("meta[name=csrf-token]").attr("content"), n = e("meta[name=csrf-param]").attr("content");
        e('form input[name="' + n + '"]').val(t)
    }, fire: function (t, n, o) {
        var i = e.Event(n);
        return t.trigger(i, o), i.result !== !1
    }, confirm: function (e) {
        return confirm(e)
    }, ajax: function (t) {
        return e.ajax(t)
    }, href: function (e) {
        return e.attr("href")
    }, handleRemote: function (o) {
        var i, r, a, s, l, c, u, d;
        if (n.fire(o, "ajax:before")) {
            if (s = o.data("cross-domain"), l = s === t ? null : s, c = o.data("with-credentials") || null, u = o.data("type") || e.ajaxSettings && e.ajaxSettings.dataType, o.is("form")) {
                i = o.attr("method"), r = o.attr("action"), a = o.serializeArray();
                var h = o.data("ujs:submit-button");
                h && (a.push(h), o.data("ujs:submit-button", null))
            } else o.is(n.inputChangeSelector) ? (i = o.data("method"), r = o.data("url"), a = o.serialize(), o.data("params") && (a = a + "&" + o.data("params"))) : o.is(n.buttonClickSelector) ? (i = o.data("method") || "get", r = o.data("url"), a = o.serialize(), o.data("params") && (a = a + "&" + o.data("params"))) : (i = o.data("method"), r = n.href(o), a = o.data("params") || null);
            return d = {type: i || "GET", data: a, dataType: u, beforeSend: function (e, i) {
                return i.dataType === t && e.setRequestHeader("accept", "*/*;q=0.5, " + i.accepts.script), n.fire(o, "ajax:beforeSend", [e, i]) ? void o.trigger("ajax:send", e) : !1
            }, success: function (e, t, n) {
                o.trigger("ajax:success", [e, t, n])
            }, complete: function (e, t) {
                o.trigger("ajax:complete", [e, t])
            }, error: function (e, t, n) {
                o.trigger("ajax:error", [e, t, n])
            }, crossDomain: l}, c && (d.xhrFields = {withCredentials: c}), r && (d.url = r), n.ajax(d)
        }
        return!1
    }, handleMethod: function (o) {
        var i = n.href(o), r = o.data("method"), a = o.attr("target"), s = e("meta[name=csrf-token]").attr("content"), l = e("meta[name=csrf-param]").attr("content"), c = e('<form method="post" action="' + i + '"></form>'), u = '<input name="_method" value="' + r + '" type="hidden" />';
        l !== t && s !== t && (u += '<input name="' + l + '" value="' + s + '" type="hidden" />'), a && c.attr("target", a), c.hide().append(u).appendTo("body"), c.submit()
    }, formElements: function (t, n) {
        return t.is("form") ? e(t[0].elements).filter(n) : t.find(n)
    }, disableFormElements: function (t) {
        n.formElements(t, n.disableSelector).each(function () {
            n.disableFormElement(e(this))
        })
    }, disableFormElement: function (e) {
        var n, o;
        n = e.is("button") ? "html" : "val", o = e.data("disable-with"), e.data("ujs:enable-with", e[n]()), o !== t && e[n](o), e.prop("disabled", !0)
    }, enableFormElements: function (t) {
        n.formElements(t, n.enableSelector).each(function () {
            n.enableFormElement(e(this))
        })
    }, enableFormElement: function (e) {
        var t = e.is("button") ? "html" : "val";
        e.data("ujs:enable-with") && e[t](e.data("ujs:enable-with")), e.prop("disabled", !1)
    }, allowAction: function (e) {
        var t, o = e.data("confirm"), i = !1;
        return o ? (n.fire(e, "confirm") && (i = n.confirm(o), t = n.fire(e, "confirm:complete", [i])), i && t) : !0
    }, blankInputs: function (t, n, o) {
        var i, r, a = e(), s = n || "input,textarea", l = t.find(s);
        return l.each(function () {
            if (i = e(this), r = i.is("input[type=checkbox],input[type=radio]") ? i.is(":checked") : i.val(), !r == !o) {
                if (i.is("input[type=radio]") && l.filter('input[type=radio]:checked[name="' + i.attr("name") + '"]').length)return!0;
                a = a.add(i)
            }
        }), a.length ? a : !1
    }, nonBlankInputs: function (e, t) {
        return n.blankInputs(e, t, !0)
    }, stopEverything: function (t) {
        return e(t.target).trigger("ujs:everythingStopped"), t.stopImmediatePropagation(), !1
    }, disableElement: function (e) {
        var o = e.data("disable-with");
        e.data("ujs:enable-with", e.html()), o !== t && e.html(o), e.bind("click.railsDisable", function (e) {
            return n.stopEverything(e)
        })
    }, enableElement: function (e) {
        e.data("ujs:enable-with") !== t && (e.html(e.data("ujs:enable-with")), e.removeData("ujs:enable-with")), e.unbind("click.railsDisable")
    }}, n.fire(o, "rails:attachBindings") && (e.ajaxPrefilter(function (e, t, o) {
        e.crossDomain || n.CSRFProtection(o)
    }), o.delegate(n.linkDisableSelector, "ajax:complete", function () {
        n.enableElement(e(this))
    }), o.delegate(n.buttonDisableSelector, "ajax:complete", function () {
        n.enableFormElement(e(this))
    }), o.delegate(n.linkClickSelector, "click.rails", function (o) {
        var i = e(this), r = i.data("method"), a = i.data("params"), s = o.metaKey || o.ctrlKey;
        if (!n.allowAction(i))return n.stopEverything(o);
        if (!s && i.is(n.linkDisableSelector) && n.disableElement(i), i.data("remote") !== t) {
            if (s && (!r || "GET" === r) && !a)return!0;
            var l = n.handleRemote(i);
            return l === !1 ? n.enableElement(i) : l.error(function () {
                n.enableElement(i)
            }), !1
        }
        return i.data("method") ? (n.handleMethod(i), !1) : void 0
    }), o.delegate(n.buttonClickSelector, "click.rails", function (t) {
        var o = e(this);
        if (!n.allowAction(o))return n.stopEverything(t);
        o.is(n.buttonDisableSelector) && n.disableFormElement(o);
        var i = n.handleRemote(o);
        return i === !1 ? n.enableFormElement(o) : i.error(function () {
            n.enableFormElement(o)
        }), !1
    }), o.delegate(n.inputChangeSelector, "change.rails", function (t) {
        var o = e(this);
        return n.allowAction(o) ? (n.handleRemote(o), !1) : n.stopEverything(t)
    }), o.delegate(n.formSubmitSelector, "submit.rails", function (o) {
        var i, r, a = e(this), s = a.data("remote") !== t;
        if (!n.allowAction(a))return n.stopEverything(o);
        if (a.attr("novalidate") == t && (i = n.blankInputs(a, n.requiredInputSelector), i && n.fire(a, "ajax:aborted:required", [i])))return n.stopEverything(o);
        if (s) {
            if (r = n.nonBlankInputs(a, n.fileInputSelector)) {
                setTimeout(function () {
                    n.disableFormElements(a)
                }, 13);
                var l = n.fire(a, "ajax:aborted:file", [r]);
                return l || setTimeout(function () {
                    n.enableFormElements(a)
                }, 13), l
            }
            return n.handleRemote(a), !1
        }
        setTimeout(function () {
            n.disableFormElements(a)
        }, 13)
    }), o.delegate(n.formInputClickSelector, "click.rails", function (t) {
        var o = e(this);
        if (!n.allowAction(o))return n.stopEverything(t);
        var i = o.attr("name"), r = i ? {name: i, value: o.val()} : null;
        o.closest("form").data("ujs:submit-button", r)
    }), o.delegate(n.formSubmitSelector, "ajax:send.rails", function (t) {
        this == t.target && n.disableFormElements(e(this))
    }), o.delegate(n.formSubmitSelector, "ajax:complete.rails", function (t) {
        this == t.target && n.enableFormElements(e(this))
    }), e(function () {
        n.refreshCSRFTokens()
    }))
}(jQuery), function () {
    var e, t, n, o, i, r, a, s, l, c, u, d, h, f, p, m, g, y, v, b, w, C, x, E, T, N, S, _, R, A, k, D, L, O, I, $, M, H, P, B, j, F, q, W, z, U, V, X, G, K, Y, Q, J, Z = [].indexOf || function (e) {
        for (var t = 0, n = this.length; n > t; t++)if (t in this && this[t] === e)return t;
        return-1
    }, et = {}.hasOwnProperty, tt = function (e, t) {
        function n() {
            this.constructor = e
        }

        for (var o in t)et.call(t, o) && (e[o] = t[o]);
        return n.prototype = t.prototype, e.prototype = new n, e.__super__ = t.prototype, e
    }, nt = [].slice;
    D = {}, h = 10, X = !1, y = null, A = null, H = null, g = null, Q = null, o = {BEFORE_CHANGE: "page:before-change", FETCH: "page:fetch", RECEIVE: "page:receive", CHANGE: "page:change", UPDATE: "page:update", LOAD: "page:load", RESTORE: "page:restore", BEFORE_UNLOAD: "page:before-unload", EXPIRE: "page:expire"}, C = function (e) {
        var t;
        return e = new n(e), q(), d(), X && (t = G(e.absolute)) ? (x(t), E(e)) : E(e, U)
    }, G = function (e) {
        var t;
        return t = D[e], t && !t.transitionCacheDisabled ? t : void 0
    }, v = function (e) {
        return null == e && (e = !0), X = e
    }, E = function (e, t) {
        return null == t && (t = function () {
            return function () {
            }
        }(this)), K(o.FETCH, {url: e.absolute}), null != Q && Q.abort(), Q = new XMLHttpRequest, Q.open("GET", e.withoutHashForIE10compatibility(), !0), Q.setRequestHeader("Accept", "text/html, application/xhtml+xml, application/xml"), Q.setRequestHeader("X-XHR-Referer", H), Q.onload = function () {
            var n;
            return K(o.RECEIVE, {url: e.absolute}), (n = $()) ? (P(e), f.apply(null, w(n)), k(), B(), t(), K(o.LOAD)) : document.location.href = e.absolute
        }, Q.onloadend = function () {
            return Q = null
        }, Q.onerror = function () {
            return document.location.href = e.absolute
        }, Q.send()
    }, x = function (e) {
        return null != Q && Q.abort(), f(e.title, e.body), M(e), K(o.RESTORE)
    }, d = function () {
        var e;
        return e = new n(y.url), D[e.absolute] = {url: e.relative, body: document.body, title: document.title, positionY: window.pageYOffset, positionX: window.pageXOffset, cachedAt: (new Date).getTime(), transitionCacheDisabled: null != document.querySelector("[data-no-transition-cache]")}, m(h)
    }, O = function (e) {
        return null == e && (e = h), /^[\d]+$/.test(e) ? h = parseInt(e) : void 0
    }, m = function (e) {
        var t, n, i, r, a, s;
        for (i = Object.keys(D), t = i.map(function (e) {
            return D[e].cachedAt
        }).sort(function (e, t) {
            return t - e
        }), s = [], r = 0, a = i.length; a > r; r++)n = i[r], D[n].cachedAt <= t[e] && (K(o.EXPIRE, D[n]), s.push(delete D[n]));
        return s
    }, f = function (t, n, i, r) {
        return K(o.BEFORE_UNLOAD), document.title = t, document.documentElement.replaceChild(n, document.body), null != i && e.update(i), V(), r && b(), y = window.history.state, K(o.CHANGE), K(o.UPDATE)
    }, b = function () {
        var e, t, n, o, i, r, a, s, l, c, u, d;
        for (r = Array.prototype.slice.call(document.body.querySelectorAll('script:not([data-turbolinks-eval="false"])')), a = 0, l = r.length; l > a; a++)if (i = r[a], "" === (u = i.type) || "text/javascript" === u) {
            for (t = document.createElement("script"), d = i.attributes, s = 0, c = d.length; c > s; s++)e = d[s], t.setAttribute(e.name, e.value);
            i.hasAttribute("async") || (t.async = !1), t.appendChild(document.createTextNode(i.innerHTML)), o = i.parentNode, n = i.nextSibling, o.removeChild(i), o.insertBefore(t, n)
        }
    }, W = function (e) {
        return e.innerHTML = e.innerHTML.replace(/<noscript[\S\s]*?<\/noscript>/gi, ""), e
    }, V = function () {
        var e, t;
        return e = (t = document.querySelectorAll("input[autofocus], textarea[autofocus]"))[t.length - 1], e && document.activeElement !== e ? e.focus() : void 0
    }, P = function (e) {
        return(e = new n(e)).absolute !== H ? window.history.pushState({turbolinks: !0, url: e.absolute}, "", e.absolute) : void 0
    }, B = function () {
        var e, t;
        return(e = Q.getResponseHeader("X-XHR-Redirected-To")) ? (e = new n(e), t = e.hasNoHash() ? document.location.hash : "", window.history.replaceState(y, "", e.href + t)) : void 0
    }, q = function () {
        return H = document.location.href
    }, F = function () {
        return window.history.replaceState({turbolinks: !0, url: document.location.href}, "", document.location.href)
    }, j = function () {
        return y = window.history.state
    }, k = function () {
        var e;
        return navigator.userAgent.match(/Firefox/) && !(e = new n).hasNoHash() ? (window.history.replaceState(y, "", e.withoutHash()), document.location.hash = e.hash) : void 0
    }, M = function (e) {
        return window.scrollTo(e.positionX, e.positionY)
    }, U = function () {
        return document.location.hash ? document.location.href = document.location.href : window.scrollTo(0, 0)
    }, p = function (e) {
        var t, n, o;
        if (null == e || "object" != typeof e)return e;
        t = new e.constructor;
        for (n in e)o = e[n], t[n] = p(o);
        return t
    }, I = function (e) {
        var t, n;
        return t = (null != (n = document.cookie.match(new RegExp(e + "=(\\w+)"))) ? n[1].toUpperCase() : void 0) || "", document.cookie = e + "=; expires=Thu, 01-Jan-70 00:00:01 GMT; path=/", t
    }, K = function (e, t) {
        var n;
        return"undefined" != typeof Prototype && Event.fire(document, e, t, !0), n = document.createEvent("Events"), t && (n.data = t), n.initEvent(e, !0, !0), document.dispatchEvent(n)
    }, L = function (e) {
        return!K(o.BEFORE_CHANGE, {url: e})
    }, $ = function () {
        var e, t, n, o, i, r;
        return t = function () {
            var e;
            return 400 <= (e = Q.status) && 600 > e
        }, r = function () {
            var e;
            return null != (e = Q.getResponseHeader("Content-Type")) && e.match(/^(?:text\/html|application\/xhtml\+xml|application\/xml)(?:;|$)/)
        }, o = function (e) {
            var t, n, o, i, r;
            for (i = e.querySelector("head").childNodes, r = [], n = 0, o = i.length; o > n; n++)t = i[n], null != ("function" == typeof t.getAttribute ? t.getAttribute("data-turbolinks-track") : void 0) && r.push(t.getAttribute("src") || t.getAttribute("href"));
            return r
        }, e = function (e) {
            var t;
            return A || (A = o(document)), t = o(e), t.length !== A.length || i(t, A).length !== A.length
        }, i = function (e, t) {
            var n, o, i, r, a;
            for (e.length > t.length && (r = [t, e], e = r[0], t = r[1]), a = [], o = 0, i = e.length; i > o; o++)n = e[o], Z.call(t, n) >= 0 && a.push(n);
            return a
        }, !t() && r() && (n = g(Q.responseText), n && !e(n)) ? n : void 0
    }, w = function (t) {
        var n;
        return n = t.querySelector("title"), [null != n ? n.textContent : void 0, W(t.querySelector("body")), e.get(t).token, "runScripts"]
    }, e = {get: function (e) {
        var t;
        return null == e && (e = document), {node: t = e.querySelector('meta[name="csrf-token"]'), token: null != t && "function" == typeof t.getAttribute ? t.getAttribute("content") : void 0}
    }, update: function (e) {
        var t;
        return t = this.get(), null != t.token && null != e && t.token !== e ? t.node.setAttribute("content", e) : void 0
    }}, r = function () {
        var e, t, n, o, i, r, a, s, l, c;
        o = function (e) {
            return(new DOMParser).parseFromString(e, "text/html")
        }, t = function (e) {
            var t;
            return t = document.implementation.createHTMLDocument(""), t.documentElement.innerHTML = e, t
        }, i = function (e) {
            var t;
            return t = document.implementation.createHTMLDocument(""), t.open("replace"), t.write(e), t.close(), t
        }, n = function (e) {
            var t, n, o, i, r, a;
            return o = (null != (r = e.match(/<head[^>]*>([\s\S.]*)<\/head>/i)) ? r[0] : void 0) || "<head></head>", t = (null != (a = e.match(/<body[^>]*>([\s\S.]*)<\/body>/i)) ? a[0] : void 0) || "<body></body>", i = document.createElement("html"), i.innerHTML = o + t, n = document.createDocumentFragment(), n.appendChild(i), n
        }, e = function (e) {
            var t, o, r;
            return t = function (e, t) {
                return{passes: t(), fallback: e}
            }, r = t(i, function () {
                return function () {
                    var t, n;
                    return 1 === (null != (t = e("<html><body><p>test")) && null != (n = t.body) ? n.childNodes.length : void 0)
                }
            }(this)), o = t(n, function () {
                return function () {
                    var t, n;
                    return 2 === (null != (t = e("<html><body><form></form><div></div></body></html>")) && null != (n = t.body) ? n.childNodes.length : void 0)
                }
            }(this)), [r, o]
        };
        try {
            if (window.DOMParser)return a = e(o), o
        } catch (u) {
            return s = u, a = e(t), t
        } finally {
            for (l = 0, c = a.length; c > l; l++)if (r = a[l], !r.passes)return r.fallback
        }
    }, n = function () {
        function e(t) {
            return this.original = null != t ? t : document.location.href, this.original.constructor === e ? this.original : void this._parse()
        }

        return e.prototype.withoutHash = function () {
            return this.href.replace(this.hash, "").replace("#", "")
        }, e.prototype.withoutHashForIE10compatibility = function () {
            return this.withoutHash()
        }, e.prototype.hasNoHash = function () {
            return 0 === this.hash.length
        }, e.prototype._parse = function () {
            var e;
            return(null != this.link ? this.link : this.link = document.createElement("a")).href = this.original, e = this.link, this.href = e.href, this.protocol = e.protocol, this.host = e.host, this.hostname = e.hostname, this.port = e.port, this.pathname = e.pathname, this.search = e.search, this.hash = e.hash, this.origin = [this.protocol, "//", this.hostname].join(""), 0 !== this.port.length && (this.origin += ":" + this.port), this.relative = [this.pathname, this.search, this.hash].join(""), this.absolute = this.href
        }, e
    }(), i = function (e) {
        function t(e) {
            return this.link = e, this.link.constructor === t ? this.link : (this.original = this.link.href, this.originalElement = this.link, this.link = this.link.cloneNode(!1), void t.__super__.constructor.apply(this, arguments))
        }

        return tt(t, e), t.HTML_EXTENSIONS = ["html"], t.allowExtensions = function () {
            var e, n, o, i;
            for (n = 1 <= arguments.length ? nt.call(arguments, 0) : [], o = 0, i = n.length; i > o; o++)e = n[o], t.HTML_EXTENSIONS.push(e);
            return t.HTML_EXTENSIONS
        }, t.prototype.shouldIgnore = function () {
            return this._crossOrigin() || this._anchored() || this._nonHtml() || this._optOut() || this._target()
        }, t.prototype._crossOrigin = function () {
            return this.origin !== (new n).origin
        }, t.prototype._anchored = function () {
            return(this.hash.length > 0 || "#" === this.href.charAt(this.href.length - 1)) && this.withoutHash() === (new n).withoutHash()
        }, t.prototype._nonHtml = function () {
            return this.pathname.match(/\.[a-z]+$/g) && !this.pathname.match(new RegExp("\\.(?:" + t.HTML_EXTENSIONS.join("|") + ")?$", "g"))
        }, t.prototype._optOut = function () {
            var e, t;
            for (t = this.originalElement; !e && t !== document;)e = null != t.getAttribute("data-no-turbolink"), t = t.parentNode;
            return e
        }, t.prototype._target = function () {
            return 0 !== this.link.target.length
        }, t
    }(n), t = function () {
        function e(e) {
            this.event = e, this.event.defaultPrevented || (this._extractLink(), this._validForTurbolinks() && (L(this.link.absolute) || Y(this.link.href), this.event.preventDefault()))
        }

        return e.installHandlerLast = function (t) {
            return t.defaultPrevented ? void 0 : (document.removeEventListener("click", e.handle, !1), document.addEventListener("click", e.handle, !1))
        }, e.handle = function (t) {
            return new e(t)
        }, e.prototype._extractLink = function () {
            var e;
            for (e = this.event.target; e.parentNode && "A" !== e.nodeName;)e = e.parentNode;
            return"A" === e.nodeName && 0 !== e.href.length ? this.link = new i(e) : void 0
        }, e.prototype._validForTurbolinks = function () {
            return null != this.link && !(this.link.shouldIgnore() || this._nonStandardClick())
        }, e.prototype._nonStandardClick = function () {
            return this.event.which > 1 || this.event.metaKey || this.event.ctrlKey || this.event.shiftKey || this.event.altKey
        }, e
    }(), u = function (e) {
        return setTimeout(e, 500)
    }, S = function () {
        return document.addEventListener("DOMContentLoaded", function () {
            return K(o.CHANGE), K(o.UPDATE)
        }, !0)
    }, R = function () {
        return"undefined" != typeof jQuery ? jQuery(document).on("ajaxSuccess", function (e, t) {
            return jQuery.trim(t.responseText) ? K(o.UPDATE) : void 0
        }) : void 0
    }, _ = function (e) {
        var t, o;
        return(null != (o = e.state) ? o.turbolinks : void 0) ? (t = D[new n(e.state.url).absolute]) ? (d(), x(t)) : Y(e.target.location.href) : void 0
    }, N = function () {
        return F(), j(), g = r(), document.addEventListener("click", t.installHandlerLast, !0), window.addEventListener("hashchange", function () {
            return F(), j()
        }, !1), u(function () {
            return window.addEventListener("popstate", _, !1)
        })
    }, T = void 0 !== window.history.state || navigator.userAgent.match(/Firefox\/2[6|7]/), l = window.history && window.history.pushState && window.history.replaceState && T, a = !navigator.userAgent.match(/CriOS\//), z = "GET" === (J = I("request_method")) || "" === J, c = l && a && z, s = document.addEventListener && document.createEvent, s && (S(), R()), c ? (Y = C, N()) : Y = function (e) {
        return document.location.href = e
    }, this.Turbolinks = {visit: Y, pagesCached: O, enableTransitionCache: v, allowLinkExtensions: i.allowExtensions, supported: c, EVENTS: p(o)}
}.call(this), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.affix"), r = "object" == typeof t && t;
            i || o.data("bs.affix", i = new n(this, r)), "string" == typeof t && i[t]()
        })
    }

    var n = function (t, o) {
        this.options = e.extend({}, n.DEFAULTS, o), this.$target = e(this.options.target).on("scroll.bs.affix.data-api", e.proxy(this.checkPosition, this)).on("click.bs.affix.data-api", e.proxy(this.checkPositionWithEventLoop, this)), this.$element = e(t), this.affixed = this.unpin = this.pinnedOffset = null, this.checkPosition()
    };
    n.VERSION = "3.2.0", n.RESET = "affix affix-top affix-bottom", n.DEFAULTS = {offset: 0, target: window}, n.prototype.getPinnedOffset = function () {
        if (this.pinnedOffset)return this.pinnedOffset;
        this.$element.removeClass(n.RESET).addClass("affix");
        var e = this.$target.scrollTop(), t = this.$element.offset();
        return this.pinnedOffset = t.top - e
    }, n.prototype.checkPositionWithEventLoop = function () {
        setTimeout(e.proxy(this.checkPosition, this), 1)
    }, n.prototype.checkPosition = function () {
        if (this.$element.is(":visible")) {
            var t = e(document).height(), o = this.$target.scrollTop(), i = this.$element.offset(), r = this.options.offset, a = r.top, s = r.bottom;
            "object" != typeof r && (s = a = r), "function" == typeof a && (a = r.top(this.$element)), "function" == typeof s && (s = r.bottom(this.$element));
            var l = null != this.unpin && o + this.unpin <= i.top ? !1 : null != s && i.top + this.$element.height() >= t - s ? "bottom" : null != a && a >= o ? "top" : !1;
            if (this.affixed !== l) {
                null != this.unpin && this.$element.css("top", "");
                var c = "affix" + (l ? "-" + l : ""), u = e.Event(c + ".bs.affix");
                this.$element.trigger(u), u.isDefaultPrevented() || (this.affixed = l, this.unpin = "bottom" == l ? this.getPinnedOffset() : null, this.$element.removeClass(n.RESET).addClass(c).trigger(e.Event(c.replace("affix", "affixed"))), "bottom" == l && this.$element.offset({top: t - this.$element.height() - s}))
            }
        }
    };
    var o = e.fn.affix;
    e.fn.affix = t, e.fn.affix.Constructor = n, e.fn.affix.noConflict = function () {
        return e.fn.affix = o, this
    }, e(window).on("load", function () {
        e('[data-spy="affix"]').each(function () {
            var n = e(this), o = n.data();
            o.offset = o.offset || {}, o.offsetBottom && (o.offset.bottom = o.offsetBottom), o.offsetTop && (o.offset.top = o.offsetTop), t.call(n, o)
        })
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var n = e(this), i = n.data("bs.alert");
            i || n.data("bs.alert", i = new o(this)), "string" == typeof t && i[t].call(n)
        })
    }

    var n = '[data-dismiss="alert"]', o = function (t) {
        e(t).on("click", n, this.close)
    };
    o.VERSION = "3.2.0", o.prototype.close = function (t) {
        function n() {
            r.detach().trigger("closed.bs.alert").remove()
        }

        var o = e(this), i = o.attr("data-target");
        i || (i = o.attr("href"), i = i && i.replace(/.*(?=#[^\s]*$)/, ""));
        var r = e(i);
        t && t.preventDefault(), r.length || (r = o.hasClass("alert") ? o : o.parent()), r.trigger(t = e.Event("close.bs.alert")), t.isDefaultPrevented() || (r.removeClass("in"), e.support.transition && r.hasClass("fade") ? r.one("bsTransitionEnd", n).emulateTransitionEnd(150) : n())
    };
    var i = e.fn.alert;
    e.fn.alert = t, e.fn.alert.Constructor = o, e.fn.alert.noConflict = function () {
        return e.fn.alert = i, this
    }, e(document).on("click.bs.alert.data-api", n, o.prototype.close)
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.button"), r = "object" == typeof t && t;
            i || o.data("bs.button", i = new n(this, r)), "toggle" == t ? i.toggle() : t && i.setState(t)
        })
    }

    var n = function (t, o) {
        this.$element = e(t), this.options = e.extend({}, n.DEFAULTS, o), this.isLoading = !1
    };
    n.VERSION = "3.2.0", n.DEFAULTS = {loadingText: "loading..."}, n.prototype.setState = function (t) {
        var n = "disabled", o = this.$element, i = o.is("input") ? "val" : "html", r = o.data();
        t += "Text", null == r.resetText && o.data("resetText", o[i]()), o[i](null == r[t] ? this.options[t] : r[t]), setTimeout(e.proxy(function () {
            "loadingText" == t ? (this.isLoading = !0, o.addClass(n).attr(n, n)) : this.isLoading && (this.isLoading = !1, o.removeClass(n).removeAttr(n))
        }, this), 0)
    }, n.prototype.toggle = function () {
        var e = !0, t = this.$element.closest('[data-toggle="buttons"]');
        if (t.length) {
            var n = this.$element.find("input");
            "radio" == n.prop("type") && (n.prop("checked") && this.$element.hasClass("active") ? e = !1 : t.find(".active").removeClass("active")), e && n.prop("checked", !this.$element.hasClass("active")).trigger("change")
        }
        e && this.$element.toggleClass("active")
    };
    var o = e.fn.button;
    e.fn.button = t, e.fn.button.Constructor = n, e.fn.button.noConflict = function () {
        return e.fn.button = o, this
    }, e(document).on("click.bs.button.data-api", '[data-toggle^="button"]', function (n) {
        var o = e(n.target);
        o.hasClass("btn") || (o = o.closest(".btn")), t.call(o, "toggle"), n.preventDefault()
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.carousel"), r = e.extend({}, n.DEFAULTS, o.data(), "object" == typeof t && t), a = "string" == typeof t ? t : r.slide;
            i || o.data("bs.carousel", i = new n(this, r)), "number" == typeof t ? i.to(t) : a ? i[a]() : r.interval && i.pause().cycle()
        })
    }

    var n = function (t, n) {
        this.$element = e(t).on("keydown.bs.carousel", e.proxy(this.keydown, this)), this.$indicators = this.$element.find(".carousel-indicators"), this.options = n, this.paused = this.sliding = this.interval = this.$active = this.$items = null, "hover" == this.options.pause && this.$element.on("mouseenter.bs.carousel", e.proxy(this.pause, this)).on("mouseleave.bs.carousel", e.proxy(this.cycle, this))
    };
    n.VERSION = "3.2.0", n.DEFAULTS = {interval: 5e3, pause: "hover", wrap: !0}, n.prototype.keydown = function (e) {
        switch (e.which) {
            case 37:
                this.prev();
                break;
            case 39:
                this.next();
                break;
            default:
                return
        }
        e.preventDefault()
    }, n.prototype.cycle = function (t) {
        return t || (this.paused = !1), this.interval && clearInterval(this.interval), this.options.interval && !this.paused && (this.interval = setInterval(e.proxy(this.next, this), this.options.interval)), this
    }, n.prototype.getItemIndex = function (e) {
        return this.$items = e.parent().children(".item"), this.$items.index(e || this.$active)
    }, n.prototype.to = function (t) {
        var n = this, o = this.getItemIndex(this.$active = this.$element.find(".item.active"));
        return t > this.$items.length - 1 || 0 > t ? void 0 : this.sliding ? this.$element.one("slid.bs.carousel", function () {
            n.to(t)
        }) : o == t ? this.pause().cycle() : this.slide(t > o ? "next" : "prev", e(this.$items[t]))
    }, n.prototype.pause = function (t) {
        return t || (this.paused = !0), this.$element.find(".next, .prev").length && e.support.transition && (this.$element.trigger(e.support.transition.end), this.cycle(!0)), this.interval = clearInterval(this.interval), this
    }, n.prototype.next = function () {
        return this.sliding ? void 0 : this.slide("next")
    }, n.prototype.prev = function () {
        return this.sliding ? void 0 : this.slide("prev")
    }, n.prototype.slide = function (t, n) {
        var o = this.$element.find(".item.active"), i = n || o[t](), r = this.interval, a = "next" == t ? "left" : "right", s = "next" == t ? "first" : "last", l = this;
        if (!i.length) {
            if (!this.options.wrap)return;
            i = this.$element.find(".item")[s]()
        }
        if (i.hasClass("active"))return this.sliding = !1;
        var c = i[0], u = e.Event("slide.bs.carousel", {relatedTarget: c, direction: a});
        if (this.$element.trigger(u), !u.isDefaultPrevented()) {
            if (this.sliding = !0, r && this.pause(), this.$indicators.length) {
                this.$indicators.find(".active").removeClass("active");
                var d = e(this.$indicators.children()[this.getItemIndex(i)]);
                d && d.addClass("active")
            }
            var h = e.Event("slid.bs.carousel", {relatedTarget: c, direction: a});
            return e.support.transition && this.$element.hasClass("slide") ? (i.addClass(t), i[0].offsetWidth, o.addClass(a), i.addClass(a), o.one("bsTransitionEnd", function () {
                i.removeClass([t, a].join(" ")).addClass("active"), o.removeClass(["active", a].join(" ")), l.sliding = !1, setTimeout(function () {
                    l.$element.trigger(h)
                }, 0)
            }).emulateTransitionEnd(1e3 * o.css("transition-duration").slice(0, -1))) : (o.removeClass("active"), i.addClass("active"), this.sliding = !1, this.$element.trigger(h)), r && this.cycle(), this
        }
    };
    var o = e.fn.carousel;
    e.fn.carousel = t, e.fn.carousel.Constructor = n, e.fn.carousel.noConflict = function () {
        return e.fn.carousel = o, this
    }, e(document).on("click.bs.carousel.data-api", "[data-slide], [data-slide-to]", function (n) {
        var o, i = e(this), r = e(i.attr("data-target") || (o = i.attr("href")) && o.replace(/.*(?=#[^\s]+$)/, ""));
        if (r.hasClass("carousel")) {
            var a = e.extend({}, r.data(), i.data()), s = i.attr("data-slide-to");
            s && (a.interval = !1), t.call(r, a), s && r.data("bs.carousel").to(s), n.preventDefault()
        }
    }), e(window).on("load", function () {
        e('[data-ride="carousel"]').each(function () {
            var n = e(this);
            t.call(n, n.data())
        })
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.collapse"), r = e.extend({}, n.DEFAULTS, o.data(), "object" == typeof t && t);
            !i && r.toggle && "show" == t && (t = !t), i || o.data("bs.collapse", i = new n(this, r)), "string" == typeof t && i[t]()
        })
    }

    var n = function (t, o) {
        this.$element = e(t), this.options = e.extend({}, n.DEFAULTS, o), this.transitioning = null, this.options.parent && (this.$parent = e(this.options.parent)), this.options.toggle && this.toggle()
    };
    n.VERSION = "3.2.0", n.DEFAULTS = {toggle: !0}, n.prototype.dimension = function () {
        var e = this.$element.hasClass("width");
        return e ? "width" : "height"
    }, n.prototype.show = function () {
        if (!this.transitioning && !this.$element.hasClass("in")) {
            var n = e.Event("show.bs.collapse");
            if (this.$element.trigger(n), !n.isDefaultPrevented()) {
                var o = this.$parent && this.$parent.find("> .panel > .in");
                if (o && o.length) {
                    var i = o.data("bs.collapse");
                    if (i && i.transitioning)return;
                    t.call(o, "hide"), i || o.data("bs.collapse", null)
                }
                var r = this.dimension();
                this.$element.removeClass("collapse").addClass("collapsing")[r](0), this.transitioning = 1;
                var a = function () {
                    this.$element.removeClass("collapsing").addClass("collapse in")[r](""), this.transitioning = 0, this.$element.trigger("shown.bs.collapse")
                };
                if (!e.support.transition)return a.call(this);
                var s = e.camelCase(["scroll", r].join("-"));
                this.$element.one("bsTransitionEnd", e.proxy(a, this)).emulateTransitionEnd(350)[r](this.$element[0][s])
            }
        }
    }, n.prototype.hide = function () {
        if (!this.transitioning && this.$element.hasClass("in")) {
            var t = e.Event("hide.bs.collapse");
            if (this.$element.trigger(t), !t.isDefaultPrevented()) {
                var n = this.dimension();
                this.$element[n](this.$element[n]())[0].offsetHeight, this.$element.addClass("collapsing").removeClass("collapse").removeClass("in"), this.transitioning = 1;
                var o = function () {
                    this.transitioning = 0, this.$element.trigger("hidden.bs.collapse").removeClass("collapsing").addClass("collapse")
                };
                return e.support.transition ? void this.$element[n](0).one("bsTransitionEnd", e.proxy(o, this)).emulateTransitionEnd(350) : o.call(this)
            }
        }
    }, n.prototype.toggle = function () {
        this[this.$element.hasClass("in") ? "hide" : "show"]()
    };
    var o = e.fn.collapse;
    e.fn.collapse = t, e.fn.collapse.Constructor = n, e.fn.collapse.noConflict = function () {
        return e.fn.collapse = o, this
    }, e(document).on("click.bs.collapse.data-api", '[data-toggle="collapse"]', function (n) {
        var o, i = e(this), r = i.attr("data-target") || n.preventDefault() || (o = i.attr("href")) && o.replace(/.*(?=#[^\s]+$)/, ""), a = e(r), s = a.data("bs.collapse"), l = s ? "toggle" : i.data(), c = i.attr("data-parent"), u = c && e(c);
        s && s.transitioning || (u && u.find('[data-toggle="collapse"][data-parent="' + c + '"]').not(i).addClass("collapsed"), i[a.hasClass("in") ? "addClass" : "removeClass"]("collapsed")), t.call(a, l)
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        t && 3 === t.which || (e(i).remove(), e(r).each(function () {
            var o = n(e(this)), i = {relatedTarget: this};
            o.hasClass("open") && (o.trigger(t = e.Event("hide.bs.dropdown", i)), t.isDefaultPrevented() || o.removeClass("open").trigger("hidden.bs.dropdown", i))
        }))
    }

    function n(t) {
        var n = t.attr("data-target");
        n || (n = t.attr("href"), n = n && /#[A-Za-z]/.test(n) && n.replace(/.*(?=#[^\s]*$)/, ""));
        var o = n && e(n);
        return o && o.length ? o : t.parent()
    }

    function o(t) {
        return this.each(function () {
            var n = e(this), o = n.data("bs.dropdown");
            o || n.data("bs.dropdown", o = new a(this)), "string" == typeof t && o[t].call(n)
        })
    }

    var i = ".dropdown-backdrop", r = '[data-toggle="dropdown"]', a = function (t) {
        e(t).on("click.bs.dropdown", this.toggle)
    };
    a.VERSION = "3.2.0", a.prototype.toggle = function (o) {
        var i = e(this);
        if (!i.is(".disabled, :disabled")) {
            var r = n(i), a = r.hasClass("open");
            if (t(), !a) {
                "ontouchstart"in document.documentElement && !r.closest(".navbar-nav").length && e('<div class="dropdown-backdrop"/>').insertAfter(e(this)).on("click", t);
                var s = {relatedTarget: this};
                if (r.trigger(o = e.Event("show.bs.dropdown", s)), o.isDefaultPrevented())return;
                i.trigger("focus"), r.toggleClass("open").trigger("shown.bs.dropdown", s)
            }
            return!1
        }
    }, a.prototype.keydown = function (t) {
        if (/(38|40|27)/.test(t.keyCode)) {
            var o = e(this);
            if (t.preventDefault(), t.stopPropagation(), !o.is(".disabled, :disabled")) {
                var i = n(o), a = i.hasClass("open");
                if (!a || a && 27 == t.keyCode)return 27 == t.which && i.find(r).trigger("focus"), o.trigger("click");
                var s = " li:not(.divider):visible a", l = i.find('[role="menu"]' + s + ', [role="listbox"]' + s);
                if (l.length) {
                    var c = l.index(l.filter(":focus"));
                    38 == t.keyCode && c > 0 && c--, 40 == t.keyCode && c < l.length - 1 && c++, ~c || (c = 0), l.eq(c).trigger("focus")
                }
            }
        }
    };
    var s = e.fn.dropdown;
    e.fn.dropdown = o, e.fn.dropdown.Constructor = a, e.fn.dropdown.noConflict = function () {
        return e.fn.dropdown = s, this
    }, e(document).on("click.bs.dropdown.data-api", t).on("click.bs.dropdown.data-api", ".dropdown form", function (e) {
        e.stopPropagation()
    }).on("click.bs.dropdown.data-api", r, a.prototype.toggle).on("keydown.bs.dropdown.data-api", r + ', [role="menu"], [role="listbox"]', a.prototype.keydown)
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.tab");
            i || o.data("bs.tab", i = new n(this)), "string" == typeof t && i[t]()
        })
    }

    var n = function (t) {
        this.element = e(t)
    };
    n.VERSION = "3.2.0", n.prototype.show = function () {
        var t = this.element, n = t.closest("ul:not(.dropdown-menu)"), o = t.data("target");
        if (o || (o = t.attr("href"), o = o && o.replace(/.*(?=#[^\s]*$)/, "")), !t.parent("li").hasClass("active")) {
            var i = n.find(".active:last a")[0], r = e.Event("show.bs.tab", {relatedTarget: i});
            if (t.trigger(r), !r.isDefaultPrevented()) {
                var a = e(o);
                this.activate(t.closest("li"), n), this.activate(a, a.parent(), function () {
                    t.trigger({type: "shown.bs.tab", relatedTarget: i})
                })
            }
        }
    }, n.prototype.activate = function (t, n, o) {
        function i() {
            r.removeClass("active").find("> .dropdown-menu > .active").removeClass("active"), t.addClass("active"), a ? (t[0].offsetWidth, t.addClass("in")) : t.removeClass("fade"), t.parent(".dropdown-menu") && t.closest("li.dropdown").addClass("active"), o && o()
        }

        var r = n.find("> .active"), a = o && e.support.transition && r.hasClass("fade");
        a ? r.one("bsTransitionEnd", i).emulateTransitionEnd(150) : i(), r.removeClass("in")
    };
    var o = e.fn.tab;
    e.fn.tab = t, e.fn.tab.Constructor = n, e.fn.tab.noConflict = function () {
        return e.fn.tab = o, this
    }, e(document).on("click.bs.tab.data-api", '[data-toggle="tab"], [data-toggle="pill"]', function (n) {
        n.preventDefault(), t.call(e(this), "show")
    })
}(jQuery), +function (e) {
    "use strict";
    function t() {
        var e = document.createElement("bootstrap"), t = {WebkitTransition: "webkitTransitionEnd", MozTransition: "transitionend", OTransition: "oTransitionEnd otransitionend", transition: "transitionend"};
        for (var n in t)if (void 0 !== e.style[n])return{end: t[n]};
        return!1
    }

    e.fn.emulateTransitionEnd = function (t) {
        var n = !1, o = this;
        e(this).one("bsTransitionEnd", function () {
            n = !0
        });
        var i = function () {
            n || e(o).trigger(e.support.transition.end)
        };
        return setTimeout(i, t), this
    }, e(function () {
        e.support.transition = t(), e.support.transition && (e.event.special.bsTransitionEnd = {bindType: e.support.transition.end, delegateType: e.support.transition.end, handle: function (t) {
            return e(t.target).is(this) ? t.handleObj.handler.apply(this, arguments) : void 0
        }})
    })
}(jQuery), +function (e) {
    "use strict";
    function t(n, o) {
        var i = e.proxy(this.process, this);
        this.$body = e("body"), this.$scrollElement = e(e(n).is("body") ? window : n), this.options = e.extend({}, t.DEFAULTS, o), this.selector = (this.options.target || "") + " .nav li > a", this.offsets = [], this.targets = [], this.activeTarget = null, this.scrollHeight = 0, this.$scrollElement.on("scroll.bs.scrollspy", i), this.refresh(), this.process()
    }

    function n(n) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.scrollspy"), r = "object" == typeof n && n;
            i || o.data("bs.scrollspy", i = new t(this, r)), "string" == typeof n && i[n]()
        })
    }

    t.VERSION = "3.2.0", t.DEFAULTS = {offset: 10}, t.prototype.getScrollHeight = function () {
        return this.$scrollElement[0].scrollHeight || Math.max(this.$body[0].scrollHeight, document.documentElement.scrollHeight)
    }, t.prototype.refresh = function () {
        var t = "offset", n = 0;
        e.isWindow(this.$scrollElement[0]) || (t = "position", n = this.$scrollElement.scrollTop()), this.offsets = [], this.targets = [], this.scrollHeight = this.getScrollHeight();
        var o = this;
        this.$body.find(this.selector).map(function () {
            var o = e(this), i = o.data("target") || o.attr("href"), r = /^#./.test(i) && e(i);
            return r && r.length && r.is(":visible") && [
                [r[t]().top + n, i]
            ] || null
        }).sort(function (e, t) {
            return e[0] - t[0]
        }).each(function () {
            o.offsets.push(this[0]), o.targets.push(this[1])
        })
    }, t.prototype.process = function () {
        var e, t = this.$scrollElement.scrollTop() + this.options.offset, n = this.getScrollHeight(), o = this.options.offset + n - this.$scrollElement.height(), i = this.offsets, r = this.targets, a = this.activeTarget;
        if (this.scrollHeight != n && this.refresh(), t >= o)return a != (e = r[r.length - 1]) && this.activate(e);
        if (a && t <= i[0])return a != (e = r[0]) && this.activate(e);
        for (e = i.length; e--;)a != r[e] && t >= i[e] && (!i[e + 1] || t <= i[e + 1]) && this.activate(r[e])
    }, t.prototype.activate = function (t) {
        this.activeTarget = t, e(this.selector).parentsUntil(this.options.target, ".active").removeClass("active");
        var n = this.selector + '[data-target="' + t + '"],' + this.selector + '[href="' + t + '"]', o = e(n).parents("li").addClass("active");
        o.parent(".dropdown-menu").length && (o = o.closest("li.dropdown").addClass("active")), o.trigger("activate.bs.scrollspy")
    };
    var o = e.fn.scrollspy;
    e.fn.scrollspy = n, e.fn.scrollspy.Constructor = t, e.fn.scrollspy.noConflict = function () {
        return e.fn.scrollspy = o, this
    }, e(window).on("load.bs.scrollspy.data-api", function () {
        e('[data-spy="scroll"]').each(function () {
            var t = e(this);
            n.call(t, t.data())
        })
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t, o) {
        return this.each(function () {
            var i = e(this), r = i.data("bs.modal"), a = e.extend({}, n.DEFAULTS, i.data(), "object" == typeof t && t);
            r || i.data("bs.modal", r = new n(this, a)), "string" == typeof t ? r[t](o) : a.show && r.show(o)
        })
    }

    var n = function (t, n) {
        this.options = n, this.$body = e(document.body), this.$element = e(t), this.$backdrop = this.isShown = null, this.scrollbarWidth = 0, this.options.remote && this.$element.find(".modal-content").load(this.options.remote, e.proxy(function () {
            this.$element.trigger("loaded.bs.modal")
        }, this))
    };
    n.VERSION = "3.2.0", n.DEFAULTS = {backdrop: !0, keyboard: !0, show: !0}, n.prototype.toggle = function (e) {
        return this.isShown ? this.hide() : this.show(e)
    }, n.prototype.show = function (t) {
        var n = this, o = e.Event("show.bs.modal", {relatedTarget: t});
        this.$element.trigger(o), this.isShown || o.isDefaultPrevented() || (this.isShown = !0, this.checkScrollbar(), this.$body.addClass("modal-open"), this.setScrollbar(), this.escape(), this.$element.on("click.dismiss.bs.modal", '[data-dismiss="modal"]', e.proxy(this.hide, this)), this.backdrop(function () {
            var o = e.support.transition && n.$element.hasClass("fade");
            n.$element.parent().length || n.$element.appendTo(n.$body), n.$element.show().scrollTop(0), o && n.$element[0].offsetWidth, n.$element.addClass("in").attr("aria-hidden", !1), n.enforceFocus();
            var i = e.Event("shown.bs.modal", {relatedTarget: t});
            o ? n.$element.find(".modal-dialog").one("bsTransitionEnd", function () {
                n.$element.trigger("focus").trigger(i)
            }).emulateTransitionEnd(300) : n.$element.trigger("focus").trigger(i)
        }))
    }, n.prototype.hide = function (t) {
        t && t.preventDefault(), t = e.Event("hide.bs.modal"), this.$element.trigger(t), this.isShown && !t.isDefaultPrevented() && (this.isShown = !1, this.$body.removeClass("modal-open"), this.resetScrollbar(), this.escape(), e(document).off("focusin.bs.modal"), this.$element.removeClass("in").attr("aria-hidden", !0).off("click.dismiss.bs.modal"), e.support.transition && this.$element.hasClass("fade") ? this.$element.one("bsTransitionEnd", e.proxy(this.hideModal, this)).emulateTransitionEnd(300) : this.hideModal())
    }, n.prototype.enforceFocus = function () {
        e(document).off("focusin.bs.modal").on("focusin.bs.modal", e.proxy(function (e) {
            this.$element[0] === e.target || this.$element.has(e.target).length || this.$element.trigger("focus")
        }, this))
    }, n.prototype.escape = function () {
        this.isShown && this.options.keyboard ? this.$element.on("keyup.dismiss.bs.modal", e.proxy(function (e) {
            27 == e.which && this.hide()
        }, this)) : this.isShown || this.$element.off("keyup.dismiss.bs.modal")
    }, n.prototype.hideModal = function () {
        var e = this;
        this.$element.hide(), this.backdrop(function () {
            e.$element.trigger("hidden.bs.modal")
        })
    }, n.prototype.removeBackdrop = function () {
        this.$backdrop && this.$backdrop.remove(), this.$backdrop = null
    }, n.prototype.backdrop = function (t) {
        var n = this, o = this.$element.hasClass("fade") ? "fade" : "";
        if (this.isShown && this.options.backdrop) {
            var i = e.support.transition && o;
            if (this.$backdrop = e('<div class="modal-backdrop ' + o + '" />').appendTo(this.$body), this.$element.on("click.dismiss.bs.modal", e.proxy(function (e) {
                e.target === e.currentTarget && ("static" == this.options.backdrop ? this.$element[0].focus.call(this.$element[0]) : this.hide.call(this))
            }, this)), i && this.$backdrop[0].offsetWidth, this.$backdrop.addClass("in"), !t)return;
            i ? this.$backdrop.one("bsTransitionEnd", t).emulateTransitionEnd(150) : t()
        } else if (!this.isShown && this.$backdrop) {
            this.$backdrop.removeClass("in");
            var r = function () {
                n.removeBackdrop(), t && t()
            };
            e.support.transition && this.$element.hasClass("fade") ? this.$backdrop.one("bsTransitionEnd", r).emulateTransitionEnd(150) : r()
        } else t && t()
    }, n.prototype.checkScrollbar = function () {
        document.body.clientWidth >= window.innerWidth || (this.scrollbarWidth = this.scrollbarWidth || this.measureScrollbar())
    }, n.prototype.setScrollbar = function () {
        var e = parseInt(this.$body.css("padding-right") || 0, 10);
        this.scrollbarWidth && this.$body.css("padding-right", e + this.scrollbarWidth)
    }, n.prototype.resetScrollbar = function () {
        this.$body.css("padding-right", "")
    }, n.prototype.measureScrollbar = function () {
        var e = document.createElement("div");
        e.className = "modal-scrollbar-measure", this.$body.append(e);
        var t = e.offsetWidth - e.clientWidth;
        return this.$body[0].removeChild(e), t
    };
    var o = e.fn.modal;
    e.fn.modal = t, e.fn.modal.Constructor = n, e.fn.modal.noConflict = function () {
        return e.fn.modal = o, this
    }, e(document).on("click.bs.modal.data-api", '[data-toggle="modal"]', function (n) {
        var o = e(this), i = o.attr("href"), r = e(o.attr("data-target") || i && i.replace(/.*(?=#[^\s]+$)/, "")), a = r.data("bs.modal") ? "toggle" : e.extend({remote: !/#/.test(i) && i}, r.data(), o.data());
        o.is("a") && n.preventDefault(), r.one("show.bs.modal", function (e) {
            e.isDefaultPrevented() || r.one("hidden.bs.modal", function () {
                o.is(":visible") && o.trigger("focus")
            })
        }), t.call(r, a, this)
    })
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.tooltip"), r = "object" == typeof t && t;
            (i || "destroy" != t) && (i || o.data("bs.tooltip", i = new n(this, r)), "string" == typeof t && i[t]())
        })
    }

    var n = function (e, t) {
        this.type = this.options = this.enabled = this.timeout = this.hoverState = this.$element = null, this.init("tooltip", e, t)
    };
    n.VERSION = "3.2.0", n.DEFAULTS = {animation: !0, placement: "top", selector: !1, template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow"></div><div class="tooltip-inner"></div></div>', trigger: "hover focus", title: "", delay: 0, html: !1, container: !1, viewport: {selector: "body", padding: 0}}, n.prototype.init = function (t, n, o) {
        this.enabled = !0, this.type = t, this.$element = e(n), this.options = this.getOptions(o), this.$viewport = this.options.viewport && e(this.options.viewport.selector || this.options.viewport);
        for (var i = this.options.trigger.split(" "), r = i.length; r--;) {
            var a = i[r];
            if ("click" == a)this.$element.on("click." + this.type, this.options.selector, e.proxy(this.toggle, this)); else if ("manual" != a) {
                var s = "hover" == a ? "mouseenter" : "focusin", l = "hover" == a ? "mouseleave" : "focusout";
                this.$element.on(s + "." + this.type, this.options.selector, e.proxy(this.enter, this)), this.$element.on(l + "." + this.type, this.options.selector, e.proxy(this.leave, this))
            }
        }
        this.options.selector ? this._options = e.extend({}, this.options, {trigger: "manual", selector: ""}) : this.fixTitle()
    }, n.prototype.getDefaults = function () {
        return n.DEFAULTS
    }, n.prototype.getOptions = function (t) {
        return t = e.extend({}, this.getDefaults(), this.$element.data(), t), t.delay && "number" == typeof t.delay && (t.delay = {show: t.delay, hide: t.delay}), t
    }, n.prototype.getDelegateOptions = function () {
        var t = {}, n = this.getDefaults();
        return this._options && e.each(this._options, function (e, o) {
            n[e] != o && (t[e] = o)
        }), t
    }, n.prototype.enter = function (t) {
        var n = t instanceof this.constructor ? t : e(t.currentTarget).data("bs." + this.type);
        return n || (n = new this.constructor(t.currentTarget, this.getDelegateOptions()), e(t.currentTarget).data("bs." + this.type, n)), clearTimeout(n.timeout), n.hoverState = "in", n.options.delay && n.options.delay.show ? void(n.timeout = setTimeout(function () {
            "in" == n.hoverState && n.show()
        }, n.options.delay.show)) : n.show()
    }, n.prototype.leave = function (t) {
        var n = t instanceof this.constructor ? t : e(t.currentTarget).data("bs." + this.type);
        return n || (n = new this.constructor(t.currentTarget, this.getDelegateOptions()), e(t.currentTarget).data("bs." + this.type, n)), clearTimeout(n.timeout), n.hoverState = "out", n.options.delay && n.options.delay.hide ? void(n.timeout = setTimeout(function () {
            "out" == n.hoverState && n.hide()
        }, n.options.delay.hide)) : n.hide()
    }, n.prototype.show = function () {
        var t = e.Event("show.bs." + this.type);
        if (this.hasContent() && this.enabled) {
            this.$element.trigger(t);
            var n = e.contains(document.documentElement, this.$element[0]);
            if (t.isDefaultPrevented() || !n)return;
            var o = this, i = this.tip(), r = this.getUID(this.type);
            this.setContent(), i.attr("id", r), this.$element.attr("aria-describedby", r), this.options.animation && i.addClass("fade");
            var a = "function" == typeof this.options.placement ? this.options.placement.call(this, i[0], this.$element[0]) : this.options.placement, s = /\s?auto?\s?/i, l = s.test(a);
            l && (a = a.replace(s, "") || "top"), i.detach().css({top: 0, left: 0, display: "block"}).addClass(a).data("bs." + this.type, this), this.options.container ? i.appendTo(this.options.container) : i.insertAfter(this.$element);
            var c = this.getPosition(), u = i[0].offsetWidth, d = i[0].offsetHeight;
            if (l) {
                var h = a, f = this.$element.parent(), p = this.getPosition(f);
                a = "bottom" == a && c.top + c.height + d - p.scroll > p.height ? "top" : "top" == a && c.top - p.scroll - d < 0 ? "bottom" : "right" == a && c.right + u > p.width ? "left" : "left" == a && c.left - u < p.left ? "right" : a, i.removeClass(h).addClass(a)
            }
            var m = this.getCalculatedOffset(a, c, u, d);
            this.applyPlacement(m, a);
            var g = function () {
                o.$element.trigger("shown.bs." + o.type), o.hoverState = null
            };
            e.support.transition && this.$tip.hasClass("fade") ? i.one("bsTransitionEnd", g).emulateTransitionEnd(150) : g()
        }
    }, n.prototype.applyPlacement = function (t, n) {
        var o = this.tip(), i = o[0].offsetWidth, r = o[0].offsetHeight, a = parseInt(o.css("margin-top"), 10), s = parseInt(o.css("margin-left"), 10);
        isNaN(a) && (a = 0), isNaN(s) && (s = 0), t.top = t.top + a, t.left = t.left + s, e.offset.setOffset(o[0], e.extend({using: function (e) {
            o.css({top: Math.round(e.top), left: Math.round(e.left)})
        }}, t), 0), o.addClass("in");
        var l = o[0].offsetWidth, c = o[0].offsetHeight;
        "top" == n && c != r && (t.top = t.top + r - c);
        var u = this.getViewportAdjustedDelta(n, t, l, c);
        u.left ? t.left += u.left : t.top += u.top;
        var d = u.left ? 2 * u.left - i + l : 2 * u.top - r + c, h = u.left ? "left" : "top", f = u.left ? "offsetWidth" : "offsetHeight";
        o.offset(t), this.replaceArrow(d, o[0][f], h)
    }, n.prototype.replaceArrow = function (e, t, n) {
        this.arrow().css(n, e ? 50 * (1 - e / t) + "%" : "")
    }, n.prototype.setContent = function () {
        var e = this.tip(), t = this.getTitle();
        e.find(".tooltip-inner")[this.options.html ? "html" : "text"](t), e.removeClass("fade in top bottom left right")
    }, n.prototype.hide = function () {
        function t() {
            "in" != n.hoverState && o.detach(), n.$element.trigger("hidden.bs." + n.type)
        }

        var n = this, o = this.tip(), i = e.Event("hide.bs." + this.type);
        return this.$element.removeAttr("aria-describedby"), this.$element.trigger(i), i.isDefaultPrevented() ? void 0 : (o.removeClass("in"), e.support.transition && this.$tip.hasClass("fade") ? o.one("bsTransitionEnd", t).emulateTransitionEnd(150) : t(), this.hoverState = null, this)
    }, n.prototype.fixTitle = function () {
        var e = this.$element;
        (e.attr("title") || "string" != typeof e.attr("data-original-title")) && e.attr("data-original-title", e.attr("title") || "").attr("title", "")
    }, n.prototype.hasContent = function () {
        return this.getTitle()
    }, n.prototype.getPosition = function (t) {
        t = t || this.$element;
        var n = t[0], o = "BODY" == n.tagName;
        return e.extend({}, "function" == typeof n.getBoundingClientRect ? n.getBoundingClientRect() : null, {scroll: o ? document.documentElement.scrollTop || document.body.scrollTop : t.scrollTop(), width: o ? e(window).width() : t.outerWidth(), height: o ? e(window).height() : t.outerHeight()}, o ? {top: 0, left: 0} : t.offset())
    }, n.prototype.getCalculatedOffset = function (e, t, n, o) {
        return"bottom" == e ? {top: t.top + t.height, left: t.left + t.width / 2 - n / 2} : "top" == e ? {top: t.top - o, left: t.left + t.width / 2 - n / 2} : "left" == e ? {top: t.top + t.height / 2 - o / 2, left: t.left - n} : {top: t.top + t.height / 2 - o / 2, left: t.left + t.width}
    }, n.prototype.getViewportAdjustedDelta = function (e, t, n, o) {
        var i = {top: 0, left: 0};
        if (!this.$viewport)return i;
        var r = this.options.viewport && this.options.viewport.padding || 0, a = this.getPosition(this.$viewport);
        if (/right|left/.test(e)) {
            var s = t.top - r - a.scroll, l = t.top + r - a.scroll + o;
            s < a.top ? i.top = a.top - s : l > a.top + a.height && (i.top = a.top + a.height - l)
        } else {
            var c = t.left - r, u = t.left + r + n;
            c < a.left ? i.left = a.left - c : u > a.width && (i.left = a.left + a.width - u)
        }
        return i
    }, n.prototype.getTitle = function () {
        var e, t = this.$element, n = this.options;
        return e = t.attr("data-original-title") || ("function" == typeof n.title ? n.title.call(t[0]) : n.title)
    }, n.prototype.getUID = function (e) {
        do e += ~~(1e6 * Math.random()); while (document.getElementById(e));
        return e
    }, n.prototype.tip = function () {
        return this.$tip = this.$tip || e(this.options.template)
    }, n.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".tooltip-arrow")
    }, n.prototype.validate = function () {
        this.$element[0].parentNode || (this.hide(), this.$element = null, this.options = null)
    }, n.prototype.enable = function () {
        this.enabled = !0
    }, n.prototype.disable = function () {
        this.enabled = !1
    }, n.prototype.toggleEnabled = function () {
        this.enabled = !this.enabled
    }, n.prototype.toggle = function (t) {
        var n = this;
        t && (n = e(t.currentTarget).data("bs." + this.type), n || (n = new this.constructor(t.currentTarget, this.getDelegateOptions()), e(t.currentTarget).data("bs." + this.type, n))), n.tip().hasClass("in") ? n.leave(n) : n.enter(n)
    }, n.prototype.destroy = function () {
        clearTimeout(this.timeout), this.hide().$element.off("." + this.type).removeData("bs." + this.type)
    };
    var o = e.fn.tooltip;
    e.fn.tooltip = t, e.fn.tooltip.Constructor = n, e.fn.tooltip.noConflict = function () {
        return e.fn.tooltip = o, this
    }
}(jQuery), +function (e) {
    "use strict";
    function t(t) {
        return this.each(function () {
            var o = e(this), i = o.data("bs.popover"), r = "object" == typeof t && t;
            (i || "destroy" != t) && (i || o.data("bs.popover", i = new n(this, r)), "string" == typeof t && i[t]())
        })
    }

    var n = function (e, t) {
        this.init("popover", e, t)
    };
    if (!e.fn.tooltip)throw new Error("Popover requires tooltip.js");
    n.VERSION = "3.2.0", n.DEFAULTS = e.extend({}, e.fn.tooltip.Constructor.DEFAULTS, {placement: "right", trigger: "click", content: "", template: '<div class="popover" role="tooltip"><div class="arrow"></div><h3 class="popover-title"></h3><div class="popover-content"></div></div>'}), n.prototype = e.extend({}, e.fn.tooltip.Constructor.prototype), n.prototype.constructor = n, n.prototype.getDefaults = function () {
        return n.DEFAULTS
    }, n.prototype.setContent = function () {
        var e = this.tip(), t = this.getTitle(), n = this.getContent();
        e.find(".popover-title")[this.options.html ? "html" : "text"](t), e.find(".popover-content").empty()[this.options.html ? "string" == typeof n ? "html" : "append" : "text"](n), e.removeClass("fade top bottom left right in"), e.find(".popover-title").html() || e.find(".popover-title").hide()
    }, n.prototype.hasContent = function () {
        return this.getTitle() || this.getContent()
    }, n.prototype.getContent = function () {
        var e = this.$element, t = this.options;
        return e.attr("data-content") || ("function" == typeof t.content ? t.content.call(e[0]) : t.content)
    }, n.prototype.arrow = function () {
        return this.$arrow = this.$arrow || this.tip().find(".arrow")
    }, n.prototype.tip = function () {
        return this.$tip || (this.$tip = e(this.options.template)), this.$tip
    };
    var o = e.fn.popover;
    e.fn.popover = t, e.fn.popover.Constructor = n, e.fn.popover.noConflict = function () {
        return e.fn.popover = o, this
    }
}(jQuery), !function (e) {
    var t = function (t, n) {
        if (this.$element = e(t), this.type = this.$element.data("uploadtype") || (this.$element.find(".thumbnail").length > 0 ? "image" : "file"), this.$input = this.$element.find(":file"), 0 !== this.$input.length) {
            this.name = this.$input.attr("name") || n.name, this.$hidden = this.$element.find('input[type=hidden][name="' + this.name + '"]'), 0 === this.$hidden.length && (this.$hidden = e('<input type="hidden" />'), this.$element.prepend(this.$hidden)), this.$preview = this.$element.find(".fileupload-preview");
            var o = this.$preview.css("height");
            "inline" != this.$preview.css("display") && "0px" != o && "none" != o && this.$preview.css("line-height", o), this.original = {exists: this.$element.hasClass("fileupload-exists"), preview: this.$preview.html(), hiddenVal: this.$hidden.val()}, this.$remove = this.$element.find('[data-dismiss="fileupload"]'), this.$element.find('[data-trigger="fileupload"]').on("click.fileupload", e.proxy(this.trigger, this)), this.listen()
        }
    };
    t.prototype = {listen: function () {
        this.$input.on("change.fileupload", e.proxy(this.change, this)), e(this.$input[0].form).on("reset.fileupload", e.proxy(this.reset, this)), this.$remove && this.$remove.on("click.fileupload", e.proxy(this.clear, this))
    }, change: function (e, t) {
        if ("clear" !== t) {
            var n = void 0 !== e.target.files ? e.target.files[0] : e.target.value ? {name: e.target.value.replace(/^.+\\/, "")} : null;
            if (!n)return void this.clear();
            if (this.$hidden.val(""), this.$hidden.attr("name", ""), this.$input.attr("name", this.name), "image" === this.type && this.$preview.length > 0 && ("undefined" != typeof n.type ? n.type.match("image.*") : n.name.match(/\.(gif|png|jpe?g)$/i)) && "undefined" != typeof FileReader) {
                var o = new FileReader, i = this.$preview, r = this.$element;
                o.onload = function (e) {
                    i.html('<img src="' + e.target.result + '" ' + ("none" != i.css("max-height") ? 'style="max-height: ' + i.css("max-height") + ';"' : "") + " />"), r.addClass("fileupload-exists").removeClass("fileupload-new")
                }, o.readAsDataURL(n)
            } else this.$preview.text(n.name), this.$element.addClass("fileupload-exists").removeClass("fileupload-new")
        }
    }, clear: function (e) {
        if (this.$hidden.val(""), this.$hidden.attr("name", this.name), this.$input.attr("name", ""), navigator.userAgent.match(/msie/i)) {
            var t = this.$input.clone(!0);
            this.$input.after(t), this.$input.remove(), this.$input = t
        } else this.$input.val("");
        this.$preview.html(""), this.$element.addClass("fileupload-new").removeClass("fileupload-exists"), e && (this.$input.trigger("change", ["clear"]), e.preventDefault())
    }, reset: function () {
        this.clear(), this.$hidden.val(this.original.hiddenVal), this.$preview.html(this.original.preview), this.original.exists ? this.$element.addClass("fileupload-exists").removeClass("fileupload-new") : this.$element.addClass("fileupload-new").removeClass("fileupload-exists")
    }, trigger: function (e) {
        this.$input.trigger("click"), e.preventDefault()
    }}, e.fn.fileupload = function (n) {
        return this.each(function () {
            var o = e(this), i = o.data("fileupload");
            i || o.data("fileupload", i = new t(this, n)), "string" == typeof n && i[n]()
        })
    }, e.fn.fileupload.Constructor = t, e(document).on("click.fileupload.data-api", '[data-provides="fileupload"]', function (t) {
        var n = e(this);
        if (!n.data("fileupload")) {
            n.fileupload(n.data());
            var o = e(t.target).closest('[data-dismiss="fileupload"],[data-trigger="fileupload"]');
            o.length > 0 && (o.trigger("click.fileupload"), t.preventDefault())
        }
    })
}(window.jQuery), Object.defineProperty && Object.getOwnPropertyDescriptor && Object.getOwnPropertyDescriptor(Element.prototype, "textContent") && !Object.getOwnPropertyDescriptor(Element.prototype, "textContent").get && !function () {
    var e = Object.getOwnPropertyDescriptor(Element.prototype, "innerText");
    Object.defineProperty(Element.prototype, "textContent", {get: function () {
        return e.get.call(this)
    }, set: function (t) {
        return e.set.call(this, t)
    }})
}(), Array.isArray || (Array.isArray = function (e) {
    return"[object Array]" === Object.prototype.toString.call(e)
});
var wysihtml5 = {version: "0.4.13", commands: {}, dom: {}, quirks: {}, toolbar: {}, lang: {}, selection: {}, views: {}, INVISIBLE_SPACE: "", EMPTY_FUNCTION: function () {
}, ELEMENT_NODE: 1, TEXT_NODE: 3, BACKSPACE_KEY: 8, ENTER_KEY: 13, ESCAPE_KEY: 27, SPACE_KEY: 32, DELETE_KEY: 46};
!function (e, t) {
    "function" == typeof define && define.amd ? define(e) : t.rangy = e()
}(function () {
    function e(e, t) {
        var n = typeof e[t];
        return n == v || !(n != y || !e[t]) || "unknown" == n
    }

    function t(e, t) {
        return!(typeof e[t] != y || !e[t])
    }

    function n(e, t) {
        return typeof e[t] != b
    }

    function o(e) {
        return function (t, n) {
            for (var o = n.length; o--;)if (!e(t, n[o]))return!1;
            return!0
        }
    }

    function i(e) {
        return e && T(e, E) && S(e, x)
    }

    function r(e) {
        return t(e, "body") ? e.body : e.getElementsByTagName("body")[0]
    }

    function a(n) {
        t(window, "console") && e(window.console, "log") && window.console.log(n)
    }

    function s(e, t) {
        t ? window.alert(e) : a(e)
    }

    function l(e) {
        R.initialized = !0, R.supported = !1, s("Rangy is not supported on this page in your browser. Reason: " + e, R.config.alertOnFail)
    }

    function c(e) {
        s("Rangy warning: " + e, R.config.alertOnWarn)
    }

    function u(e) {
        return e.message || e.description || String(e)
    }

    function d() {
        if (!R.initialized) {
            var t, n = !1, o = !1;
            e(document, "createRange") && (t = document.createRange(), T(t, C) && S(t, w) && (n = !0));
            var s = r(document);
            if (!s || "body" != s.nodeName.toLowerCase())return void l("No body element found");
            if (s && e(s, "createTextRange") && (t = s.createTextRange(), i(t) && (o = !0)), !n && !o)return void l("Neither Range nor TextRange are available");
            R.initialized = !0, R.features = {implementsDomRange: n, implementsTextRange: o};
            var c, d;
            for (var h in _)(c = _[h])instanceof f && c.init(c, R);
            for (var p = 0, m = k.length; m > p; ++p)try {
                k[p](R)
            } catch (g) {
                d = "Rangy init listener threw an exception. Continuing. Detail: " + u(g), a(d)
            }
        }
    }

    function h(e) {
        e = e || window, d();
        for (var t = 0, n = D.length; n > t; ++t)D[t](e)
    }

    function f(e, t, n) {
        this.name = e, this.dependencies = t, this.initialized = !1, this.supported = !1, this.initializer = n
    }

    function p(e, t, n, o) {
        var i = new f(t, n, function (e) {
            if (!e.initialized) {
                e.initialized = !0;
                try {
                    o(R, e), e.supported = !0
                } catch (n) {
                    var i = "Module '" + t + "' failed to load: " + u(n);
                    a(i)
                }
            }
        });
        _[t] = i
    }

    function m() {
    }

    function g() {
    }

    var y = "object", v = "function", b = "undefined", w = ["startContainer", "startOffset", "endContainer", "endOffset", "collapsed", "commonAncestorContainer"], C = ["setStart", "setStartBefore", "setStartAfter", "setEnd", "setEndBefore", "setEndAfter", "collapse", "selectNode", "selectNodeContents", "compareBoundaryPoints", "deleteContents", "extractContents", "cloneContents", "insertNode", "surroundContents", "cloneRange", "toString", "detach"], x = ["boundingHeight", "boundingLeft", "boundingTop", "boundingWidth", "htmlText", "text"], E = ["collapse", "compareEndPoints", "duplicate", "moveToElementText", "parentElement", "select", "setEndPoint", "getBoundingClientRect"], T = o(e), N = o(t), S = o(n), _ = {}, R = {version: "1.3alpha.20140804", initialized: !1, supported: !0, util: {isHostMethod: e, isHostObject: t, isHostProperty: n, areHostMethods: T, areHostObjects: N, areHostProperties: S, isTextRange: i, getBody: r}, features: {}, modules: _, config: {alertOnFail: !0, alertOnWarn: !1, preferTextRange: !1, autoInitialize: typeof rangyAutoInitialize == b ? !0 : rangyAutoInitialize}};
    R.fail = l, R.warn = c, {}.hasOwnProperty ? R.util.extend = function (e, t, n) {
        var o, i;
        for (var r in t)t.hasOwnProperty(r) && (o = e[r], i = t[r], n && null !== o && "object" == typeof o && null !== i && "object" == typeof i && R.util.extend(o, i, !0), e[r] = i);
        return t.hasOwnProperty("toString") && (e.toString = t.toString), e
    } : l("hasOwnProperty not supported"), function () {
        var e = document.createElement("div");
        e.appendChild(document.createElement("span"));
        var t, n = [].slice;
        try {
            1 == n.call(e.childNodes, 0)[0].nodeType && (t = function (e) {
                return n.call(e, 0)
            })
        } catch (o) {
        }
        t || (t = function (e) {
            for (var t = [], n = 0, o = e.length; o > n; ++n)t[n] = e[n];
            return t
        }), R.util.toArray = t
    }();
    var A;
    e(document, "addEventListener") ? A = function (e, t, n) {
        e.addEventListener(t, n, !1)
    } : e(document, "attachEvent") ? A = function (e, t, n) {
        e.attachEvent("on" + t, n)
    } : l("Document does not have required addEventListener or attachEvent method"), R.util.addListener = A;
    var k = [];
    R.init = d, R.addInitListener = function (e) {
        R.initialized ? e(R) : k.push(e)
    };
    var D = [];
    R.addShimListener = function (e) {
        D.push(e)
    }, R.shim = R.createMissingNativeApi = h, f.prototype = {init: function () {
        for (var e, t, n = this.dependencies || [], o = 0, i = n.length; i > o; ++o) {
            if (t = n[o], e = _[t], !(e && e instanceof f))throw new Error("required module '" + t + "' not found");
            if (e.init(), !e.supported)throw new Error("required module '" + t + "' not supported")
        }
        this.initializer(this)
    }, fail: function (e) {
        throw this.initialized = !0, this.supported = !1, new Error("Module '" + this.name + "' failed to load: " + e)
    }, warn: function (e) {
        R.warn("Module " + this.name + ": " + e)
    }, deprecationNotice: function (e, t) {
        R.warn("DEPRECATED: " + e + " in module " + this.name + "is deprecated. Please use " + t + " instead")
    }, createError: function (e) {
        return new Error("Error in Rangy " + this.name + " module: " + e)
    }}, R.createModule = function (e) {
        var t, n;
        2 == arguments.length ? (t = arguments[1], n = []) : (t = arguments[2], n = arguments[1]);
        var o = p(!1, e, n, t);
        R.initialized && o.init()
    }, R.createCoreModule = function (e, t, n) {
        p(!0, e, t, n)
    }, R.RangePrototype = m, R.rangePrototype = new m, R.selectionPrototype = new g;
    var L = !1, O = function () {
        L || (L = !0, !R.initialized && R.config.autoInitialize && d())
    };
    return typeof window == b ? void l("No window found") : typeof document == b ? void l("No document found") : (e(document, "addEventListener") && document.addEventListener("DOMContentLoaded", O, !1), A(window, "load", O), R.createCoreModule("DomUtil", [], function (e, t) {
        function n(e) {
            var t;
            return typeof e.namespaceURI == A || null === (t = e.namespaceURI) || "http://www.w3.org/1999/xhtml" == t
        }

        function o(e) {
            var t = e.parentNode;
            return 1 == t.nodeType ? t : null
        }

        function i(e) {
            for (var t = 0; e = e.previousSibling;)++t;
            return t
        }

        function r(e) {
            switch (e.nodeType) {
                case 7:
                case 10:
                    return 0;
                case 3:
                case 8:
                    return e.length;
                default:
                    return e.childNodes.length
            }
        }

        function a(e, t) {
            var n, o = [];
            for (n = e; n; n = n.parentNode)o.push(n);
            for (n = t; n; n = n.parentNode)if (O(o, n))return n;
            return null
        }

        function s(e, t, n) {
            for (var o = n ? t : t.parentNode; o;) {
                if (o === e)return!0;
                o = o.parentNode
            }
            return!1
        }

        function l(e, t) {
            return s(e, t, !0)
        }

        function c(e, t, n) {
            for (var o, i = n ? e : e.parentNode; i;) {
                if (o = i.parentNode, o === t)return i;
                i = o
            }
            return null
        }

        function u(e) {
            var t = e.nodeType;
            return 3 == t || 4 == t || 8 == t
        }

        function d(e) {
            if (!e)return!1;
            var t = e.nodeType;
            return 3 == t || 8 == t
        }

        function h(e, t) {
            var n = t.nextSibling, o = t.parentNode;
            return n ? o.insertBefore(e, n) : o.appendChild(e), e
        }

        function f(e, t, n) {
            var o = e.cloneNode(!1);
            if (o.deleteData(0, t), e.deleteData(t, e.length - t), h(o, e), n)for (var r, a = 0; r = n[a++];)r.node == e && r.offset > t ? (r.node = o, r.offset -= t) : r.node == e.parentNode && r.offset > i(e) && ++r.offset;
            return o
        }

        function p(e) {
            if (9 == e.nodeType)return e;
            if (typeof e.ownerDocument != A)return e.ownerDocument;
            if (typeof e.document != A)return e.document;
            if (e.parentNode)return p(e.parentNode);
            throw t.createError("getDocument: no document found for node")
        }

        function m(e) {
            var n = p(e);
            if (typeof n.defaultView != A)return n.defaultView;
            if (typeof n.parentWindow != A)return n.parentWindow;
            throw t.createError("Cannot get a window object for node")
        }

        function g(e) {
            if (typeof e.contentDocument != A)return e.contentDocument;
            if (typeof e.contentWindow != A)return e.contentWindow.document;
            throw t.createError("getIframeDocument: No Document object found for iframe element")
        }

        function y(e) {
            if (typeof e.contentWindow != A)return e.contentWindow;
            if (typeof e.contentDocument != A)return e.contentDocument.defaultView;
            throw t.createError("getIframeWindow: No Window object found for iframe element")
        }

        function v(e) {
            return e && k.isHostMethod(e, "setTimeout") && k.isHostObject(e, "document")
        }

        function b(e, t, n) {
            var o;
            if (e ? k.isHostProperty(e, "nodeType") ? o = 1 == e.nodeType && "iframe" == e.tagName.toLowerCase() ? g(e) : p(e) : v(e) && (o = e.document) : o = document, !o)throw t.createError(n + "(): Parameter must be a Window object or DOM node");
            return o
        }

        function w(e) {
            for (var t; t = e.parentNode;)e = t;
            return e
        }

        function C(e, n, o, r) {
            var s, l, u, d, h;
            if (e == o)return n === r ? 0 : r > n ? -1 : 1;
            if (s = c(o, e, !0))return n <= i(s) ? -1 : 1;
            if (s = c(e, o, !0))return i(s) < r ? -1 : 1;
            if (l = a(e, o), !l)throw new Error("comparePoints error: nodes have no common ancestor");
            if (u = e === l ? l : c(e, l, !0), d = o === l ? l : c(o, l, !0), u === d)throw t.createError("comparePoints got to case 4 and childA and childB are the same!");
            for (h = l.firstChild; h;) {
                if (h === u)return-1;
                if (h === d)return 1;
                h = h.nextSibling
            }
        }

        function x(e) {
            var t;
            try {
                return t = e.parentNode, !1
            } catch (n) {
                return!0
            }
        }

        function E(e) {
            if (!e)return"[No node]";
            if (I && x(e))return"[Broken node]";
            if (u(e))return'"' + e.data + '"';
            if (1 == e.nodeType) {
                var t = e.id ? ' id="' + e.id + '"' : "";
                return"<" + e.nodeName + t + ">[index:" + i(e) + ",length:" + e.childNodes.length + "][" + (e.innerHTML || "[innerHTML not supported]").slice(0, 25) + "]"
            }
            return e.nodeName
        }

        function T(e) {
            for (var t, n = p(e).createDocumentFragment(); t = e.firstChild;)n.appendChild(t);
            return n
        }

        function N(e) {
            this.root = e, this._next = e
        }

        function S(e) {
            return new N(e)
        }

        function _(e, t) {
            this.node = e, this.offset = t
        }

        function R(e) {
            this.code = this[e], this.codeName = e, this.message = "DOMException: " + this.codeName
        }

        var A = "undefined", k = e.util;
        k.areHostMethods(document, ["createDocumentFragment", "createElement", "createTextNode"]) || t.fail("document missing a Node creation method"), k.isHostMethod(document, "getElementsByTagName") || t.fail("document missing getElementsByTagName method");
        var D = document.createElement("div");
        k.areHostMethods(D, ["insertBefore", "appendChild", "cloneNode"] || !k.areHostObjects(D, ["previousSibling", "nextSibling", "childNodes", "parentNode"])) || t.fail("Incomplete Element implementation"), k.isHostProperty(D, "innerHTML") || t.fail("Element is missing innerHTML property");
        var L = document.createTextNode("test");
        k.areHostMethods(L, ["splitText", "deleteData", "insertData", "appendData", "cloneNode"] || !k.areHostObjects(D, ["previousSibling", "nextSibling", "childNodes", "parentNode"]) || !k.areHostProperties(L, ["data"])) || t.fail("Incomplete Text Node implementation");
        var O = function (e, t) {
            for (var n = e.length; n--;)if (e[n] === t)return!0;
            return!1
        }, I = !1;
        !function () {
            var t = document.createElement("b");
            t.innerHTML = "1";
            var n = t.firstChild;
            t.innerHTML = "<br>", I = x(n), e.features.crashyTextNodes = I
        }();
        var $;
        typeof window.getComputedStyle != A ? $ = function (e, t) {
            return m(e).getComputedStyle(e, null)[t]
        } : typeof document.documentElement.currentStyle != A ? $ = function (e, t) {
            return e.currentStyle[t]
        } : t.fail("No means of obtaining computed style properties found"), N.prototype = {_current: null, hasNext: function () {
            return!!this._next
        }, next: function () {
            var e, t, n = this._current = this._next;
            if (this._current)if (e = n.firstChild)this._next = e; else {
                for (t = null; n !== this.root && !(t = n.nextSibling);)n = n.parentNode;
                this._next = t
            }
            return this._current
        }, detach: function () {
            this._current = this._next = this.root = null
        }}, _.prototype = {equals: function (e) {
            return!!e && this.node === e.node && this.offset == e.offset
        }, inspect: function () {
            return"[DomPosition(" + E(this.node) + ":" + this.offset + ")]"
        }, toString: function () {
            return this.inspect()
        }}, R.prototype = {INDEX_SIZE_ERR: 1, HIERARCHY_REQUEST_ERR: 3, WRONG_DOCUMENT_ERR: 4, NO_MODIFICATION_ALLOWED_ERR: 7, NOT_FOUND_ERR: 8, NOT_SUPPORTED_ERR: 9, INVALID_STATE_ERR: 11, INVALID_NODE_TYPE_ERR: 24}, R.prototype.toString = function () {
            return this.message
        }, e.dom = {arrayContains: O, isHtmlNamespace: n, parentElement: o, getNodeIndex: i, getNodeLength: r, getCommonAncestor: a, isAncestorOf: s, isOrIsAncestorOf: l, getClosestAncestorIn: c, isCharacterDataNode: u, isTextOrCommentNode: d, insertAfter: h, splitDataNode: f, getDocument: p, getWindow: m, getIframeWindow: y, getIframeDocument: g, getBody: k.getBody, isWindow: v, getContentDocument: b, getRootContainer: w, comparePoints: C, isBrokenNode: x, inspectNode: E, getComputedStyleProperty: $, fragmentFromNodeChildren: T, createIterator: S, DomPosition: _}, e.DOMException = R
    }), R.createCoreModule("DomRange", ["DomUtil"], function (e) {
        function t(e, t) {
            return 3 != e.nodeType && (j(e, t.startContainer) || j(e, t.endContainer))
        }

        function n(e) {
            return e.document || F(e.startContainer)
        }

        function o(e) {
            return new M(e.parentNode, B(e))
        }

        function i(e) {
            return new M(e.parentNode, B(e) + 1)
        }

        function r(e, t, n) {
            var o = 11 == e.nodeType ? e.firstChild : e;
            return P(t) ? n == t.length ? I.insertAfter(e, t) : t.parentNode.insertBefore(e, 0 == n ? t : W(t, n)) : n >= t.childNodes.length ? t.appendChild(e) : t.insertBefore(e, t.childNodes[n]), o
        }

        function a(e, t, o) {
            if (T(e), T(t), n(t) != n(e))throw new H("WRONG_DOCUMENT_ERR");
            var i = q(e.startContainer, e.startOffset, t.endContainer, t.endOffset), r = q(e.endContainer, e.endOffset, t.startContainer, t.startOffset);
            return o ? 0 >= i && r >= 0 : 0 > i && r > 0
        }

        function s(e) {
            for (var t, o, i, r = n(e.range).createDocumentFragment(); o = e.next();) {
                if (t = e.isPartiallySelectedSubtree(), o = o.cloneNode(!t), t && (i = e.getSubtreeIterator(), o.appendChild(s(i)), i.detach()), 10 == o.nodeType)throw new H("HIERARCHY_REQUEST_ERR");
                r.appendChild(o)
            }
            return r
        }

        function l(e, t, n) {
            var o, i;
            n = n || {stop: !1};
            for (var r, a; r = e.next();)if (e.isPartiallySelectedSubtree()) {
                if (t(r) === !1)return void(n.stop = !0);
                if (a = e.getSubtreeIterator(), l(a, t, n), a.detach(), n.stop)return
            } else for (o = I.createIterator(r); i = o.next();)if (t(i) === !1)return void(n.stop = !0)
        }

        function c(e) {
            for (var t; e.next();)e.isPartiallySelectedSubtree() ? (t = e.getSubtreeIterator(), c(t), t.detach()) : e.remove()
        }

        function u(e) {
            for (var t, o, i = n(e.range).createDocumentFragment(); t = e.next();) {
                if (e.isPartiallySelectedSubtree() ? (t = t.cloneNode(!1), o = e.getSubtreeIterator(), t.appendChild(u(o)), o.detach()) : e.remove(), 10 == t.nodeType)throw new H("HIERARCHY_REQUEST_ERR");
                i.appendChild(t)
            }
            return i
        }

        function d(e, t, n) {
            var o, i = !(!t || !t.length), r = !!n;
            i && (o = new RegExp("^(" + t.join("|") + ")$"));
            var a = [];
            return l(new f(e, !1), function (t) {
                if (!(i && !o.test(t.nodeType) || r && !n(t))) {
                    var s = e.startContainer;
                    if (t != s || !P(s) || e.startOffset != s.length) {
                        var l = e.endContainer;
                        t == l && P(l) && 0 == e.endOffset || a.push(t)
                    }
                }
            }), a
        }

        function h(e) {
            var t = "undefined" == typeof e.getName ? "Range" : e.getName();
            return"[" + t + "(" + I.inspectNode(e.startContainer) + ":" + e.startOffset + ", " + I.inspectNode(e.endContainer) + ":" + e.endOffset + ")]"
        }

        function f(e, t) {
            if (this.range = e, this.clonePartiallySelectedTextNodes = t, !e.collapsed) {
                this.sc = e.startContainer, this.so = e.startOffset, this.ec = e.endContainer, this.eo = e.endOffset;
                var n = e.commonAncestorContainer;
                this.sc === this.ec && P(this.sc) ? (this.isSingleCharacterDataNode = !0, this._first = this._last = this._next = this.sc) : (this._first = this._next = this.sc !== n || P(this.sc) ? z(this.sc, n, !0) : this.sc.childNodes[this.so], this._last = this.ec !== n || P(this.ec) ? z(this.ec, n, !0) : this.ec.childNodes[this.eo - 1])
            }
        }

        function p(e) {
            return function (t, n) {
                for (var o, i = n ? t : t.parentNode; i;) {
                    if (o = i.nodeType, V(e, o))return i;
                    i = i.parentNode
                }
                return null
            }
        }

        function m(e, t) {
            if (nt(e, t))throw new H("INVALID_NODE_TYPE_ERR")
        }

        function g(e, t) {
            if (!V(t, e.nodeType))throw new H("INVALID_NODE_TYPE_ERR")
        }

        function y(e, t) {
            if (0 > t || t > (P(e) ? e.length : e.childNodes.length))throw new H("INDEX_SIZE_ERR")
        }

        function v(e, t) {
            if (et(e, !0) !== et(t, !0))throw new H("WRONG_DOCUMENT_ERR")
        }

        function b(e) {
            if (tt(e, !0))throw new H("NO_MODIFICATION_ALLOWED_ERR")
        }

        function w(e, t) {
            if (!e)throw new H(t)
        }

        function C(e) {
            return G && I.isBrokenNode(e) || !V(Y, e.nodeType) && !et(e, !0)
        }

        function x(e, t) {
            return t <= (P(e) ? e.length : e.childNodes.length)
        }

        function E(e) {
            return!!e.startContainer && !!e.endContainer && !C(e.startContainer) && !C(e.endContainer) && x(e.startContainer, e.startOffset) && x(e.endContainer, e.endOffset)
        }

        function T(e) {
            if (!E(e))throw new Error("Range error: Range is no longer valid after DOM mutation (" + e.inspect() + ")")
        }

        function N(e, t) {
            T(e);
            var n = e.startContainer, o = e.startOffset, i = e.endContainer, r = e.endOffset, a = n === i;
            P(i) && r > 0 && r < i.length && W(i, r, t), P(n) && o > 0 && o < n.length && (n = W(n, o, t), a ? (r -= o, i = n) : i == n.parentNode && r >= B(n) && r++, o = 0), e.setStartAndEnd(n, o, i, r)
        }

        function S(e) {
            T(e);
            var t = e.commonAncestorContainer.parentNode.cloneNode(!1);
            return t.appendChild(e.cloneContents()), t.innerHTML
        }

        function _(e) {
            e.START_TO_START = lt, e.START_TO_END = ct, e.END_TO_END = ut, e.END_TO_START = dt, e.NODE_BEFORE = ht, e.NODE_AFTER = ft, e.NODE_BEFORE_AND_AFTER = pt, e.NODE_INSIDE = mt
        }

        function R(e) {
            _(e), _(e.prototype)
        }

        function A(e, t) {
            return function () {
                T(this);
                var n, o, r = this.startContainer, a = this.startOffset, s = this.commonAncestorContainer, c = new f(this, !0);
                r !== s && (n = z(r, s, !0), o = i(n), r = o.node, a = o.offset), l(c, b), c.reset();
                var u = e(c);
                return c.detach(), t(this, r, a, r, a), u
            }
        }

        function k(n, r) {
            function a(e, t) {
                return function (n) {
                    g(n, K), g(X(n), Y);
                    var r = (e ? o : i)(n);
                    (t ? s : l)(this, r.node, r.offset)
                }
            }

            function s(e, t, n) {
                var o = e.endContainer, i = e.endOffset;
                (t !== e.startContainer || n !== e.startOffset) && ((X(t) != X(o) || 1 == q(t, n, o, i)) && (o = t, i = n), r(e, t, n, o, i))
            }

            function l(e, t, n) {
                var o = e.startContainer, i = e.startOffset;
                (t !== e.endContainer || n !== e.endOffset) && ((X(t) != X(o) || -1 == q(t, n, o, i)) && (o = t, i = n), r(e, o, i, t, n))
            }

            var d = function () {
            };
            d.prototype = e.rangePrototype, n.prototype = new d, $.extend(n.prototype, {setStart: function (e, t) {
                m(e, !0), y(e, t), s(this, e, t)
            }, setEnd: function (e, t) {
                m(e, !0), y(e, t), l(this, e, t)
            }, setStartAndEnd: function () {
                var e = arguments, t = e[0], n = e[1], o = t, i = n;
                switch (e.length) {
                    case 3:
                        i = e[2];
                        break;
                    case 4:
                        o = e[2], i = e[3]
                }
                r(this, t, n, o, i)
            }, setBoundary: function (e, t, n) {
                this["set" + (n ? "Start" : "End")](e, t)
            }, setStartBefore: a(!0, !0), setStartAfter: a(!1, !0), setEndBefore: a(!0, !1), setEndAfter: a(!1, !1), collapse: function (e) {
                T(this), e ? r(this, this.startContainer, this.startOffset, this.startContainer, this.startOffset) : r(this, this.endContainer, this.endOffset, this.endContainer, this.endOffset)
            }, selectNodeContents: function (e) {
                m(e, !0), r(this, e, 0, e, U(e))
            }, selectNode: function (e) {
                m(e, !1), g(e, K);
                var t = o(e), n = i(e);
                r(this, t.node, t.offset, n.node, n.offset)
            }, extractContents: A(u, r), deleteContents: A(c, r), canSurroundContents: function () {
                T(this), b(this.startContainer), b(this.endContainer);
                var e = new f(this, !0), n = e._first && t(e._first, this) || e._last && t(e._last, this);
                return e.detach(), !n
            }, splitBoundaries: function () {
                N(this)
            }, splitBoundariesPreservingPositions: function (e) {
                N(this, e)
            }, normalizeBoundaries: function () {
                T(this);
                var e = this.startContainer, t = this.startOffset, n = this.endContainer, o = this.endOffset, i = function (e) {
                    var t = e.nextSibling;
                    t && t.nodeType == e.nodeType && (n = e, o = e.length, e.appendData(t.data), t.parentNode.removeChild(t))
                }, a = function (i) {
                    var r = i.previousSibling;
                    if (r && r.nodeType == i.nodeType) {
                        e = i;
                        var a = i.length;
                        if (t = r.length, i.insertData(0, r.data), r.parentNode.removeChild(r), e == n)o += t, n = e; else if (n == i.parentNode) {
                            var s = B(i);
                            o == s ? (n = i, o = a) : o > s && o--
                        }
                    }
                }, s = !0;
                if (P(n))n.length == o && i(n); else {
                    if (o > 0) {
                        var l = n.childNodes[o - 1];
                        l && P(l) && i(l)
                    }
                    s = !this.collapsed
                }
                if (s) {
                    if (P(e))0 == t && a(e); else if (t < e.childNodes.length) {
                        var c = e.childNodes[t];
                        c && P(c) && a(c)
                    }
                } else e = n, t = o;
                r(this, e, t, n, o)
            }, collapseToPoint: function (e, t) {
                m(e, !0), y(e, t), this.setStartAndEnd(e, t)
            }}), R(n)
        }

        function D(e) {
            e.collapsed = e.startContainer === e.endContainer && e.startOffset === e.endOffset, e.commonAncestorContainer = e.collapsed ? e.startContainer : I.getCommonAncestor(e.startContainer, e.endContainer)
        }

        function L(e, t, n, o, i) {
            e.startContainer = t, e.startOffset = n, e.endContainer = o, e.endOffset = i, e.document = I.getDocument(t), D(e)
        }

        function O(e) {
            this.startContainer = e, this.startOffset = 0, this.endContainer = e, this.endOffset = 0, this.document = e, D(this)
        }

        var I = e.dom, $ = e.util, M = I.DomPosition, H = e.DOMException, P = I.isCharacterDataNode, B = I.getNodeIndex, j = I.isOrIsAncestorOf, F = I.getDocument, q = I.comparePoints, W = I.splitDataNode, z = I.getClosestAncestorIn, U = I.getNodeLength, V = I.arrayContains, X = I.getRootContainer, G = e.features.crashyTextNodes;
        f.prototype = {_current: null, _next: null, _first: null, _last: null, isSingleCharacterDataNode: !1, reset: function () {
            this._current = null, this._next = this._first
        }, hasNext: function () {
            return!!this._next
        }, next: function () {
            var e = this._current = this._next;
            return e && (this._next = e !== this._last ? e.nextSibling : null, P(e) && this.clonePartiallySelectedTextNodes && (e === this.ec && (e = e.cloneNode(!0)).deleteData(this.eo, e.length - this.eo), this._current === this.sc && (e = e.cloneNode(!0)).deleteData(0, this.so))), e
        }, remove: function () {
            var e, t, n = this._current;
            !P(n) || n !== this.sc && n !== this.ec ? n.parentNode && n.parentNode.removeChild(n) : (e = n === this.sc ? this.so : 0, t = n === this.ec ? this.eo : n.length, e != t && n.deleteData(e, t - e))
        }, isPartiallySelectedSubtree: function () {
            var e = this._current;
            return t(e, this.range)
        }, getSubtreeIterator: function () {
            var e;
            if (this.isSingleCharacterDataNode)e = this.range.cloneRange(), e.collapse(!1); else {
                e = new O(n(this.range));
                var t = this._current, o = t, i = 0, r = t, a = U(t);
                j(t, this.sc) && (o = this.sc, i = this.so), j(t, this.ec) && (r = this.ec, a = this.eo), L(e, o, i, r, a)
            }
            return new f(e, this.clonePartiallySelectedTextNodes)
        }, detach: function () {
            this.range = this._current = this._next = this._first = this._last = this.sc = this.so = this.ec = this.eo = null
        }};
        var K = [1, 3, 4, 5, 7, 8, 10], Y = [2, 9, 11], Q = [5, 6, 10, 12], J = [1, 3, 4, 5, 7, 8, 10, 11], Z = [1, 3, 4, 5, 7, 8], et = p([9, 11]), tt = p(Q), nt = p([6, 10, 12]), ot = document.createElement("style"), it = !1;
        try {
            ot.innerHTML = "<b>x</b>", it = 3 == ot.firstChild.nodeType
        } catch (rt) {
        }
        e.features.htmlParsingConforms = it;
        var at = it ? function (e) {
            var t = this.startContainer, n = F(t);
            if (!t)throw new H("INVALID_STATE_ERR");
            var o = null;
            return 1 == t.nodeType ? o = t : P(t) && (o = I.parentElement(t)), o = null === o || "HTML" == o.nodeName && I.isHtmlNamespace(F(o).documentElement) && I.isHtmlNamespace(o) ? n.createElement("body") : o.cloneNode(!1), o.innerHTML = e, I.fragmentFromNodeChildren(o)
        } : function (e) {
            var t = n(this), o = t.createElement("body");
            return o.innerHTML = e, I.fragmentFromNodeChildren(o)
        }, st = ["startContainer", "startOffset", "endContainer", "endOffset", "collapsed", "commonAncestorContainer"], lt = 0, ct = 1, ut = 2, dt = 3, ht = 0, ft = 1, pt = 2, mt = 3;
        $.extend(e.rangePrototype, {compareBoundaryPoints: function (e, t) {
            T(this), v(this.startContainer, t.startContainer);
            var n, o, i, r, a = e == dt || e == lt ? "start" : "end", s = e == ct || e == lt ? "start" : "end";
            return n = this[a + "Container"], o = this[a + "Offset"], i = t[s + "Container"], r = t[s + "Offset"], q(n, o, i, r)
        }, insertNode: function (e) {
            if (T(this), g(e, J), b(this.startContainer), j(e, this.startContainer))throw new H("HIERARCHY_REQUEST_ERR");
            var t = r(e, this.startContainer, this.startOffset);
            this.setStartBefore(t)
        }, cloneContents: function () {
            T(this);
            var e, t;
            if (this.collapsed)return n(this).createDocumentFragment();
            if (this.startContainer === this.endContainer && P(this.startContainer))return e = this.startContainer.cloneNode(!0), e.data = e.data.slice(this.startOffset, this.endOffset), t = n(this).createDocumentFragment(), t.appendChild(e), t;
            var o = new f(this, !0);
            return e = s(o), o.detach(), e
        }, canSurroundContents: function () {
            T(this), b(this.startContainer), b(this.endContainer);
            var e = new f(this, !0), n = e._first && t(e._first, this) || e._last && t(e._last, this);
            return e.detach(), !n
        }, surroundContents: function (e) {
            if (g(e, Z), !this.canSurroundContents())throw new H("INVALID_STATE_ERR");
            var t = this.extractContents();
            if (e.hasChildNodes())for (; e.lastChild;)e.removeChild(e.lastChild);
            r(e, this.startContainer, this.startOffset), e.appendChild(t), this.selectNode(e)
        }, cloneRange: function () {
            T(this);
            for (var e, t = new O(n(this)), o = st.length; o--;)e = st[o], t[e] = this[e];
            return t
        }, toString: function () {
            T(this);
            var e = this.startContainer;
            if (e === this.endContainer && P(e))return 3 == e.nodeType || 4 == e.nodeType ? e.data.slice(this.startOffset, this.endOffset) : "";
            var t = [], n = new f(this, !0);
            return l(n, function (e) {
                (3 == e.nodeType || 4 == e.nodeType) && t.push(e.data)
            }), n.detach(), t.join("")
        }, compareNode: function (e) {
            T(this);
            var t = e.parentNode, n = B(e);
            if (!t)throw new H("NOT_FOUND_ERR");
            var o = this.comparePoint(t, n), i = this.comparePoint(t, n + 1);
            return 0 > o ? i > 0 ? pt : ht : i > 0 ? ft : mt
        }, comparePoint: function (e, t) {
            return T(this), w(e, "HIERARCHY_REQUEST_ERR"), v(e, this.startContainer), q(e, t, this.startContainer, this.startOffset) < 0 ? -1 : q(e, t, this.endContainer, this.endOffset) > 0 ? 1 : 0
        }, createContextualFragment: at, toHtml: function () {
            return S(this)
        }, intersectsNode: function (e, t) {
            if (T(this), w(e, "NOT_FOUND_ERR"), F(e) !== n(this))return!1;
            var o = e.parentNode, i = B(e);
            w(o, "NOT_FOUND_ERR");
            var r = q(o, i, this.endContainer, this.endOffset), a = q(o, i + 1, this.startContainer, this.startOffset);
            return t ? 0 >= r && a >= 0 : 0 > r && a > 0
        }, isPointInRange: function (e, t) {
            return T(this), w(e, "HIERARCHY_REQUEST_ERR"), v(e, this.startContainer), q(e, t, this.startContainer, this.startOffset) >= 0 && q(e, t, this.endContainer, this.endOffset) <= 0
        }, intersectsRange: function (e) {
            return a(this, e, !1)
        }, intersectsOrTouchesRange: function (e) {
            return a(this, e, !0)
        }, intersection: function (e) {
            if (this.intersectsRange(e)) {
                var t = q(this.startContainer, this.startOffset, e.startContainer, e.startOffset), n = q(this.endContainer, this.endOffset, e.endContainer, e.endOffset), o = this.cloneRange();
                return-1 == t && o.setStart(e.startContainer, e.startOffset), 1 == n && o.setEnd(e.endContainer, e.endOffset), o
            }
            return null
        }, union: function (e) {
            if (this.intersectsOrTouchesRange(e)) {
                var t = this.cloneRange();
                return-1 == q(e.startContainer, e.startOffset, this.startContainer, this.startOffset) && t.setStart(e.startContainer, e.startOffset), 1 == q(e.endContainer, e.endOffset, this.endContainer, this.endOffset) && t.setEnd(e.endContainer, e.endOffset), t
            }
            throw new H("Ranges do not intersect")
        }, containsNode: function (e, t) {
            return t ? this.intersectsNode(e, !1) : this.compareNode(e) == mt
        }, containsNodeContents: function (e) {
            return this.comparePoint(e, 0) >= 0 && this.comparePoint(e, U(e)) <= 0
        }, containsRange: function (e) {
            var t = this.intersection(e);
            return null !== t && e.equals(t)
        }, containsNodeText: function (e) {
            var t = this.cloneRange();
            t.selectNode(e);
            var n = t.getNodes([3]);
            if (n.length > 0) {
                t.setStart(n[0], 0);
                var o = n.pop();
                return t.setEnd(o, o.length), this.containsRange(t)
            }
            return this.containsNodeContents(e)
        }, getNodes: function (e, t) {
            return T(this), d(this, e, t)
        }, getDocument: function () {
            return n(this)
        }, collapseBefore: function (e) {
            this.setEndBefore(e), this.collapse(!1)
        }, collapseAfter: function (e) {
            this.setStartAfter(e), this.collapse(!0)
        }, getBookmark: function (t) {
            var o = n(this), i = e.createRange(o);
            t = t || I.getBody(o), i.selectNodeContents(t);
            var r = this.intersection(i), a = 0, s = 0;
            return r && (i.setEnd(r.startContainer, r.startOffset), a = i.toString().length, s = a + r.toString().length), {start: a, end: s, containerNode: t}
        }, moveToBookmark: function (e) {
            var t = e.containerNode, n = 0;
            this.setStart(t, 0), this.collapse(!0);
            for (var o, i, r, a, s = [t], l = !1, c = !1; !c && (o = s.pop());)if (3 == o.nodeType)i = n + o.length, !l && e.start >= n && e.start <= i && (this.setStart(o, e.start - n), l = !0), l && e.end >= n && e.end <= i && (this.setEnd(o, e.end - n), c = !0), n = i; else for (a = o.childNodes, r = a.length; r--;)s.push(a[r])
        }, getName: function () {
            return"DomRange"
        }, equals: function (e) {
            return O.rangesEqual(this, e)
        }, isValid: function () {
            return E(this)
        }, inspect: function () {
            return h(this)
        }, detach: function () {
        }}), k(O, L), $.extend(O, {rangeProperties: st, RangeIterator: f, copyComparisonConstants: R, createPrototypeRange: k, inspect: h, toHtml: S, getRangeDocument: n, rangesEqual: function (e, t) {
            return e.startContainer === t.startContainer && e.startOffset === t.startOffset && e.endContainer === t.endContainer && e.endOffset === t.endOffset
        }}), e.DomRange = O
    }), R.createCoreModule("WrappedRange", ["DomRange"], function (e, t) {
        var n, o, i = e.dom, r = e.util, a = i.DomPosition, s = e.DomRange, l = i.getBody, c = i.getContentDocument, u = i.isCharacterDataNode;
        if (e.features.implementsDomRange && !function () {
            function o(e) {
                for (var t, n = h.length; n--;)t = h[n], e[t] = e.nativeRange[t];
                e.collapsed = e.startContainer === e.endContainer && e.startOffset === e.endOffset
            }

            function a(e, t, n, o, i) {
                var r = e.startContainer !== t || e.startOffset != n, a = e.endContainer !== o || e.endOffset != i, s = !e.equals(e.nativeRange);
                (r || a || s) && (e.setEnd(o, i), e.setStart(t, n))
            }

            var u, d, h = s.rangeProperties;
            n = function (e) {
                if (!e)throw t.createError("WrappedRange: Range must be specified");
                this.nativeRange = e, o(this)
            }, s.createPrototypeRange(n, a), u = n.prototype, u.selectNode = function (e) {
                this.nativeRange.selectNode(e), o(this)
            }, u.cloneContents = function () {
                return this.nativeRange.cloneContents()
            }, u.surroundContents = function (e) {
                this.nativeRange.surroundContents(e), o(this)
            }, u.collapse = function (e) {
                this.nativeRange.collapse(e), o(this)
            }, u.cloneRange = function () {
                return new n(this.nativeRange.cloneRange())
            }, u.refresh = function () {
                o(this)
            }, u.toString = function () {
                return this.nativeRange.toString()
            };
            var f = document.createTextNode("test");
            l(document).appendChild(f);
            var p = document.createRange();
            p.setStart(f, 0), p.setEnd(f, 0);
            try {
                p.setStart(f, 1), u.setStart = function (e, t) {
                    this.nativeRange.setStart(e, t), o(this)
                }, u.setEnd = function (e, t) {
                    this.nativeRange.setEnd(e, t), o(this)
                }, d = function (e) {
                    return function (t) {
                        this.nativeRange[e](t), o(this)
                    }
                }
            } catch (m) {
                u.setStart = function (e, t) {
                    try {
                        this.nativeRange.setStart(e, t)
                    } catch (n) {
                        this.nativeRange.setEnd(e, t), this.nativeRange.setStart(e, t)
                    }
                    o(this)
                }, u.setEnd = function (e, t) {
                    try {
                        this.nativeRange.setEnd(e, t)
                    } catch (n) {
                        this.nativeRange.setStart(e, t), this.nativeRange.setEnd(e, t)
                    }
                    o(this)
                }, d = function (e, t) {
                    return function (n) {
                        try {
                            this.nativeRange[e](n)
                        } catch (i) {
                            this.nativeRange[t](n), this.nativeRange[e](n)
                        }
                        o(this)
                    }
                }
            }
            u.setStartBefore = d("setStartBefore", "setEndBefore"), u.setStartAfter = d("setStartAfter", "setEndAfter"), u.setEndBefore = d("setEndBefore", "setStartBefore"), u.setEndAfter = d("setEndAfter", "setStartAfter"), u.selectNodeContents = function (e) {
                this.setStartAndEnd(e, 0, i.getNodeLength(e))
            }, p.selectNodeContents(f), p.setEnd(f, 3);
            var g = document.createRange();
            g.selectNodeContents(f), g.setEnd(f, 4), g.setStart(f, 2), u.compareBoundaryPoints = -1 == p.compareBoundaryPoints(p.START_TO_END, g) && 1 == p.compareBoundaryPoints(p.END_TO_START, g) ? function (e, t) {
                return t = t.nativeRange || t, e == t.START_TO_END ? e = t.END_TO_START : e == t.END_TO_START && (e = t.START_TO_END), this.nativeRange.compareBoundaryPoints(e, t)
            } : function (e, t) {
                return this.nativeRange.compareBoundaryPoints(e, t.nativeRange || t)
            };
            var y = document.createElement("div");
            y.innerHTML = "123";
            var v = y.firstChild, b = l(document);
            b.appendChild(y), p.setStart(v, 1), p.setEnd(v, 2), p.deleteContents(), "13" == v.data && (u.deleteContents = function () {
                this.nativeRange.deleteContents(), o(this)
            }, u.extractContents = function () {
                var e = this.nativeRange.extractContents();
                return o(this), e
            }), b.removeChild(y), b = null, r.isHostMethod(p, "createContextualFragment") && (u.createContextualFragment = function (e) {
                return this.nativeRange.createContextualFragment(e)
            }), l(document).removeChild(f), u.getName = function () {
                return"WrappedRange"
            }, e.WrappedRange = n, e.createNativeRange = function (e) {
                return e = c(e, t, "createNativeRange"), e.createRange()
            }
        }(), e.features.implementsTextRange) {
            var d = function (e) {
                var t = e.parentElement(), n = e.duplicate();
                n.collapse(!0);
                var o = n.parentElement();
                n = e.duplicate(), n.collapse(!1);
                var r = n.parentElement(), a = o == r ? o : i.getCommonAncestor(o, r);
                return a == t ? a : i.getCommonAncestor(t, a)
            }, h = function (e) {
                return 0 == e.compareEndPoints("StartToEnd", e)
            }, f = function (e, t, n, o, r) {
                var s = e.duplicate();
                s.collapse(n);
                var l = s.parentElement();
                if (i.isOrIsAncestorOf(t, l) || (l = t), !l.canHaveHTML) {
                    var c = new a(l.parentNode, i.getNodeIndex(l));
                    return{boundaryPosition: c, nodeInfo: {nodeIndex: c.offset, containerElement: c.node}}
                }
                var d = i.getDocument(l).createElement("span");
                d.parentNode && d.parentNode.removeChild(d);
                for (var h, f, p, m, g, y = n ? "StartToStart" : "StartToEnd", v = r && r.containerElement == l ? r.nodeIndex : 0, b = l.childNodes.length, w = b, C = w; C == b ? l.appendChild(d) : l.insertBefore(d, l.childNodes[C]), s.moveToElementText(d), h = s.compareEndPoints(y, e), 0 != h && v != w;) {
                    if (-1 == h) {
                        if (w == v + 1)break;
                        v = C
                    } else w = w == v + 1 ? v : C;
                    C = Math.floor((v + w) / 2), l.removeChild(d)
                }
                if (g = d.nextSibling, -1 == h && g && u(g)) {
                    s.setEndPoint(n ? "EndToStart" : "EndToEnd", e);
                    var x;
                    if (/[\r\n]/.test(g.data)) {
                        var E = s.duplicate(), T = E.text.replace(/\r\n/g, "\r").length;
                        for (x = E.moveStart("character", T); -1 == (h = E.compareEndPoints("StartToEnd", E));)x++, E.moveStart("character", 1)
                    } else x = s.text.length;
                    m = new a(g, x)
                } else f = (o || !n) && d.previousSibling, p = (o || n) && d.nextSibling, m = p && u(p) ? new a(p, 0) : f && u(f) ? new a(f, f.data.length) : new a(l, i.getNodeIndex(d));
                return d.parentNode.removeChild(d), {boundaryPosition: m, nodeInfo: {nodeIndex: C, containerElement: l}}
            }, p = function (e, t) {
                var n, o, r, a, s = e.offset, c = i.getDocument(e.node), d = l(c).createTextRange(), h = u(e.node);
                return h ? (n = e.node, o = n.parentNode) : (a = e.node.childNodes, n = s < a.length ? a[s] : null, o = e.node), r = c.createElement("span"), r.innerHTML = "&#feff;", n ? o.insertBefore(r, n) : o.appendChild(r), d.moveToElementText(r), d.collapse(!t), o.removeChild(r), h && d[t ? "moveStart" : "moveEnd"]("character", s), d
            };
            o = function (e) {
                this.textRange = e, this.refresh()
            }, o.prototype = new s(document), o.prototype.refresh = function () {
                var e, t, n, o = d(this.textRange);
                h(this.textRange) ? t = e = f(this.textRange, o, !0, !0).boundaryPosition : (n = f(this.textRange, o, !0, !1), e = n.boundaryPosition, t = f(this.textRange, o, !1, !1, n.nodeInfo).boundaryPosition), this.setStart(e.node, e.offset), this.setEnd(t.node, t.offset)
            }, o.prototype.getName = function () {
                return"WrappedTextRange"
            }, s.copyComparisonConstants(o);
            var m = function (e) {
                if (e.collapsed)return p(new a(e.startContainer, e.startOffset), !0);
                var t = p(new a(e.startContainer, e.startOffset), !0), n = p(new a(e.endContainer, e.endOffset), !1), o = l(s.getRangeDocument(e)).createTextRange();
                return o.setEndPoint("StartToStart", t), o.setEndPoint("EndToEnd", n), o
            };
            if (o.rangeToTextRange = m, o.prototype.toTextRange = function () {
                return m(this)
            }, e.WrappedTextRange = o, !e.features.implementsDomRange || e.config.preferTextRange) {
                var g = function () {
                    return this
                }();
                "undefined" == typeof g.Range && (g.Range = o), e.createNativeRange = function (e) {
                    return e = c(e, t, "createNativeRange"), l(e).createTextRange()
                }, e.WrappedRange = o
            }
        }
        e.createRange = function (n) {
            return n = c(n, t, "createRange"), new e.WrappedRange(e.createNativeRange(n))
        }, e.createRangyRange = function (e) {
            return e = c(e, t, "createRangyRange"), new s(e)
        }, e.createIframeRange = function (n) {
            return t.deprecationNotice("createIframeRange()", "createRange(iframeEl)"), e.createRange(n)
        }, e.createIframeRangyRange = function (n) {
            return t.deprecationNotice("createIframeRangyRange()", "createRangyRange(iframeEl)"), e.createRangyRange(n)
        }, e.addShimListener(function (t) {
            var n = t.document;
            "undefined" == typeof n.createRange && (n.createRange = function () {
                return e.createRange(n)
            }), n = t = null
        })
    }), R.createCoreModule("WrappedSelection", ["DomRange", "WrappedRange"], function (e, t) {
        function n(e) {
            return"string" == typeof e ? /^backward(s)?$/i.test(e) : !!e
        }

        function o(e, n) {
            if (e) {
                if (R.isWindow(e))return e;
                if (e instanceof y)return e.win;
                var o = R.getContentDocument(e, t, n);
                return R.getWindow(o)
            }
            return window
        }

        function i(e) {
            return o(e, "getWinSelection").getSelection()
        }

        function r(e) {
            return o(e, "getDocSelection").document.selection
        }

        function a(e) {
            var t = !1;
            return e.anchorNode && (t = 1 == R.comparePoints(e.anchorNode, e.anchorOffset, e.focusNode, e.focusOffset)), t
        }

        function s(e, t, n) {
            var o = n ? "end" : "start", i = n ? "start" : "end";
            e.anchorNode = t[o + "Container"], e.anchorOffset = t[o + "Offset"], e.focusNode = t[i + "Container"], e.focusOffset = t[i + "Offset"]
        }

        function l(e) {
            var t = e.nativeSelection;
            e.anchorNode = t.anchorNode, e.anchorOffset = t.anchorOffset, e.focusNode = t.focusNode, e.focusOffset = t.focusOffset
        }

        function c(e) {
            e.anchorNode = e.focusNode = null, e.anchorOffset = e.focusOffset = 0, e.rangeCount = 0, e.isCollapsed = !0, e._ranges.length = 0
        }

        function u(t) {
            var n;
            return t instanceof D ? (n = e.createNativeRange(t.getDocument()), n.setEnd(t.endContainer, t.endOffset), n.setStart(t.startContainer, t.startOffset)) : t instanceof L ? n = t.nativeRange : $.implementsDomRange && t instanceof R.getWindow(t.startContainer).Range && (n = t), n
        }

        function d(e) {
            if (!e.length || 1 != e[0].nodeType)return!1;
            for (var t = 1, n = e.length; n > t; ++t)if (!R.isAncestorOf(e[0], e[t]))return!1;
            return!0
        }

        function h(e) {
            var n = e.getNodes();
            if (!d(n))throw t.createError("getSingleElementFromRange: range " + e.inspect() + " did not consist of a single element");
            return n[0]
        }

        function f(e) {
            return!!e && "undefined" != typeof e.text
        }

        function p(e, t) {
            var n = new L(t);
            e._ranges = [n], s(e, n, !1), e.rangeCount = 1, e.isCollapsed = n.collapsed
        }

        function m(t) {
            if (t._ranges.length = 0, "None" == t.docSelection.type)c(t); else {
                var n = t.docSelection.createRange();
                if (f(n))p(t, n); else {
                    t.rangeCount = n.length;
                    for (var o, i = H(n.item(0)), r = 0; r < t.rangeCount; ++r)o = e.createRange(i), o.selectNode(n.item(r)), t._ranges.push(o);
                    t.isCollapsed = 1 == t.rangeCount && t._ranges[0].collapsed, s(t, t._ranges[t.rangeCount - 1], !1)
                }
            }
        }

        function g(e, n) {
            for (var o = e.docSelection.createRange(), i = h(n), r = H(o.item(0)), a = P(r).createControlRange(), s = 0, l = o.length; l > s; ++s)a.add(o.item(s));
            try {
                a.add(i)
            } catch (c) {
                throw t.createError("addRange(): Element within the specified Range could not be added to control selection (does it have layout?)")
            }
            a.select(), m(e)
        }

        function y(e, t, n) {
            this.nativeSelection = e, this.docSelection = t, this._ranges = [], this.win = n, this.refresh()
        }

        function v(e) {
            e.win = e.anchorNode = e.focusNode = e._ranges = null, e.rangeCount = e.anchorOffset = e.focusOffset = 0, e.detached = !0
        }

        function b(e, t) {
            for (var n, o, i = tt.length; i--;)if (n = tt[i], o = n.selection, "deleteAll" == t)v(o); else if (n.win == e)return"delete" == t ? (tt.splice(i, 1), !0) : o;
            return"deleteAll" == t && (tt.length = 0), null
        }

        function w(e, n) {
            for (var o, i = H(n[0].startContainer), r = P(i).createControlRange(), a = 0, s = n.length; s > a; ++a) {
                o = h(n[a]);
                try {
                    r.add(o)
                } catch (l) {
                    throw t.createError("setRanges(): Element within one of the specified Ranges could not be added to control selection (does it have layout?)")
                }
            }
            r.select(), m(e)
        }

        function C(e, t) {
            if (e.win.document != H(t))throw new O("WRONG_DOCUMENT_ERR")
        }

        function x(t) {
            return function (n, o) {
                var i;
                this.rangeCount ? (i = this.getRangeAt(0), i["set" + (t ? "Start" : "End")](n, o)) : (i = e.createRange(this.win.document), i.setStartAndEnd(n, o)), this.setSingleRange(i, this.isBackward())
            }
        }

        function E(e) {
            var t = [], n = new I(e.anchorNode, e.anchorOffset), o = new I(e.focusNode, e.focusOffset), i = "function" == typeof e.getName ? e.getName() : "Selection";
            if ("undefined" != typeof e.rangeCount)for (var r = 0, a = e.rangeCount; a > r; ++r)t[r] = D.inspect(e.getRangeAt(r));
            return"[" + i + "(Ranges: " + t.join(", ") + ")(anchor: " + n.inspect() + ", focus: " + o.inspect() + "]"
        }

        e.config.checkSelectionRanges = !0;
        var T, N, S = "boolean", _ = "number", R = e.dom, A = e.util, k = A.isHostMethod, D = e.DomRange, L = e.WrappedRange, O = e.DOMException, I = R.DomPosition, $ = e.features, M = "Control", H = R.getDocument, P = R.getBody, B = D.rangesEqual, j = k(window, "getSelection"), F = A.isHostObject(document, "selection");
        $.implementsWinGetSelection = j, $.implementsDocSelection = F;
        var q = F && (!j || e.config.preferTextRange);
        q ? (T = r, e.isSelectionValid = function (e) {
            var t = o(e, "isSelectionValid").document, n = t.selection;
            return"None" != n.type || H(n.createRange().parentElement()) == t
        }) : j ? (T = i, e.isSelectionValid = function () {
            return!0
        }) : t.fail("Neither document.selection or window.getSelection() detected."), e.getNativeSelection = T;
        var W = T(), z = e.createNativeRange(document), U = P(document), V = A.areHostProperties(W, ["anchorNode", "focusNode", "anchorOffset", "focusOffset"]);
        $.selectionHasAnchorAndFocus = V;
        var X = k(W, "extend");
        $.selectionHasExtend = X;
        var G = typeof W.rangeCount == _;
        $.selectionHasRangeCount = G;
        var K = !1, Y = !0, Q = X ? function (t, n) {
            var o = D.getRangeDocument(n), i = e.createRange(o);
            i.collapseToPoint(n.endContainer, n.endOffset), t.addRange(u(i)), t.extend(n.startContainer, n.startOffset)
        } : null;
        A.areHostMethods(W, ["addRange", "getRangeAt", "removeAllRanges"]) && typeof W.rangeCount == _ && $.implementsDomRange && !function () {
            var t = window.getSelection();
            if (t) {
                for (var n = t.rangeCount, o = n > 1, i = [], r = a(t), s = 0; n > s; ++s)i[s] = t.getRangeAt(s);
                var l = P(document), c = l.appendChild(document.createElement("div"));
                c.contentEditable = "false";
                var u = c.appendChild(document.createTextNode("\xa0\xa0\xa0")), d = document.createRange();
                if (d.setStart(u, 1), d.collapse(!0), t.addRange(d), Y = 1 == t.rangeCount, t.removeAllRanges(), !o) {
                    var h = window.navigator.appVersion.match(/Chrome\/(.*?) /);
                    if (h && parseInt(h[1]) >= 36)K = !1; else {
                        var f = d.cloneRange();
                        d.setStart(u, 0), f.setEnd(u, 3), f.setStart(u, 2), t.addRange(d), t.addRange(f), K = 2 == t.rangeCount
                    }
                }
                for (l.removeChild(c), t.removeAllRanges(), s = 0; n > s; ++s)0 == s && r ? Q ? Q(t, i[s]) : (e.warn("Rangy initialization: original selection was backwards but selection has been restored forwards because the browser does not support Selection.extend"), t.addRange(i[s])) : t.addRange(i[s])
            }
        }(), $.selectionSupportsMultipleRanges = K, $.collapsedNonEditableSelectionsSupported = Y;
        var J, Z = !1;
        U && k(U, "createControlRange") && (J = U.createControlRange(), A.areHostProperties(J, ["item", "add"]) && (Z = !0)), $.implementsControlRange = Z, N = V ? function (e) {
            return e.anchorNode === e.focusNode && e.anchorOffset === e.focusOffset
        } : function (e) {
            return e.rangeCount ? e.getRangeAt(e.rangeCount - 1).collapsed : !1
        };
        var et;
        k(W, "getRangeAt") ? et = function (e, t) {
            try {
                return e.getRangeAt(t)
            } catch (n) {
                return null
            }
        } : V && (et = function (t) {
            var n = H(t.anchorNode), o = e.createRange(n);
            return o.setStartAndEnd(t.anchorNode, t.anchorOffset, t.focusNode, t.focusOffset), o.collapsed !== this.isCollapsed && o.setStartAndEnd(t.focusNode, t.focusOffset, t.anchorNode, t.anchorOffset), o
        }), y.prototype = e.selectionPrototype;
        var tt = [], nt = function (e) {
            if (e && e instanceof y)return e.refresh(), e;
            e = o(e, "getNativeSelection");
            var t = b(e), n = T(e), i = F ? r(e) : null;
            return t ? (t.nativeSelection = n, t.docSelection = i, t.refresh()) : (t = new y(n, i, e), tt.push({win: e, selection: t})), t
        };
        e.getSelection = nt, e.getIframeSelection = function (n) {
            return t.deprecationNotice("getIframeSelection()", "getSelection(iframeEl)"), e.getSelection(R.getIframeWindow(n))
        };
        var ot = y.prototype;
        if (!q && V && A.areHostMethods(W, ["removeAllRanges", "addRange"])) {
            ot.removeAllRanges = function () {
                this.nativeSelection.removeAllRanges(), c(this)
            };
            var it = function (e, t) {
                Q(e.nativeSelection, t), e.refresh()
            };
            ot.addRange = G ? function (t, o) {
                if (Z && F && this.docSelection.type == M)g(this, t); else if (n(o) && X)it(this, t); else {
                    var i;
                    if (K ? i = this.rangeCount : (this.removeAllRanges(), i = 0), this.nativeSelection.addRange(u(t).cloneRange()), this.rangeCount = this.nativeSelection.rangeCount, this.rangeCount == i + 1) {
                        if (e.config.checkSelectionRanges) {
                            var r = et(this.nativeSelection, this.rangeCount - 1);
                            r && !B(r, t) && (t = new L(r))
                        }
                        this._ranges[this.rangeCount - 1] = t, s(this, t, st(this.nativeSelection)), this.isCollapsed = N(this)
                    } else this.refresh()
                }
            } : function (e, t) {
                n(t) && X ? it(this, e) : (this.nativeSelection.addRange(u(e)), this.refresh())
            }, ot.setRanges = function (e) {
                if (Z && F && e.length > 1)w(this, e); else {
                    this.removeAllRanges();
                    for (var t = 0, n = e.length; n > t; ++t)this.addRange(e[t])
                }
            }
        } else {
            if (!(k(W, "empty") && k(z, "select") && Z && q))return t.fail("No means of selecting a Range or TextRange was found"), !1;
            ot.removeAllRanges = function () {
                try {
                    if (this.docSelection.empty(), "None" != this.docSelection.type) {
                        var e;
                        if (this.anchorNode)e = H(this.anchorNode); else if (this.docSelection.type == M) {
                            var t = this.docSelection.createRange();
                            t.length && (e = H(t.item(0)))
                        }
                        if (e) {
                            var n = P(e).createTextRange();
                            n.select(), this.docSelection.empty()
                        }
                    }
                } catch (o) {
                }
                c(this)
            }, ot.addRange = function (t) {
                this.docSelection.type == M ? g(this, t) : (e.WrappedTextRange.rangeToTextRange(t).select(), this._ranges[0] = t, this.rangeCount = 1, this.isCollapsed = this._ranges[0].collapsed, s(this, t, !1))
            }, ot.setRanges = function (e) {
                this.removeAllRanges();
                var t = e.length;
                t > 1 ? w(this, e) : t && this.addRange(e[0])
            }
        }
        ot.getRangeAt = function (e) {
            if (0 > e || e >= this.rangeCount)throw new O("INDEX_SIZE_ERR");
            return this._ranges[e].cloneRange()
        };
        var rt;
        if (q)rt = function (t) {
            var n;
            e.isSelectionValid(t.win) ? n = t.docSelection.createRange() : (n = P(t.win.document).createTextRange(), n.collapse(!0)), t.docSelection.type == M ? m(t) : f(n) ? p(t, n) : c(t)
        }; else if (k(W, "getRangeAt") && typeof W.rangeCount == _)rt = function (t) {
            if (Z && F && t.docSelection.type == M)m(t); else if (t._ranges.length = t.rangeCount = t.nativeSelection.rangeCount, t.rangeCount) {
                for (var n = 0, o = t.rangeCount; o > n; ++n)t._ranges[n] = new e.WrappedRange(t.nativeSelection.getRangeAt(n));
                s(t, t._ranges[t.rangeCount - 1], st(t.nativeSelection)), t.isCollapsed = N(t)
            } else c(t)
        }; else {
            if (!V || typeof W.isCollapsed != S || typeof z.collapsed != S || !$.implementsDomRange)return t.fail("No means of obtaining a Range or TextRange from the user's selection was found"), !1;
            rt = function (e) {
                var t, n = e.nativeSelection;
                n.anchorNode ? (t = et(n, 0), e._ranges = [t], e.rangeCount = 1, l(e), e.isCollapsed = N(e)) : c(e)
            }
        }
        ot.refresh = function (e) {
            var t = e ? this._ranges.slice(0) : null, n = this.anchorNode, o = this.anchorOffset;
            if (rt(this), e) {
                var i = t.length;
                if (i != this._ranges.length)return!0;
                if (this.anchorNode != n || this.anchorOffset != o)return!0;
                for (; i--;)if (!B(t[i], this._ranges[i]))return!0;
                return!1
            }
        };
        var at = function (e, t) {
            var n = e.getAllRanges();
            e.removeAllRanges();
            for (var o = 0, i = n.length; i > o; ++o)B(t, n[o]) || e.addRange(n[o]);
            e.rangeCount || c(e)
        };
        ot.removeRange = Z && F ? function (e) {
            if (this.docSelection.type == M) {
                for (var t, n = this.docSelection.createRange(), o = h(e), i = H(n.item(0)), r = P(i).createControlRange(), a = !1, s = 0, l = n.length; l > s; ++s)t = n.item(s), t !== o || a ? r.add(n.item(s)) : a = !0;
                r.select(), m(this)
            } else at(this, e)
        } : function (e) {
            at(this, e)
        };
        var st;
        !q && V && $.implementsDomRange ? (st = a, ot.isBackward = function () {
            return st(this)
        }) : st = ot.isBackward = function () {
            return!1
        }, ot.isBackwards = ot.isBackward, ot.toString = function () {
            for (var e = [], t = 0, n = this.rangeCount; n > t; ++t)e[t] = "" + this._ranges[t];
            return e.join("")
        }, ot.collapse = function (t, n) {
            C(this, t);
            var o = e.createRange(t);
            o.collapseToPoint(t, n), this.setSingleRange(o), this.isCollapsed = !0
        }, ot.collapseToStart = function () {
            if (!this.rangeCount)throw new O("INVALID_STATE_ERR");
            var e = this._ranges[0];
            this.collapse(e.startContainer, e.startOffset)
        }, ot.collapseToEnd = function () {
            if (!this.rangeCount)throw new O("INVALID_STATE_ERR");
            var e = this._ranges[this.rangeCount - 1];
            this.collapse(e.endContainer, e.endOffset)
        }, ot.selectAllChildren = function (t) {
            C(this, t);
            var n = e.createRange(t);
            n.selectNodeContents(t), this.setSingleRange(n)
        }, ot.deleteFromDocument = function () {
            if (Z && F && this.docSelection.type == M) {
                for (var e, t = this.docSelection.createRange(); t.length;)e = t.item(0), t.remove(e), e.parentNode.removeChild(e);
                this.refresh()
            } else if (this.rangeCount) {
                var n = this.getAllRanges();
                if (n.length) {
                    this.removeAllRanges();
                    for (var o = 0, i = n.length; i > o; ++o)n[o].deleteContents();
                    this.addRange(n[i - 1])
                }
            }
        }, ot.eachRange = function (e, t) {
            for (var n = 0, o = this._ranges.length; o > n; ++n)if (e(this.getRangeAt(n)))return t
        }, ot.getAllRanges = function () {
            var e = [];
            return this.eachRange(function (t) {
                e.push(t)
            }), e
        }, ot.setSingleRange = function (e, t) {
            this.removeAllRanges(), this.addRange(e, t)
        }, ot.callMethodOnEachRange = function (e, t) {
            var n = [];
            return this.eachRange(function (o) {
                n.push(o[e].apply(o, t))
            }), n
        }, ot.setStart = x(!0), ot.setEnd = x(!1), e.rangePrototype.select = function (e) {
            nt(this.getDocument()).setSingleRange(this, e)
        }, ot.changeEachRange = function (e) {
            var t = [], n = this.isBackward();
            this.eachRange(function (n) {
                e(n), t.push(n)
            }), this.removeAllRanges(), n && 1 == t.length ? this.addRange(t[0], "backward") : this.setRanges(t)
        }, ot.containsNode = function (e, t) {
            return this.eachRange(function (n) {
                return n.containsNode(e, t)
            }, !0) || !1
        }, ot.getBookmark = function (e) {
            return{backward: this.isBackward(), rangeBookmarks: this.callMethodOnEachRange("getBookmark", [e])}
        }, ot.moveToBookmark = function (t) {
            for (var n, o, i = [], r = 0; n = t.rangeBookmarks[r++];)o = e.createRange(this.win), o.moveToBookmark(n), i.push(o);
            t.backward ? this.setSingleRange(i[0], "backward") : this.setRanges(i)
        }, ot.toHtml = function () {
            var e = [];
            return this.eachRange(function (t) {
                e.push(D.toHtml(t))
            }), e.join("")
        }, $.implementsTextRange && (ot.getNativeTextRange = function () {
            var n;
            if (n = this.docSelection) {
                var o = n.createRange();
                if (f(o))return o;
                throw t.createError("getNativeTextRange: selection is a control selection")
            }
            if (this.rangeCount > 0)return e.WrappedTextRange.rangeToTextRange(this.getRangeAt(0));
            throw t.createError("getNativeTextRange: selection contains no range")
        }), ot.getName = function () {
            return"WrappedSelection"
        }, ot.inspect = function () {
            return E(this)
        }, ot.detach = function () {
            b(this.win, "delete"), v(this)
        }, y.detachAll = function () {
            b(null, "deleteAll")
        }, y.inspect = E, y.isDirectionBackward = n, e.Selection = y, e.selectionPrototype = ot, e.addShimListener(function (e) {
            "undefined" == typeof e.getSelection && (e.getSelection = function () {
                return nt(e)
            }), e = null
        })
    }), R)
}, this), function (e, t) {
    "function" == typeof define && define.amd ? define(["rangy"], e) : e(t.rangy)
}(function (e) {
    e.createModule("SaveRestore", ["WrappedRange"], function (e, t) {
        function n(e, t) {
            return(t || document).getElementById(e)
        }

        function o(e, t) {
            var n, o = "selectionBoundary_" + +new Date + "_" + ("" + Math.random()).slice(2), i = p.getDocument(e.startContainer), r = e.cloneRange();
            return r.collapse(t), n = i.createElement("span"), n.id = o, n.style.lineHeight = "0", n.style.display = "none", n.className = "rangySelectionBoundary", n.appendChild(i.createTextNode(m)), r.insertNode(n), n
        }

        function i(e, o, i, r) {
            var a = n(i, e);
            a ? (o[r ? "setStartBefore" : "setEndBefore"](a), a.parentNode.removeChild(a)) : t.warn("Marker element has been removed. Cannot restore selection.")
        }

        function r(e, t) {
            return t.compareBoundaryPoints(e.START_TO_START, e)
        }

        function a(t, n) {
            var i, r, a = e.DomRange.getRangeDocument(t), s = t.toString();
            return t.collapsed ? (r = o(t, !1), {document: a, markerId: r.id, collapsed: !0}) : (r = o(t, !1), i = o(t, !0), {document: a, startMarkerId: i.id, endMarkerId: r.id, collapsed: !1, backward: n, toString: function () {
                return"original text: '" + s + "', new text: '" + t.toString() + "'"
            }})
        }

        function s(o, r) {
            var a = o.document;
            "undefined" == typeof r && (r = !0);
            var s = e.createRange(a);
            if (o.collapsed) {
                var l = n(o.markerId, a);
                if (l) {
                    l.style.display = "inline";
                    var c = l.previousSibling;
                    c && 3 == c.nodeType ? (l.parentNode.removeChild(l), s.collapseToPoint(c, c.length)) : (s.collapseBefore(l), l.parentNode.removeChild(l))
                } else t.warn("Marker element has been removed. Cannot restore selection.")
            } else i(a, s, o.startMarkerId, !0), i(a, s, o.endMarkerId, !1);
            return r && s.normalizeBoundaries(), s
        }

        function l(t, o) {
            var i, s, l = [];
            t = t.slice(0), t.sort(r);
            for (var c = 0, u = t.length; u > c; ++c)l[c] = a(t[c], o);
            for (c = u - 1; c >= 0; --c)i = t[c], s = e.DomRange.getRangeDocument(i), i.collapsed ? i.collapseAfter(n(l[c].markerId, s)) : (i.setEndBefore(n(l[c].endMarkerId, s)), i.setStartAfter(n(l[c].startMarkerId, s)));
            return l
        }

        function c(n) {
            if (!e.isSelectionValid(n))return t.warn("Cannot save selection. This usually happens when the selection is collapsed and the selection document has lost focus."), null;
            var o = e.getSelection(n), i = o.getAllRanges(), r = 1 == i.length && o.isBackward(), a = l(i, r);
            return r ? o.setSingleRange(i[0], "backward") : o.setRanges(i), {win: n, rangeInfos: a, restored: !1}
        }

        function u(e) {
            for (var t = [], n = e.length, o = n - 1; o >= 0; o--)t[o] = s(e[o], !0);
            return t
        }

        function d(t, n) {
            if (!t.restored) {
                var o = t.rangeInfos, i = e.getSelection(t.win), r = u(o), a = o.length;
                1 == a && n && e.features.selectionHasExtend && o[0].backward ? (i.removeAllRanges(), i.addRange(r[0], !0)) : i.setRanges(r), t.restored = !0
            }
        }

        function h(e, t) {
            var o = n(t, e);
            o && o.parentNode.removeChild(o)
        }

        function f(e) {
            for (var t, n = e.rangeInfos, o = 0, i = n.length; i > o; ++o)t = n[o], t.collapsed ? h(e.doc, t.markerId) : (h(e.doc, t.startMarkerId), h(e.doc, t.endMarkerId))
        }

        var p = e.dom, m = "";
        e.util.extend(e, {saveRange: a, restoreRange: s, saveRanges: l, restoreRanges: u, saveSelection: c, restoreSelection: d, removeMarkerElement: h, removeMarkers: f})
    })
}, this);
var Base = function () {
};
Base.extend = function (e, t) {
    var n = Base.prototype.extend;
    Base._prototyping = !0;
    var o = new this;
    n.call(o, e), o.base = function () {
    }, delete Base._prototyping;
    var i = o.constructor, r = o.constructor = function () {
        if (!Base._prototyping)if (this._constructing || this.constructor == r)this._constructing = !0, i.apply(this, arguments), delete this._constructing; else if (null != arguments[0])return(arguments[0].extend || n).call(arguments[0], o)
    };
    return r.ancestor = this, r.extend = this.extend, r.forEach = this.forEach, r.implement = this.implement, r.prototype = o, r.toString = this.toString, r.valueOf = function (e) {
        return"object" == e ? r : i.valueOf()
    }, n.call(r, t), "function" == typeof r.init && r.init(), r
}, Base.prototype = {extend: function (e, t) {
    if (arguments.length > 1) {
        var n = this[e];
        if (n && "function" == typeof t && (!n.valueOf || n.valueOf() != t.valueOf()) && /\bbase\b/.test(t)) {
            var o = t.valueOf();
            t = function () {
                var e = this.base || Base.prototype.base;
                this.base = n;
                var t = o.apply(this, arguments);
                return this.base = e, t
            }, t.valueOf = function (e) {
                return"object" == e ? t : o
            }, t.toString = Base.toString
        }
        this[e] = t
    } else if (e) {
        var i = Base.prototype.extend;
        Base._prototyping || "function" == typeof this || (i = this.extend || i);
        for (var r = {toSource: null}, a = ["constructor", "toString", "valueOf"], s = Base._prototyping ? 0 : 1; l = a[s++];)e[l] != r[l] && i.call(this, l, e[l]);
        for (var l in e)r[l] || i.call(this, l, e[l])
    }
    return this
}}, Base = Base.extend({constructor: function () {
    this.extend(arguments[0])
}}, {ancestor: Object, version: "1.1", forEach: function (e, t, n) {
    for (var o in e)void 0 === this.prototype[o] && t.call(n, e[o], o, e)
}, implement: function () {
    for (var e = 0; e < arguments.length; e++)"function" == typeof arguments[e] ? arguments[e](this.prototype) : this.prototype.extend(arguments[e]);
    return this
}, toString: function () {
    return String(this.valueOf())
}}), wysihtml5.browser = function () {
    function e(e) {
        return+(/ipad|iphone|ipod/.test(e) && e.match(/ os (\d+).+? like mac os x/) || [void 0, 0])[1]
    }

    function t(e) {
        return+(e.match(/android (\d+)/) || [void 0, 0])[1]
    }

    function n(e, t) {
        var n, o = -1;
        return"Microsoft Internet Explorer" == navigator.appName ? n = new RegExp("MSIE ([0-9]{1,}[.0-9]{0,})") : "Netscape" == navigator.appName && (n = new RegExp("Trident/.*rv:([0-9]{1,}[.0-9]{0,})")), n && null != n.exec(navigator.userAgent) && (o = parseFloat(RegExp.$1)), -1 === o ? !1 : e ? t ? "<" === t ? o > e : ">" === t ? e > o : "<=" === t ? o >= e : ">=" === t ? e >= o : void 0 : e === o : !0
    }

    var o = navigator.userAgent, i = document.createElement("div"), r = -1 !== o.indexOf("Gecko") && -1 === o.indexOf("KHTML"), a = -1 !== o.indexOf("AppleWebKit/"), s = -1 !== o.indexOf("Chrome/"), l = -1 !== o.indexOf("Opera/");
    return{USER_AGENT: o, supported: function () {
        var n = this.USER_AGENT.toLowerCase(), o = "contentEditable"in i, r = document.execCommand && document.queryCommandSupported && document.queryCommandState, a = document.querySelector && document.querySelectorAll, s = this.isIos() && e(n) < 5 || this.isAndroid() && t(n) < 4 || -1 !== n.indexOf("opera mobi") || -1 !== n.indexOf("hpwos/");
        return o && r && a && !s
    }, isTouchDevice: function () {
        return this.supportsEvent("touchmove")
    }, isIos: function () {
        return/ipad|iphone|ipod/i.test(this.USER_AGENT)
    }, isAndroid: function () {
        return-1 !== this.USER_AGENT.indexOf("Android")
    }, supportsSandboxedIframes: function () {
        return n()
    }, throwsMixedContentWarningWhenIframeSrcIsEmpty: function () {
        return!("querySelector"in document)
    }, displaysCaretInEmptyContentEditableCorrectly: function () {
        return n()
    }, hasCurrentStyleProperty: function () {
        return"currentStyle"in i
    }, hasHistoryIssue: function () {
        return r && "Mac" === navigator.platform.substr(0, 3)
    }, insertsLineBreaksOnReturn: function () {
        return r
    }, supportsPlaceholderAttributeOn: function (e) {
        return"placeholder"in e
    }, supportsEvent: function (e) {
        return"on" + e in i || function () {
            return i.setAttribute("on" + e, "return;"), "function" == typeof i["on" + e]
        }()
    }, supportsEventsInIframeCorrectly: function () {
        return!l
    }, supportsHTML5Tags: function (e) {
        var t = e.createElement("div"), n = "<article>foo</article>";
        return t.innerHTML = n, t.innerHTML.toLowerCase() === n
    }, supportsCommand: function () {
        var e = {formatBlock: n(10, "<="), insertUnorderedList: n(), insertOrderedList: n()}, t = {insertHTML: r};
        return function (n, o) {
            var i = e[o];
            if (!i) {
                try {
                    return n.queryCommandSupported(o)
                } catch (r) {
                }
                try {
                    return n.queryCommandEnabled(o)
                } catch (a) {
                    return!!t[o]
                }
            }
            return!1
        }
    }(), doesAutoLinkingInContentEditable: function () {
        return n()
    }, canDisableAutoLinking: function () {
        return this.supportsCommand(document, "AutoUrlDetect")
    }, clearsContentEditableCorrectly: function () {
        return r || l || a
    }, supportsGetAttributeCorrectly: function () {
        var e = document.createElement("td");
        return"1" != e.getAttribute("rowspan")
    }, canSelectImagesInContentEditable: function () {
        return r || n() || l
    }, autoScrollsToCaret: function () {
        return!a
    }, autoClosesUnclosedTags: function () {
        var e, t, n = i.cloneNode(!1);
        return n.innerHTML = "<p><div></div>", t = n.innerHTML.toLowerCase(), e = "<p></p><div></div>" === t || "<p><div></div></p>" === t, this.autoClosesUnclosedTags = function () {
            return e
        }, e
    }, supportsNativeGetElementsByClassName: function () {
        return-1 !== String(document.getElementsByClassName).indexOf("[native code]")
    }, supportsSelectionModify: function () {
        return"getSelection"in window && "modify"in window.getSelection()
    }, needsSpaceAfterLineBreak: function () {
        return l
    }, supportsSpeechApiOn: function (e) {
        var t = o.match(/Chrome\/(\d+)/) || [void 0, 0];
        return t[1] >= 11 && ("onwebkitspeechchange"in e || "speech"in e)
    }, crashesWhenDefineProperty: function (e) {
        return n(9) && ("XMLHttpRequest" === e || "XDomainRequest" === e)
    }, doesAsyncFocus: function () {
        return n()
    }, hasProblemsSettingCaretAfterImg: function () {
        return n()
    }, hasUndoInContextMenu: function () {
        return r || s || l
    }, hasInsertNodeIssue: function () {
        return l
    }, hasIframeFocusIssue: function () {
        return n()
    }, createsNestedInvalidMarkupAfterPaste: function () {
        return a
    }, supportsMutationEvents: function () {
        return"MutationEvent"in window
    }}
}(), wysihtml5.lang.array = function (e) {
    return{contains: function (t) {
        if (Array.isArray(t)) {
            for (var n = t.length; n--;)if (-1 !== wysihtml5.lang.array(e).indexOf(t[n]))return!0;
            return!1
        }
        return-1 !== wysihtml5.lang.array(e).indexOf(t)
    }, indexOf: function (t) {
        if (e.indexOf)return e.indexOf(t);
        for (var n = 0, o = e.length; o > n; n++)if (e[n] === t)return n;
        return-1
    }, without: function (t) {
        t = wysihtml5.lang.array(t);
        for (var n = [], o = 0, i = e.length; i > o; o++)t.contains(e[o]) || n.push(e[o]);
        return n
    }, get: function () {
        for (var t = 0, n = e.length, o = []; n > t; t++)o.push(e[t]);
        return o
    }, map: function (t, n) {
        if (Array.prototype.map)return e.map(t, n);
        for (var o = e.length >>> 0, i = new Array(o), r = 0; o > r; r++)i[r] = t.call(n, e[r], r, e);
        return i
    }, unique: function () {
        for (var t = [], n = e.length, o = 0; n > o;)wysihtml5.lang.array(t).contains(e[o]) || t.push(e[o]), o++;
        return t
    }}
}, wysihtml5.lang.Dispatcher = Base.extend({on: function (e, t) {
    return this.events = this.events || {}, this.events[e] = this.events[e] || [], this.events[e].push(t), this
}, off: function (e, t) {
    this.events = this.events || {};
    var n, o, i = 0;
    if (e) {
        for (n = this.events[e] || [], o = []; i < n.length; i++)n[i] !== t && t && o.push(n[i]);
        this.events[e] = o
    } else this.events = {};
    return this
}, fire: function (e, t) {
    this.events = this.events || {};
    for (var n = this.events[e] || [], o = 0; o < n.length; o++)n[o].call(this, t);
    return this
}, observe: function () {
    return this.on.apply(this, arguments)
}, stopObserving: function () {
    return this.off.apply(this, arguments)
}}), wysihtml5.lang.object = function (e) {
    return{merge: function (t) {
        for (var n in t)e[n] = t[n];
        return this
    }, get: function () {
        return e
    }, clone: function () {
        var t, n = {};
        for (t in e)n[t] = e[t];
        return n
    }, isArray: function () {
        return"[object Array]" === Object.prototype.toString.call(e)
    }}
}, function () {
    var e = /^\s+/, t = /\s+$/, n = /[&<>"]/g, o = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;"};
    wysihtml5.lang.string = function (i) {
        return i = String(i), {trim: function () {
            return i.replace(e, "").replace(t, "")
        }, interpolate: function (e) {
            for (var t in e)i = this.replace("#{"+t+"}").by(e[t]);
            return i
        }, replace: function (e) {
            return{by: function (t) {
                return i.split(e).join(t)
            }}
        }, escapeHTML: function () {
            return i.replace(n, function (e) {
                return o[e]
            })
        }}
    }
}(), function (e) {
    function t(e, t) {
        return r(e, t) ? e : (e === e.ownerDocument.documentElement && (e = e.ownerDocument.body), a(e, t))
    }

    function n(e) {
        return e.replace(l, function (e, t) {
            var n = (t.match(c) || [])[1] || "", o = d[n];
            t = t.replace(c, ""), t.split(o).length > t.split(n).length && (t += n, n = "");
            var i = t, r = t;
            return t.length > u && (r = r.substr(0, u) + "..."), "www." === i.substr(0, 4) && (i = "http://" + i), '<a href="' + i + '">' + r + "</a>" + n
        })
    }

    function o(e) {
        var t = e._wysihtml5_tempElement;
        return t || (t = e._wysihtml5_tempElement = e.createElement("div")), t
    }

    function i(t) {
        var i = t.parentNode, r = e.lang.string(t.data).escapeHTML(), a = o(i.ownerDocument);
        for (a.innerHTML = "<span></span>" + n(r), a.removeChild(a.firstChild); a.firstChild;)i.insertBefore(a.firstChild, t);
        i.removeChild(t)
    }

    function r(t, n) {
        for (var o; t.parentNode;) {
            if (t = t.parentNode, o = t.nodeName, t.className && e.lang.array(t.className.split(" ")).contains(n))return!0;
            if (s.contains(o))return!0;
            if ("body" === o)return!1
        }
        return!1
    }

    function a(t, n) {
        if (!(s.contains(t.nodeName) || t.className && e.lang.array(t.className.split(" ")).contains(n))) {
            if (t.nodeType === e.TEXT_NODE && t.data.match(l))return void i(t);
            for (var o = e.lang.array(t.childNodes).get(), r = o.length, c = 0; r > c; c++)a(o[c], n);
            return t
        }
    }

    var s = e.lang.array(["CODE", "PRE", "A", "SCRIPT", "HEAD", "TITLE", "STYLE"]), l = /((https?:\/\/|www\.)[^\s<]{3,})/gi, c = /([^\w\/\-](,?))$/i, u = 100, d = {")": "(", "]": "[", "}": "{"};
    e.dom.autoLink = t, e.dom.autoLink.URL_REG_EXP = l
}(wysihtml5), function (e) {
    var t = e.dom;
    t.addClass = function (e, n) {
        var o = e.classList;
        return o ? o.add(n) : void(t.hasClass(e, n) || (e.className += " " + n))
    }, t.removeClass = function (e, t) {
        var n = e.classList;
        return n ? n.remove(t) : void(e.className = e.className.replace(new RegExp("(^|\\s+)" + t + "(\\s+|$)"), " "))
    }, t.hasClass = function (e, t) {
        var n = e.classList;
        if (n)return n.contains(t);
        var o = e.className;
        return o.length > 0 && (o == t || new RegExp("(^|\\s)" + t + "(\\s|$)").test(o))
    }
}(wysihtml5), wysihtml5.dom.contains = function () {
    var e = document.documentElement;
    return e.contains ? function (e, t) {
        return t.nodeType !== wysihtml5.ELEMENT_NODE && (t = t.parentNode), e !== t && e.contains(t)
    } : e.compareDocumentPosition ? function (e, t) {
        return!!(16 & e.compareDocumentPosition(t))
    } : void 0
}(), wysihtml5.dom.convertToList = function () {
    function e(e, t) {
        var n = e.createElement("li");
        return t.appendChild(n), n
    }

    function t(e, t) {
        return e.createElement(t)
    }

    function n(n, o, i) {
        if ("UL" === n.nodeName || "OL" === n.nodeName || "MENU" === n.nodeName)return n;
        var r, a, s, l, c, u, d, h, f, p = n.ownerDocument, m = t(p, o), g = n.querySelectorAll("br"), y = g.length;
        for (f = 0; y > f; f++)for (l = g[f]; (c = l.parentNode) && c !== n && c.lastChild === l;) {
            if ("block" === wysihtml5.dom.getStyle("display").from(c)) {
                c.removeChild(l);
                break
            }
            wysihtml5.dom.insert(l).after(l.parentNode)
        }
        for (r = wysihtml5.lang.array(n.childNodes).get(), a = r.length, f = 0; a > f; f++)h = h || e(p, m), s = r[f], u = "block" === wysihtml5.dom.getStyle("display").from(s), d = "BR" === s.nodeName, !u || i && wysihtml5.dom.hasClass(s, i) ? d ? h = h.firstChild ? null : h : h.appendChild(s) : (h = h.firstChild ? e(p, m) : h, h.appendChild(s), h = null);
        return 0 === r.length && e(p, m), n.parentNode.replaceChild(m, n), m
    }

    return n
}(), wysihtml5.dom.copyAttributes = function (e) {
    return{from: function (t) {
        return{to: function (n) {
            for (var o, i = 0, r = e.length; r > i; i++)o = e[i], "undefined" != typeof t[o] && "" !== t[o] && (n[o] = t[o]);
            return{andTo: arguments.callee}
        }}
    }}
}, function (e) {
    var t = ["-webkit-box-sizing", "-moz-box-sizing", "-ms-box-sizing", "box-sizing"], n = function (t) {
        return o(t) ? parseInt(e.getStyle("width").from(t), 10) < t.offsetWidth : !1
    }, o = function (n) {
        for (var o = 0, i = t.length; i > o; o++)if ("border-box" === e.getStyle(t[o]).from(n))return t[o]
    };
    e.copyStyles = function (o) {
        return{from: function (i) {
            n(i) && (o = wysihtml5.lang.array(o).without(t));
            for (var r, a = "", s = o.length, l = 0; s > l; l++)r = o[l], a += r + ":" + e.getStyle(r).from(i) + ";";
            return{to: function (t) {
                return e.setStyles(a).on(t), {andTo: arguments.callee}
            }}
        }}
    }
}(wysihtml5.dom), function (e) {
    e.dom.delegate = function (t, n, o, i) {
        return e.dom.observe(t, o, function (o) {
            for (var r = o.target, a = e.lang.array(t.querySelectorAll(n)); r && r !== t;) {
                if (a.contains(r)) {
                    i.call(r, o);
                    break
                }
                r = r.parentNode
            }
        })
    }
}(wysihtml5), function (e) {
    e.dom.domNode = function (t) {
        var n = [e.ELEMENT_NODE, e.TEXT_NODE], o = function (t) {
            return t.nodeType === e.TEXT_NODE && /^\s*$/g.test(t.data)
        };
        return{prev: function (i) {
            var r = t.previousSibling, a = i && i.nodeTypes ? i.nodeTypes : n;
            return r ? !e.lang.array(a).contains(r.nodeType) || i && i.ignoreBlankTexts && o(r) ? e.dom.domNode(r).prev(i) : r : null
        }, next: function (i) {
            var r = t.nextSibling, a = i && i.nodeTypes ? i.nodeTypes : n;
            return r ? !e.lang.array(a).contains(r.nodeType) || i && i.ignoreBlankTexts && o(r) ? e.dom.domNode(r).next(i) : r : null
        }}
    }
}(wysihtml5), wysihtml5.dom.getAsDom = function () {
    var e = function (e, t) {
        var n = t.createElement("div");
        n.style.display = "none", t.body.appendChild(n);
        try {
            n.innerHTML = e
        } catch (o) {
        }
        return t.body.removeChild(n), n
    }, t = function (e) {
        if (!e._wysihtml5_supportsHTML5Tags) {
            for (var t = 0, o = n.length; o > t; t++)e.createElement(n[t]);
            e._wysihtml5_supportsHTML5Tags = !0
        }
    }, n = ["abbr", "article", "aside", "audio", "bdi", "canvas", "command", "datalist", "details", "figcaption", "figure", "footer", "header", "hgroup", "keygen", "mark", "meter", "nav", "output", "progress", "rp", "rt", "ruby", "svg", "section", "source", "summary", "time", "track", "video", "wbr"];
    return function (n, o) {
        o = o || document;
        var i;
        return"object" == typeof n && n.nodeType ? (i = o.createElement("div"), i.appendChild(n)) : wysihtml5.browser.supportsHTML5Tags(o) ? (i = o.createElement("div"), i.innerHTML = n) : (t(o), i = e(n, o)), i
    }
}(), wysihtml5.dom.getParentElement = function () {
    function e(e, t) {
        return t && t.length ? "string" == typeof t ? e === t : wysihtml5.lang.array(t).contains(e) : !0
    }

    function t(e) {
        return e.nodeType === wysihtml5.ELEMENT_NODE
    }

    function n(e, t, n) {
        var o = (e.className || "").match(n) || [];
        return t ? o[o.length - 1] === t : !!o.length
    }

    function o(e, t, n) {
        var o = (e.getAttribute("style") || "").match(n) || [];
        return t ? o[o.length - 1] === t : !!o.length
    }

    return function (i, r, a, s) {
        var l = r.cssStyle || r.styleRegExp, c = r.className || r.classRegExp;
        for (a = a || 50; a-- && i && "BODY" !== i.nodeName && (!s || i !== s);) {
            if (t(i) && e(i.nodeName, r.nodeName) && (!l || o(i, r.cssStyle, r.styleRegExp)) && (!c || n(i, r.className, r.classRegExp)))return i;
            i = i.parentNode
        }
        return null
    }
}(), wysihtml5.dom.getStyle = function () {
    function e(e) {
        return e.replace(n, function (e) {
            return e.charAt(1).toUpperCase()
        })
    }

    var t = {"float": "styleFloat"in document.createElement("div").style ? "styleFloat" : "cssFloat"}, n = /\-[a-z]/g;
    return function (n) {
        return{from: function (o) {
            if (o.nodeType === wysihtml5.ELEMENT_NODE) {
                var i = o.ownerDocument, r = t[n] || e(n), a = o.style, s = o.currentStyle, l = a[r];
                if (l)return l;
                if (s)try {
                    return s[r]
                } catch (c) {
                }
                var u, d, h = i.defaultView || i.parentWindow, f = ("height" === n || "width" === n) && "TEXTAREA" === o.nodeName;
                return h.getComputedStyle ? (f && (u = a.overflow, a.overflow = "hidden"), d = h.getComputedStyle(o, null).getPropertyValue(n), f && (a.overflow = u || ""), d) : void 0
            }
        }}
    }
}(), wysihtml5.dom.getTextNodes = function (e, t) {
    var n = [];
    for (e = e.firstChild; e; e = e.nextSibling)3 == e.nodeType ? t && /^\s*$/.test(e.innerText || e.textContent) || n.push(e) : n = n.concat(wysihtml5.dom.getTextNodes(e, t));
    return n
}, wysihtml5.dom.hasElementWithTagName = function () {
    function e(e) {
        return e._wysihtml5_identifier || (e._wysihtml5_identifier = n++)
    }

    var t = {}, n = 1;
    return function (n, o) {
        var i = e(n) + ":" + o, r = t[i];
        return r || (r = t[i] = n.getElementsByTagName(o)), r.length > 0
    }
}(), function (e) {
    function t(e) {
        return e._wysihtml5_identifier || (e._wysihtml5_identifier = o++)
    }

    var n = {}, o = 1;
    e.dom.hasElementWithClassName = function (o, i) {
        if (!e.browser.supportsNativeGetElementsByClassName())return!!o.querySelector("." + i);
        var r = t(o) + ":" + i, a = n[r];
        return a || (a = n[r] = o.getElementsByClassName(i)), a.length > 0
    }
}(wysihtml5), wysihtml5.dom.insert = function (e) {
    return{after: function (t) {
        t.parentNode.insertBefore(e, t.nextSibling)
    }, before: function (t) {
        t.parentNode.insertBefore(e, t)
    }, into: function (t) {
        t.appendChild(e)
    }}
}, wysihtml5.dom.insertCSS = function (e) {
    return e = e.join("\n"), {into: function (t) {
        var n = t.createElement("style");
        n.type = "text/css", n.styleSheet ? n.styleSheet.cssText = e : n.appendChild(t.createTextNode(e));
        var o = t.querySelector("head link");
        if (o)return void o.parentNode.insertBefore(n, o);
        var i = t.querySelector("head");
        i && i.appendChild(n)
    }}
}, function (e) {
    e.dom.lineBreaks = function (t) {
        function n(e) {
            return"BR" === e.nodeName
        }

        function o(t) {
            return n(t) ? !0 : "block" === e.dom.getStyle("display").from(t) ? !0 : !1
        }

        return{add: function () {
            var n = t.ownerDocument, i = e.dom.domNode(t).next({ignoreBlankTexts: !0}), r = e.dom.domNode(t).prev({ignoreBlankTexts: !0});
            i && !o(i) && e.dom.insert(n.createElement("br")).after(t), r && !o(r) && e.dom.insert(n.createElement("br")).before(t)
        }, remove: function () {
            var o = e.dom.domNode(t).next({ignoreBlankTexts: !0}), i = e.dom.domNode(t).prev({ignoreBlankTexts: !0});
            o && n(o) && o.parentNode.removeChild(o), i && n(i) && i.parentNode.removeChild(i)
        }}
    }
}(wysihtml5), wysihtml5.dom.observe = function (e, t, n) {
    t = "string" == typeof t ? [t] : t;
    for (var o, i, r = 0, a = t.length; a > r; r++)i = t[r], e.addEventListener ? e.addEventListener(i, n, !1) : (o = function (t) {
        "target"in t || (t.target = t.srcElement), t.preventDefault = t.preventDefault || function () {
            this.returnValue = !1
        }, t.stopPropagation = t.stopPropagation || function () {
            this.cancelBubble = !0
        }, n.call(e, t)
    }, e.attachEvent("on" + i, o));
    return{stop: function () {
        for (var i, r = 0, a = t.length; a > r; r++)i = t[r], e.removeEventListener ? e.removeEventListener(i, n, !1) : e.detachEvent("on" + i, o)
    }}
}, wysihtml5.dom.parse = function () {
    function e(e, n) {
        wysihtml5.lang.object(m).merge(p).merge(n.rules).get();
        var o, i, r, a = n.context || e.ownerDocument || document, s = a.createDocumentFragment(), l = "string" == typeof e, c = !1;
        for (n.clearInternals === !0 && (c = !0), n.uneditableClass && (g = n.uneditableClass), o = l ? wysihtml5.dom.getAsDom(e, a) : e; o.firstChild;)r = o.firstChild, i = t(r, n.cleanUp, c), i && s.appendChild(i), r !== i && o.removeChild(r);
        return o.innerHTML = "", o.appendChild(s), l ? wysihtml5.quirks.getCorrectInnerHTML(o) : o
    }

    function t(e, n, o) {
        var i, r, a, s = e.nodeType, l = e.childNodes, c = l.length, u = d[s], f = 0;
        if (g && 1 === s && wysihtml5.dom.hasClass(e, g))return e;
        if (r = u && u(e, o), !r) {
            if (r === !1) {
                for (i = e.ownerDocument.createDocumentFragment(), f = c; f--;)l[f] && (a = t(l[f], n, o), a && (l[f] === a && f--, i.insertBefore(a, i.firstChild)));
                return wysihtml5.lang.array(["div", "pre", "p", "table", "td", "th", "ul", "ol", "li", "dd", "dl", "footer", "header", "section", "h1", "h2", "h3", "h4", "h5", "h6"]).contains(e.nodeName.toLowerCase()) && e.parentNode.lastChild !== e && (e.nextSibling && 3 === e.nextSibling.nodeType && /^\s/.test(e.nextSibling.nodeValue) || i.appendChild(e.ownerDocument.createTextNode(" "))), i.normalize && i.normalize(), i
            }
            return null
        }
        for (f = 0; c > f; f++)l[f] && (a = t(l[f], n, o), a && (l[f] === a && f--, r.appendChild(a)));
        if (n && r.nodeName.toLowerCase() === h && (!r.childNodes.length || /^\s*$/gi.test(r.innerHTML) && (o || "_wysihtml5-temp-placeholder" !== e.className && "rangySelectionBoundary" !== e.className) || !r.attributes.length)) {
            for (i = r.ownerDocument.createDocumentFragment(); r.firstChild;)i.appendChild(r.firstChild);
            return i.normalize && i.normalize(), i
        }
        return r.normalize && r.normalize(), r
    }

    function n(e, t) {
        var n, i, s = m.tags, l = e.nodeName.toLowerCase(), c = e.scopeName;
        if (e._wysihtml5)return null;
        if (e._wysihtml5 = 1, "wysihtml5-temp" === e.className)return null;
        if (c && "HTML" != c && (l = c + ":" + l), "outerHTML"in e && (wysihtml5.browser.autoClosesUnclosedTags() || "P" !== e.nodeName || "</p>" === e.outerHTML.slice(-4).toLowerCase() || (l = "div")), l in s) {
            if (n = s[l], !n || n.remove)return null;
            if (n.unwrap)return!1;
            n = "string" == typeof n ? {rename_tag: n} : n
        } else {
            if (!e.firstChild)return null;
            n = {rename_tag: h}
        }
        return i = e.ownerDocument.createElement(n.rename_tag || l), a(e, i, n, t), r(e, i, n), n.one_of_type && !o(e, m, n.one_of_type, t) ? n.remove_action && "unwrap" == n.remove_action ? !1 : null : (e = null, i.normalize && i.normalize(), i)
    }

    function o(e, t, n, o) {
        var r, a;
        if ("SPAN" === e.nodeName && !o && ("_wysihtml5-temp-placeholder" === e.className || "rangySelectionBoundary" === e.className))return!0;
        for (a in n)if (n.hasOwnProperty(a) && t.type_definitions && t.type_definitions[a] && (r = t.type_definitions[a], i(e, r)))return!0;
        return!1
    }

    function i(e, t) {
        var n, o, i, r, a, l = e.getAttribute("class"), c = e.getAttribute("style");
        if (t.methods)for (var u in t.methods)if (t.methods.hasOwnProperty(u) && x[u] && x[u](e))return!0;
        if (l && t.classes) {
            l = l.replace(/^\s+/g, "").replace(/\s+$/g, "").split(f), n = l.length;
            for (var d = 0; n > d; d++)if (t.classes[l[d]])return!0
        }
        if (c && t.styles) {
            c = c.split(";");
            for (o in t.styles)if (t.styles.hasOwnProperty(o))for (var h = c.length; h--;)if (a = c[h].split(":"), a[0].replace(/\s/g, "").toLowerCase() === o && (t.styles[o] === !0 || 1 === t.styles[o] || wysihtml5.lang.array(t.styles[o]).contains(a[1].replace(/\s/g, "").toLowerCase())))return!0
        }
        if (t.attrs)for (i in t.attrs)if (t.attrs.hasOwnProperty(i) && (r = s(e, i), "string" == typeof r && r.search(t.attrs[i]) > -1))return!0;
        return!1
    }

    function r(e, t, n) {
        var o;
        if (n && n.keep_styles)for (o in n.keep_styles)n.keep_styles.hasOwnProperty(o) && ("float" == o ? (e.style.styleFloat && (t.style.styleFloat = e.style.styleFloat), e.style.cssFloat && (t.style.cssFloat = e.style.cssFloat)) : e.style[o] && (t.style[o] = e.style[o]))
    }

    function a(e, t, n, o) {
        var i, r, a, l, c, u, d, h = {}, p = n.set_class, g = n.add_class, y = n.add_style, v = n.set_attributes, x = n.check_attributes, E = m.classes, T = 0, N = [], S = [], _ = [], R = [];
        if (v && (h = wysihtml5.lang.object(v).clone()), x)for (l in x)u = b[x[l]], u && (d = s(e, l), (d || "alt" === l && "IMG" == e.nodeName) && (c = u(d), "string" == typeof c && (h[l] = c)));
        if (p && N.push(p), g)for (l in g)u = C[g[l]], u && (a = u(s(e, l)), "string" == typeof a && N.push(a));
        if (y)for (l in y)u = w[y[l]], u && (newStyle = u(s(e, l)), "string" == typeof newStyle && S.push(newStyle));
        if ("string" == typeof E && "any" === E && e.getAttribute("class"))h["class"] = e.getAttribute("class"); else {
            for (o || (E["_wysihtml5-temp-placeholder"] = 1, E._rangySelectionBoundary = 1, E["wysiwyg-tmp-selected-cell"] = 1), R = e.getAttribute("class"), R && (N = N.concat(R.split(f))), i = N.length; i > T; T++)r = N[T], E[r] && _.push(r);
            _.length && (h["class"] = wysihtml5.lang.array(_).unique().join(" "))
        }
        h["class"] && o && (h["class"] = h["class"].replace("wysiwyg-tmp-selected-cell", ""), /^\s*$/g.test(h["class"]) && delete h["class"]), S.length && (h.style = wysihtml5.lang.array(S).unique().join(" "));
        for (l in h)try {
            t.setAttribute(l, h[l])
        } catch (A) {
        }
        h.src && ("undefined" != typeof h.width && t.setAttribute("width", h.width), "undefined" != typeof h.height && t.setAttribute("height", h.height))
    }

    function s(e, t) {
        t = t.toLowerCase();
        var n = e.nodeName;
        if ("IMG" == n && "src" == t && l(e) === !0)return e.src;
        if (y && "outerHTML"in e) {
            var o = e.outerHTML.toLowerCase(), i = -1 != o.indexOf(" " + t + "=");
            return i ? e.getAttribute(t) : null
        }
        return e.getAttribute(t)
    }

    function l(e) {
        try {
            return e.complete && !e.mozMatchesSelector(":-moz-broken")
        } catch (t) {
            if (e.complete && "complete" === e.readyState)return!0
        }
    }

    function c(e) {
        var t = e.nextSibling;
        if (!t || t.nodeType !== wysihtml5.TEXT_NODE) {
            var n = e.data.replace(v, "");
            return e.ownerDocument.createTextNode(n)
        }
        t.data = e.data.replace(v, "") + t.data.replace(v, "")
    }

    function u(e) {
        return m.comments ? e.ownerDocument.createComment(e.nodeValue) : void 0
    }

    var d = {1: n, 3: c, 8: u}, h = "span", f = /\s+/, p = {tags: {}, classes: {}}, m = {}, g = !1, y = !wysihtml5.browser.supportsGetAttributeCorrectly(), v = /\uFEFF/g, b = {url: function () {
        var e = /^https?:\/\//i;
        return function (t) {
            return t && t.match(e) ? t.replace(e, function (e) {
                return e.toLowerCase()
            }) : null
        }
    }(), src: function () {
        var e = /^(\/|https?:\/\/)/i;
        return function (t) {
            return t && t.match(e) ? t.replace(e, function (e) {
                return e.toLowerCase()
            }) : null
        }
    }(), href: function () {
        var e = /^(#|\/|https?:\/\/|mailto:)/i;
        return function (t) {
            return t && t.match(e) ? t.replace(e, function (e) {
                return e.toLowerCase()
            }) : null
        }
    }(), alt: function () {
        var e = /[^ a-z0-9_\-]/gi;
        return function (t) {
            return t ? t.replace(e, "") : ""
        }
    }(), numbers: function () {
        var e = /\D/g;
        return function (t) {
            return t = (t || "").replace(e, ""), t || null
        }
    }(), any: function () {
        return function (e) {
            return e
        }
    }()}, w = {align_text: function () {
        var e = {left: "text-align: left;", right: "text-align: right;", center: "text-align: center;"};
        return function (t) {
            return e[String(t).toLowerCase()]
        }
    }()}, C = {align_img: function () {
        var e = {left: "wysiwyg-float-left", right: "wysiwyg-float-right"};
        return function (t) {
            return e[String(t).toLowerCase()]
        }
    }(), align_text: function () {
        var e = {left: "wysiwyg-text-align-left", right: "wysiwyg-text-align-right", center: "wysiwyg-text-align-center", justify: "wysiwyg-text-align-justify"};
        return function (t) {
            return e[String(t).toLowerCase()]
        }
    }(), clear_br: function () {
        var e = {left: "wysiwyg-clear-left", right: "wysiwyg-clear-right", both: "wysiwyg-clear-both", all: "wysiwyg-clear-both"};
        return function (t) {
            return e[String(t).toLowerCase()]
        }
    }(), size_font: function () {
        var e = {1: "wysiwyg-font-size-xx-small", 2: "wysiwyg-font-size-small", 3: "wysiwyg-font-size-medium", 4: "wysiwyg-font-size-large", 5: "wysiwyg-font-size-x-large", 6: "wysiwyg-font-size-xx-large", 7: "wysiwyg-font-size-xx-large", "-": "wysiwyg-font-size-smaller", "+": "wysiwyg-font-size-larger"};
        return function (t) {
            return e[String(t).charAt(0)]
        }
    }()}, x = {has_visible_contet: function () {
        var e, t = ["img", "video", "picture", "br", "script", "noscript", "style", "table", "iframe", "object", "embed", "audio", "svg", "input", "button", "select", "textarea", "canvas"];
        return function (n) {
            if (e = (n.innerText || n.textContent).replace(/\s/g, ""), e && e.length > 0)return!0;
            for (var o = t.length; o--;)if (n.querySelector(t[o]))return!0;
            return n.offsetWidth && n.offsetWidth > 0 && n.offsetHeight && n.offsetHeight > 0 ? !0 : !1
        }
    }()};
    return e
}(), wysihtml5.dom.removeEmptyTextNodes = function (e) {
    for (var t, n = wysihtml5.lang.array(e.childNodes).get(), o = n.length, i = 0; o > i; i++)t = n[i], t.nodeType === wysihtml5.TEXT_NODE && "" === t.data && t.parentNode.removeChild(t)
}, wysihtml5.dom.renameElement = function (e, t) {
    for (var n, o = e.ownerDocument.createElement(t); n = e.firstChild;)o.appendChild(n);
    return wysihtml5.dom.copyAttributes(["align", "className"]).from(e).to(o), e.parentNode.replaceChild(o, e), o
}, wysihtml5.dom.replaceWithChildNodes = function (e) {
    if (e.parentNode) {
        if (!e.firstChild)return void e.parentNode.removeChild(e);
        for (var t = e.ownerDocument.createDocumentFragment(); e.firstChild;)t.appendChild(e.firstChild);
        e.parentNode.replaceChild(t, e), e = t = null
    }
}, function (e) {
    function t(t) {
        return"block" === e.getStyle("display").from(t)
    }

    function n(e) {
        return"BR" === e.nodeName
    }

    function o(e) {
        var t = e.ownerDocument.createElement("br");
        e.appendChild(t)
    }

    function i(e, i) {
        if (e.nodeName.match(/^(MENU|UL|OL)$/)) {
            var r, a, s, l, c, u, d = e.ownerDocument, h = d.createDocumentFragment(), f = wysihtml5.dom.domNode(e).prev({ignoreBlankTexts: !0});
            if (i)for (!f || t(f) || n(f) || o(h); u = e.firstElementChild || e.firstChild;) {
                for (a = u.lastChild; r = u.firstChild;)s = r === a, l = s && !t(r) && !n(r), h.appendChild(r), l && o(h);
                u.parentNode.removeChild(u)
            } else for (; u = e.firstElementChild || e.firstChild;) {
                if (u.querySelector && u.querySelector("div, p, ul, ol, menu, blockquote, h1, h2, h3, h4, h5, h6"))for (; r = u.firstChild;)h.appendChild(r); else {
                    for (c = d.createElement("p"); r = u.firstChild;)c.appendChild(r);
                    h.appendChild(c)
                }
                u.parentNode.removeChild(u)
            }
            e.parentNode.replaceChild(h, e)
        }
    }

    e.resolveList = i
}(wysihtml5.dom), function (e) {
    var t = document, n = ["parent", "top", "opener", "frameElement", "frames", "localStorage", "globalStorage", "sessionStorage", "indexedDB"], o = ["open", "close", "openDialog", "showModalDialog", "alert", "confirm", "prompt", "openDatabase", "postMessage", "XMLHttpRequest", "XDomainRequest"], i = ["referrer", "write", "open", "close"];
    e.dom.Sandbox = Base.extend({constructor: function (t, n) {
        this.callback = t || e.EMPTY_FUNCTION, this.config = e.lang.object({}).merge(n).get(), this.editableArea = this._createIframe()
    }, insertInto: function (e) {
        "string" == typeof e && (e = t.getElementById(e)), e.appendChild(this.editableArea)
    }, getIframe: function () {
        return this.editableArea
    }, getWindow: function () {
        this._readyError()
    }, getDocument: function () {
        this._readyError()
    }, destroy: function () {
        var e = this.getIframe();
        e.parentNode.removeChild(e)
    }, _readyError: function () {
        throw new Error("wysihtml5.Sandbox: Sandbox iframe isn't loaded yet")
    }, _createIframe: function () {
        var n = this, o = t.createElement("iframe");
        return o.className = "wysihtml5-sandbox", e.dom.setAttributes({security: "restricted", allowtransparency: "true", frameborder: 0, width: 0, height: 0, marginwidth: 0, marginheight: 0}).on(o), e.browser.throwsMixedContentWarningWhenIframeSrcIsEmpty() && (o.src = "javascript:'<html></html>'"), o.onload = function () {
            o.onreadystatechange = o.onload = null, n._onLoadIframe(o)
        }, o.onreadystatechange = function () {
            /loaded|complete/.test(o.readyState) && (o.onreadystatechange = o.onload = null, n._onLoadIframe(o))
        }, o
    }, _onLoadIframe: function (r) {
        if (e.dom.contains(t.documentElement, r)) {
            var a = this, s = r.contentWindow, l = r.contentWindow.document, c = t.characterSet || t.charset || "utf-8", u = this._getHtml({charset: c, stylesheets: this.config.stylesheets});
            if (l.open("text/html", "replace"), l.write(u), l.close(), this.getWindow = function () {
                return r.contentWindow
            }, this.getDocument = function () {
                return r.contentWindow.document
            }, s.onerror = function (e, t, n) {
                throw new Error("wysihtml5.Sandbox: " + e, t, n)
            }, !e.browser.supportsSandboxedIframes()) {
                var d, h;
                for (d = 0, h = n.length; h > d; d++)this._unset(s, n[d]);
                for (d = 0, h = o.length; h > d; d++)this._unset(s, o[d], e.EMPTY_FUNCTION);
                for (d = 0, h = i.length; h > d; d++)this._unset(l, i[d]);
                this._unset(l, "cookie", "", !0)
            }
            this.loaded = !0, setTimeout(function () {
                a.callback(a)
            }, 0)
        }
    }, _getHtml: function (t) {
        var n, o = t.stylesheets, i = "", r = 0;
        if (o = "string" == typeof o ? [o] : o)for (n = o.length; n > r; r++)i += '<link rel="stylesheet" href="' + o[r] + '">';
        return t.stylesheets = i, e.lang.string('<!DOCTYPE html><html><head><meta charset="#{charset}">#{stylesheets}</head><body></body></html>').interpolate(t)
    }, _unset: function (t, n, o, i) {
        try {
            t[n] = o
        } catch (r) {
        }
        try {
            t.__defineGetter__(n, function () {
                return o
            })
        } catch (r) {
        }
        if (i)try {
            t.__defineSetter__(n, function () {
            })
        } catch (r) {
        }
        if (!e.browser.crashesWhenDefineProperty(n))try {
            var a = {get: function () {
                return o
            }};
            i && (a.set = function () {
            }), Object.defineProperty(t, n, a)
        } catch (r) {
        }
    }})
}(wysihtml5), function (e) {
    var t = document;
    e.dom.ContentEditableArea = Base.extend({getContentEditable: function () {
        return this.element
    }, getWindow: function () {
        return this.element.ownerDocument.defaultView
    }, getDocument: function () {
        return this.element.ownerDocument
    }, constructor: function (t, n, o) {
        this.callback = t || e.EMPTY_FUNCTION, this.config = e.lang.object({}).merge(n).get(), this.element = o ? this._bindElement(o) : this._createElement()
    }, _createElement: function () {
        var e = t.createElement("div");
        return e.className = "wysihtml5-sandbox", this._loadElement(e), e
    }, _bindElement: function (e) {
        return e.className = e.className && "" != e.className ? e.className + " wysihtml5-sandbox" : "wysihtml5-sandbox", this._loadElement(e, !0), e
    }, _loadElement: function (e, t) {
        var n = this;
        if (!t) {
            var o = this._getHtml();
            e.innerHTML = o
        }
        this.getWindow = function () {
            return e.ownerDocument.defaultView
        }, this.getDocument = function () {
            return e.ownerDocument
        }, this.loaded = !0, setTimeout(function () {
            n.callback(n)
        }, 0)
    }, _getHtml: function () {
        return""
    }})
}(wysihtml5), function () {
    var e = {className: "class"};
    wysihtml5.dom.setAttributes = function (t) {
        return{on: function (n) {
            for (var o in t)n.setAttribute(e[o] || o, t[o])
        }}
    }
}(), wysihtml5.dom.setStyles = function (e) {
    return{on: function (t) {
        var n = t.style;
        if ("string" == typeof e)return void(n.cssText += ";" + e);
        for (var o in e)"float" === o ? (n.cssFloat = e[o], n.styleFloat = e[o]) : n[o] = e[o]
    }}
}, function (e) {
    e.simulatePlaceholder = function (t, n, o) {
        var i = "placeholder", r = function () {
            var t = n.element.offsetWidth > 0 && n.element.offsetHeight > 0;
            n.hasPlaceholderSet() && (n.clear(), n.element.focus(), t && setTimeout(function () {
                var e = n.selection.getSelection();
                e.focusNode && e.anchorNode || n.selection.selectNode(n.element.firstChild || n.element)
            }, 0)), n.placeholderSet = !1, e.removeClass(n.element, i)
        }, a = function () {
            n.isEmpty() && (n.placeholderSet = !0, n.setValue(o), e.addClass(n.element, i))
        };
        t.on("set_placeholder", a).on("unset_placeholder", r).on("focus:composer", r).on("paste:composer", r).on("blur:composer", a), a()
    }
}(wysihtml5.dom), function (e) {
    var t = document.documentElement;
    "textContent"in t ? (e.setTextContent = function (e, t) {
        e.textContent = t
    }, e.getTextContent = function (e) {
        return e.textContent
    }) : "innerText"in t ? (e.setTextContent = function (e, t) {
        e.innerText = t
    }, e.getTextContent = function (e) {
        return e.innerText
    }) : (e.setTextContent = function (e, t) {
        e.nodeValue = t
    }, e.getTextContent = function (e) {
        return e.nodeValue
    })
}(wysihtml5.dom), wysihtml5.dom.getAttribute = function (e, t) {
    var n = !wysihtml5.browser.supportsGetAttributeCorrectly();
    t = t.toLowerCase();
    var o = e.nodeName;
    if ("IMG" == o && "src" == t && _isLoadedImage(e) === !0)return e.src;
    if (n && "outerHTML"in e) {
        var i = e.outerHTML.toLowerCase(), r = -1 != i.indexOf(" " + t + "=");
        return r ? e.getAttribute(t) : null
    }
    return e.getAttribute(t)
}, function (e) {
    function t(e, t) {
        for (var n, o = [], i = 0, r = e.length; r > i; i++)if (n = e[i].querySelectorAll(t))for (var a = n.length; a--; o.unshift(n[a]));
        return o
    }

    function n(e) {
        e.parentNode.removeChild(e)
    }

    function o(e, t) {
        e.parentNode.insertBefore(t, e.nextSibling)
    }

    function i(e, t) {
        for (var n = e.nextSibling; 1 != n.nodeType;)if (n = n.nextSibling, !t || t == n.tagName.toLowerCase())return n;
        return null
    }

    var r = e.dom, a = function (e) {
        this.el = e, this.isColspan = !1, this.isRowspan = !1, this.firstCol = !0, this.lastCol = !0, this.firstRow = !0, this.lastRow = !0, this.isReal = !0, this.spanCollection = [], this.modified = !1
    }, s = function (e, t) {
        e ? (this.cell = e, this.table = r.getParentElement(e, {nodeName: ["TABLE"]})) : t && (this.table = t, this.cell = this.table.querySelectorAll("th, td")[0])
    };
    s.prototype = {addSpannedCellToMap: function (e, t, n, o, i, r) {
        for (var s = [], l = n + (r ? parseInt(r, 10) - 1 : 0), c = o + (i ? parseInt(i, 10) - 1 : 0), u = n; l >= u; u++) {
            "undefined" == typeof t[u] && (t[u] = []);
            for (var d = o; c >= d; d++)t[u][d] = new a(e), t[u][d].isColspan = i && parseInt(i, 10) > 1, t[u][d].isRowspan = r && parseInt(r, 10) > 1, t[u][d].firstCol = d == o, t[u][d].lastCol = d == c, t[u][d].firstRow = u == n, t[u][d].lastRow = u == l, t[u][d].isReal = d == o && u == n, t[u][d].spanCollection = s, s.push(t[u][d])
        }
    }, setCellAsModified: function (e) {
        if (e.modified = !0, e.spanCollection.length > 0)for (var t = 0, n = e.spanCollection.length; n > t; t++)e.spanCollection[t].modified = !0
    }, setTableMap: function () {
        var e, t, n, o, i, s, l, c, u = [], d = this.getTableRows();
        for (e = 0; e < d.length; e++)for (t = d[e], n = this.getRowCells(t), s = 0, "undefined" == typeof u[e] && (u[e] = []), o = 0; o < n.length; o++) {
            for (i = n[o]; "undefined" != typeof u[e][s];)s++;
            l = r.getAttribute(i, "colspan"), c = r.getAttribute(i, "rowspan"), l || c ? (this.addSpannedCellToMap(i, u, e, s, l, c), s += l ? parseInt(l, 10) : 1) : (u[e][s] = new a(i), s++)
        }
        return this.map = u, u
    }, getRowCells: function (n) {
        var o = this.table.querySelectorAll("table"), i = o ? t(o, "th, td") : [], r = n.querySelectorAll("th, td"), a = i.length > 0 ? e.lang.array(r).without(i) : r;
        return a
    }, getTableRows: function () {
        var n = this.table.querySelectorAll("table"), o = n ? t(n, "tr") : [], i = this.table.querySelectorAll("tr"), r = o.length > 0 ? e.lang.array(i).without(o) : i;
        return r
    }, getMapIndex: function (e) {
        for (var t = this.map.length, n = this.map && this.map[0] ? this.map[0].length : 0, o = 0; t > o; o++)for (var i = 0; n > i; i++)if (this.map[o][i].el === e)return{row: o, col: i};
        return!1
    }, getElementAtIndex: function (e) {
        return this.setTableMap(), this.map[e.row] && this.map[e.row][e.col] && this.map[e.row][e.col].el ? this.map[e.row][e.col].el : null
    }, getMapElsTo: function (e) {
        var t = [];
        if (this.setTableMap(), this.idx_start = this.getMapIndex(this.cell), this.idx_end = this.getMapIndex(e), this.idx_start.row > this.idx_end.row || this.idx_start.row == this.idx_end.row && this.idx_start.col > this.idx_end.col) {
            var n = this.idx_start;
            this.idx_start = this.idx_end, this.idx_end = n
        }
        if (this.idx_start.col > this.idx_end.col) {
            var o = this.idx_start.col;
            this.idx_start.col = this.idx_end.col, this.idx_end.col = o
        }
        if (null != this.idx_start && null != this.idx_end)for (var i = this.idx_start.row, r = this.idx_end.row; r >= i; i++)for (var a = this.idx_start.col, s = this.idx_end.col; s >= a; a++)t.push(this.map[i][a].el);
        return t
    }, orderSelectionEnds: function (e) {
        if (this.setTableMap(), this.idx_start = this.getMapIndex(this.cell), this.idx_end = this.getMapIndex(e), this.idx_start.row > this.idx_end.row || this.idx_start.row == this.idx_end.row && this.idx_start.col > this.idx_end.col) {
            var t = this.idx_start;
            this.idx_start = this.idx_end, this.idx_end = t
        }
        if (this.idx_start.col > this.idx_end.col) {
            var n = this.idx_start.col;
            this.idx_start.col = this.idx_end.col, this.idx_end.col = n
        }
        return{start: this.map[this.idx_start.row][this.idx_start.col].el, end: this.map[this.idx_end.row][this.idx_end.col].el}
    }, createCells: function (e, t, n) {
        for (var o, i = this.table.ownerDocument, r = i.createDocumentFragment(), a = 0; t > a; a++) {
            if (o = i.createElement(e), n)for (var s in n)n.hasOwnProperty(s) && o.setAttribute(s, n[s]);
            o.appendChild(document.createTextNode("\xa0")), r.appendChild(o)
        }
        return r
    }, correctColIndexForUnreals: function (e, t) {
        for (var n = this.map[t], o = -1, i = 0; e > i; i++)n[i].isReal && o++;
        return o
    }, getLastNewCellOnRow: function (e, t) {
        for (var n, o, i = this.getRowCells(e), r = 0, a = i.length; a > r; r++)if (n = i[r], o = this.getMapIndex(n), o === !1 || "undefined" != typeof t && o.row != t)return n;
        return null
    }, removeEmptyTable: function () {
        var e = this.table.querySelectorAll("td, th");
        return e && 0 != e.length ? !1 : (n(this.table), !0)
    }, splitRowToCells: function (e) {
        if (e.isColspan) {
            var t = parseInt(r.getAttribute(e.el, "colspan") || 1, 10), n = e.el.tagName.toLowerCase();
            if (t > 1) {
                var i = this.createCells(n, t - 1);
                o(e.el, i)
            }
            e.el.removeAttribute("colspan")
        }
    }, getRealRowEl: function (e, t) {
        var n = null, o = null;
        t = t || this.idx;
        for (var i = 0, a = this.map[t.row].length; a > i; i++)if (o = this.map[t.row][i], o.isReal && (n = r.getParentElement(o.el, {nodeName: ["TR"]})))return n;
        return null === n && e && (n = r.getParentElement(this.map[t.row][t.col].el, {nodeName: ["TR"]}) || null), n
    }, injectRowAt: function (e, t, n, i, a) {
        var s = this.getRealRowEl(!1, {row: e, col: t}), l = this.createCells(i, n);
        if (s) {
            var c = this.correctColIndexForUnreals(t, e);
            c >= 0 ? o(this.getRowCells(s)[c], l) : s.insertBefore(l, s.firstChild)
        } else {
            var u = this.table.ownerDocument.createElement("tr");
            u.appendChild(l), o(r.getParentElement(a.el, {nodeName: ["TR"]}), u)
        }
    }, canMerge: function (e) {
        if (this.to = e, this.setTableMap(), this.idx_start = this.getMapIndex(this.cell), this.idx_end = this.getMapIndex(this.to), this.idx_start.row > this.idx_end.row || this.idx_start.row == this.idx_end.row && this.idx_start.col > this.idx_end.col) {
            var t = this.idx_start;
            this.idx_start = this.idx_end, this.idx_end = t
        }
        if (this.idx_start.col > this.idx_end.col) {
            var n = this.idx_start.col;
            this.idx_start.col = this.idx_end.col, this.idx_end.col = n
        }
        for (var o = this.idx_start.row, i = this.idx_end.row; i >= o; o++)for (var r = this.idx_start.col, a = this.idx_end.col; a >= r; r++)if (this.map[o][r].isColspan || this.map[o][r].isRowspan)return!1;
        return!0
    }, decreaseCellSpan: function (e, t) {
        var n = parseInt(r.getAttribute(e.el, t), 10) - 1;
        n >= 1 ? e.el.setAttribute(t, n) : (e.el.removeAttribute(t), "colspan" == t && (e.isColspan = !1), "rowspan" == t && (e.isRowspan = !1), e.firstCol = !0, e.lastCol = !0, e.firstRow = !0, e.lastRow = !0, e.isReal = !0)
    }, removeSurplusLines: function () {
        var e, t, o, i, a, s, l;
        if (this.setTableMap(), this.map) {
            for (o = 0, i = this.map.length; i > o; o++) {
                for (e = this.map[o], l = !0, a = 0, s = e.length; s > a; a++)if (t = e[a], !(r.getAttribute(t.el, "rowspan") && parseInt(r.getAttribute(t.el, "rowspan"), 10) > 1 && t.firstRow !== !0)) {
                    l = !1;
                    break
                }
                if (l)for (a = 0; s > a; a++)this.decreaseCellSpan(e[a], "rowspan")
            }
            var c = this.getTableRows();
            for (o = 0, i = c.length; i > o; o++)e = c[o], 0 == e.childNodes.length && /^\s*$/.test(e.textContent || e.innerText) && n(e)
        }
    }, fillMissingCells: function () {
        var e = 0, t = 0, n = null;
        if (this.setTableMap(), this.map) {
            e = this.map.length;
            for (var i = 0; e > i; i++)this.map[i].length > t && (t = this.map[i].length);
            for (var r = 0; e > r; r++)for (var s = 0; t > s; s++)this.map[r] && !this.map[r][s] && s > 0 && (this.map[r][s] = new a(this.createCells("td", 1)), n = this.map[r][s - 1], n && n.el && n.el.parent && o(this.map[r][s - 1].el, this.map[r][s].el))
        }
    }, rectify: function () {
        return this.removeEmptyTable() ? !1 : (this.removeSurplusLines(), this.fillMissingCells(), !0)
    }, unmerge: function () {
        if (this.rectify() && (this.setTableMap(), this.idx = this.getMapIndex(this.cell), this.idx)) {
            var e = this.map[this.idx.row][this.idx.col], t = r.getAttribute(e.el, "colspan") ? parseInt(r.getAttribute(e.el, "colspan"), 10) : 1, n = e.el.tagName.toLowerCase();
            if (e.isRowspan) {
                var o = parseInt(r.getAttribute(e.el, "rowspan"), 10);
                if (o > 1)for (var i = 1, a = o - 1; a >= i; i++)this.injectRowAt(this.idx.row + i, this.idx.col, t, n, e);
                e.el.removeAttribute("rowspan")
            }
            this.splitRowToCells(e)
        }
    }, merge: function (e) {
        if (this.rectify())if (this.canMerge(e)) {
            for (var t = this.idx_end.row - this.idx_start.row + 1, o = this.idx_end.col - this.idx_start.col + 1, i = this.idx_start.row, r = this.idx_end.row; r >= i; i++)for (var a = this.idx_start.col, s = this.idx_end.col; s >= a; a++)i == this.idx_start.row && a == this.idx_start.col ? (t > 1 && this.map[i][a].el.setAttribute("rowspan", t), o > 1 && this.map[i][a].el.setAttribute("colspan", o)) : (/^\s*<br\/?>\s*$/.test(this.map[i][a].el.innerHTML.toLowerCase()) || (this.map[this.idx_start.row][this.idx_start.col].el.innerHTML += " " + this.map[i][a].el.innerHTML), n(this.map[i][a].el));
            this.rectify()
        } else window.console
    }, collapseCellToNextRow: function (e) {
        var t = this.getMapIndex(e.el), n = t.row + 1, i = {row: n, col: t.col};
        if (n < this.map.length) {
            var a = this.getRealRowEl(!1, i);
            if (null !== a) {
                var s = this.correctColIndexForUnreals(i.col, i.row);
                if (s >= 0)o(this.getRowCells(a)[s], e.el); else {
                    var l = this.getLastNewCellOnRow(a, n);
                    null !== l ? o(l, e.el) : a.insertBefore(e.el, a.firstChild)
                }
                parseInt(r.getAttribute(e.el, "rowspan"), 10) > 2 ? e.el.setAttribute("rowspan", parseInt(r.getAttribute(e.el, "rowspan"), 10) - 1) : e.el.removeAttribute("rowspan")
            }
        }
    }, removeRowCell: function (e) {
        e.isReal ? e.isRowspan ? this.collapseCellToNextRow(e) : n(e.el) : parseInt(r.getAttribute(e.el, "rowspan"), 10) > 2 ? e.el.setAttribute("rowspan", parseInt(r.getAttribute(e.el, "rowspan"), 10) - 1) : e.el.removeAttribute("rowspan")
    }, getRowElementsByCell: function () {
        var e = [];
        if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), this.idx !== !1)for (var t = this.map[this.idx.row], n = 0, o = t.length; o > n; n++)t[n].isReal && e.push(t[n].el);
        return e
    }, getColumnElementsByCell: function () {
        var e = [];
        if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), this.idx !== !1)for (var t = 0, n = this.map.length; n > t; t++)this.map[t][this.idx.col] && this.map[t][this.idx.col].isReal && e.push(this.map[t][this.idx.col].el);
        return e
    }, removeRow: function () {
        var e = r.getParentElement(this.cell, {nodeName: ["TR"]});
        if (e) {
            if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), this.idx !== !1)for (var t = this.map[this.idx.row], o = 0, i = t.length; i > o; o++)t[o].modified || (this.setCellAsModified(t[o]), this.removeRowCell(t[o]));
            n(e)
        }
    }, removeColCell: function (e) {
        e.isColspan ? parseInt(r.getAttribute(e.el, "colspan"), 10) > 2 ? e.el.setAttribute("colspan", parseInt(r.getAttribute(e.el, "colspan"), 10) - 1) : e.el.removeAttribute("colspan") : e.isReal && n(e.el)
    }, removeColumn: function () {
        if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), this.idx !== !1)for (var e = 0, t = this.map.length; t > e; e++)this.map[e][this.idx.col].modified || (this.setCellAsModified(this.map[e][this.idx.col]), this.removeColCell(this.map[e][this.idx.col]))
    }, remove: function (e) {
        if (this.rectify()) {
            switch (e) {
                case"row":
                    this.removeRow();
                    break;
                case"column":
                    this.removeColumn()
            }
            this.rectify()
        }
    }, addRow: function (e) {
        var t = this.table.ownerDocument;
        if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), "below" == e && r.getAttribute(this.cell, "rowspan") && (this.idx.row = this.idx.row + parseInt(r.getAttribute(this.cell, "rowspan"), 10) - 1), this.idx !== !1) {
            for (var n = this.map[this.idx.row], i = t.createElement("tr"), a = 0, s = n.length; s > a; a++)n[a].modified || (this.setCellAsModified(n[a]), this.addRowCell(n[a], i, e));
            switch (e) {
                case"below":
                    o(this.getRealRowEl(!0), i);
                    break;
                case"above":
                    var l = r.getParentElement(this.map[this.idx.row][this.idx.col].el, {nodeName: ["TR"]});
                    l && l.parentNode.insertBefore(i, l)
            }
        }
    }, addRowCell: function (e, t, n) {
        var o = e.isColspan ? {colspan: r.getAttribute(e.el, "colspan")} : null;
        e.isReal ? "above" != n && e.isRowspan ? e.el.setAttribute("rowspan", parseInt(r.getAttribute(e.el, "rowspan"), 10) + 1) : t.appendChild(this.createCells("td", 1, o)) : "above" != n && e.isRowspan && e.lastRow ? t.appendChild(this.createCells("td", 1, o)) : c.isRowspan && e.el.attr("rowspan", parseInt(r.getAttribute(e.el, "rowspan"), 10) + 1)
    }, add: function (e) {
        this.rectify() && (("below" == e || "above" == e) && this.addRow(e), ("before" == e || "after" == e) && this.addColumn(e))
    }, addColCell: function (e, t, n) {
        var i, a = e.el.tagName.toLowerCase();
        switch (n) {
            case"before":
                i = !e.isColspan || e.firstCol;
                break;
            case"after":
                i = !e.isColspan || e.lastCol || e.isColspan && c.el == this.cell
        }
        if (i) {
            switch (n) {
                case"before":
                    e.el.parentNode.insertBefore(this.createCells(a, 1), e.el);
                    break;
                case"after":
                    o(e.el, this.createCells(a, 1))
            }
            e.isRowspan && this.handleCellAddWithRowspan(e, t + 1, n)
        } else e.el.setAttribute("colspan", parseInt(r.getAttribute(e.el, "colspan"), 10) + 1)
    }, addColumn: function (e) {
        var t, n;
        if (this.setTableMap(), this.idx = this.getMapIndex(this.cell), "after" == e && r.getAttribute(this.cell, "colspan") && (this.idx.col = this.idx.col + parseInt(r.getAttribute(this.cell, "colspan"), 10) - 1), this.idx !== !1)for (var o = 0, i = this.map.length; i > o; o++)t = this.map[o], t[this.idx.col] && (n = t[this.idx.col], n.modified || (this.setCellAsModified(n), this.addColCell(n, o, e)))
    }, handleCellAddWithRowspan: function (e, t, n) {
        for (var a, s, l, c = parseInt(r.getAttribute(this.cell, "rowspan"), 10) - 1, u = r.getParentElement(e.el, {nodeName: ["TR"]}), d = e.el.tagName.toLowerCase(), h = this.table.ownerDocument, f = 0; c > f; f++)if (a = this.correctColIndexForUnreals(this.idx.col, t + f), u = i(u, "tr"))if (a > 0)switch (n) {
            case"before":
                s = this.getRowCells(u), a > 0 && this.map[t + f][this.idx.col].el != s[a] && a == s.length - 1 ? o(s[a], this.createCells(d, 1)) : s[a].parentNode.insertBefore(this.createCells(d, 1), s[a]);
                break;
            case"after":
                o(this.getRowCells(u)[a], this.createCells(d, 1))
        } else u.insertBefore(this.createCells(d, 1), u.firstChild); else l = h.createElement("tr"), l.appendChild(this.createCells(d, 1)), this.table.appendChild(l)
    }}, r.table = {getCellsBetween: function (e, t) {
        var n = new s(e);
        return n.getMapElsTo(t)
    }, addCells: function (e, t) {
        var n = new s(e);
        n.add(t)
    }, removeCells: function (e, t) {
        var n = new s(e);
        n.remove(t)
    }, mergeCellsBetween: function (e, t) {
        var n = new s(e);
        n.merge(t)
    }, unmergeCell: function (e) {
        var t = new s(e);
        t.unmerge()
    }, orderSelectionEnds: function (e, t) {
        var n = new s(e);
        return n.orderSelectionEnds(t)
    }, indexOf: function (e) {
        var t = new s(e);
        return t.setTableMap(), t.getMapIndex(e)
    }, findCell: function (e, t) {
        var n = new s(null, e);
        return n.getElementAtIndex(t)
    }, findRowByCell: function (e) {
        var t = new s(e);
        return t.getRowElementsByCell()
    }, findColumnByCell: function (e) {
        var t = new s(e);
        return t.getColumnElementsByCell()
    }, canMerge: function (e, t) {
        var n = new s(e);
        return n.canMerge(t)
    }}
}(wysihtml5), wysihtml5.dom.query = function (e, t) {
    var n, o = [];
    e.nodeType && (e = [e]);
    for (var i = 0, r = e.length; r > i; i++)if (n = e[i].querySelectorAll(t))for (var a = n.length; a--; o.unshift(n[a]));
    return o
}, wysihtml5.dom.compareDocumentPosition = function () {
    var e = document.documentElement;
    return e.compareDocumentPosition ? function (e, t) {
        return e.compareDocumentPosition(t)
    } : function (e, t) {
        var n, o;
        if (n = 9 === e.nodeType ? e : e.ownerDocument, o = 9 === t.nodeType ? t : t.ownerDocument, e === t)return 0;
        if (e === t.ownerDocument)return 20;
        if (e.ownerDocument === t)return 10;
        if (n !== o)return 1;
        if (2 === e.nodeType && e.childNodes && -1 !== wysihtml5.lang.array(e.childNodes).indexOf(t))return 20;
        if (2 === t.nodeType && t.childNodes && -1 !== wysihtml5.lang.array(t.childNodes).indexOf(e))return 10;
        for (var i = e, r = [], a = null; i;) {
            if (i == t)return 10;
            r.push(i), i = i.parentNode
        }
        for (i = t, a = null; i;) {
            if (i == e)return 20;
            var s = wysihtml5.lang.array(r).indexOf(i);
            if (-1 !== s) {
                var l = r[s], c = wysihtml5.lang.array(l.childNodes).indexOf(r[s - 1]), u = wysihtml5.lang.array(l.childNodes).indexOf(a);
                return c > u ? 2 : 4
            }
            a = i, i = i.parentNode
        }
        return 1
    }
}(), wysihtml5.dom.unwrap = function (e) {
    if (e.parentNode) {
        for (; e.lastChild;)wysihtml5.dom.insert(e.lastChild).after(e);
        e.parentNode.removeChild(e)
    }
}, wysihtml5.quirks.cleanPastedHTML = function () {
    function e(e, n, o) {
        n = n || t, o = o || e.ownerDocument || document;
        var i, r, a, s, l, c, u = "string" == typeof e, d = 0;
        i = u ? wysihtml5.dom.getAsDom(e, o) : e;
        for (l in n)for (a = i.querySelectorAll(l), r = n[l], s = a.length; s > d; d++)r(a[d]);
        var h = wysihtml5.dom.getTextNodes(i);
        for (c = h.length; c--;)h[c].nodeValue = h[c].nodeValue.replace(/([\S\u00A0])\u00A0/gi, "$1 ");
        return a = e = n = null, u ? i.innerHTML : i
    }

    var t = {"a u": wysihtml5.dom.replaceWithChildNodes};
    return e
}(), wysihtml5.quirks.ensureProperClearing = function () {
    var e = function () {
        var e = this;
        setTimeout(function () {
            var t = e.innerHTML.toLowerCase();
            ("<p>&nbsp;</p>" == t || "<p>&nbsp;</p><p>&nbsp;</p>" == t) && (e.innerHTML = "")
        }, 0)
    };
    return function (t) {
        wysihtml5.dom.observe(t.element, ["cut", "keydown"], e)
    }
}(), function (e) {
    var t = "%7E";
    e.quirks.getCorrectInnerHTML = function (n) {
        var o = n.innerHTML;
        if (-1 === o.indexOf(t))return o;
        var i, r, a, s, l = n.querySelectorAll("[href*='~'], [src*='~']");
        for (s = 0, a = l.length; a > s; s++)i = l[s].href || l[s].src, r = e.lang.string(i).replace("~").by(t), o = e.lang.string(o).replace(r).by(i);
        return o
    }
}(wysihtml5), function (e) {
    var t = "wysihtml5-quirks-redraw";
    e.quirks.redraw = function (n) {
        e.dom.addClass(n, t), e.dom.removeClass(n, t);
        try {
            var o = n.ownerDocument;
            o.execCommand("italic", !1, null), o.execCommand("italic", !1, null)
        } catch (i) {
        }
    }
}(wysihtml5), wysihtml5.quirks.tableCellsSelection = function (e, t) {
    function n() {
        return u.observe(e, "mousedown", function (e) {
            var t = wysihtml5.dom.getParentElement(e.target, {nodeName: ["TD", "TH"]});
            t && o(t)
        }), d
    }

    function o(n) {
        d.start = n, d.end = n, d.cells = [n], d.table = u.getParentElement(d.start, {nodeName: ["TABLE"]}), d.table && (i(), u.addClass(n, h), f = u.observe(e, "mousemove", a), p = u.observe(e, "mouseup", s), t.fire("tableselectstart").fire("tableselectstart:composer"))
    }

    function i() {
        if (e) {
            var t = e.querySelectorAll("." + h);
            if (t.length > 0)for (var n = 0; n < t.length; n++)u.removeClass(t[n], h)
        }
    }

    function r(e) {
        for (var t = 0; t < e.length; t++)u.addClass(e[t], h)
    }

    function a(e) {
        var n, o = null, a = u.getParentElement(e.target, {nodeName: ["TD", "TH"]});
        a && d.table && d.start && (o = u.getParentElement(a, {nodeName: ["TABLE"]}), o && o === d.table && (i(), n = d.end, d.end = a, d.cells = u.table.getCellsBetween(d.start, a), d.cells.length > 1 && t.composer.selection.deselect(), r(d.cells), d.end !== n && t.fire("tableselectchange").fire("tableselectchange:composer")))
    }

    function s() {
        f.stop(), p.stop(), t.fire("tableselect").fire("tableselect:composer"), setTimeout(function () {
            l()
        }, 0)
    }

    function l() {
        var n = u.observe(e.ownerDocument, "click", function (e) {
            n.stop(), u.getParentElement(e.target, {nodeName: ["TABLE"]}) != d.table && (i(), d.table = null, d.start = null, d.end = null, t.fire("tableunselect").fire("tableunselect:composer"))
        })
    }

    function c(e, n) {
        d.start = e, d.end = n, d.table = u.getParentElement(d.start, {nodeName: ["TABLE"]}), selectedCells = u.table.getCellsBetween(d.start, d.end), r(selectedCells), l(), t.fire("tableselect").fire("tableselect:composer")
    }

    var u = wysihtml5.dom, d = {table: null, start: null, end: null, cells: null, select: c}, h = "wysiwyg-tmp-selected-cell", f = null, p = null;
    return n()
}, function (e) {
    var t = /^rgba\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*([\d\.]+)\s*\)/i, n = /^rgb\(\s*(\d{1,3})\s*,\s*(\d{1,3})\s*,\s*(\d{1,3})\s*\)/i, o = /^#([0-9a-f][0-9a-f])([0-9a-f][0-9a-f])([0-9a-f][0-9a-f])/i, i = /^#([0-9a-f])([0-9a-f])([0-9a-f])/i, r = function (e) {
        return new RegExp("(^|\\s|;)" + e + "\\s*:\\s*[^;$]+", "gi")
    };
    e.quirks.styleParser = {parseColor: function (a, s) {
        var l, c, u = r(s), d = a.match(u), h = 10;
        if (d) {
            for (var f = d.length; f--;)d[f] = e.lang.string(d[f].split(":")[1]).trim();
            if (l = d[d.length - 1], t.test(l))c = l.match(t); else if (n.test(l))c = l.match(n); else if (o.test(l))c = l.match(o), h = 16; else if (i.test(l))return c = l.match(i), c.shift(), c.push(1), e.lang.array(c).map(function (e, t) {
                return 3 > t ? 16 * parseInt(e, 16) + parseInt(e, 16) : parseFloat(e)
            });
            if (c)return c.shift(), c[3] || c.push(1), e.lang.array(c).map(function (e, t) {
                return 3 > t ? parseInt(e, h) : parseFloat(e)
            })
        }
        return!1
    }, unparseColor: function (e, t) {
        if (t) {
            if ("hex" == t)return e[0].toString(16).toUpperCase() + e[1].toString(16).toUpperCase() + e[2].toString(16).toUpperCase();
            if ("hash" == t)return"#" + e[0].toString(16).toUpperCase() + e[1].toString(16).toUpperCase() + e[2].toString(16).toUpperCase();
            if ("rgb" == t)return"rgb(" + e[0] + "," + e[1] + "," + e[2] + ")";
            if ("rgba" == t)return"rgba(" + e[0] + "," + e[1] + "," + e[2] + "," + e[3] + ")";
            if ("csv" == t)return e[0] + "," + e[1] + "," + e[2] + "," + e[3]
        }
        return e[3] && 1 !== e[3] ? "rgba(" + e[0] + "," + e[1] + "," + e[2] + "," + e[3] + ")" : "rgb(" + e[0] + "," + e[1] + "," + e[2] + ")"
    }, parseFontSize: function (t) {
        var n = t.match(r("font-size"));
        return n ? e.lang.string(n[n.length - 1].split(":")[1]).trim() : !1
    }}
}(wysihtml5), function (e) {
    function t(e) {
        var t = 0;
        if (e.parentNode)do t += e.offsetTop || 0, e = e.offsetParent; while (e);
        return t
    }

    function n(e, t) {
        for (var n = 0; t !== e;)if (n++, t = t.parentNode, !t)throw new Error("not a descendant of ancestor!");
        return n
    }

    function o(e) {
        if (!e.canSurroundContents())for (var t = e.commonAncestorContainer, o = n(t, e.startContainer), i = n(t, e.endContainer); !e.canSurroundContents();)o > i ? (e.setStartBefore(e.startContainer), o = n(t, e.startContainer)) : (e.setEndAfter(e.endContainer), i = n(t, e.endContainer))
    }

    var i = e.dom;
    e.Selection = Base.extend({constructor: function (e, t, n) {
        window.rangy.init(), this.editor = e, this.composer = e.composer, this.doc = this.composer.doc, this.contain = t, this.unselectableClass = n || !1
    }, getBookmark: function () {
        var e = this.getRange();
        return e && o(e), e && e.cloneRange()
    }, setBookmark: function (e) {
        e && this.setSelection(e)
    }, setBefore: function (e) {
        var t = rangy.createRange(this.doc);
        return t.setStartBefore(e), t.setEndBefore(e), this.setSelection(t)
    }, setAfter: function (e) {
        var t = rangy.createRange(this.doc);
        return t.setStartAfter(e), t.setEndAfter(e), this.setSelection(t)
    }, selectNode: function (t, n) {
        var o = rangy.createRange(this.doc), r = t.nodeType === e.ELEMENT_NODE, a = "canHaveHTML"in t ? t.canHaveHTML : "IMG" !== t.nodeName, s = r ? t.innerHTML : t.data, l = "" === s || s === e.INVISIBLE_SPACE, c = i.getStyle("display").from(t), u = "block" === c || "list-item" === c;
        if (l && r && a && !n)try {
            t.innerHTML = e.INVISIBLE_SPACE
        } catch (d) {
        }
        a ? o.selectNodeContents(t) : o.selectNode(t), a && l && r ? o.collapse(u) : a && l && (o.setStartAfter(t), o.setEndAfter(t)), this.setSelection(o)
    }, getSelectedNode: function (e) {
        var t, n;
        return e && this.doc.selection && "Control" === this.doc.selection.type && (n = this.doc.selection.createRange(), n && n.length) ? n.item(0) : (t = this.getSelection(this.doc), t.focusNode === t.anchorNode ? t.focusNode : (n = this.getRange(this.doc), n ? n.commonAncestorContainer : this.doc.body))
    }, fixSelBorders: function () {
        var e = this.getRange();
        o(e), this.setSelection(e)
    }, getSelectedOwnNodes: function () {
        for (var e = this.getOwnRanges(), t = [], n = 0, o = e.length; o > n; n++)t.push(e[n].commonAncestorContainer || this.doc.body);
        return t
    }, findNodesInSelection: function (t) {
        for (var n, o = this.getOwnRanges(), i = [], r = 0, a = o.length; a > r; r++)n = o[r].getNodes([1], function (n) {
            return e.lang.array(t).contains(n.nodeName)
        }), i = i.concat(n);
        return i
    }, containsUneditable: function () {
        for (var e = this.getOwnUneditables(), t = this.getSelection(), n = 0, o = e.length; o > n; n++)if (t.containsNode(e[n]))return!0;
        return!1
    }, deleteContents: function () {
        for (var e = this.getOwnRanges(), t = e.length; t--;)e[t].deleteContents();
        this.setSelection(e[0])
    }, getPreviousNode: function (t, n) {
        if (!t) {
            var o = this.getSelection();
            t = o.anchorNode
        }
        if (t === this.contain)return!1;
        var i, r = t.previousSibling;
        return r === this.contain ? !1 : (r && 3 !== r.nodeType && 1 !== r.nodeType ? r = this.getPreviousNode(r, n) : r && 3 === r.nodeType && /^\s*$/.test(r.textContent) ? r = this.getPreviousNode(r, n) : n && r && 1 === r.nodeType && !e.lang.array(["BR", "HR", "IMG"]).contains(r.nodeName) && /^[\s]*$/.test(r.innerHTML) ? r = this.getPreviousNode(r, n) : r || t === this.contain || (i = t.parentNode, i !== this.contain && (r = this.getPreviousNode(i, n))), r !== this.contain ? r : !1)
    }, getSelectionParentsByTag: function () {
        for (var t, n = this.getSelectedOwnNodes(), o = [], i = 0, r = n.length; r > i; i++)t = n[i].nodeName && "LI" === n[i].nodeName ? n[i] : e.dom.getParentElement(n[i], {nodeName: ["LI"]}, !1, this.contain), t && o.push(t);
        return o.length ? o : null
    }, getRangeToNodeEnd: function () {
        if (this.isCollapsed()) {
            var e = this.getRange(), t = e.startContainer, n = e.startOffset, o = rangy.createRange(this.doc);
            return o.selectNodeContents(t), o.setStart(t, n), o
        }
    }, caretIsLastInSelection: function () {
        var e = (rangy.createRange(this.doc), this.getSelection(), this.getRangeToNodeEnd().cloneContents()), t = e.textContent;
        return/^\s*$/.test(t)
    }, caretIsFirstInSelection: function () {
        var t = rangy.createRange(this.doc), n = this.getSelection(), o = this.getRange(), i = o.startContainer;
        return i.nodeType === e.TEXT_NODE ? this.isCollapsed() && i.nodeType === e.TEXT_NODE && /^\s*$/.test(i.data.substr(0, o.startOffset)) : (t.selectNodeContents(this.getRange().commonAncestorContainer), t.collapse(!0), this.isCollapsed() && (t.startContainer === n.anchorNode || t.endContainer === n.anchorNode) && t.startOffset === n.anchorOffset)
    }, caretIsInTheBeginnig: function (t) {
        var n = this.getSelection(), o = n.anchorNode, i = n.anchorOffset;
        return t ? 0 === i && (o.nodeName && o.nodeName === t.toUpperCase() || e.dom.getParentElement(o.parentNode, {nodeName: t}, 1)) : 0 === i && !this.getPreviousNode(o, !0)
    }, caretIsBeforeUneditable: function () {
        var e = this.getSelection(), t = e.anchorNode, n = e.anchorOffset;
        if (0 === n) {
            var o = this.getPreviousNode(t, !0);
            if (o)for (var i = this.getOwnUneditables(), r = 0, a = i.length; a > r; r++)if (o === i[r])return i[r]
        }
        return!1
    }, executeAndRestoreRangy: function (e) {
        var t = this.doc.defaultView || this.doc.parentWindow, n = rangy.saveSelection(t);
        if (n)try {
            e()
        } catch (o) {
            setTimeout(function () {
                throw o
            }, 0)
        } else e();
        rangy.restoreSelection(n)
    }, executeAndRestore: function (t, n) {
        var o, r, a, s, l, c, u, d, h = this.doc.body, f = n && h.scrollTop, p = n && h.scrollLeft, m = "_wysihtml5-temp-placeholder", g = '<span class="' + m + '">' + e.INVISIBLE_SPACE + "</span>", y = this.getRange(!0);
        if (!y)return void t(h, h);
        y.collapsed || (u = y.cloneRange(), c = u.createContextualFragment(g), u.collapse(!1), u.insertNode(c), u.detach()), l = y.createContextualFragment(g), y.insertNode(l), c && (o = this.contain.querySelectorAll("." + m), y.setStartBefore(o[0]), y.setEndAfter(o[o.length - 1])), this.setSelection(y);
        try {
            t(y.startContainer, y.endContainer)
        } catch (v) {
            setTimeout(function () {
                throw v
            }, 0)
        }
        if (o = this.contain.querySelectorAll("." + m), o && o.length) {
            d = rangy.createRange(this.doc), a = o[0].nextSibling, o.length > 1 && (s = o[o.length - 1].previousSibling), s && a ? (d.setStartBefore(a), d.setEndAfter(s)) : (r = this.doc.createTextNode(e.INVISIBLE_SPACE), i.insert(r).after(o[0]), d.setStartBefore(r), d.setEndAfter(r)), this.setSelection(d);
            for (var b = o.length; b--;)o[b].parentNode.removeChild(o[b])
        } else this.contain.focus();
        n && (h.scrollTop = f, h.scrollLeft = p);
        try {
            o.parentNode.removeChild(o)
        } catch (w) {
        }
    }, set: function (e, t) {
        var n = rangy.createRange(this.doc);
        n.setStart(e, t || 0), this.setSelection(n)
    }, insertHTML: function (e) {
        var t = rangy.createRange(this.doc), n = t.createContextualFragment(e), o = n.lastChild;
        this.insertNode(n), o && this.setAfter(o)
    }, insertNode: function (e) {
        var t = this.getRange();
        t && t.insertNode(e)
    }, surround: function (e) {
        var t, n = this.getOwnRanges(), o = [];
        if (0 == n.length)return o;
        for (var i = n.length; i--;) {
            t = this.doc.createElement(e.nodeName), o.push(t), e.className && (t.className = e.className), e.cssStyle && t.setAttribute("style", e.cssStyle);
            try {
                n[i].surroundContents(t), this.selectNode(t)
            } catch (r) {
                t.appendChild(n[i].extractContents()), n[i].insertNode(t)
            }
        }
        return o
    }, deblockAndSurround: function (t) {
        var n, o, i, r = this.doc.createElement("div"), a = rangy.createRange(this.doc);
        if (r.className = t.className, this.composer.commands.exec("formatBlock", t.nodeName, t.className), n = this.contain.querySelectorAll("." + t.className), n[0])for (n[0].parentNode.insertBefore(r, n[0]), a.setStartBefore(n[0]), a.setEndAfter(n[n.length - 1]), o = a.extractContents(); o.firstChild;)if (i = o.firstChild, 1 == i.nodeType && e.dom.hasClass(i, t.className)) {
            for (; i.firstChild;)r.appendChild(i.firstChild);
            "BR" !== i.nodeName && r.appendChild(this.doc.createElement("br")), o.removeChild(i)
        } else r.appendChild(i); else r = null;
        return r
    }, scrollIntoView: function () {
        var n, o = this.doc, i = 5, r = o.documentElement.scrollHeight > o.documentElement.offsetHeight, a = o._wysihtml5ScrollIntoViewElement = o._wysihtml5ScrollIntoViewElement || function () {
            var t = o.createElement("span");
            return t.innerHTML = e.INVISIBLE_SPACE, t
        }();
        r && (this.insertNode(a), n = t(a), a.parentNode.removeChild(a), n >= o.body.scrollTop + o.documentElement.offsetHeight - i && (o.body.scrollTop = n))
    }, selectLine: function () {
        e.browser.supportsSelectionModify() ? this._selectLine_W3C() : this.doc.selection && this._selectLine_MSIE()
    }, _selectLine_W3C: function () {
        var e = this.doc.defaultView, t = e.getSelection();
        t.modify("move", "left", "lineboundary"), t.modify("extend", "right", "lineboundary")
    }, _selectLine_MSIE: function () {
        var e, t, n, o, i, r = this.doc.selection.createRange(), a = r.boundingTop, s = this.doc.body.scrollWidth;
        if (r.moveToPoint) {
            for (0 === a && (n = this.doc.createElement("span"), this.insertNode(n), a = n.offsetTop, n.parentNode.removeChild(n)), a += 1, o = -10; s > o; o += 2)try {
                r.moveToPoint(o, a);
                break
            } catch (l) {
            }
            for (e = a, t = this.doc.selection.createRange(), i = s; i >= 0; i--)try {
                t.moveToPoint(i, e);
                break
            } catch (c) {
            }
            r.setEndPoint("EndToEnd", t), r.select()
        }
    }, getText: function () {
        var e = this.getSelection();
        return e ? e.toString() : ""
    }, getNodes: function (e, t) {
        var n = this.getRange();
        return n ? n.getNodes([e], t) : []
    }, fixRangeOverflow: function (e) {
        if (this.contain && this.contain.firstChild && e) {
            var t = e.compareNode(this.contain);
            if (2 !== t)1 === t && e.setStartBefore(this.contain.firstChild), 0 === t && e.setEndAfter(this.contain.lastChild), 3 === t && (e.setStartBefore(this.contain.firstChild), e.setEndAfter(this.contain.lastChild)); else if (this._detectInlineRangeProblems(e)) {
                var n = e.endContainer.previousElementSibling;
                n && e.setEnd(n, this._endOffsetForNode(n))
            }
        }
    }, _endOffsetForNode: function (e) {
        var t = document.createRange();
        return t.selectNodeContents(e), t.endOffset
    }, _detectInlineRangeProblems: function (e) {
        var t = i.compareDocumentPosition(e.startContainer, e.endContainer);
        return 0 == e.endOffset && 4 & t
    }, getRange: function (e) {
        var t = this.getSelection(), n = t && t.rangeCount && t.getRangeAt(0);
        return e !== !0 && this.fixRangeOverflow(n), n
    }, getOwnUneditables: function () {
        var t = i.query(this.contain, "." + this.unselectableClass), n = i.query(t, "." + this.unselectableClass);
        return e.lang.array(t).without(n)
    }, getOwnRanges: function () {
        var e, t = [], n = this.getRange();
        if (n && t.push(n), this.unselectableClass && this.contain && n) {
            var o, i = this.getOwnUneditables();
            if (i.length > 0)for (var r = 0, a = i.length; a > r; r++) {
                e = [];
                for (var s = 0, l = t.length; l > s; s++) {
                    if (t[s])switch (t[s].compareNode(i[r])) {
                        case 2:
                            break;
                        case 3:
                            o = t[s].cloneRange(), o.setEndBefore(i[r]), e.push(o), o = t[s].cloneRange(), o.setStartAfter(i[r]), e.push(o);
                            break;
                        default:
                            e.push(t[s])
                    }
                    t = e
                }
            }
        }
        return t
    }, getSelection: function () {
        return rangy.getSelection(this.doc.defaultView || this.doc.parentWindow)
    }, setSelection: function (e) {
        var t = this.doc.defaultView || this.doc.parentWindow, n = rangy.getSelection(t);
        return n.setSingleRange(e)
    }, createRange: function () {
        return rangy.createRange(this.doc)
    }, isCollapsed: function () {
        return this.getSelection().isCollapsed
    }, isEndToEndInNode: function (t) {
        var n = this.getRange(), o = n.commonAncestorContainer, i = n.startContainer, r = n.endContainer;
        if (o.nodeType === e.TEXT_NODE && (o = o.parentNode), i.nodeType === e.TEXT_NODE && !/^\s*$/.test(i.data.substr(n.startOffset)))return!1;
        if (r.nodeType === e.TEXT_NODE && !/^\s*$/.test(r.data.substr(n.endOffset)))return!1;
        for (; i && i !== o;) {
            if (i.nodeType !== e.TEXT_NODE && !e.dom.contains(o, i))return!1;
            if (e.dom.domNode(i).prev({ignoreBlankTexts: !0}))return!1;
            i = i.parentNode
        }
        for (; r && r !== o;) {
            if (r.nodeType !== e.TEXT_NODE && !e.dom.contains(o, r))return!1;
            if (e.dom.domNode(r).next({ignoreBlankTexts: !0}))return!1;
            r = r.parentNode
        }
        return e.lang.array(t).contains(o.nodeName) ? o : !1
    }, deselect: function () {
        var e = this.getSelection();
        e && e.removeAllRanges()
    }})
}(wysihtml5), function (e, t) {
    function n(e, t, n) {
        if (!e.className)return!1;
        var o = e.className.match(n) || [];
        return o[o.length - 1] === t
    }

    function o(e, t) {
        return e.getAttribute && e.getAttribute("style") ? (e.getAttribute("style").match(t), e.getAttribute("style").match(t) ? !0 : !1) : !1
    }

    function i(e, t, n) {
        e.getAttribute("style") ? (s(e, n), e.getAttribute("style") && !/^\s*$/.test(e.getAttribute("style")) ? e.setAttribute("style", t + ";" + e.getAttribute("style")) : e.setAttribute("style", t)) : e.setAttribute("style", t)
    }

    function r(e, t, n) {
        e.className ? (a(e, n), e.className += " " + t) : e.className = t
    }

    function a(e, t) {
        e.className && (e.className = e.className.replace(t, ""))
    }

    function s(e, t) {
        var n, o = [];
        if (e.getAttribute("style")) {
            n = e.getAttribute("style").split(";");
            for (var i = n.length; i--;)n[i].match(t) || /^\s*$/.test(n[i]) || o.push(n[i]);
            o.length ? e.setAttribute("style", o.join(";")) : e.removeAttribute("style")
        }
    }

    function l(e, t) {
        var n = [], o = t.split(";"), i = e.getAttribute("style");
        if (i) {
            i = i.replace(/\s/gi, "").toLowerCase(), n.push(new RegExp("(^|\\s|;)" + t.replace(/\s/gi, "").replace(/([\(\)])/gi, "\\$1").toLowerCase().replace(";", ";?").replace(/rgb\\\((\d+),(\d+),(\d+)\\\)/gi, "\\s?rgb\\($1,\\s?$2,\\s?$3\\)"), "gi"));
            for (var r = o.length; r-- > 0;)/^\s*$/.test(o[r]) || n.push(new RegExp("(^|\\s|;)" + o[r].replace(/\s/gi, "").replace(/([\(\)])/gi, "\\$1").toLowerCase().replace(";", ";?").replace(/rgb\\\((\d+),(\d+),(\d+)\\\)/gi, "\\s?rgb\\($1,\\s?$2,\\s?$3\\)"), "gi"));
            for (var a = 0, s = n.length; s > a; a++)if (i.match(n[a]))return n[a]
        }
        return!1
    }

    function c(n, o, i, r) {
        return i ? l(n, i) : r ? e.dom.hasClass(n, r) : t.dom.arrayContains(o, n.tagName.toLowerCase())
    }

    function u(e, t, n, o) {
        for (var i = e.length; i--;)if (!c(e[i], t, n, o))return!1;
        return e.length ? !0 : !1
    }

    function d(e, t, n) {
        var o = l(e, t);
        return o ? (s(e, o), "remove") : (i(e, t, n), "change")
    }

    function h(e, t) {
        return e.className.replace(w, " ") == t.className.replace(w, " ")
    }

    function f(e) {
        for (var t = e.parentNode; e.firstChild;)t.insertBefore(e.firstChild, e);
        t.removeChild(e)
    }

    function p(e, t) {
        if (e.attributes.length != t.attributes.length)return!1;
        for (var n, o, i, r = 0, a = e.attributes.length; a > r; ++r)if (n = e.attributes[r], i = n.name, "class" != i) {
            if (o = t.attributes.getNamedItem(i), n.specified != o.specified)return!1;
            if (n.specified && n.nodeValue !== o.nodeValue)return!1
        }
        return!0
    }

    function m(e, n) {
        return t.dom.isCharacterDataNode(e) ? 0 == n ? !!e.previousSibling : n == e.length ? !!e.nextSibling : !0 : n > 0 && n < e.childNodes.length
    }

    function g(e, n, o, i) {
        var r;
        if (t.dom.isCharacterDataNode(n) && (0 == o ? (o = t.dom.getNodeIndex(n), n = n.parentNode) : o == n.length ? (o = t.dom.getNodeIndex(n) + 1, n = n.parentNode) : r = t.dom.splitDataNode(n, o)), !(r || i && n === i)) {
            r = n.cloneNode(!1), r.id && r.removeAttribute("id");
            for (var a; a = n.childNodes[o];)r.appendChild(a);
            t.dom.insertAfter(r, n)
        }
        return n == e ? r : g(e, r.parentNode, t.dom.getNodeIndex(r), i)
    }

    function y(t) {
        this.isElementMerge = t.nodeType == e.ELEMENT_NODE, this.firstTextNode = this.isElementMerge ? t.lastChild : t, this.textNodes = [this.firstTextNode]
    }

    function v(e, t, n, o, i, r, a) {
        this.tagNames = e || [b], this.cssClass = t || (t === !1 ? !1 : ""), this.similarClassRegExp = n, this.cssStyle = i || "", this.similarStyleRegExp = r, this.normalize = o, this.applyToAnyTagName = !1, this.container = a
    }

    var b = "span", w = /\s+/g;
    y.prototype = {doMerge: function () {
        for (var e, t, n, o = [], i = 0, r = this.textNodes.length; r > i; ++i)e = this.textNodes[i], t = e.parentNode, o[i] = e.data, i && (t.removeChild(e), t.hasChildNodes() || t.parentNode.removeChild(t));
        return this.firstTextNode.data = n = o.join(""), n
    }, getLength: function () {
        for (var e = this.textNodes.length, t = 0; e--;)t += this.textNodes[e].length;
        return t
    }, toString: function () {
        for (var e = [], t = 0, n = this.textNodes.length; n > t; ++t)e[t] = "'" + this.textNodes[t].data + "'";
        return"[Merge(" + e.join(",") + ")]"
    }}, v.prototype = {getAncestorWithClass: function (o) {
        for (var i; o;) {
            if (i = this.cssClass ? n(o, this.cssClass, this.similarClassRegExp) : "" !== this.cssStyle ? !1 : !0, o.nodeType == e.ELEMENT_NODE && "false" != o.getAttribute("contenteditable") && t.dom.arrayContains(this.tagNames, o.tagName.toLowerCase()) && i)return o;
            o = o.parentNode
        }
        return!1
    }, getAncestorWithStyle: function (n) {
        for (var i; n;) {
            if (i = this.cssStyle ? o(n, this.similarStyleRegExp) : !1, n.nodeType == e.ELEMENT_NODE && "false" != n.getAttribute("contenteditable") && t.dom.arrayContains(this.tagNames, n.tagName.toLowerCase()) && i)return n;
            n = n.parentNode
        }
        return!1
    }, getMatchingAncestor: function (e) {
        var t = this.getAncestorWithClass(e), n = !1;
        return t ? this.cssStyle && (n = "class") : (t = this.getAncestorWithStyle(e), t && (n = "style")), {element: t, type: n}
    }, postApply: function (e, t) {
        for (var n, o, i, r = e[0], a = e[e.length - 1], s = [], l = r, c = a, u = 0, d = a.length, h = 0, f = e.length; f > h; ++h)o = e[h], i = null, o && o.parentNode && (i = this.getAdjacentMergeableTextNode(o.parentNode, !1)), i ? (n || (n = new y(i), s.push(n)), n.textNodes.push(o), o === r && (l = n.firstTextNode, u = l.length), o === a && (c = n.firstTextNode, d = n.getLength())) : n = null;
        if (a && a.parentNode) {
            var p = this.getAdjacentMergeableTextNode(a.parentNode, !0);
            p && (n || (n = new y(a), s.push(n)), n.textNodes.push(p))
        }
        if (s.length) {
            for (h = 0, f = s.length; f > h; ++h)s[h].doMerge();
            t.setStart(l, u), t.setEnd(c, d)
        }
    }, getAdjacentMergeableTextNode: function (t, n) {
        var o, i = t.nodeType == e.TEXT_NODE, r = i ? t.parentNode : t, a = n ? "nextSibling" : "previousSibling";
        if (i) {
            if (o = t[a], o && o.nodeType == e.TEXT_NODE)return o
        } else if (o = r[a], o && this.areElementsMergeable(t, o))return o[n ? "firstChild" : "lastChild"];
        return null
    }, areElementsMergeable: function (e, n) {
        return t.dom.arrayContains(this.tagNames, (e.tagName || "").toLowerCase()) && t.dom.arrayContains(this.tagNames, (n.tagName || "").toLowerCase()) && h(e, n) && p(e, n)
    }, createContainer: function (e) {
        var t = e.createElement(this.tagNames[0]);
        return this.cssClass && (t.className = this.cssClass), this.cssStyle && t.setAttribute("style", this.cssStyle), t
    }, applyToTextNode: function (e) {
        var n = e.parentNode;
        if (1 == n.childNodes.length && t.dom.arrayContains(this.tagNames, n.tagName.toLowerCase()))this.cssClass && r(n, this.cssClass, this.similarClassRegExp), this.cssStyle && i(n, this.cssStyle, this.similarStyleRegExp); else {
            var o = this.createContainer(t.dom.getDocument(e));
            e.parentNode.insertBefore(o, e), o.appendChild(e)
        }
    }, isRemovable: function (n) {
        return t.dom.arrayContains(this.tagNames, n.tagName.toLowerCase()) && "" === e.lang.string(n.className).trim() && (!n.getAttribute("style") || "" === e.lang.string(n.getAttribute("style")).trim())
    }, undoToTextNode: function (e, t, n, o) {
        var i = n ? !1 : !0, r = n || o, s = !1;
        if (!t.containsNode(r)) {
            var l = t.cloneRange();
            l.selectNode(r), l.isPointInRange(t.endContainer, t.endOffset) && m(t.endContainer, t.endOffset) && (g(r, t.endContainer, t.endOffset, this.container), t.setEndAfter(r)), l.isPointInRange(t.startContainer, t.startOffset) && m(t.startContainer, t.startOffset) && (r = g(r, t.startContainer, t.startOffset, this.container))
        }
        !i && this.similarClassRegExp && a(r, this.similarClassRegExp), i && this.similarStyleRegExp && (s = "change" === d(r, this.cssStyle, this.similarStyleRegExp)), this.isRemovable(r) && !s && f(r)
    }, applyToRange: function (t) {
        for (var n, o = t.length; o--;) {
            if (n = t[o].getNodes([e.TEXT_NODE]), !n.length)try {
                var i = this.createContainer(t[o].endContainer.ownerDocument);
                return t[o].surroundContents(i), void this.selectNode(t[o], i)
            } catch (r) {
            }
            if (t[o].splitBoundaries(), n = t[o].getNodes([e.TEXT_NODE]), n.length) {
                for (var a, s = 0, l = n.length; l > s; ++s)a = n[s], this.getMatchingAncestor(a).element || this.applyToTextNode(a);
                t[o].setStart(n[0], 0), a = n[n.length - 1], t[o].setEnd(a, a.length), this.normalize && this.postApply(n, t[o])
            }
        }
    }, undoToRange: function (t) {
        for (var n, o, i, r = t.length; r--;) {
            if (n = t[r].getNodes([e.TEXT_NODE]), n.length)t[r].splitBoundaries(), n = t[r].getNodes([e.TEXT_NODE]); else {
                var a = t[r].endContainer.ownerDocument, s = a.createTextNode(e.INVISIBLE_SPACE);
                t[r].insertNode(s), t[r].selectNode(s), n = [s]
            }
            for (var l = 0, c = n.length; c > l; ++l)t[r].isValid() && (o = n[l], i = this.getMatchingAncestor(o), "style" === i.type ? this.undoToTextNode(o, t[r], !1, i.element) : i.element && this.undoToTextNode(o, t[r], i.element));
            1 == c ? this.selectNode(t[r], n[0]) : (t[r].setStart(n[0], 0), o = n[n.length - 1], t[r].setEnd(o, o.length), this.normalize && this.postApply(n, t[r]))
        }
    }, selectNode: function (t, n) {
        var o = n.nodeType === e.ELEMENT_NODE, i = "canHaveHTML"in n ? n.canHaveHTML : !0, r = o ? n.innerHTML : n.data, a = "" === r || r === e.INVISIBLE_SPACE;
        if (a && o && i)try {
            n.innerHTML = e.INVISIBLE_SPACE
        } catch (s) {
        }
        t.selectNodeContents(n), a && o ? t.collapse(!1) : a && (t.setStartAfter(n), t.setEndAfter(n))
    }, getTextSelectedByRange: function (e, t) {
        var n = t.cloneRange();
        n.selectNodeContents(e);
        var o = n.intersection(t), i = o ? o.toString() : "";
        return n.detach(), i
    }, isAppliedToRange: function (t) {
        for (var n, o, i = [], r = "full", a = t.length; a--;) {
            if (o = t[a].getNodes([e.TEXT_NODE]), !o.length)return n = this.getMatchingAncestor(t[a].startContainer).element, n ? {elements: [n], coverage: r} : !1;
            for (var s, l = 0, c = o.length; c > l; ++l)s = this.getTextSelectedByRange(o[l], t[a]), n = this.getMatchingAncestor(o[l]).element, n && "" != s ? (i.push(n), 1 === e.dom.getTextNodes(n, !0).length ? r = "full" : "full" === r && (r = "inline")) : n || (r = "partial")
        }
        return i.length ? {elements: i, coverage: r} : !1
    }, toggleRange: function (e) {
        var t, n = this.isAppliedToRange(e);
        n ? "full" === n.coverage ? this.undoToRange(e) : "inline" === n.coverage ? (t = u(n.elements, this.tagNames, this.cssStyle, this.cssClass), this.undoToRange(e), t || this.applyToRange(e)) : (u(n.elements, this.tagNames, this.cssStyle, this.cssClass) || this.undoToRange(e), this.applyToRange(e)) : this.applyToRange(e)
    }}, e.selection.HTMLApplier = v
}(wysihtml5, rangy), wysihtml5.Commands = Base.extend({constructor: function (e) {
    this.editor = e, this.composer = e.composer, this.doc = this.composer.doc
}, support: function (e) {
    return wysihtml5.browser.supportsCommand(this.doc, e)
}, exec: function (e, t) {
    var n = wysihtml5.commands[e], o = wysihtml5.lang.array(arguments).get(), i = n && n.exec, r = null;
    if (this.editor.fire("beforecommand:composer"), i)o.unshift(this.composer), r = i.apply(n, o); else try {
        r = this.doc.execCommand(e, !1, t)
    } catch (a) {
    }
    return this.editor.fire("aftercommand:composer"), r
}, state: function (e) {
    var t = wysihtml5.commands[e], n = wysihtml5.lang.array(arguments).get(), o = t && t.state;
    if (o)return n.unshift(this.composer), o.apply(t, n);
    try {
        return this.doc.queryCommandState(e)
    } catch (i) {
        return!1
    }
}, stateValue: function (e) {
    var t = wysihtml5.commands[e], n = wysihtml5.lang.array(arguments).get(), o = t && t.stateValue;
    return o ? (n.unshift(this.composer), o.apply(t, n)) : !1
}}), wysihtml5.commands.bold = {exec: function (e, t) {
    wysihtml5.commands.formatInline.execWithToggle(e, t, "b")
}, state: function (e, t) {
    return wysihtml5.commands.formatInline.state(e, t, "b")
}}, function (e) {
    function t(t, n) {
        var a, s, l, c, u, d, h, f, p, m = t.doc, g = "_wysihtml5-temp-" + +new Date, y = /non-matching-class/g, v = 0;
        for (e.commands.formatInline.exec(t, o, i, g, y, o, o, !0, !0), s = m.querySelectorAll(i + "." + g), a = s.length; a > v; v++) {
            l = s[v], l.removeAttribute("class");
            for (p in n)"text" !== p && l.setAttribute(p, n[p])
        }
        d = l, 1 === a && (h = r.getTextContent(l), c = !!l.querySelector("*"), u = "" === h || h === e.INVISIBLE_SPACE, !c && u && (r.setTextContent(l, n.text || l.href), f = m.createTextNode(" "), t.selection.setAfter(l), r.insert(f).after(l), d = f)), t.selection.setAfter(d)
    }

    function n(e, t, n) {
        for (var o, i = t.length; i--;) {
            o = t[i].attributes;
            for (var r = o.length; r--;)t[i].removeAttribute(o.item(r).name);
            for (var a in n)n.hasOwnProperty(a) && t[i].setAttribute(a, n[a])
        }
    }

    var o, i = "A", r = e.dom;
    e.commands.createLink = {exec: function (e, o, i) {
        var r = this.state(e, o);
        r ? e.selection.executeAndRestore(function () {
            n(e, r, i)
        }) : (i = "object" == typeof i ? i : {href: i}, t(e, i))
    }, state: function (t, n) {
        return e.commands.formatInline.state(t, n, "A")
    }}
}(wysihtml5), function (e) {
    function t(e, t) {
        for (var o, i, r, a = t.length, s = 0; a > s; s++)o = t[s], i = n.getParentElement(o, {nodeName: "code"}), r = n.getTextContent(o), r.match(n.autoLink.URL_REG_EXP) && !i ? i = n.renameElement(o, "code") : n.replaceWithChildNodes(o)
    }

    var n = e.dom;
    e.commands.removeLink = {exec: function (e, n) {
        var o = this.state(e, n);
        o && e.selection.executeAndRestore(function () {
            t(e, o)
        })
    }, state: function (t, n) {
        return e.commands.formatInline.state(t, n, "A")
    }}
}(wysihtml5), function (e) {
    var t = /wysiwyg-font-size-[0-9a-z\-]+/g;
    e.commands.fontSize = {exec: function (n, o, i) {
        e.commands.formatInline.execWithToggle(n, o, "span", "wysiwyg-font-size-" + i, t)
    }, state: function (n, o, i) {
        return e.commands.formatInline.state(n, o, "span", "wysiwyg-font-size-" + i, t)
    }}
}(wysihtml5), function (e) {
    var t = /(\s|^)font-size\s*:\s*[^;\s]+;?/gi;
    e.commands.fontSizeStyle = {exec: function (n, o, i) {
        i = "object" == typeof i ? i.size : i, /^\s*$/.test(i) || e.commands.formatInline.execWithToggle(n, o, "span", !1, !1, "font-size:" + i, t)
    }, state: function (n, o) {
        return e.commands.formatInline.state(n, o, "span", !1, !1, "font-size", t)
    }, stateValue: function (t, n) {
        var o, i = this.state(t, n);
        return i && e.lang.object(i).isArray() && (i = i[0]), i && (o = i.getAttribute("style")) ? e.quirks.styleParser.parseFontSize(o) : !1
    }}
}(wysihtml5), function (e) {
    var t = /wysiwyg-color-[0-9a-z]+/g;
    e.commands.foreColor = {exec: function (n, o, i) {
        e.commands.formatInline.execWithToggle(n, o, "span", "wysiwyg-color-" + i, t)
    }, state: function (n, o, i) {
        return e.commands.formatInline.state(n, o, "span", "wysiwyg-color-" + i, t)
    }}
}(wysihtml5), function (e) {
    var t = /(\s|^)color\s*:\s*[^;\s]+;?/gi;
    e.commands.foreColorStyle = {exec: function (n, o, i) {
        var r, a = e.quirks.styleParser.parseColor("object" == typeof i ? "color:" + i.color : "color:" + i, "color");
        a && (r = "color: rgb(" + a[0] + "," + a[1] + "," + a[2] + ");", 1 !== a[3] && (r += "color: rgba(" + a[0] + "," + a[1] + "," + a[2] + "," + a[3] + ");"), e.commands.formatInline.execWithToggle(n, o, "span", !1, !1, r, t))
    }, state: function (n, o) {
        return e.commands.formatInline.state(n, o, "span", !1, !1, "color", t)
    }, stateValue: function (t, n, o) {
        var i, r = this.state(t, n);
        return r && e.lang.object(r).isArray() && (r = r[0]), r && (i = r.getAttribute("style"), i && i) ? (val = e.quirks.styleParser.parseColor(i, "color"), e.quirks.styleParser.unparseColor(val, o)) : !1
    }}
}(wysihtml5), function (e) {
    var t = /(\s|^)background-color\s*:\s*[^;\s]+;?/gi;
    e.commands.bgColorStyle = {exec: function (n, o, i) {
        var r, a = e.quirks.styleParser.parseColor("object" == typeof i ? "background-color:" + i.color : "background-color:" + i, "background-color");
        a && (r = "background-color: rgb(" + a[0] + "," + a[1] + "," + a[2] + ");", 1 !== a[3] && (r += "background-color: rgba(" + a[0] + "," + a[1] + "," + a[2] + "," + a[3] + ");"), e.commands.formatInline.execWithToggle(n, o, "span", !1, !1, r, t))
    }, state: function (n, o) {
        return e.commands.formatInline.state(n, o, "span", !1, !1, "background-color", t)
    }, stateValue: function (t, n, o) {
        var i, r = this.state(t, n), a = !1;
        return r && e.lang.object(r).isArray() && (r = r[0]), r && (i = r.getAttribute("style")) ? (a = e.quirks.styleParser.parseColor(i, "background-color"), e.quirks.styleParser.unparseColor(a, o)) : !1
    }}
}(wysihtml5), function (e) {
    function t(t, n, i) {
        t.className ? (o(t, i), t.className = e.lang.string(t.className + " " + n).trim()) : t.className = n
    }

    function n(t, n, o) {
        i(t, o), t.getAttribute("style") ? t.setAttribute("style", e.lang.string(t.getAttribute("style") + " " + n).trim()) : t.setAttribute("style", n)
    }

    function o(t, n) {
        var o = n.test(t.className);
        return t.className = t.className.replace(n, ""), "" == e.lang.string(t.className).trim() && t.removeAttribute("class"), o
    }

    function i(t, n) {
        var o = n.test(t.getAttribute("style"));
        return t.setAttribute("style", (t.getAttribute("style") || "").replace(n, "")), "" == e.lang.string(t.getAttribute("style") || "").trim() && t.removeAttribute("style"), o
    }

    function r(e) {
        var t = e.lastChild;
        t && a(t) && t.parentNode.removeChild(t)
    }

    function a(e) {
        return"BR" === e.nodeName
    }

    function s(t, n) {
        t.selection.isCollapsed() && t.selection.selectLine();
        for (var o = t.selection.surround(n), i = 0, a = o.length; a > i; i++)e.dom.lineBreaks(o[i]).remove(), r(o[i])
    }

    function l(t) {
        return!!e.lang.string(t.className).trim()
    }

    function c(t) {
        return!!e.lang.string(t.getAttribute("style") || "").trim()
    }

    var u = e.dom, d = ["H1", "H2", "H3", "H4", "H5", "H6", "P", "PRE", "DIV"];
    e.commands.formatBlock = {exec: function (r, a, h, f, p, m, g) {
        var y, v, b, w, C, x = (r.doc, this.state(r, a, h, f, p, m, g)), E = r.config.useLineBreaks, T = E ? "DIV" : "P";
        return h = "string" == typeof h ? h.toUpperCase() : h, x.length ? void r.selection.executeAndRestoreRangy(function () {
            for (var t = x.length; t--;) {
                if (p && (v = o(x[t], p)), g && (w = i(x[t], g)), (w || v) && null === h && x[t].nodeName != T)return;
                var n = l(x[t]), r = c(x[t]);
                n || r || !E && "P" !== h ? u.renameElement(x[t], "P" === h ? "DIV" : T) : (e.dom.lineBreaks(x[t]).add(), u.replaceWithChildNodes(x[t]))
            }
        }) : void((null !== h && !e.lang.array(d).contains(h) || (y = r.selection.findNodesInSelection(d).concat(r.selection.getSelectedOwnNodes()), r.selection.executeAndRestoreRangy(function () {
            for (var e = y.length; e--;)C = u.getParentElement(y[e], {nodeName: d}), C == r.element && (C = null), C && (h && (C = u.renameElement(C, h)), f && t(C, f, p), m && n(C, m, g), b = !0)
        }), !b)) && s(r, {nodeName: h || T, className: f || null, cssStyle: m || null}))
    }, state: function (t, n, o, i, r, a, s) {
        var l, c = t.selection.getSelectedOwnNodes(), d = [];
        o = "string" == typeof o ? o.toUpperCase() : o;
        for (var h = 0, f = c.length; f > h; h++)l = u.getParentElement(c[h], {nodeName: o, className: i, classRegExp: r, cssStyle: a, styleRegExp: s}), l && -1 == e.lang.array(d).indexOf(l) && d.push(l);
        return 0 == d.length ? !1 : d
    }}
}(wysihtml5), wysihtml5.commands.formatCode = {exec: function (e, t, n) {
    var o, i, r, a = this.state(e);
    a ? e.selection.executeAndRestore(function () {
        o = a.querySelector("code"), wysihtml5.dom.replaceWithChildNodes(a), o && wysihtml5.dom.replaceWithChildNodes(o)
    }) : (i = e.selection.getRange(), r = i.extractContents(), a = e.doc.createElement("pre"), o = e.doc.createElement("code"), n && (o.className = n), a.appendChild(o), o.appendChild(r), i.insertNode(a), e.selection.selectNode(a))
}, state: function (e) {
    var t = e.selection.getSelectedNode();
    return t && t.nodeName && "PRE" == t.nodeName && t.firstChild && t.firstChild.nodeName && "CODE" == t.firstChild.nodeName ? t : wysihtml5.dom.getParentElement(t, {nodeName: "CODE"}) && wysihtml5.dom.getParentElement(t, {nodeName: "PRE"})
}}, function (e) {
    function t(e) {
        var t = o[e];
        return t ? [e.toLowerCase(), t.toLowerCase()] : [e.toLowerCase()]
    }

    function n(n, o, r, a, s, l) {
        var c = n;
        return o && (c += ":" + o), a && (c += ":" + a), i[c] || (i[c] = new e.selection.HTMLApplier(t(n), o, r, !0, a, s, l)), i[c]
    }

    var o = {strong: "b", em: "i", b: "strong", i: "em"}, i = {};
    e.commands.formatInline = {exec: function (e, t, o, i, r, a, s, l, c) {
        var u = e.selection.createRange(), d = e.selection.getOwnRanges();
        return d && 0 != d.length ? (e.selection.getSelection().removeAllRanges(), n(o, i, r, a, s, e.element).toggleRange(d), void(l ? c || e.cleanUp() : (u.setStart(d[0].startContainer, d[0].startOffset), u.setEnd(d[d.length - 1].endContainer, d[d.length - 1].endOffset), e.selection.setSelection(u), e.selection.executeAndRestore(function () {
            c || e.cleanUp()
        }, !0, !0)))) : !1
    }, execWithToggle: function (t, n, o, i, r, a, s) {
        var l = this;
        if (this.state(t, n, o, i, r, a, s) && t.selection.isCollapsed() && !t.selection.caretIsLastInSelection() && !t.selection.caretIsFirstInSelection()) {
            var c = l.state(t, n, o, i, r)[0];
            t.selection.executeAndRestoreRangy(function () {
                c.parentNode, t.selection.selectNode(c, !0), e.commands.formatInline.exec(t, n, o, i, r, a, s, !0, !0)
            })
        } else this.state(t, n, o, i, r, a, s) && !t.selection.isCollapsed() ? t.selection.executeAndRestoreRangy(function () {
            e.commands.formatInline.exec(t, n, o, i, r, a, s, !0, !0)
        }) : e.commands.formatInline.exec(t, n, o, i, r, a, s)
    }, state: function (t, i, r, a, s, l, c) {
        var u, d, h = t.doc, f = o[r] || r;
        return e.dom.hasElementWithTagName(h, r) || e.dom.hasElementWithTagName(h, f) ? a && !e.dom.hasElementWithClassName(h, a) ? !1 : (u = t.selection.getOwnRanges(), u && 0 !== u.length ? (d = n(r, a, s, l, c, t.element).isAppliedToRange(u), d && d.elements ? d.elements : !1) : !1) : !1
    }}
}(wysihtml5), function (e) {
    e.commands.insertBlockQuote = {exec: function (t, n) {
        var o = this.state(t, n), i = t.selection.isEndToEndInNode(["H1", "H2", "H3", "H4", "H5", "H6", "P"]);
        t.selection.executeAndRestore(function () {
            if (o)t.config.useLineBreaks && e.dom.lineBreaks(o).add(), e.dom.unwrap(o); else if (t.selection.isCollapsed() && t.selection.selectLine(), i) {
                var n = i.ownerDocument.createElement("blockquote");
                e.dom.insert(n).after(i), n.appendChild(i)
            } else t.selection.surround({nodeName: "blockquote"})
        })
    }, state: function (t) {
        var n = t.selection.getSelectedNode(), o = e.dom.getParentElement(n, {nodeName: "BLOCKQUOTE"}, !1, t.element);
        return o ? o : !1
    }}
}(wysihtml5), wysihtml5.commands.insertHTML = {exec: function (e, t, n) {
    e.commands.support(t) ? e.doc.execCommand(t, !1, n) : e.selection.insertHTML(n)
}, state: function () {
    return!1
}}, function (e) {
    var t = "IMG";
    e.commands.insertImage = {exec: function (n, o, i) {
        i = "object" == typeof i ? i : {src: i};
        var r, a, s = n.doc, l = this.state(n);
        if (l)return n.selection.setBefore(l), a = l.parentNode, a.removeChild(l), e.dom.removeEmptyTextNodes(a), "A" !== a.nodeName || a.firstChild || (n.selection.setAfter(a), a.parentNode.removeChild(a)), void e.quirks.redraw(n.element);
        l = s.createElement(t);
        for (var c in i)l.setAttribute("className" === c ? "class" : c, i[c]);
        n.selection.insertNode(l), e.browser.hasProblemsSettingCaretAfterImg() ? (r = s.createTextNode(e.INVISIBLE_SPACE), n.selection.insertNode(r), n.selection.setAfter(r)) : n.selection.setAfter(l)
    }, state: function (n) {
        var o, i, r, a = n.doc;
        return e.dom.hasElementWithTagName(a, t) && (o = n.selection.getSelectedNode()) ? o.nodeName === t ? o : o.nodeType !== e.ELEMENT_NODE ? !1 : (i = n.selection.getText(), (i = e.lang.string(i).trim()) ? !1 : (r = n.selection.getNodes(e.ELEMENT_NODE, function (e) {
            return"IMG" === e.nodeName
        }), 1 !== r.length ? !1 : r[0])) : !1
    }}
}(wysihtml5), function (e) {
    var t = "<br>" + (e.browser.needsSpaceAfterLineBreak() ? " " : "");
    e.commands.insertLineBreak = {exec: function (n, o) {
        n.commands.support(o) ? (n.doc.execCommand(o, !1, null), e.browser.autoScrollsToCaret() || n.selection.scrollIntoView()) : n.commands.exec("insertHTML", t)
    }, state: function () {
        return!1
    }}
}(wysihtml5), wysihtml5.commands.insertOrderedList = {exec: function (e, t) {
    wysihtml5.commands.insertList.exec(e, t, "OL")
}, state: function (e, t) {
    return wysihtml5.commands.insertList.state(e, t, "OL")
}}, wysihtml5.commands.insertUnorderedList = {exec: function (e, t) {
    wysihtml5.commands.insertList.exec(e, t, "UL")
}, state: function (e, t) {
    return wysihtml5.commands.insertList.state(e, t, "UL")
}}, wysihtml5.commands.insertList = function (e) {
    var t = function (e, t) {
        if (e && e.nodeName) {
            "string" == typeof t && (t = [t]);
            for (var n = t.length; n--;)if (e.nodeName === t[n])return!0
        }
        return!1
    }, n = function (n, o, i) {
        var r = {el: null, other: !1};
        if (n) {
            var a = e.dom.getParentElement(n, {nodeName: "LI"}), s = "UL" === o ? "OL" : "UL";
            t(n, o) ? r.el = n : t(n, s) ? r = {el: n, other: !0} : a && (t(a.parentNode, o) ? r.el = a.parentNode : t(a.parentNode, s) && (r = {el: a.parentNode, other: !0}))
        }
        return r.el && !i.element.contains(r.el) && (r.el = null), r
    }, o = function (t, n, o) {
        var i, a = "UL" === n ? "OL" : "UL";
        o.selection.executeAndRestore(function () {
            var s = r(a, o);
            if (s.length)for (var l = s.length; l--;)e.dom.renameElement(s[l], n.toLowerCase()); else {
                i = r(["OL", "UL"], o);
                for (var c = i.length; c--;)e.dom.resolveList(i[c], o.config.useLineBreaks);
                e.dom.resolveList(t, o.config.useLineBreaks)
            }
        })
    }, i = function (t, n, o) {
        var i = "UL" === n ? "OL" : "UL";
        o.selection.executeAndRestore(function () {
            for (var a = [t].concat(r(i, o)), s = a.length; s--;)e.dom.renameElement(a[s], n.toLowerCase())
        })
    }, r = function (e, n) {
        for (var o = n.selection.getOwnRanges(), i = [], r = o.length; r--;)i = i.concat(o[r].getNodes([1], function (n) {
            return t(n, e)
        }));
        return i
    }, a = function (t, n) {
        n.selection.executeAndRestoreRangy(function () {
            var o, i, r = "_wysihtml5-temp-" + (new Date).getTime(), a = n.selection.deblockAndSurround({nodeName: "div", className: r}), s = /\uFEFF/g;
            a.innerHTML = a.innerHTML.replace(s, ""), a && (o = e.lang.array(["", "<br>", e.INVISIBLE_SPACE]).contains(a.innerHTML), i = e.dom.convertToList(a, t.toLowerCase(), n.parent.config.uneditableContainerClassname), o && n.selection.selectNode(i.querySelector("li"), !0))
        })
    };
    return{exec: function (e, t, r) {
        var s = e.doc, l = "OL" === r ? "insertOrderedList" : "insertUnorderedList", c = e.selection.getSelectedNode(), u = n(c, r, e);
        u.el ? u.other ? i(u.el, r, e) : o(u.el, r, e) : e.commands.support(l) ? s.execCommand(l, !1, null) : a(r, e)
    }, state: function (e, t, o) {
        var i = e.selection.getSelectedNode(), r = n(i, o, e);
        return r.el && !r.other ? r.el : !1
    }}
}(wysihtml5), wysihtml5.commands.italic = {exec: function (e, t) {
    wysihtml5.commands.formatInline.execWithToggle(e, t, "i")
}, state: function (e, t) {
    return wysihtml5.commands.formatInline.state(e, t, "i")
}}, function (e) {
    var t = "wysiwyg-text-align-center", n = /wysiwyg-text-align-[0-9a-z]+/g;
    e.commands.justifyCenter = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "wysiwyg-text-align-left", n = /wysiwyg-text-align-[0-9a-z]+/g;
    e.commands.justifyLeft = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "wysiwyg-text-align-right", n = /wysiwyg-text-align-[0-9a-z]+/g;
    e.commands.justifyRight = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "wysiwyg-text-align-justify", n = /wysiwyg-text-align-[0-9a-z]+/g;
    e.commands.justifyFull = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "text-align: right;", n = /(\s|^)text-align\s*:\s*[^;\s]+;?/gi;
    e.commands.alignRightStyle = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, null, null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, null, null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "text-align: left;", n = /(\s|^)text-align\s*:\s*[^;\s]+;?/gi;
    e.commands.alignLeftStyle = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, null, null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, null, null, t, n)
    }}
}(wysihtml5), function (e) {
    var t = "text-align: center;", n = /(\s|^)text-align\s*:\s*[^;\s]+;?/gi;
    e.commands.alignCenterStyle = {exec: function (o) {
        return e.commands.formatBlock.exec(o, "formatBlock", null, null, null, t, n)
    }, state: function (o) {
        return e.commands.formatBlock.state(o, "formatBlock", null, null, null, t, n)
    }}
}(wysihtml5), wysihtml5.commands.redo = {exec: function (e) {
    return e.undoManager.redo()
}, state: function () {
    return!1
}}, wysihtml5.commands.underline = {exec: function (e, t) {
    wysihtml5.commands.formatInline.execWithToggle(e, t, "u")
}, state: function (e, t) {
    return wysihtml5.commands.formatInline.state(e, t, "u")
}}, wysihtml5.commands.undo = {exec: function (e) {
    return e.undoManager.undo()
}, state: function () {
    return!1
}}, wysihtml5.commands.createTable = {exec: function (e, t, n) {
    var o, i, r;
    if (n && n.cols && n.rows && parseInt(n.cols, 10) > 0 && parseInt(n.rows, 10) > 0) {
        for (r = n.tableStyle ? '<table style="' + n.tableStyle + '">' : "<table>", r += "<tbody>", i = 0; i < n.rows; i++) {
            for (r += "<tr>", o = 0; o < n.cols; o++)r += "<td>&nbsp;</td>";
            r += "</tr>"
        }
        r += "</tbody></table>", e.commands.exec("insertHTML", r)
    }
}, state: function () {
    return!1
}}, wysihtml5.commands.mergeTableCells = {exec: function (e, t) {
    e.tableSelection && e.tableSelection.start && e.tableSelection.end && (this.state(e, t) ? wysihtml5.dom.table.unmergeCell(e.tableSelection.start) : wysihtml5.dom.table.mergeCellsBetween(e.tableSelection.start, e.tableSelection.end))
}, state: function (e) {
    if (e.tableSelection) {
        var t = e.tableSelection.start, n = e.tableSelection.end;
        if (t && n && t == n && (wysihtml5.dom.getAttribute(t, "colspan") && parseInt(wysihtml5.dom.getAttribute(t, "colspan"), 10) > 1 || wysihtml5.dom.getAttribute(t, "rowspan") && parseInt(wysihtml5.dom.getAttribute(t, "rowspan"), 10) > 1))return[t]
    }
    return!1
}}, wysihtml5.commands.addTableCells = {exec: function (e, t, n) {
    if (e.tableSelection && e.tableSelection.start && e.tableSelection.end) {
        var o = wysihtml5.dom.table.orderSelectionEnds(e.tableSelection.start, e.tableSelection.end);
        "before" == n || "above" == n ? wysihtml5.dom.table.addCells(o.start, n) : ("after" == n || "below" == n) && wysihtml5.dom.table.addCells(o.end, n), setTimeout(function () {
            e.tableSelection.select(o.start, o.end)
        }, 0)
    }
}, state: function () {
    return!1
}}, wysihtml5.commands.deleteTableCells = {exec: function (e, t, n) {
    if (e.tableSelection && e.tableSelection.start && e.tableSelection.end) {
        var o, i = wysihtml5.dom.table.orderSelectionEnds(e.tableSelection.start, e.tableSelection.end), r = wysihtml5.dom.table.indexOf(i.start), a = e.tableSelection.table;
        wysihtml5.dom.table.removeCells(i.start, n), setTimeout(function () {
            o = wysihtml5.dom.table.findCell(a, r), o || ("row" == n && (o = wysihtml5.dom.table.findCell(a, {row: r.row - 1, col: r.col})), "column" == n && (o = wysihtml5.dom.table.findCell(a, {row: r.row, col: r.col - 1}))), o && e.tableSelection.select(o, o)
        }, 0)
    }
}, state: function () {
    return!1
}}, wysihtml5.commands.indentList = {exec: function (e) {
    var t = e.selection.getSelectionParentsByTag("LI");
    return t ? this.tryToPushLiLevel(t, e.selection) : !1
}, state: function () {
    return!1
}, tryToPushLiLevel: function (e, t) {
    var n, o, i, r, a, s = !1;
    return t.executeAndRestoreRangy(function () {
        for (var t = e.length; t--;)r = e[t], n = "OL" === r.parentNode.nodeName ? "OL" : "UL", o = r.ownerDocument.createElement(n), i = wysihtml5.dom.domNode(r).prev({nodeTypes: [wysihtml5.ELEMENT_NODE]}), a = i ? i.querySelector("ul, ol") : null, i && (a ? a.appendChild(r) : (o.appendChild(r), i.appendChild(o)), s = !0)
    }), s
}}, wysihtml5.commands.outdentList = {exec: function (e) {
    var t = e.selection.getSelectionParentsByTag("LI");
    return t ? this.tryToPullLiLevel(t, e) : !1
}, state: function () {
    return!1
}, tryToPullLiLevel: function (e, t) {
    var n, o, i, r, a, s = !1, l = this;
    return t.selection.executeAndRestoreRangy(function () {
        for (var c = e.length; c--;)if (r = e[c], r.parentNode && (n = r.parentNode, "OL" === n.tagName || "UL" === n.tagName)) {
            if (s = !0, o = wysihtml5.dom.getParentElement(n.parentNode, {nodeName: ["OL", "UL"]}, !1, t.element), i = wysihtml5.dom.getParentElement(n.parentNode, {nodeName: ["LI"]}, !1, t.element), o && i)r.nextSibling && (a = l.getAfterList(n, r), r.appendChild(a)), o.insertBefore(r, i.nextSibling); else {
                r.nextSibling && (a = l.getAfterList(n, r), r.appendChild(a));
                for (var u = r.childNodes.length; u--;)n.parentNode.insertBefore(r.childNodes[u], n.nextSibling);
                n.parentNode.insertBefore(document.createElement("br"), n.nextSibling), r.parentNode.removeChild(r)
            }
            0 === n.childNodes.length && n.parentNode.removeChild(n)
        }
    }), s
}, getAfterList: function (e, t) {
    for (var n = e.nodeName, o = document.createElement(n); t.nextSibling;)o.appendChild(t.nextSibling);
    return o
}}, function (e) {
    var t = 90, n = 89, o = 8, i = 46, r = 25, a = "data-wysihtml5-selection-node", s = "data-wysihtml5-selection-offset", l = ('<span id="_wysihtml5-undo" class="_wysihtml5-temp">' + e.INVISIBLE_SPACE + "</span>", '<span id="_wysihtml5-redo" class="_wysihtml5-temp">' + e.INVISIBLE_SPACE + "</span>", e.dom);
    e.UndoManager = e.lang.Dispatcher.extend({constructor: function (e) {
        this.editor = e, this.composer = e.composer, this.element = this.composer.element, this.position = 0, this.historyStr = [], this.historyDom = [], this.transact(), this._observe()
    }, _observe: function () {
        var e, r = this;
        this.composer.sandbox.getDocument(), l.observe(this.element, "keydown", function (e) {
            if (!e.altKey && (e.ctrlKey || e.metaKey)) {
                var o = e.keyCode, i = o === t && !e.shiftKey, a = o === t && e.shiftKey || o === n;
                i ? (r.undo(), e.preventDefault()) : a && (r.redo(), e.preventDefault())
            }
        }), l.observe(this.element, "keydown", function (t) {
            var n = t.keyCode;
            n !== e && (e = n, (n === o || n === i) && r.transact())
        }), this.editor.on("newword:composer", function () {
            r.transact()
        }).on("beforecommand:composer", function () {
            r.transact()
        })
    }, transact: function () {
        var t, n, o, i, l, c = this.historyStr[this.position - 1], u = this.composer.getValue(!1, !1), d = this.element.offsetWidth > 0 && this.element.offsetHeight > 0;
        if (u !== c) {
            var h = this.historyStr.length = this.historyDom.length = this.position;
            h > r && (this.historyStr.shift(), this.historyDom.shift(), this.position--), this.position++, d && (t = this.composer.selection.getRange(), n = t && t.startContainer ? t.startContainer : this.element, o = t && t.startOffset ? t.startOffset : 0, n.nodeType === e.ELEMENT_NODE ? i = n : (i = n.parentNode, l = this.getChildNodeIndex(i, n)), i.setAttribute(s, o), "undefined" != typeof l && i.setAttribute(a, l));
            var f = this.element.cloneNode(!!u);
            this.historyDom.push(f), this.historyStr.push(u), i && (i.removeAttribute(s), i.removeAttribute(a))
        }
    }, undo: function () {
        this.transact(), this.undoPossible() && (this.set(this.historyDom[--this.position - 1]), this.editor.fire("undo:composer"))
    }, redo: function () {
        this.redoPossible() && (this.set(this.historyDom[++this.position - 1]), this.editor.fire("redo:composer"))
    }, undoPossible: function () {
        return this.position > 1
    }, redoPossible: function () {
        return this.position < this.historyStr.length
    }, set: function (e) {
        this.element.innerHTML = "";
        for (var t = 0, n = e.childNodes, o = e.childNodes.length; o > t; t++)this.element.appendChild(n[t].cloneNode(!0));
        var i, r, l;
        e.hasAttribute(s) ? (i = e.getAttribute(s), l = e.getAttribute(a), r = this.element) : (r = this.element.querySelector("[" + s + "]") || this.element, i = r.getAttribute(s), l = r.getAttribute(a), r.removeAttribute(s), r.removeAttribute(a)), null !== l && (r = this.getChildNodeByIndex(r, +l)), this.composer.selection.set(r, i)
    }, getChildNodeIndex: function (e, t) {
        for (var n = 0, o = e.childNodes, i = o.length; i > n; n++)if (o[n] === t)return n
    }, getChildNodeByIndex: function (e, t) {
        return e.childNodes[t]
    }})
}(wysihtml5), wysihtml5.views.View = Base.extend({constructor: function (e, t, n) {
    this.parent = e, this.element = t, this.config = n, this.config.noTextarea || this._observeViewChange()
}, _observeViewChange: function () {
    var e = this;
    this.parent.on("beforeload", function () {
        e.parent.on("change_view", function (t) {
            t === e.name ? (e.parent.currentView = e, e.show(), setTimeout(function () {
                e.focus()
            }, 0)) : e.hide()
        })
    })
}, focus: function () {
    if (this.element.ownerDocument.querySelector(":focus") !== this.element)try {
        this.element.focus()
    } catch (e) {
    }
}, hide: function () {
    this.element.style.display = "none"
}, show: function () {
    this.element.style.display = ""
}, disable: function () {
    this.element.setAttribute("disabled", "disabled")
}, enable: function () {
    this.element.removeAttribute("disabled")
}}), function (e) {
    var t = e.dom, n = e.browser;
    e.views.Composer = e.views.View.extend({name: "composer", CARET_HACK: "<br>", constructor: function (e, t, n) {
        this.base(e, t, n), this.config.noTextarea ? this.editableArea = t : this.textarea = this.parent.textarea, this.config.contentEditableMode ? this._initContentEditableArea() : this._initSandbox()
    }, clear: function () {
        this.element.innerHTML = n.displaysCaretInEmptyContentEditableCorrectly() ? "" : this.CARET_HACK
    }, getValue: function (t, n) {
        var o = this.isEmpty() ? "" : e.quirks.getCorrectInnerHTML(this.element);
        return t !== !1 && (o = this.parent.parse(o, n === !1 ? !1 : !0)), o
    }, setValue: function (e, t) {
        t && (e = this.parent.parse(e));
        try {
            this.element.innerHTML = e
        } catch (n) {
            this.element.innerText = e
        }
    }, cleanUp: function () {
        this.parent.parse(this.element)
    }, show: function () {
        this.editableArea.style.display = this._displayStyle || "", this.config.noTextarea || this.textarea.element.disabled || (this.disable(), this.enable())
    }, hide: function () {
        this._displayStyle = t.getStyle("display").from(this.editableArea), "none" === this._displayStyle && (this._displayStyle = null), this.editableArea.style.display = "none"
    }, disable: function () {
        this.parent.fire("disable:composer"), this.element.removeAttribute("contentEditable")
    }, enable: function () {
        this.parent.fire("enable:composer"), this.element.setAttribute("contentEditable", "true")
    }, focus: function (t) {
        e.browser.doesAsyncFocus() && this.hasPlaceholderSet() && this.clear(), this.base();
        var n = this.element.lastChild;
        t && n && this.selection && ("BR" === n.nodeName ? this.selection.setBefore(this.element.lastChild) : this.selection.setAfter(this.element.lastChild))
    }, getTextContent: function () {
        return t.getTextContent(this.element)
    }, hasPlaceholderSet: function () {
        return this.getTextContent() == (this.config.noTextarea ? this.editableArea.getAttribute("data-placeholder") : this.textarea.element.getAttribute("placeholder")) && this.placeholderSet
    }, isEmpty: function () {
        var e = this.element.innerHTML.toLowerCase();
        return/^(\s|<br>|<\/br>|<p>|<\/p>)*$/i.test(e) || "" === e || "<br>" === e || "<p></p>" === e || "<p><br></p>" === e || this.hasPlaceholderSet()
    }, _initContentEditableArea: function () {
        var e = this;
        this.config.noTextarea ? this.sandbox = new t.ContentEditableArea(function () {
            e._create()
        }, {}, this.editableArea) : (this.sandbox = new t.ContentEditableArea(function () {
            e._create()
        }), this.editableArea = this.sandbox.getContentEditable(), t.insert(this.editableArea).after(this.textarea.element), this._createWysiwygFormField())
    }, _initSandbox: function () {
        var e = this;
        this.sandbox = new t.Sandbox(function () {
            e._create()
        }, {stylesheets: this.config.stylesheets}), this.editableArea = this.sandbox.getIframe();
        var n = this.textarea.element;
        t.insert(this.editableArea).after(n), this._createWysiwygFormField()
    }, _createWysiwygFormField: function () {
        if (this.textarea.element.form) {
            var e = document.createElement("input");
            e.type = "hidden", e.name = "_wysihtml5_mode", e.value = 1, t.insert(e).after(this.textarea.element)
        }
    }, _create: function () {
        var o = this;
        this.doc = this.sandbox.getDocument(), this.element = this.config.contentEditableMode ? this.sandbox.getContentEditable() : this.doc.body, this.config.noTextarea ? this.cleanUp() : (this.textarea = this.parent.textarea, this.element.innerHTML = this.textarea.getValue(!0, !1)), this.selection = new e.Selection(this.parent, this.element, this.config.uneditableContainerClassname), this.commands = new e.Commands(this.parent), this.config.noTextarea || t.copyAttributes(["className", "spellcheck", "title", "lang", "dir", "accessKey"]).from(this.textarea.element).to(this.element), t.addClass(this.element, this.config.composerClassName), this.config.style && !this.config.contentEditableMode && this.style(), this.observe();
        var i = this.config.name;
        i && (t.addClass(this.element, i), this.config.contentEditableMode || t.addClass(this.editableArea, i)), this.enable(), !this.config.noTextarea && this.textarea.element.disabled && this.disable();
        var r = "string" == typeof this.config.placeholder ? this.config.placeholder : this.config.noTextarea ? this.editableArea.getAttribute("data-placeholder") : this.textarea.element.getAttribute("placeholder");
        r && t.simulatePlaceholder(this.parent, this, r), this.commands.exec("styleWithCSS", !1), this._initAutoLinking(), this._initObjectResizing(), this._initUndoManager(), this._initLineBreaking(), this.config.noTextarea || !this.textarea.element.hasAttribute("autofocus") && document.querySelector(":focus") != this.textarea.element || n.isIos() || setTimeout(function () {
            o.focus(!0)
        }, 100), n.clearsContentEditableCorrectly() || e.quirks.ensureProperClearing(this), this.initSync && this.config.sync && this.initSync(), this.config.noTextarea || this.textarea.hide(), this.parent.fire("beforeload").fire("load")
    }, _initAutoLinking: function () {
        var o = this, i = n.canDisableAutoLinking(), r = n.doesAutoLinkingInContentEditable();
        if (i && this.commands.exec("autoUrlDetect", !1), this.config.autoLink) {
            (!r || r && i) && (this.parent.on("newword:composer", function () {
                t.getTextContent(o.element).match(t.autoLink.URL_REG_EXP) && o.selection.executeAndRestore(function (n, i) {
                    for (var r = o.element.querySelectorAll("." + o.config.uneditableContainerClassname), a = !1, s = r.length; s--;)e.dom.contains(r[s], i) && (a = !0);
                    a || t.autoLink(i.parentNode, [o.config.uneditableContainerClassname])
                })
            }), t.observe(this.element, "blur", function () {
                t.autoLink(o.element, [o.config.uneditableContainerClassname])
            }));
            var a = this.sandbox.getDocument().getElementsByTagName("a"), s = t.autoLink.URL_REG_EXP, l = function (n) {
                var o = e.lang.string(t.getTextContent(n)).trim();
                return"www." === o.substr(0, 4) && (o = "http://" + o), o
            };
            t.observe(this.element, "keydown", function (e) {
                if (a.length) {
                    var n, i = o.selection.getSelectedNode(e.target.ownerDocument), r = t.getParentElement(i, {nodeName: "A"}, 4);
                    r && (n = l(r), setTimeout(function () {
                        var e = l(r);
                        e !== n && e.match(s) && r.setAttribute("href", e)
                    }, 0))
                }
            })
        }
    }, _initObjectResizing: function () {
        if (this.commands.exec("enableObjectResizing", !0), n.supportsEvent("resizeend")) {
            var o = ["width", "height"], i = o.length, r = this.element;
            t.observe(r, "resizeend", function (t) {
                var n, a = t.target || t.srcElement, s = a.style, l = 0;
                if ("IMG" === a.nodeName) {
                    for (; i > l; l++)n = o[l], s[n] && (a.setAttribute(n, parseInt(s[n], 10)), s[n] = "");
                    e.quirks.redraw(r)
                }
            })
        }
    }, _initUndoManager: function () {
        this.undoManager = new e.UndoManager(this.parent)
    }, _initLineBreaking: function () {
        function o(e) {
            var n = t.getParentElement(e, {nodeName: ["P", "DIV"]}, 2);
            n && t.contains(i.element, n) && i.selection.executeAndRestore(function () {
                i.config.useLineBreaks ? t.replaceWithChildNodes(n) : "P" !== n.nodeName && t.renameElement(n, "p")
            })
        }

        var i = this, r = ["LI", "P", "H1", "H2", "H3", "H4", "H5", "H6"], a = ["UL", "OL", "MENU"];
        this.config.useLineBreaks || t.observe(this.element, ["focus", "keydown"], function () {
            if (i.isEmpty()) {
                var e = i.doc.createElement("P");
                i.element.innerHTML = "", i.element.appendChild(e), n.displaysCaretInEmptyContentEditableCorrectly() ? i.selection.selectNode(e, !0) : (e.innerHTML = "<br>", i.selection.setBefore(e.firstChild))
            }
        }), t.observe(this.element, "keydown", function (n) {
            var s = n.keyCode;
            if (!n.shiftKey && (s === e.ENTER_KEY || s === e.BACKSPACE_KEY)) {
                var l = t.getParentElement(i.selection.getSelectedNode(), {nodeName: r}, 4);
                return l ? void setTimeout(function () {
                    var n, r = i.selection.getSelectedNode();
                    if ("LI" === l.nodeName) {
                        if (!r)return;
                        n = t.getParentElement(r, {nodeName: a}, 2), n || o(r)
                    }
                    s === e.ENTER_KEY && l.nodeName.match(/^H[1-6]$/) && o(r)
                }, 0) : void(i.config.useLineBreaks && s === e.ENTER_KEY && !e.browser.insertsLineBreaksOnReturn() && (n.preventDefault(), i.commands.exec("insertLineBreak")))
            }
        })
    }})
}(wysihtml5), function (e) {
    var t = e.dom, n = document, o = window, i = n.createElement("div"), r = ["background-color", "color", "cursor", "font-family", "font-size", "font-style", "font-variant", "font-weight", "line-height", "letter-spacing", "text-align", "text-decoration", "text-indent", "text-rendering", "word-break", "word-wrap", "word-spacing"], a = ["background-color", "border-collapse", "border-bottom-color", "border-bottom-style", "border-bottom-width", "border-left-color", "border-left-style", "border-left-width", "border-right-color", "border-right-style", "border-right-width", "border-top-color", "border-top-style", "border-top-width", "clear", "display", "float", "margin-bottom", "margin-left", "margin-right", "margin-top", "outline-color", "outline-offset", "outline-width", "outline-style", "padding-left", "padding-right", "padding-top", "padding-bottom", "position", "top", "left", "right", "bottom", "z-index", "vertical-align", "text-align", "-webkit-box-sizing", "-moz-box-sizing", "-ms-box-sizing", "box-sizing", "-webkit-box-shadow", "-moz-box-shadow", "-ms-box-shadow", "box-shadow", "-webkit-border-top-right-radius", "-moz-border-radius-topright", "border-top-right-radius", "-webkit-border-bottom-right-radius", "-moz-border-radius-bottomright", "border-bottom-right-radius", "-webkit-border-bottom-left-radius", "-moz-border-radius-bottomleft", "border-bottom-left-radius", "-webkit-border-top-left-radius", "-moz-border-radius-topleft", "border-top-left-radius", "width", "height"], s = ["html                 { height: 100%; }", "body                 { height: 100%; padding: 1px 0 0 0; margin: -1px 0 0 0; }", "body > p:first-child { margin-top: 0; }", "._wysihtml5-temp     { display: none; }", e.browser.isGecko ? "body.placeholder { color: graytext !important; }" : "body.placeholder { color: #a9a9a9 !important; }", "img:-moz-broken      { -moz-force-broken-image-icon: 1; height: 24px; width: 24px; }"], l = function (e) {
        if (e.setActive)try {
            e.setActive()
        } catch (i) {
        } else {
            var r = e.style, a = n.documentElement.scrollTop || n.body.scrollTop, s = n.documentElement.scrollLeft || n.body.scrollLeft, l = {position: r.position, top: r.top, left: r.left, WebkitUserSelect: r.WebkitUserSelect};
            t.setStyles({position: "absolute", top: "-99999px", left: "-99999px", WebkitUserSelect: "none"}).on(e), e.focus(), t.setStyles(l).on(e), o.scrollTo && o.scrollTo(s, a)
        }
    };
    e.views.Composer.prototype.style = function () {
        var o, c = this, u = n.querySelector(":focus"), d = this.textarea.element, h = d.hasAttribute("placeholder"), f = h && d.getAttribute("placeholder"), p = d.style.display, m = d.disabled;
        this.focusStylesHost = i.cloneNode(!1), this.blurStylesHost = i.cloneNode(!1), this.disabledStylesHost = i.cloneNode(!1), h && d.removeAttribute("placeholder"), d === u && d.blur(), d.disabled = !1, d.style.display = o = "none", (d.getAttribute("rows") && "auto" === t.getStyle("height").from(d) || d.getAttribute("cols") && "auto" === t.getStyle("width").from(d)) && (d.style.display = o = p), t.copyStyles(a).from(d).to(this.editableArea).andTo(this.blurStylesHost), t.copyStyles(r).from(d).to(this.element).andTo(this.blurStylesHost), t.insertCSS(s).into(this.element.ownerDocument), d.disabled = !0, t.copyStyles(a).from(d).to(this.disabledStylesHost), t.copyStyles(r).from(d).to(this.disabledStylesHost), d.disabled = m, d.style.display = p, l(d), d.style.display = o, t.copyStyles(a).from(d).to(this.focusStylesHost), t.copyStyles(r).from(d).to(this.focusStylesHost), d.style.display = p, t.copyStyles(["display"]).from(d).to(this.editableArea);
        var g = e.lang.array(a).without(["display"]);
        return u ? u.focus() : d.blur(), h && d.setAttribute("placeholder", f), this.parent.on("focus:composer", function () {
            t.copyStyles(g).from(c.focusStylesHost).to(c.editableArea), t.copyStyles(r).from(c.focusStylesHost).to(c.element)
        }), this.parent.on("blur:composer", function () {
            t.copyStyles(g).from(c.blurStylesHost).to(c.editableArea), t.copyStyles(r).from(c.blurStylesHost).to(c.element)
        }), this.parent.observe("disable:composer", function () {
            t.copyStyles(g).from(c.disabledStylesHost).to(c.editableArea), t.copyStyles(r).from(c.disabledStylesHost).to(c.element)
        }), this.parent.observe("enable:composer", function () {
            t.copyStyles(g).from(c.blurStylesHost).to(c.editableArea), t.copyStyles(r).from(c.blurStylesHost).to(c.element)
        }), this
    }
}(wysihtml5), function (e) {
    var t = e.dom, n = e.browser, o = {66: "bold", 73: "italic", 85: "underline"}, i = function (e, t, n) {
        var o = e.getPreviousNode(t, !0), i = e.getSelectedNode();
        if (1 !== i.nodeType && i.parentNode !== n && (i = i.parentNode), o)if (1 == i.nodeType) {
            var r = i.firstChild;
            if (1 == o.nodeType)for (; i.firstChild;)o.appendChild(i.firstChild); else for (; i.firstChild;)t.parentNode.insertBefore(i.firstChild, t);
            i.parentNode && i.parentNode.removeChild(i), e.setBefore(r)
        } else 1 == o.nodeType ? o.appendChild(i) : t.parentNode.insertBefore(i, t), e.setBefore(i)
    }, r = function (e, t, n, o) {
        if (t.isCollapsed())if (t.caretIsInTheBeginnig("LI"))e.preventDefault(), o.commands.exec("outdentList"); else if (t.caretIsInTheBeginnig())e.preventDefault(); else {
            if (t.caretIsFirstInSelection() && t.getPreviousNode() && t.getPreviousNode().nodeName && /^H\d$/gi.test(t.getPreviousNode().nodeName)) {
                var r = t.getPreviousNode();
                if (e.preventDefault(), /^\s*$/.test(r.textContent || r.innerText))r.parentNode.removeChild(r); else {
                    var a = r.ownerDocument.createRange();
                    a.selectNodeContents(r), a.collapse(!1), t.setSelection(a)
                }
            }
            var s = t.caretIsBeforeUneditable();
            s && (e.preventDefault(), i(t, s, n))
        } else t.containsUneditable() && (e.preventDefault(), t.deleteContents())
    }, a = function (e) {
        if (e.selection.isCollapsed()) {
            if (e.selection.caretIsInTheBeginnig("LI") && e.commands.exec("indentList"))return
        } else e.selection.deleteContents();
        e.commands.exec("insertHTML", "&emsp;")
    };
    e.views.Composer.prototype.observe = function () {
        var i = this, s = this.getValue(!1, !1), l = this.sandbox.getIframe ? this.sandbox.getIframe() : this.sandbox.getContentEditable(), c = this.element, u = n.supportsEventsInIframeCorrectly() || this.sandbox.getContentEditable ? c : this.sandbox.getWindow(), d = ["drop", "paste"], h = ["drop", "paste", "mouseup", "focus", "keyup"];
        if (t.observe(l, "DOMNodeRemoved", function () {
            clearInterval(f), i.parent.fire("destroy:composer")
        }), !n.supportsMutationEvents())var f = setInterval(function () {
            t.contains(document.documentElement, l) || (clearInterval(f), i.parent.fire("destroy:composer"))
        }, 250);
        t.observe(u, h, function () {
            setTimeout(function () {
                i.parent.fire("interaction").fire("interaction:composer")
            }, 0)
        }), this.config.handleTables && (!this.tableClickHandle && this.doc.execCommand && e.browser.supportsCommand(this.doc, "enableObjectResizing") && e.browser.supportsCommand(this.doc, "enableInlineTableEditing") && (this.sandbox.getIframe ? this.tableClickHandle = t.observe(l, ["focus", "mouseup", "mouseover"], function () {
            i.doc.execCommand("enableObjectResizing", !1, "false"), i.doc.execCommand("enableInlineTableEditing", !1, "false"), i.tableClickHandle.stop()
        }) : setTimeout(function () {
            i.doc.execCommand("enableObjectResizing", !1, "false"), i.doc.execCommand("enableInlineTableEditing", !1, "false")
        }, 0)), this.tableSelection = e.quirks.tableCellsSelection(c, i.parent)), t.observe(u, "focus", function (e) {
            i.parent.fire("focus", e).fire("focus:composer", e), setTimeout(function () {
                s = i.getValue(!1, !1)
            }, 0)
        }), t.observe(u, "blur", function (e) {
            if (s !== i.getValue(!1, !1)) {
                var t = e;
                "function" == typeof Object.create && (t = Object.create(e, {type: {value: "change"}})), i.parent.fire("change", t).fire("change:composer", t)
            }
            i.parent.fire("blur", e).fire("blur:composer", e)
        }), t.observe(c, "dragenter", function () {
            i.parent.fire("unset_placeholder")
        }), t.observe(c, d, function (e) {
            setTimeout(function () {
                i.parent.fire(e.type, e).fire(e.type + ":composer", e)
            }, 0)
        }), t.observe(c, "keyup", function (t) {
            var n = t.keyCode;
            (n === e.SPACE_KEY || n === e.ENTER_KEY) && i.parent.fire("newword:composer")
        }), this.parent.on("paste:composer", function () {
            setTimeout(function () {
                i.parent.fire("newword:composer")
            }, 0)
        }), n.canSelectImagesInContentEditable() || t.observe(c, "mousedown", function (t) {
            var n = t.target, o = c.querySelectorAll("img"), r = c.querySelectorAll("." + i.config.uneditableContainerClassname + " img"), a = e.lang.array(o).without(r);
            "IMG" === n.nodeName && e.lang.array(a).contains(n) && i.selection.selectNode(n)
        }), n.canSelectImagesInContentEditable() || t.observe(c, "drop", function () {
            setTimeout(function () {
                i.selection.getSelection().removeAllRanges()
            }, 0)
        }), n.hasHistoryIssue() && n.supportsSelectionModify() && t.observe(c, "keydown", function (e) {
            if (e.metaKey || e.ctrlKey) {
                var t = e.keyCode, n = c.ownerDocument.defaultView, o = n.getSelection();
                (37 === t || 39 === t) && (37 === t && (o.modify("extend", "left", "lineboundary"), e.shiftKey || o.collapseToStart()), 39 === t && (o.modify("extend", "right", "lineboundary"), e.shiftKey || o.collapseToEnd()), e.preventDefault())
            }
        }), t.observe(c, "keydown", function (e) {
            var t = e.keyCode, n = o[t];
            (e.ctrlKey || e.metaKey) && !e.altKey && n && (i.commands.exec(n), e.preventDefault()), 8 === t ? r(e, i.selection, c, i) : i.config.handleTabKey && 9 === t && (e.preventDefault(), a(i, c))
        }), t.observe(c, "keydown", function (t) {
            var n, o = i.selection.getSelectedNode(!0), r = t.keyCode;
            !o || "IMG" !== o.nodeName || r !== e.BACKSPACE_KEY && r !== e.DELETE_KEY || (n = o.parentNode, n.removeChild(o), "A" !== n.nodeName || n.firstChild || n.parentNode.removeChild(n), setTimeout(function () {
                e.quirks.redraw(c)
            }, 0), t.preventDefault())
        }), !this.config.contentEditableMode && n.hasIframeFocusIssue() && (t.observe(l, "focus", function () {
            setTimeout(function () {
                i.doc.querySelector(":focus") !== i.element && i.focus()
            }, 0)
        }), t.observe(this.element, "blur", function () {
            setTimeout(function () {
                i.selection.getSelection().removeAllRanges()
            }, 0)
        }));
        var p = {IMG: "Image: ", A: "Link: "};
        t.observe(c, "mouseover", function (e) {
            var t, n = e.target, o = n.nodeName;
            if ("A" === o || "IMG" === o) {
                var i = n.hasAttribute("title");
                i || (t = p[o] + (n.getAttribute("href") || n.getAttribute("src")), n.setAttribute("title", t))
            }
        })
    }
}(wysihtml5), function (e) {
    var t = 400;
    e.views.Synchronizer = Base.extend({constructor: function (e, t, n) {
        this.editor = e, this.textarea = t, this.composer = n, this._observe()
    }, fromComposerToTextarea: function (t) {
        this.textarea.setValue(e.lang.string(this.composer.getValue(!1, !1)).trim(), t)
    }, fromTextareaToComposer: function (e) {
        var t = this.textarea.getValue(!1, !1);
        t ? this.composer.setValue(t, e) : (this.composer.clear(), this.editor.fire("set_placeholder"))
    }, sync: function (e) {
        "textarea" === this.editor.currentView.name ? this.fromTextareaToComposer(e) : this.fromComposerToTextarea(e)
    }, _observe: function () {
        var n, o = this, i = this.textarea.element.form, r = function () {
            n = setInterval(function () {
                o.fromComposerToTextarea()
            }, t)
        }, a = function () {
            clearInterval(n), n = null
        };
        r(), i && (e.dom.observe(i, "submit", function () {
            o.sync(!0)
        }), e.dom.observe(i, "reset", function () {
            setTimeout(function () {
                o.fromTextareaToComposer()
            }, 0)
        })), this.editor.on("change_view", function (e) {
            "composer" !== e || n ? "textarea" === e && (o.fromComposerToTextarea(!0), a()) : (o.fromTextareaToComposer(!0), r())
        }), this.editor.on("destroy:composer", a)
    }})
}(wysihtml5), wysihtml5.views.Textarea = wysihtml5.views.View.extend({name: "textarea", constructor: function (e, t, n) {
    this.base(e, t, n), this._observe()
}, clear: function () {
    this.element.value = ""
}, getValue: function (e) {
    var t = this.isEmpty() ? "" : this.element.value;
    return e !== !1 && (t = this.parent.parse(t)), t
}, setValue: function (e, t) {
    t && (e = this.parent.parse(e)), this.element.value = e
}, cleanUp: function () {
    var e = this.parent.parse(this.element.value);
    this.element.value = e
}, hasPlaceholderSet: function () {
    var e = wysihtml5.browser.supportsPlaceholderAttributeOn(this.element), t = this.element.getAttribute("placeholder") || null, n = this.element.value, o = !n;
    return e && o || n === t
}, isEmpty: function () {
    return!wysihtml5.lang.string(this.element.value).trim() || this.hasPlaceholderSet()
}, _observe: function () {
    var e = this.element, t = this.parent, n = {focusin: "focus", focusout: "blur"}, o = wysihtml5.browser.supportsEvent("focusin") ? ["focusin", "focusout", "change"] : ["focus", "blur", "change"];
    t.on("beforeload", function () {
        wysihtml5.dom.observe(e, o, function (e) {
            var o = n[e.type] || e.type;
            t.fire(o).fire(o + ":textarea")
        }), wysihtml5.dom.observe(e, ["paste", "drop"], function () {
            setTimeout(function () {
                t.fire("paste").fire("paste:textarea")
            }, 0)
        })
    })
}}), function (e) {
    var t, n = {name: t, style: !0, toolbar: t, showToolbarAfterInit: !0, autoLink: !0, handleTables: !0, handleTabKey: !0, parserRules: {tags: {br: {}, span: {}, div: {}, p: {}}, classes: {}}, parser: e.dom.parse, composerClassName: "wysihtml5-editor", bodyClassName: "wysihtml5-supported", useLineBreaks: !0, stylesheets: [], placeholderText: t, supportTouchDevices: !0, cleanUp: !0, contentEditableMode: !1, uneditableContainerClassname: "wysihtml5-uneditable-container"};
    e.Editor = e.lang.Dispatcher.extend({constructor: function (t, o) {
        if (this.editableElement = "string" == typeof t ? document.getElementById(t) : t, this.config = e.lang.object({}).merge(n).merge(o).get(), this._isCompatible = e.browser.supported(), "textarea" != this.editableElement.nodeName.toLowerCase() && (this.config.contentEditableMode = !0, this.config.noTextarea = !0), this.config.noTextarea || (this.textarea = new e.views.Textarea(this, this.editableElement, this.config), this.currentView = this.textarea), !this._isCompatible || !this.config.supportTouchDevices && e.browser.isTouchDevice()) {
            var i = this;
            return void setTimeout(function () {
                i.fire("beforeload").fire("load")
            }, 0)
        }
        e.dom.addClass(document.body, this.config.bodyClassName), this.composer = new e.views.Composer(this, this.editableElement, this.config), this.currentView = this.composer, "function" == typeof this.config.parser && this._initParser(), this.on("beforeload", this.handleBeforeLoad)
    }, handleBeforeLoad: function () {
        this.config.noTextarea || (this.synchronizer = new e.views.Synchronizer(this, this.textarea, this.composer)), this.config.toolbar && (this.toolbar = new e.toolbar.Toolbar(this, this.config.toolbar, this.config.showToolbarAfterInit))
    }, isCompatible: function () {
        return this._isCompatible
    }, clear: function () {
        return this.currentView.clear(), this
    }, getValue: function (e, t) {
        return this.currentView.getValue(e, t)
    }, setValue: function (e, t) {
        return this.fire("unset_placeholder"), e ? (this.currentView.setValue(e, t), this) : this.clear()
    }, cleanUp: function () {
        this.currentView.cleanUp()
    }, focus: function (e) {
        return this.currentView.focus(e), this
    }, disable: function () {
        return this.currentView.disable(), this
    }, enable: function () {
        return this.currentView.enable(), this
    }, isEmpty: function () {
        return this.currentView.isEmpty()
    }, hasPlaceholderSet: function () {
        return this.currentView.hasPlaceholderSet()
    }, parse: function (t, n) {
        var o = this.config.contentEditableMode ? document : this.composer ? this.composer.sandbox.getDocument() : null, i = this.config.parser(t, {rules: this.config.parserRules, cleanUp: this.config.cleanUp, context: o, uneditableClass: this.config.uneditableContainerClassname, clearInternals: n});
        return"object" == typeof t && e.quirks.redraw(t), i
    }, _initParser: function () {
        this.on("paste:composer", function () {
            var t = !0, n = this;
            n.composer.selection.executeAndRestore(function () {
                e.quirks.cleanPastedHTML(n.composer.element), n.parse(n.composer.element)
            }, t)
        })
    }})
}(wysihtml5), function (e) {
    var t = e.dom, n = "wysihtml5-command-dialog-opened", o = "input, select, textarea", i = "[data-wysihtml5-dialog-field]", r = "data-wysihtml5-dialog-field";
    e.toolbar.Dialog = e.lang.Dispatcher.extend({constructor: function (e, t) {
        this.link = e, this.container = t
    }, _observe: function () {
        if (!this._observed) {
            var i = this, r = function (e) {
                var t = i._serialize();
                t == i.elementToChange ? i.fire("edit", t) : i.fire("save", t), i.hide(), e.preventDefault(), e.stopPropagation()
            };
            t.observe(i.link, "click", function () {
                t.hasClass(i.link, n) && setTimeout(function () {
                    i.hide()
                }, 0)
            }), t.observe(this.container, "keydown", function (t) {
                var n = t.keyCode;
                n === e.ENTER_KEY && r(t), n === e.ESCAPE_KEY && (i.fire("cancel"), i.hide())
            }), t.delegate(this.container, "[data-wysihtml5-dialog-action=save]", "click", r), t.delegate(this.container, "[data-wysihtml5-dialog-action=cancel]", "click", function (e) {
                i.fire("cancel"), i.hide(), e.preventDefault(), e.stopPropagation()
            });
            for (var a = this.container.querySelectorAll(o), s = 0, l = a.length, c = function () {
                clearInterval(i.interval)
            }; l > s; s++)t.observe(a[s], "change", c);
            this._observed = !0
        }
    }, _serialize: function () {
        for (var e = this.elementToChange || {}, t = this.container.querySelectorAll(i), n = t.length, o = 0; n > o; o++)e[t[o].getAttribute(r)] = t[o].value;
        return e
    }, _interpolate: function (e) {
        for (var t, n, o, a = document.querySelector(":focus"), s = this.container.querySelectorAll(i), l = s.length, c = 0; l > c; c++)t = s[c], t !== a && (e && "hidden" === t.type || (n = t.getAttribute(r), o = this.elementToChange && "boolean" != typeof this.elementToChange ? this.elementToChange.getAttribute(n) || "" : t.defaultValue, t.value = o))
    }, show: function (e) {
        if (!t.hasClass(this.link, n)) {
            var i = this, r = this.container.querySelector(o);
            if (this.elementToChange = e, this._observe(), this._interpolate(), e && (this.interval = setInterval(function () {
                i._interpolate(!0)
            }, 500)), t.addClass(this.link, n), this.container.style.display = "", this.fire("show"), r && !e)try {
                r.focus()
            } catch (a) {
            }
        }
    }, hide: function () {
        clearInterval(this.interval), this.elementToChange = null, t.removeClass(this.link, n), this.container.style.display = "none", this.fire("hide")
    }})
}(wysihtml5), function (e) {
    var t = e.dom, n = {position: "relative"}, o = {left: 0, margin: 0, opacity: 0, overflow: "hidden", padding: 0, position: "absolute", top: 0, zIndex: 1}, i = {cursor: "inherit", fontSize: "50px", height: "50px", marginTop: "-25px", outline: 0, padding: 0, position: "absolute", right: "-4px", top: "50%"}, r = {"x-webkit-speech": "", speech: ""};
    e.toolbar.Speech = function (a, s) {
        var l = document.createElement("input");
        if (!e.browser.supportsSpeechApiOn(l))return void(s.style.display = "none");
        var c = a.editor.textarea.element.getAttribute("lang");
        c && (r.lang = c);
        var u = document.createElement("div");
        e.lang.object(o).merge({width: s.offsetWidth + "px", height: s.offsetHeight + "px"}), t.insert(l).into(u), t.insert(u).into(s), t.setStyles(i).on(l), t.setAttributes(r).on(l), t.setStyles(o).on(u), t.setStyles(n).on(s);
        var d = "onwebkitspeechchange"in l ? "webkitspeechchange" : "speechchange";
        t.observe(l, d, function () {
            a.execCommand("insertText", l.value), l.value = ""
        }), t.observe(l, "click", function (e) {
            t.hasClass(s, "wysihtml5-command-disabled") && e.preventDefault(), e.stopPropagation()
        })
    }
}(wysihtml5), function (e) {
    var t = "wysihtml5-command-disabled", n = "wysihtml5-commands-disabled", o = "wysihtml5-command-active", i = "wysihtml5-action-active", r = e.dom;
    e.toolbar.Toolbar = Base.extend({constructor: function (t, n, o) {
        this.editor = t, this.container = "string" == typeof n ? document.getElementById(n) : n, this.composer = t.composer, this._getLinks("command"), this._getLinks("action"), this._observe(), o && this.show();
        for (var i = this.container.querySelectorAll("[data-wysihtml5-command=insertSpeech]"), r = i.length, a = 0; r > a; a++)new e.toolbar.Speech(this, i[a])
    }, _getLinks: function (t) {
        for (var n, o, i, r, a, s = this[t + "Links"] = e.lang.array(this.container.querySelectorAll("[data-wysihtml5-" + t + "]")).get(), l = s.length, c = 0, u = this[t + "Mapping"] = {}; l > c; c++)n = s[c], i = n.getAttribute("data-wysihtml5-" + t), r = n.getAttribute("data-wysihtml5-" + t + "-value"), o = this.container.querySelector("[data-wysihtml5-" + t + "-group='" + i + "']"), a = this._getDialog(n, i), u[i + ":" + r] = {link: n, group: o, name: i, value: r, dialog: a, state: !1}
    }, _getDialog: function (t, n) {
        var o, i, r = this, a = this.container.querySelector("[data-wysihtml5-dialog='" + n + "']");
        return a && (o = e.toolbar["Dialog_" + n] ? new e.toolbar["Dialog_" + n](t, a) : new e.toolbar.Dialog(t, a), o.on("show", function () {
            i = r.composer.selection.getBookmark(), r.editor.fire("show:dialog", {command: n, dialogContainer: a, commandLink: t})
        }), o.on("save", function (e) {
            i && r.composer.selection.setBookmark(i), r._execCommand(n, e), r.editor.fire("save:dialog", {command: n, dialogContainer: a, commandLink: t})
        }), o.on("cancel", function () {
            r.editor.focus(!1), r.editor.fire("cancel:dialog", {command: n, dialogContainer: a, commandLink: t})
        })), o
    }, execCommand: function (e, t) {
        if (!this.commandsDisabled) {
            var n = this.commandMapping[e + ":" + t];
            n && n.dialog && !n.state ? n.dialog.show() : this._execCommand(e, t)
        }
    }, _execCommand: function (e, t) {
        this.editor.focus(!1), this.composer.commands.exec(e, t), this._updateLinkStates()
    }, execAction: function (e) {
        var t = this.editor;
        "change_view" === e && t.textarea && (t.currentView === t.textarea ? t.fire("change_view", "composer") : t.fire("change_view", "textarea")), "showSource" == e && t.fire("showSource")
    }, _observe: function () {
        for (var e = this, t = this.editor, o = this.container, i = this.commandLinks.concat(this.actionLinks), a = i.length, s = 0; a > s; s++)"A" === i[s].nodeName ? r.setAttributes({href: "javascript:;", unselectable: "on"}).on(i[s]) : r.setAttributes({unselectable: "on"}).on(i[s]);
        r.delegate(o, "[data-wysihtml5-command], [data-wysihtml5-action]", "mousedown", function (e) {
            e.preventDefault()
        }), r.delegate(o, "[data-wysihtml5-command]", "click", function (t) {
            var n = this, o = n.getAttribute("data-wysihtml5-command"), i = n.getAttribute("data-wysihtml5-command-value");
            e.execCommand(o, i), t.preventDefault()
        }), r.delegate(o, "[data-wysihtml5-action]", "click", function (t) {
            var n = this.getAttribute("data-wysihtml5-action");
            e.execAction(n), t.preventDefault()
        }), t.on("interaction:composer", function () {
            e._updateLinkStates()
        }), t.on("focus:composer", function () {
            e.bookmark = null
        }), this.editor.config.handleTables && (t.on("tableselect:composer", function () {
            e.container.querySelectorAll('[data-wysihtml5-hiddentools="table"]')[0].style.display = ""
        }), t.on("tableunselect:composer", function () {
            e.container.querySelectorAll('[data-wysihtml5-hiddentools="table"]')[0].style.display = "none"
        })), t.on("change_view", function (i) {
            t.textarea && setTimeout(function () {
                e.commandsDisabled = "composer" !== i, e._updateLinkStates(), e.commandsDisabled ? r.addClass(o, n) : r.removeClass(o, n)
            }, 0)
        })
    }, _updateLinkStates: function () {
        var n, a, s, l, c = this.commandMapping, u = this.actionMapping;
        for (n in c)l = c[n], this.commandsDisabled ? (a = !1, r.removeClass(l.link, o), l.group && r.removeClass(l.group, o), l.dialog && l.dialog.hide()) : (a = this.composer.commands.state(l.name, l.value), r.removeClass(l.link, t), l.group && r.removeClass(l.group, t)), l.state !== a && (l.state = a, a ? (r.addClass(l.link, o), l.group && r.addClass(l.group, o), l.dialog && ("object" == typeof a || e.lang.object(a).isArray() ? (!l.dialog.multiselect && e.lang.object(a).isArray() && (a = 1 === a.length ? a[0] : !0, l.state = a), l.dialog.show(a)) : l.dialog.hide())) : (r.removeClass(l.link, o), l.group && r.removeClass(l.group, o), l.dialog && l.dialog.hide()));
        for (n in u)s = u[n], "change_view" === s.name && (s.state = this.editor.currentView === this.editor.textarea, s.state ? r.addClass(s.link, i) : r.removeClass(s.link, i))
    }, show: function () {
        this.container.style.display = ""
    }, hide: function () {
        this.container.style.display = "none"
    }})
}(wysihtml5), function (e) {
    e.toolbar.Dialog_createTable = e.toolbar.Dialog.extend({show: function (e) {
        this.base(e)
    }})
}(wysihtml5), function (e) {
    var t = (e.dom, "[data-wysihtml5-dialog-field]"), n = "data-wysihtml5-dialog-field";
    e.toolbar.Dialog_foreColorStyle = e.toolbar.Dialog.extend({multiselect: !0, _serialize: function () {
        for (var e = {}, o = this.container.querySelectorAll(t), i = o.length, r = 0; i > r; r++)e[o[r].getAttribute(n)] = o[r].value;
        return e
    }, _interpolate: function (o) {
        for (var i, r = document.querySelector(":focus"), a = this.container.querySelectorAll(t), s = a.length, l = 0, c = this.elementToChange ? e.lang.object(this.elementToChange).isArray() ? this.elementToChange[0] : this.elementToChange : null, u = c ? c.getAttribute("style") : null, d = u ? e.quirks.styleParser.parseColor(u, "color") : null; s > l; l++)i = a[l], i !== r && (o && "hidden" === i.type || "color" === i.getAttribute(n) && (i.value = d ? d[3] && 1 != d[3] ? "rgba(" + d[0] + "," + d[1] + "," + d[2] + "," + d[3] + ");" : "rgb(" + d[0] + "," + d[1] + "," + d[2] + ");" : "rgb(0,0,0);"))
    }})
}(wysihtml5), function (e) {
    e.dom, e.toolbar.Dialog_fontSizeStyle = e.toolbar.Dialog.extend({multiselect: !0, _serialize: function () {
        return{size: this.container.querySelector('[data-wysihtml5-dialog-field="size"]').value}
    }, _interpolate: function () {
        var t = document.querySelector(":focus"), n = this.container.querySelector("[data-wysihtml5-dialog-field='size']"), o = this.elementToChange ? e.lang.object(this.elementToChange).isArray() ? this.elementToChange[0] : this.elementToChange : null, i = o ? o.getAttribute("style") : null, r = i ? e.quirks.styleParser.parseFontSize(i) : null;
        n && n !== t && r && !/^\s*$/.test(r) && (n.value = r)
    }})
}(wysihtml5);
var Handlebars = function () {
    var e = function () {
        "use strict";
        function e(e) {
            this.string = e
        }

        var t;
        return e.prototype.toString = function () {
            return"" + this.string
        }, t = e
    }(), t = function (e) {
        "use strict";
        function t(e) {
            return s[e] || "&amp;"
        }

        function n(e, t) {
            for (var n in t)Object.prototype.hasOwnProperty.call(t, n) && (e[n] = t[n])
        }

        function o(e) {
            return e instanceof a ? e.toString() : e || 0 === e ? (e = "" + e, c.test(e) ? e.replace(l, t) : e) : ""
        }

        function i(e) {
            return e || 0 === e ? h(e) && 0 === e.length ? !0 : !1 : !0
        }

        var r = {}, a = e, s = {"&": "&amp;", "<": "&lt;", ">": "&gt;", '"': "&quot;", "'": "&#x27;", "`": "&#x60;"}, l = /[&<>"'`]/g, c = /[&<>"'`]/;
        r.extend = n;
        var u = Object.prototype.toString;
        r.toString = u;
        var d = function (e) {
            return"function" == typeof e
        };
        d(/x/) && (d = function (e) {
            return"function" == typeof e && "[object Function]" === u.call(e)
        });
        var d;
        r.isFunction = d;
        var h = Array.isArray || function (e) {
            return e && "object" == typeof e ? "[object Array]" === u.call(e) : !1
        };
        return r.isArray = h, r.escapeExpression = o, r.isEmpty = i, r
    }(e), n = function () {
        "use strict";
        function e(e, t) {
            var o;
            t && t.firstLine && (o = t.firstLine, e += " - " + o + ":" + t.firstColumn);
            for (var i = Error.prototype.constructor.call(this, e), r = 0; r < n.length; r++)this[n[r]] = i[n[r]];
            o && (this.lineNumber = o, this.column = t.firstColumn)
        }

        var t, n = ["description", "fileName", "lineNumber", "message", "name", "number", "stack"];
        return e.prototype = new Error, t = e
    }(), o = function (e, t) {
        "use strict";
        function n(e, t) {
            this.helpers = e || {}, this.partials = t || {}, o(this)
        }

        function o(e) {
            e.registerHelper("helperMissing", function (e) {
                if (2 === arguments.length)return void 0;
                throw new s("Missing helper: '" + e + "'")
            }), e.registerHelper("blockHelperMissing", function (t, n) {
                var o = n.inverse || function () {
                }, i = n.fn;
                return h(t) && (t = t.call(this)), t === !0 ? i(this) : t === !1 || null == t ? o(this) : d(t) ? t.length > 0 ? e.helpers.each(t, n) : o(this) : i(t)
            }), e.registerHelper("each", function (e, t) {
                var n, o = t.fn, i = t.inverse, r = 0, a = "";
                if (h(e) && (e = e.call(this)), t.data && (n = g(t.data)), e && "object" == typeof e)if (d(e))for (var s = e.length; s > r; r++)n && (n.index = r, n.first = 0 === r, n.last = r === e.length - 1), a += o(e[r], {data: n}); else for (var l in e)e.hasOwnProperty(l) && (n && (n.key = l, n.index = r, n.first = 0 === r), a += o(e[l], {data: n}), r++);
                return 0 === r && (a = i(this)), a
            }), e.registerHelper("if", function (e, t) {
                return h(e) && (e = e.call(this)), !t.hash.includeZero && !e || a.isEmpty(e) ? t.inverse(this) : t.fn(this)
            }), e.registerHelper("unless", function (t, n) {
                return e.helpers["if"].call(this, t, {fn: n.inverse, inverse: n.fn, hash: n.hash})
            }), e.registerHelper("with", function (e, t) {
                return h(e) && (e = e.call(this)), a.isEmpty(e) ? void 0 : t.fn(e)
            }), e.registerHelper("log", function (t, n) {
                var o = n.data && null != n.data.level ? parseInt(n.data.level, 10) : 1;
                e.log(o, t)
            })
        }

        function i(e, t) {
            m.log(e, t)
        }

        var r = {}, a = e, s = t, l = "1.3.0";
        r.VERSION = l;
        var c = 4;
        r.COMPILER_REVISION = c;
        var u = {1: "<= 1.0.rc.2", 2: "== 1.0.0-rc.3", 3: "== 1.0.0-rc.4", 4: ">= 1.0.0"};
        r.REVISION_CHANGES = u;
        var d = a.isArray, h = a.isFunction, f = a.toString, p = "[object Object]";
        r.HandlebarsEnvironment = n, n.prototype = {constructor: n, logger: m, log: i, registerHelper: function (e, t, n) {
            if (f.call(e) === p) {
                if (n || t)throw new s("Arg not supported with multiple helpers");
                a.extend(this.helpers, e)
            } else n && (t.not = n), this.helpers[e] = t
        }, registerPartial: function (e, t) {
            f.call(e) === p ? a.extend(this.partials, e) : this.partials[e] = t
        }};
        var m = {methodMap: {0: "debug", 1: "info", 2: "warn", 3: "error"}, DEBUG: 0, INFO: 1, WARN: 2, ERROR: 3, level: 3, log: function (e, t) {
            if (m.level <= e) {
                var n = m.methodMap[e];
                "undefined" != typeof console && console[n] && console[n].call(console, t)
            }
        }};
        r.logger = m, r.log = i;
        var g = function (e) {
            var t = {};
            return a.extend(t, e), t
        };
        return r.createFrame = g, r
    }(t, n), i = function (e, t, n) {
        "use strict";
        function o(e) {
            var t = e && e[0] || 1, n = h;
            if (t !== n) {
                if (n > t) {
                    var o = f[n], i = f[t];
                    throw new d("Template was precompiled with an older version of Handlebars than the current runtime. Please update your precompiler to a newer version (" + o + ") or downgrade your runtime to an older version (" + i + ").")
                }
                throw new d("Template was precompiled with a newer version of Handlebars than the current runtime. Please update your runtime to a newer version (" + e[1] + ").")
            }
        }

        function i(e, t) {
            if (!t)throw new d("No environment passed to template");
            var n = function (e, n, o, i, r, a) {
                var s = t.VM.invokePartial.apply(this, arguments);
                if (null != s)return s;
                if (t.compile) {
                    var l = {helpers: i, partials: r, data: a};
                    return r[n] = t.compile(e, {data: void 0 !== a}, t), r[n](o, l)
                }
                throw new d("The partial " + n + " could not be compiled when running in runtime-only mode")
            }, o = {escapeExpression: u.escapeExpression, invokePartial: n, programs: [], program: function (e, t, n) {
                var o = this.programs[e];
                return n ? o = a(e, t, n) : o || (o = this.programs[e] = a(e, t)), o
            }, merge: function (e, t) {
                var n = e || t;
                return e && t && e !== t && (n = {}, u.extend(n, t), u.extend(n, e)), n
            }, programWithDepth: t.VM.programWithDepth, noop: t.VM.noop, compilerInfo: null};
            return function (n, i) {
                i = i || {};
                var r, a, s = i.partial ? i : t;
                i.partial || (r = i.helpers, a = i.partials);
                var l = e.call(o, s, n, r, a, i.data);
                return i.partial || t.VM.checkRevision(o.compilerInfo), l
            }
        }

        function r(e, t, n) {
            var o = Array.prototype.slice.call(arguments, 3), i = function (e, i) {
                return i = i || {}, t.apply(this, [e, i.data || n].concat(o))
            };
            return i.program = e, i.depth = o.length, i
        }

        function a(e, t, n) {
            var o = function (e, o) {
                return o = o || {}, t(e, o.data || n)
            };
            return o.program = e, o.depth = 0, o
        }

        function s(e, t, n, o, i, r) {
            var a = {partial: !0, helpers: o, partials: i, data: r};
            if (void 0 === e)throw new d("The partial " + t + " could not be found");
            return e instanceof Function ? e(n, a) : void 0
        }

        function l() {
            return""
        }

        var c = {}, u = e, d = t, h = n.COMPILER_REVISION, f = n.REVISION_CHANGES;
        return c.checkRevision = o, c.template = i, c.programWithDepth = r, c.program = a, c.invokePartial = s, c.noop = l, c
    }(t, n, o), r = function (e, t, n, o, i) {
        "use strict";
        var r, a = e, s = t, l = n, c = o, u = i, d = function () {
            var e = new a.HandlebarsEnvironment;
            return c.extend(e, a), e.SafeString = s, e.Exception = l, e.Utils = c, e.VM = u, e.template = function (t) {
                return u.template(t, e)
            }, e
        }, h = d();
        return h.create = d, r = h
    }(o, e, n, t, i);
    return r
}();
this.wysihtml5 = this.wysihtml5 || {}, this.wysihtml5.tpl = this.wysihtml5.tpl || {}, this.wysihtml5.tpl.blockquote = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + d((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === u ? t.apply(e) : t))
    }

    function a() {
        return' \n      <span class="fa fa-quote-left"></span>\n    '
    }

    function s() {
        return'\n      <span class="glyphicon glyphicon-quote"></span>\n    '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var l, c = "", u = "function", d = this.escapeExpression, h = this;
    return c += '<li>\n  <a class="btn ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.size), {hash: {}, inverse: h.noop, fn: h.program(1, r, i), data: i}), (l || 0 === l) && (c += l), c += ' btn-default" data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="blockquote" data-wysihtml5-display-format-name="false" tabindex="-1">\n    ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.fa), {hash: {}, inverse: h.program(5, s, i), fn: h.program(3, a, i), data: i}), (l || 0 === l) && (c += l), c += "\n  </a>\n</li>\n"
}), this.wysihtml5.tpl.color = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + c((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === l ? t.apply(e) : t))
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var a, s = "", l = "function", c = this.escapeExpression, u = this;
    return s += '<li class="dropdown">\n  <a class="btn btn-default dropdown-toggle ', a = n["if"].call(t, (a = t && t.options, a = null == a || a === !1 ? a : a.toolbar, null == a || a === !1 ? a : a.size), {hash: {}, inverse: u.noop, fn: u.program(1, r, i), data: i}), (a || 0 === a) && (s += a), s += '" data-toggle="dropdown" tabindex="-1">\n    <span class="current-color">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.black, typeof a === l ? a.apply(t) : a)) + '</span>\n    <b class="caret"></b>\n  </a>\n  <ul class="dropdown-menu">\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="black"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="black">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.black, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="silver"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="silver">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.silver, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="gray"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="gray">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.gray, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="maroon"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="maroon">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.maroon, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="red"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="red">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.red, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="purple"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="purple">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.purple, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="green"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="green">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.green, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="olive"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="olive">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.olive, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="navy"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="navy">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.navy, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="blue"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="blue">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.blue, typeof a === l ? a.apply(t) : a)) + '</a></li>\n    <li><div class="wysihtml5-colors" data-wysihtml5-command-value="orange"></div><a class="wysihtml5-colors-title" data-wysihtml5-command="foreColor" data-wysihtml5-command-value="orange">' + c((a = t && t.locale, a = null == a || a === !1 ? a : a.colours, a = null == a || a === !1 ? a : a.orange, typeof a === l ? a.apply(t) : a)) + "</a></li>\n  </ul>\n</li>\n"
}), this.wysihtml5.tpl.emphasis = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + u((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === c ? t.apply(e) : t))
    }

    function a(e, t) {
        var o, i = "";
        return i += '\n    <a class="btn ', o = n["if"].call(e, (o = e && e.options, o = null == o || o === !1 ? o : o.toolbar, null == o || o === !1 ? o : o.size), {hash: {}, inverse: d.noop, fn: d.program(1, r, t), data: t}), (o || 0 === o) && (i += o), i += ' btn-default" data-wysihtml5-command="small" title="CTRL+S" tabindex="-1">' + u((o = e && e.locale, o = null == o || o === !1 ? o : o.emphasis, o = null == o || o === !1 ? o : o.small, typeof o === c ? o.apply(e) : o)) + "</a>\n    "
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var s, l = "", c = "function", u = this.escapeExpression, d = this;
    return l += '<li>\n  <div class="btn-group">\n    <a class="btn ', s = n["if"].call(t, (s = t && t.options, s = null == s || s === !1 ? s : s.toolbar, null == s || s === !1 ? s : s.size), {hash: {}, inverse: d.noop, fn: d.program(1, r, i), data: i}), (s || 0 === s) && (l += s), l += ' btn-default" data-wysihtml5-command="bold" title="CTRL+B" tabindex="-1">' + u((s = t && t.locale, s = null == s || s === !1 ? s : s.emphasis, s = null == s || s === !1 ? s : s.bold, typeof s === c ? s.apply(t) : s)) + '</a>\n    <a class="btn ', s = n["if"].call(t, (s = t && t.options, s = null == s || s === !1 ? s : s.toolbar, null == s || s === !1 ? s : s.size), {hash: {}, inverse: d.noop, fn: d.program(1, r, i), data: i}), (s || 0 === s) && (l += s), l += ' btn-default" data-wysihtml5-command="italic" title="CTRL+I" tabindex="-1">' + u((s = t && t.locale, s = null == s || s === !1 ? s : s.emphasis, s = null == s || s === !1 ? s : s.italic, typeof s === c ? s.apply(t) : s)) + '</a>\n    <a class="btn ', s = n["if"].call(t, (s = t && t.options, s = null == s || s === !1 ? s : s.toolbar, null == s || s === !1 ? s : s.size), {hash: {}, inverse: d.noop, fn: d.program(1, r, i), data: i}), (s || 0 === s) && (l += s), l += ' btn-default" data-wysihtml5-command="underline" title="CTRL+U" tabindex="-1">' + u((s = t && t.locale, s = null == s || s === !1 ? s : s.emphasis, s = null == s || s === !1 ? s : s.underline, typeof s === c ? s.apply(t) : s)) + "</a>\n    ", s = n["if"].call(t, (s = t && t.options, s = null == s || s === !1 ? s : s.toolbar, s = null == s || s === !1 ? s : s.emphasis, null == s || s === !1 ? s : s.small), {hash: {}, inverse: d.noop, fn: d.program(3, a, i), data: i}), (s || 0 === s) && (l += s), l += "\n  </div>\n</li>\n"
}), this.wysihtml5.tpl["font-styles"] = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + d((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === u ? t.apply(e) : t))
    }

    function a() {
        return'\n      <span class="fa fa-font"></span>\n    '
    }

    function s() {
        return'\n      <span class="glyphicon glyphicon-font"></span>\n    '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var l, c = "", u = "function", d = this.escapeExpression, h = this;
    return c += '<li class="dropdown">\n  <a class="btn btn-default dropdown-toggle ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.size), {hash: {}, inverse: h.noop, fn: h.program(1, r, i), data: i}), (l || 0 === l) && (c += l), c += '" data-toggle="dropdown">\n    ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.fa), {hash: {}, inverse: h.program(5, s, i), fn: h.program(3, a, i), data: i}), (l || 0 === l) && (c += l), c += '\n    <span class="current-font">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.normal, typeof l === u ? l.apply(t) : l)) + '</span>\n    <b class="caret"></b>\n  </a>\n  <ul class="dropdown-menu">\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="p" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.normal, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h1" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h1, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h2" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h2, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h3" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h3, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h4" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h4, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h5" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h5, typeof l === u ? l.apply(t) : l)) + '</a></li>\n    <li><a data-wysihtml5-command="formatBlock" data-wysihtml5-command-value="h6" tabindex="-1">' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.font_styles, l = null == l || l === !1 ? l : l.h6, typeof l === u ? l.apply(t) : l)) + "</a></li>\n  </ul>\n</li>\n"
}), this.wysihtml5.tpl.html = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + d((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === u ? t.apply(e) : t))
    }

    function a() {
        return'\n        <span class="fa fa-pencil"></span>\n      '
    }

    function s() {
        return'\n        <span class="glyphicon glyphicon-pencil"></span>\n      '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var l, c = "", u = "function", d = this.escapeExpression, h = this;
    return c += '<li>\n  <div class="btn-group">\n    <a class="btn ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.size), {hash: {}, inverse: h.noop, fn: h.program(1, r, i), data: i}), (l || 0 === l) && (c += l), c += ' btn-default" data-wysihtml5-action="change_view" title="' + d((l = t && t.locale, l = null == l || l === !1 ? l : l.html, l = null == l || l === !1 ? l : l.edit, typeof l === u ? l.apply(t) : l)) + '" tabindex="-1">\n      ', l = n["if"].call(t, (l = t && t.options, l = null == l || l === !1 ? l : l.toolbar, null == l || l === !1 ? l : l.fa), {hash: {}, inverse: h.program(5, s, i), fn: h.program(3, a, i), data: i}), (l || 0 === l) && (c += l), c += "\n    </a>\n  </div>\n</li>\n"
}), this.wysihtml5.tpl.image = Handlebars.template(function (e, t, n, o, i) {
    function r() {
        return"modal-sm"
    }

    function a(e) {
        var t, n = "";
        return n += "btn-" + h((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === d ? t.apply(e) : t))
    }

    function s() {
        return'\n      <span class="fa fa-file-image-o"></span>\n    '
    }

    function l() {
        return'\n      <span class="glyphicon glyphicon-picture"></span>\n    '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var c, u = "", d = "function", h = this.escapeExpression, f = this;
    return u += '<li>\n  <div class="bootstrap-wysihtml5-insert-image-modal modal fade" data-wysihtml5-dialog="insertImage">\n    <div class="modal-dialog ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.smallmodals), {hash: {}, inverse: f.noop, fn: f.program(1, r, i), data: i}), (c || 0 === c) && (u += c), u += '">\n      <div class="modal-content">\n        <div class="modal-header">\n          <a class="close" data-dismiss="modal">&times;</a>\n          <h3>' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.image, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '</h3>\n        </div>\n        <div class="modal-body">\n          <div class="form-group">\n            <input value="http://" class="bootstrap-wysihtml5-insert-image-url form-control">\n          </div> \n        </div>\n        <div class="modal-footer">\n          <a class="btn btn-default" data-dismiss="modal" data-wysihtml5-dialog-action="cancel" href="#">' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.image, c = null == c || c === !1 ? c : c.cancel, typeof c === d ? c.apply(t) : c)) + '</a>\n          <a class="btn btn-primary" data-dismiss="modal"  data-wysihtml5-dialog-action="save" href="#">' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.image, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '</a>\n        </div>\n      </div>\n    </div>\n  </div>\n  <a class="btn ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.size), {hash: {}, inverse: f.noop, fn: f.program(3, a, i), data: i}), (c || 0 === c) && (u += c), u += ' btn-default" data-wysihtml5-command="insertImage" title="' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.image, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '" tabindex="-1">\n    ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.fa), {hash: {}, inverse: f.program(7, l, i), fn: f.program(5, s, i), data: i}), (c || 0 === c) && (u += c), u += "\n  </a>\n</li>\n"
}), this.wysihtml5.tpl.link = Handlebars.template(function (e, t, n, o, i) {
    function r() {
        return"modal-sm"
    }

    function a(e) {
        var t, n = "";
        return n += "btn-" + h((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === d ? t.apply(e) : t))
    }

    function s() {
        return'\n      <span class="fa fa-share-square-o"></span>\n    '
    }

    function l() {
        return'\n      <span class="glyphicon glyphicon-share"></span>\n    '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var c, u = "", d = "function", h = this.escapeExpression, f = this;
    return u += '<li>\n  <div class="bootstrap-wysihtml5-insert-link-modal modal fade" data-wysihtml5-dialog="createLink">\n    <div class="modal-dialog ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.smallmodals), {hash: {}, inverse: f.noop, fn: f.program(1, r, i), data: i}), (c || 0 === c) && (u += c), u += '">\n      <div class="modal-content">\n        <div class="modal-header">\n          <a class="close" data-dismiss="modal">&times;</a>\n          <h3>' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.link, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '</h3>\n        </div>\n        <div class="modal-body">\n          <div class="form-group">\n            <input value="http://" class="bootstrap-wysihtml5-insert-link-url form-control" data-wysihtml5-dialog-field="href">\n          </div> \n          <div class="checkbox">\n            <label> \n              <input type="checkbox" class="bootstrap-wysihtml5-insert-link-target" checked>' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.link, c = null == c || c === !1 ? c : c.target, typeof c === d ? c.apply(t) : c)) + '\n            </label>\n          </div>\n        </div>\n        <div class="modal-footer">\n          <a class="btn btn-default" data-dismiss="modal" data-wysihtml5-dialog-action="cancel" href="#">' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.link, c = null == c || c === !1 ? c : c.cancel, typeof c === d ? c.apply(t) : c)) + '</a>\n          <a href="#" class="btn btn-primary" data-dismiss="modal" data-wysihtml5-dialog-action="save">' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.link, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '</a>\n        </div>\n      </div>\n    </div>\n  </div>\n  <a class="btn ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.size), {hash: {}, inverse: f.noop, fn: f.program(3, a, i), data: i}), (c || 0 === c) && (u += c), u += ' btn-default" data-wysihtml5-command="createLink" title="' + h((c = t && t.locale, c = null == c || c === !1 ? c : c.link, c = null == c || c === !1 ? c : c.insert, typeof c === d ? c.apply(t) : c)) + '" tabindex="-1">\n    ', c = n["if"].call(t, (c = t && t.options, c = null == c || c === !1 ? c : c.toolbar, null == c || c === !1 ? c : c.fa), {hash: {}, inverse: f.program(7, l, i), fn: f.program(5, s, i), data: i}), (c || 0 === c) && (u += c), u += "\n  </a>\n</li>\n"
}), this.wysihtml5.tpl.lists = Handlebars.template(function (e, t, n, o, i) {
    function r(e) {
        var t, n = "";
        return n += "btn-" + y((t = e && e.options, t = null == t || t === !1 ? t : t.toolbar, t = null == t || t === !1 ? t : t.size, typeof t === g ? t.apply(e) : t))
    }

    function a() {
        return'\n      <span class="fa fa-list-ul"></span>\n    '
    }

    function s() {
        return'\n      <span class="glyphicon glyphicon-list"></span>\n    '
    }

    function l() {
        return'\n      <span class="fa fa-list-ol"></span>\n    '
    }

    function c() {
        return'\n      <span class="glyphicon glyphicon-th-list"></span>\n    '
    }

    function u() {
        return'\n      <span class="fa fa-outdent"></span>\n    '
    }

    function d() {
        return'\n      <span class="glyphicon glyphicon-indent-right"></span>\n    '
    }

    function h() {
        return'\n      <span class="fa fa-indent"></span>\n    '
    }

    function f() {
        return'\n      <span class="glyphicon glyphicon-indent-left"></span>\n    '
    }

    this.compilerInfo = [4, ">= 1.0.0"], n = this.merge(n, e.helpers), i = i || {};
    var p, m = "", g = "function", y = this.escapeExpression, v = this;
    return m += '<li>\n  <div class="btn-group">\n    <a class="btn ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.size), {hash: {}, inverse: v.noop, fn: v.program(1, r, i), data: i}), (p || 0 === p) && (m += p), m += ' btn-default" data-wysihtml5-command="insertUnorderedList" title="' + y((p = t && t.locale, p = null == p || p === !1 ? p : p.lists, p = null == p || p === !1 ? p : p.unordered, typeof p === g ? p.apply(t) : p)) + '" tabindex="-1">\n    ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.fa), {hash: {}, inverse: v.program(5, s, i), fn: v.program(3, a, i), data: i}), (p || 0 === p) && (m += p), m += '\n    </a>\n    <a class="btn ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.size), {hash: {}, inverse: v.noop, fn: v.program(1, r, i), data: i}), (p || 0 === p) && (m += p), m += ' btn-default" data-wysihtml5-command="insertOrderedList" title="' + y((p = t && t.locale, p = null == p || p === !1 ? p : p.lists, p = null == p || p === !1 ? p : p.ordered, typeof p === g ? p.apply(t) : p)) + '" tabindex="-1">\n    ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.fa), {hash: {}, inverse: v.program(9, c, i), fn: v.program(7, l, i), data: i}), (p || 0 === p) && (m += p), m += '\n    </a>\n    <a class="btn ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.size), {hash: {}, inverse: v.noop, fn: v.program(1, r, i), data: i}), (p || 0 === p) && (m += p), m += ' btn-default" data-wysihtml5-command="Outdent" title="' + y((p = t && t.locale, p = null == p || p === !1 ? p : p.lists, p = null == p || p === !1 ? p : p.outdent, typeof p === g ? p.apply(t) : p)) + '" tabindex="-1">\n    ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.fa), {hash: {}, inverse: v.program(13, d, i), fn: v.program(11, u, i), data: i}), (p || 0 === p) && (m += p), m += '\n    </a>\n    <a class="btn ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.size), {hash: {}, inverse: v.noop, fn: v.program(1, r, i), data: i}), (p || 0 === p) && (m += p), m += ' btn-default" data-wysihtml5-command="Indent" title="' + y((p = t && t.locale, p = null == p || p === !1 ? p : p.lists, p = null == p || p === !1 ? p : p.indent, typeof p === g ? p.apply(t) : p)) + '" tabindex="-1">\n    ', p = n["if"].call(t, (p = t && t.options, p = null == p || p === !1 ? p : p.toolbar, null == p || p === !1 ? p : p.fa), {hash: {}, inverse: v.program(17, f, i), fn: v.program(15, h, i), data: i}), (p || 0 === p) && (m += p), m += "\n    </a>\n  </div>\n</li>\n"
}), function (e) {
    "function" == typeof define && define.amd ? define("bootstrap.wysihtml5", ["jquery", "wysihtml5", "bootstrap", "bootstrap.wysihtml5.templates", "bootstrap.wysihtml5.commands"], e) : e(jQuery, wysihtml5)
}(function (e, t) {
    var n = function (e, t) {
        "use strict";
        var n = function (e, n, o) {
            return t.tpl[e] ? t.tpl[e]({locale: n, options: o}) : void 0
        }, o = function (n, o) {
            this.el = n;
            var i = e.extend(!0, {}, r, o);
            for (var a in i.customTemplates)t.tpl[a] = i.customTemplates[a];
            this.toolbar = this.createToolbar(n, i), this.editor = this.createEditor(i)
        };
        o.prototype = {constructor: o, createEditor: function (n) {
            n = n || {}, n = e.extend(!0, {}, n), n.toolbar = this.toolbar[0];
            var o = new t.Editor(this.el[0], n);
            if (o.composer.editableArea.contentDocument ? this.addMoreShortcuts(o, o.composer.editableArea.contentDocument.body || o.composer.editableArea.contentDocument, n.shortcuts) : this.addMoreShortcuts(o, o.composer.editableArea, n.shortcuts), n && n.events)for (var i in n.events)o.on(i, n.events[i]);
            return o.on("beforeload", this.syncBootstrapDialogEvents), o
        }, syncBootstrapDialogEvents: function () {
            var t = this;
            e.map(this.toolbar.commandMapping, function (e) {
                return[e]
            }).filter(function (e) {
                return e.dialog
            }).map(function (e) {
                return e.dialog
            }).forEach(function (n) {
                n.on("show", function () {
                    e(this.container).modal("show")
                }), n.on("hide", function () {
                    e(this.container).modal("hide"), t.composer.focus()
                }), e(n.container).on("shown.bs.modal", function () {
                    e(this).find("input, select, textarea").first().focus()
                })
            })
        }, createToolbar: function (t, o) {
            var i = this, s = e("<ul/>", {"class": "wysihtml5-toolbar", style: "display:none"}), l = o.locale || r.locale || "en";
            a.hasOwnProperty(l) || (l = "en");
            var c = e.extend(!0, {}, a.en, a[l]);
            for (var u in o.toolbar)o.toolbar[u] && (s.append(n(u, c, o)), "html" === u && this.initHtml(s));
            return s.find('a[data-wysihtml5-command="formatBlock"]').click(function (t) {
                var n = t.delegateTarget || t.target || t.srcElement, o = e(n), r = o.data("wysihtml5-display-format-name"), a = o.data("wysihtml5-format-name") || o.html();
                (void 0 === r || "true" === r) && i.toolbar.find(".current-font").text(a)
            }), s.find('a[data-wysihtml5-command="foreColor"]').click(function (t) {
                var n = t.target || t.srcElement, o = e(n);
                i.toolbar.find(".current-color").text(o.html())
            }), this.el.before(s), s
        }, initHtml: function (e) {
            var t = 'a[data-wysihtml5-action="change_view"]';
            e.find(t).click(function () {
                e.find("a.btn").not(t).toggleClass("disabled")
            })
        }, addMoreShortcuts: function (e, n, o) {
            t.dom.observe(n, "keydown", function (n) {
                var i = n.keyCode, r = o[i];
                if ((n.ctrlKey || n.metaKey || n.altKey) && r && t.commands[r]) {
                    var a = e.toolbar.commandMapping[r + ":null"];
                    a && a.dialog && !a.state ? a.dialog.show() : t.commands[r].exec(e.composer, r), n.preventDefault()
                }
            })
        }};
        var i = {resetDefaults: function () {
            e.fn.wysihtml5.defaultOptions = e.extend(!0, {}, e.fn.wysihtml5.defaultOptionsCache)
        }, bypassDefaults: function (t) {
            return this.each(function () {
                var n = e(this);
                n.data("wysihtml5", new o(n, t))
            })
        }, shallowExtend: function (t) {
            var n = e.extend({}, e.fn.wysihtml5.defaultOptions, t || {}, e(this).data()), o = this;
            return i.bypassDefaults.apply(o, [n])
        }, deepExtend: function (t) {
            var n = e.extend(!0, {}, e.fn.wysihtml5.defaultOptions, t || {}), o = this;
            return i.bypassDefaults.apply(o, [n])
        }, init: function (e) {
            var t = this;
            return i.shallowExtend.apply(t, [e])
        }};
        e.fn.wysihtml5 = function (t) {
            return i[t] ? i[t].apply(this, Array.prototype.slice.call(arguments, 1)) : "object" != typeof t && t ? void e.error("Method " + t + " does not exist on jQuery.wysihtml5") : i.init.apply(this, arguments)
        }, e.fn.wysihtml5.Constructor = o;
        var r = e.fn.wysihtml5.defaultOptions = {toolbar: {"font-styles": !0, color: !1, emphasis: {small: !0}, blockquote: !0, lists: !0, html: !1, link: !0, image: !0, smallmodals: !1}, parserRules: {classes: {"wysiwyg-color-silver": 1, "wysiwyg-color-gray": 1, "wysiwyg-color-white": 1, "wysiwyg-color-maroon": 1, "wysiwyg-color-red": 1, "wysiwyg-color-purple": 1, "wysiwyg-color-fuchsia": 1, "wysiwyg-color-green": 1, "wysiwyg-color-lime": 1, "wysiwyg-color-olive": 1, "wysiwyg-color-yellow": 1, "wysiwyg-color-navy": 1, "wysiwyg-color-blue": 1, "wysiwyg-color-teal": 1, "wysiwyg-color-aqua": 1, "wysiwyg-color-orange": 1}, tags: {b: {}, i: {}, strong: {}, em: {}, p: {}, br: {}, ol: {}, ul: {}, li: {}, h1: {}, h2: {}, h3: {}, h4: {}, h5: {}, h6: {}, blockquote: {}, u: 1, img: {check_attributes: {width: "numbers", alt: "alt", src: "url", height: "numbers"}}, a: {check_attributes: {href: "url"}, set_attributes: {target: "_blank", rel: "nofollow"}}, span: 1, div: 1, small: 1, code: 1, pre: 1}}, locale: "en", shortcuts: {83: "small"}};
        "undefined" == typeof e.fn.wysihtml5.defaultOptionsCache && (e.fn.wysihtml5.defaultOptionsCache = e.extend(!0, {}, e.fn.wysihtml5.defaultOptions));
        var a = e.fn.wysihtml5.locale = {}
    };
    n(e, t)
}), function (e) {
    e.commands.small = {exec: function (t, n) {
        return e.commands.formatInline.exec(t, n, "small")
    }, state: function (t, n) {
        return e.commands.formatInline.state(t, n, "small")
    }}
}(wysihtml5), function (e) {
    "function" == typeof define && define.amd ? define("bootstrap.wysihtml5.en-US", ["jquery", "bootstrap.wysihtml5"], e) : e(jQuery)
}(function (e) {
    e.fn.wysihtml5.locale.en = e.fn.wysihtml5.locale["en-US"] = {font_styles: {normal: "Normal text", h1: "Heading 1", h2: "Heading 2", h3: "Heading 3", h4: "Heading 4", h5: "Heading 5", h6: "Heading 6"}, emphasis: {bold: "Bold", italic: "Italic", underline: "Underline", small: "Small"}, lists: {unordered: "Unordered list", ordered: "Ordered list", outdent: "Outdent", indent: "Indent"}, link: {insert: "Insert link", cancel: "Cancel", target: "Open link in new window"}, image: {insert: "Insert image", cancel: "Cancel"}, html: {edit: "Edit HTML"}, colours: {black: "Black", silver: "Silver", gray: "Grey", maroon: "Maroon", red: "Red", purple: "Purple", green: "Green", olive: "Olive", navy: "Navy", blue: "Blue", orange: "Orange"}}
}), function () {
    $(document).on("page:change", function () {
        return null != window._gaq ? _gaq.push(["_trackPageview"]) : null != window.pageTracker ? pageTracker._trackPageview() : void 0
    })
}.call(this);
var _downloadsChart, _chartOptions, _usersChart, suggestions_distance, section_floating, header_height, is_bottom = !1, pay_height, license_type = 1, product_price, payment_visible = !1;
$(document).on("page:load", onPageLoad), $(document).ready(onPageLoad);
var click_rate = 0;
filled = !1, fixed_section = !1;
var scrollCheckForPayment = debounce(function () {
    $(this).scrollTop() > header_height ? fixed_section || (fixed_section = !0, $pay_summary.addClass("float"), $pay_summary.hasClass("purchase-flow") && $pay_summary.removeClass("is_top")) : fixed_section && (fixed_section = !1, $pay_summary.hasClass("purchase-flow") ? $pay_summary.addClass("is_top").css("top", 0) : $pay_summary.removeClass("float")), suggestions_distance && (scroll_top = $(window).scrollTop(), distance = suggestions_distance.top - pay_height - scroll_top - 70, 0 > distance ? is_bottom || ($pay_summary.addClass("is_bottom").css("top", suggestions_distance.top - pay_height - header_height - 70), is_bottom = !0) : is_bottom && ($pay_summary.removeClass("is_bottom").css("top", 0), is_bottom = !1))
}, 10);