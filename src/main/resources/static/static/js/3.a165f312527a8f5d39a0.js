webpackJsonp([3],{A7yg:function(t,e,s){
/*!
 * vue-virtual-scroll-list v2.1.3
 * open source under the MIT license
 * https://github.com/tangbc/vue-virtual-scroll-list#readme
 */var i;i=function(t){"use strict";function e(t,e){for(var s=0;s<e.length;s++){var i=e[s];i.enumerable=i.enumerable||!1,i.configurable=!0,"value"in i&&(i.writable=!0),Object.defineProperty(t,i.key,i)}}t=t&&Object.prototype.hasOwnProperty.call(t,"default")?t.default:t;var s="FRONT",i="BEHIND",a="INIT",n="FIXED",r="DYNAMIC",o=function(){function t(e,s){!function(t,e){if(!(t instanceof e))throw new TypeError("Cannot call a class as a function")}(this,t),this.init(e,s)}var o,l,c;return o=t,(l=[{key:"init",value:function(t,e){this.param=t,this.callUpdate=e,this.sizes=new Map,this.firstRangeTotalSize=0,this.firstRangeAverageSize=0,this.lastCalcIndex=0,this.fixedSizeValue=0,this.calcType=a,this.offset=0,this.direction="",this.range=Object.create(null),t&&this.checkRange(0,t.keeps-1)}},{key:"destroy",value:function(){this.init(null,null)}},{key:"getRange",value:function(){var t=Object.create(null);return t.start=this.range.start,t.end=this.range.end,t.padFront=this.range.padFront,t.padBehind=this.range.padBehind,t}},{key:"isBehind",value:function(){return this.direction===i}},{key:"isFront",value:function(){return this.direction===s}},{key:"getOffset",value:function(t){return t<1?0:this.getIndexOffset(t)}},{key:"updateParam",value:function(t,e){this.param&&t in this.param&&(this.param[t]=e)}},{key:"saveSize",value:function(t,e){this.sizes.set(t,e),this.calcType===a?(this.fixedSizeValue=e,this.calcType=n):this.calcType===n&&this.fixedSizeValue!==e&&(this.calcType=r,delete this.fixedSizeValue),this.sizes.size<=this.param.keeps?(this.firstRangeTotalSize=this.firstRangeTotalSize+e,this.firstRangeAverageSize=Math.round(this.firstRangeTotalSize/this.sizes.size)):delete this.firstRangeTotalSize}},{key:"handleDataSourcesChange",value:function(){var t=this.range.start;this.isFront()?t-=2:this.isBehind()&&(t+=2),t=Math.max(t,0),this.updateRange(this.range.start,this.getEndByStart(t))}},{key:"handleSlotSizeChange",value:function(){this.handleDataSourcesChange()}},{key:"handleScroll",value:function(t){this.direction=t<this.offset?s:i,this.offset=t,this.direction===s?this.handleFront():this.direction===i&&this.handleBehind()}},{key:"handleFront",value:function(){var t=this.getScrollOvers();if(!(t>this.range.start)){var e=Math.max(t-this.param.buffer,0);this.checkRange(e,this.getEndByStart(e))}}},{key:"handleBehind",value:function(){var t=this.getScrollOvers();t<this.range.start+this.param.buffer||this.checkRange(t,this.getEndByStart(t))}},{key:"getScrollOvers",value:function(){var t=this.offset-this.param.slotHeaderSize;if(t<=0)return 0;if(this.isFixedType())return Math.floor(t/this.fixedSizeValue);for(var e=0,s=0,i=0,a=this.param.uniqueIds.length;e<=a;){if(s=e+Math.floor((a-e)/2),(i=this.getIndexOffset(s))===t)return s;i<t?e=s+1:i>t&&(a=s-1)}return e>0?--e:0}},{key:"getIndexOffset",value:function(t){if(!t)return 0;for(var e=0,s=0;s<t;s++)e+=this.sizes.get(this.param.uniqueIds[s])||this.getEstimateSize();return this.lastCalcIndex=Math.max(this.lastCalcIndex,t-1),this.lastCalcIndex=Math.min(this.lastCalcIndex,this.getLastIndex()),e}},{key:"isFixedType",value:function(){return this.calcType===n}},{key:"getLastIndex",value:function(){return this.param.uniqueIds.length-1}},{key:"checkRange",value:function(t,e){var s=this.param.keeps;this.param.uniqueIds.length<=s?(t=0,e=this.getLastIndex()):e-t<s-1&&(t=e-s+1),this.range.start!==t&&this.updateRange(t,e)}},{key:"updateRange",value:function(t,e){this.range.start=t,this.range.end=e,this.range.padFront=this.getPadFront(),this.range.padBehind=this.getPadBehind(),this.callUpdate(this.getRange())}},{key:"getEndByStart",value:function(t){var e=t+this.param.keeps-1;return Math.min(e,this.getLastIndex())}},{key:"getPadFront",value:function(){return this.isFixedType()?this.fixedSizeValue*this.range.start:this.getIndexOffset(this.range.start)}},{key:"getPadBehind",value:function(){var t=this.range.end,e=this.getLastIndex();return this.isFixedType()?(e-t)*this.fixedSizeValue:this.lastCalcIndex===e?this.getIndexOffset(e)-this.getIndexOffset(t):(e-t)*this.getEstimateSize()}},{key:"getEstimateSize",value:function(){return this.firstRangeAverageSize||this.param.size}}])&&e(o.prototype,l),c&&e(o,c),t}(),l={size:{type:Number,required:!0},keeps:{type:Number,required:!0},dataKey:{type:String,required:!0},dataSources:{type:Array,required:!0},dataComponent:{type:[Object,Function],required:!0},extraProps:{type:Object},rootTag:{type:String,default:"div"},wrapTag:{type:String,default:"div"},wrapClass:{type:String,default:""},direction:{type:String,default:"vertical"},topThreshold:{type:Number,default:0},bottomThreshold:{type:Number,default:0},start:{type:Number,default:0},offset:{type:Number,default:0},itemTag:{type:String,default:"div"},itemClass:{type:String,default:""},itemClassAdd:{type:Function},headerTag:{type:String,default:"div"},headerClass:{type:String,default:""},footerTag:{type:String,default:"div"},footerClass:{type:String,default:""},disabled:{type:Boolean,default:!1}},c={index:{type:Number},event:{type:String},tag:{type:String},horizontal:{type:Boolean},source:{type:Object},component:{type:[Object,Function]},uniqueKey:{type:String},extraProps:{type:Object}},u={event:{type:String},uniqueKey:{type:String},tag:{type:String},horizontal:{type:Boolean}},h={created:function(){this.shapeKey=this.horizontal?"offsetWidth":"offsetHeight"},mounted:function(){var t=this;"undefined"!=typeof ResizeObserver&&(this.resizeObserver=new ResizeObserver(function(){t.dispatchSizeChange()}),this.resizeObserver.observe(this.$el))},updated:function(){this.dispatchSizeChange()},beforeDestroy:function(){this.resizeObserver&&(this.resizeObserver.disconnect(),this.resizeObserver=null)},methods:{getCurrentSize:function(){return this.$el?this.$el[this.shapeKey]:0},dispatchSizeChange:function(){this.$parent.$emit(this.event,this.uniqueKey,this.getCurrentSize(),this.hasInitial)}}},d=t.component("virtual-list-item",{mixins:[h],props:c,render:function(t){var e=this.tag,s=this.component,i=this.extraProps,a=void 0===i?{}:i,n=this.index;return a.source=this.source,a.index=n,t(e,{role:"item"},[t(s,{props:a})])}}),f=t.component("virtual-list-slot",{mixins:[h],props:u,render:function(t){return t(this.tag,{attrs:{role:this.uniqueKey}},this.$slots.default)}}),g="item_resize",m="slot_resize",p="header",v="footer";return t.component("virtual-list",{props:l,data:function(){return{range:null}},watch:{"dataSources.length":function(){this.virtual.updateParam("uniqueIds",this.getUniqueIdFromDataSources()),this.virtual.handleDataSourcesChange()},start:function(t){this.scrollToIndex(t)},offset:function(t){this.scrollToOffset(t)}},created:function(){this.isHorizontal="horizontal"===this.direction,this.directionKey=this.isHorizontal?"scrollLeft":"scrollTop",this.installVirtual(),this.$on(g,this.onItemResized),(this.$slots.header||this.$slots.footer)&&this.$on(m,this.onSlotResized)},activated:function(){this.scrollToOffset(this.virtual.offset)},mounted:function(){this.start?this.scrollToIndex(this.start):this.offset&&this.scrollToOffset(this.offset)},beforeDestroy:function(){this.virtual.destroy()},methods:{getSize:function(t){return this.virtual.sizes.get(t)},getSizes:function(){return this.virtual.sizes.size},scrollToOffset:function(t){var e=this.$refs.root;e&&(e[this.directionKey]=t||0)},scrollToIndex:function(t){if(t>=this.dataSources.length-1)this.scrollToBottom();else{var e=this.virtual.getOffset(t);this.scrollToOffset(e)}},scrollToBottom:function(){var t=this,e=this.$refs.shepherd;e&&(e.scrollIntoView(!1),setTimeout(function(){t.getOffset()+t.getClientSize()<t.getScrollSize()&&t.scrollToBottom()},3))},reset:function(){this.virtual.destroy(),this.scrollToOffset(0),this.installVirtual()},installVirtual:function(){this.virtual=new o({size:this.size,slotHeaderSize:0,slotFooterSize:0,keeps:this.keeps,buffer:Math.round(this.keeps/3),uniqueIds:this.getUniqueIdFromDataSources()},this.onRangeChanged),this.range=this.virtual.getRange()},getUniqueIdFromDataSources:function(){var t=this;return this.dataSources.map(function(e){return e[t.dataKey]})},getOffset:function(){var t=this.$refs.root;return t?Math.ceil(t[this.directionKey]):0},getClientSize:function(){var t=this.$refs.root;return t?t[this.isHorizontal?"clientWidth":"clientHeight"]:0},getScrollSize:function(){var t=this.$refs.root;return t?t[this.isHorizontal?"scrollWidth":"scrollHeight"]:0},onItemResized:function(t,e){this.virtual.saveSize(t,e),this.$emit("resized",t,e)},onSlotResized:function(t,e,s){t===p?this.virtual.updateParam("slotHeaderSize",e):t===v&&this.virtual.updateParam("slotFooterSize",e),s&&this.virtual.handleSlotSizeChange()},onRangeChanged:function(t){this.range=t},onScroll:function(t){var e=this.getOffset(),s=this.getClientSize(),i=this.getScrollSize();e<0||e+s>i||!i||(this.virtual.handleScroll(e),this.emitEvent(e,s,i,t))},emitEvent:function(t,e,s,i){this.$emit("scroll",i,this.virtual.getRange()),this.virtual.isFront()&&this.dataSources.length&&t-this.topThreshold<=0?this.$emit("totop"):this.virtual.isBehind()&&t+e+this.bottomThreshold>=s&&this.$emit("tobottom")},getRenderSlots:function(t){for(var e=[],s=this.range,i=s.start,a=s.end,n=this.dataSources,r=this.dataKey,o=this.itemClass,l=this.itemTag,c=this.isHorizontal,u=this.extraProps,h=this.dataComponent,f=i;f<=a;f++){var m=n[f];m?m[r]?e.push(t(d,{props:{index:f,tag:l,event:g,horizontal:c,uniqueKey:m[r],source:m,extraProps:u,component:h},class:"".concat(o," ").concat(this.itemClassAdd?this.itemClassAdd(f):"")})):console.warn("Cannot get the data-key '".concat(r,"' from data-sources.")):console.warn("Cannot get the index '".concat(f,"' from data-sources."))}return e}},render:function(t){var e=this.$slots,s=e.header,i=e.footer,a=this.range,n=a.padFront,r=a.padBehind,o=this.rootTag,l=this.headerClass,c=this.headerTag,u=this.wrapTag,h=this.wrapClass,d=this.footerClass,g=this.footerTag,_=this.isHorizontal?"0px ".concat(r,"px 0px ").concat(n,"px"):"".concat(n,"px 0px ").concat(r,"px");return t(o,{ref:"root",on:{"&scroll":this.onScroll}},[s?t(f,{class:l,props:{tag:c,event:m,uniqueKey:p}},s):null,t(u,{class:h,attrs:{role:"group"},style:{padding:_}},this.getRenderSlots(t)),i?t(f,{class:d,props:{tag:g,event:m,uniqueKey:v}},i):null,t("div",{ref:"shepherd"})])}})},t.exports=i(s("7+uW"))},BO1k:function(t,e,s){t.exports={default:s("fxRn"),__esModule:!0}},CZZn:function(t,e){},GBg1:function(t,e){},"Rn/Q":function(t,e){},T2TS:function(t,e){},fxRn:function(t,e,s){s("+tPU"),s("zQR9"),t.exports=s("g8Ux")},g8Ux:function(t,e,s){var i=s("77Pl"),a=s("3fs2");t.exports=s("FeBl").getIterator=function(t){var e=a(t);if("function"!=typeof e)throw TypeError(t+" is not iterable!");return i(e.call(t))}},zfg2:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var i=s("Xxa5"),a=s.n(i),n=s("exGp"),r=s.n(n),o=s("Dd8w"),l=s.n(o),c=s("PJh5"),u=s.n(c),h=s("NYxO"),d={name:"ImDialog",props:{active:Boolean,push:Number,online:Boolean,me:Boolean,info:Object},computed:{statusText:function(){return this.online?"Онлайн":"был в сети "+u()(this.info.last_message.recipient.last_online_time).fromNow()}}},f={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"im-dialog",class:{active:t.active,push:t.push}},[s("router-link",{staticClass:"im-dailog__pic",attrs:{to:{name:"ProfileId",params:{id:t.info.last_message.recipient.id}}}},[s("img",{attrs:{src:t.info.last_message.recipient.photo,alt:t.info.last_message.recipient.first_name}})]),s("div",{staticClass:"im-dialog__info"},[s("router-link",{staticClass:"im-dialog__name",attrs:{to:{name:"ProfileId",params:{id:t.info.last_message.recipient.id}}}},[t._v(t._s(t.info.last_message.recipient.first_name+" "+t.info.last_message.recipient.last_name))]),s("span",{staticClass:"user-status",class:{online:t.online}},[t._v(t._s(t.statusText))])],1),s("div",{staticClass:"im-dialog__content"},[s("p",{staticClass:"im-dialog__last"},[t.me?s("span",{staticClass:"im-dialog__last-me"},[t._v("Вы:")]):t._e(),t._v(t._s(t.info.last_message.message_text))]),0!=t.info.last_message.time?s("span",{staticClass:"im-dialog__time"},[t._v(t._s(t._f("moment")(t.info.last_message.time,"from")))]):t._e()]),t.push>0?s("span",{staticClass:"im-dialog__push"},[t._v(t._s(t.push))]):t._e()],1)},staticRenderFns:[]};var g=s("VU/8")(d,f,!1,function(t){s("CZZn")},null,null).exports,m=s("BO1k"),p=s.n(m),v=(s("7+uW"),{name:"infinite-loading-item",props:{source:{type:Object,default:function(){return{}}}}}),_={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return t.source.stubDate?s("h5",{staticClass:"im-chat__message-title"},[t._v(t._s(t._f("moment")(t.source.date,"DD MMMM YYYY")))]):"READ"==t.source.read_status?s("div",{staticClass:"im-chat__message-block",class:{me:t.source.isSentByMe}},[s("p",{staticClass:"im-chat__message-text"},[t._v(t._s(t.source.message_text))]),s("span",{staticClass:"im-chat__message-time"},[t._v(t._s(t._f("moment")(t.source.time,"YYYY-MM-DD hh:mm")))])]):"SENT"==t.source.read_status?s("div",{staticClass:"im-chat__message-block",class:{me_unread:t.source.isSentByMe}},[s("p",{staticClass:"im-chat__message-text"},[t._v(t._s(t.source.message_text))]),s("span",{staticClass:"im-chat__message-time"},[t._v(t._s(t._f("moment")(t.source.time,"YYYY-MM-DD hh:mm")))])]):t._e()},staticRenderFns:[]};var y=s("VU/8")(v,_,!1,function(t){s("Rn/Q")},"data-v-7dc97db0",null).exports,S=(s("A7yg"),function(t){return{sid:"group-"+t,stubDate:!0,date:t}}),x={name:"ImChat",props:{info:Object,messages:Array,online:Boolean},data:function(){return{mes:"",itemComponent:y,isUserViewHistory:!1,fetching:!1}},mounted:function(){this.follow=!0},watch:{messages:function(){this.follow&&this.setVirtualListToBottom()}},computed:{statusText:function(){return this.online?"Онлайн":"был в сети "+u()(this.info.last_message.recipient.last_online_time).fromNow()},messagesGrouped:function(){var t=[],e=null,s=!0,i=!1,a=void 0;try{for(var n,r=p()(this.messages);!(s=(n=r.next()).done);s=!0){var o=n.value,l=u()(o.time).format("YYYY-MM-DD");l!==e&&(e=l,t.push(S(e))),t.push(o)}}catch(t){i=!0,a=t}finally{try{!s&&r.return&&r.return()}finally{if(i)throw a}}return t}},methods:l()({},Object(h.b)("profile/dialogs",["postMessage","loadOlderMessages","markMessageRead"]),Object(h.c)("profile/dialogs",["isHistoryEndReached"]),{onSubmitMessage:function(){this.postMessage({id:this.info.id,message_text:this.mes}),this.mes=""},onScrollToTop:function(){var t=this;return r()(a.a.mark(function e(){var s;return a.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:if(!t.$refs.vsl){e.next=8;break}if(t.isHistoryEndReached()){e.next=8;break}return s=t.messagesGrouped[0],t.fetching=!0,e.next=6,t.loadOlderMessages();case 6:t.setVirtualListToOffset(1),t.$nextTick(function(){var e=0,i=!0,a=!1,n=void 0;try{for(var r,o=p()(t.messagesGrouped);!(i=(r=o.next()).done);i=!0){var l=r.value;if(l.sid===s.sid)break;e+=t.$refs.vsl.getSize(l.sid)}}catch(t){a=!0,n=t}finally{try{!i&&o.return&&o.return()}finally{if(a)throw n}}t.setVirtualListToOffset(e),t.fetching=!1});case 8:case"end":return e.stop()}},e,t)}))()},onScroll:function(){this.follow=!1},onScrollToBottom:function(){this.follow=!0},setVirtualListToOffset:function(t){this.$refs.vsl&&this.$refs.vsl.scrollToOffset(t)},setVirtualListToBottom:function(){this.$refs.vsl&&this.$refs.vsl.scrollToBottom()}})},z={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"im-chat"},[s("div",{staticClass:"im-chat__user"},[s("router-link",{staticClass:"im-chat__user-pic",attrs:{to:{name:"ProfileId",params:{id:t.info.last_message.recipient.id}}}},[s("img",{attrs:{src:t.info.last_message.recipient.photo,alt:t.info.last_message.recipient.first_name}})]),s("router-link",{staticClass:"im-chat__user-name",attrs:{to:{name:"ProfileId",params:{id:t.info.last_message.recipient.id}}}},[t._v(t._s(t.info.last_message.recipient.first_name+" "+t.info.last_message.recipient.last_name))]),s("span",{staticClass:"user-status",class:{online:t.online}},[t._v(t._s(t.statusText))])],1),s("div",{staticClass:"im-chat__infitite_list_wrapper"},[s("virtual-list",{ref:"vsl",staticClass:"im-chat__infitite_list scroll-touch",attrs:{size:60,keeps:120,"data-key":"sid","data-sources":t.messagesGrouped,"data-component":t.itemComponent,"wrap-class":"im-chat__message","root-tag":"section"},on:{totop:t.onScrollToTop,"&scroll":function(e){return t.onScroll(e)},tobottom:t.onScrollToBottom}},[s("div",{directives:[{name:"show",rawName:"v-show",value:t.fetching,expression:"fetching"}],staticClass:"im-chat__loader",attrs:{slot:"header"},slot:"header"},[s("div",{directives:[{name:"show",rawName:"v-show",value:!t.isHistoryEndReached(),expression:"!isHistoryEndReached()"}],staticClass:"spinner"}),s("div",{directives:[{name:"show",rawName:"v-show",value:t.isHistoryEndReached(),expression:"isHistoryEndReached()"}],staticClass:"finished"},[t._v("Больше сообщений нет")])])])],1),s("form",{staticClass:"im-chat__enter",attrs:{action:"#"},on:{submit:function(e){return e.preventDefault(),t.onSubmitMessage(e)}}},[s("input",{directives:[{name:"model",rawName:"v-model",value:t.mes,expression:"mes"}],staticClass:"im-chat__enter-input",attrs:{type:"text",placeholder:"Ваше сообщение..."},domProps:{value:t.mes},on:{input:function(e){e.target.composing||(t.mes=e.target.value)}}})])])},staticRenderFns:[]};var C={name:"Im",components:{ImDialog:g,ImChat:s("VU/8")(x,z,!1,function(t){s("T2TS")},null,null).exports},computed:l()({},Object(h.c)("profile/dialogs",["messages","activeDialog","dialogs"])),methods:l()({},Object(h.b)("profile/dialogs",["loadFreshMessages","switchDialog","closeDialog","createDialogWithUser","apiLoadAllDialogs"]),{countPush:function(t){return t>0?t:null},checkOnlineUser:function(t){return u()().diff(u()(t),"seconds")<=60},clickOnDialog:function(t){this.$router.push({name:"Im",query:{activeDialog:t}})},selectDialogByRoute:function(t,e){var s=this;return r()(a.a.mark(function i(){return a.a.wrap(function(s){for(;;)switch(s.prev=s.next){case 0:if(!t.query.activeDialog){s.next=4;break}e.switchDialog(t.query.activeDialog),s.next=16;break;case 4:if(!t.query.userId){s.next=8;break}e.createDialogWithUser(t.query.userId),s.next=16;break;case 8:if(!(e.dialogs.length>0)){s.next=12;break}e.$router.push({name:"Im",query:{activeDialog:e.dialogs[0].id}}),s.next=16;break;case 12:return s.next=14,e.apiLoadAllDialogs();case 14:e.dialogs.length>0&&e.$router.push({name:"Im",query:{activeDialog:e.dialogs[0].id}}),console.log("No dialogs at all");case 16:case"end":return s.stop()}},i,s)}))()}}),beforeRouteEnter:function(t,e,s){var i,n=this;s((i=r()(a.a.mark(function e(s){return a.a.wrap(function(e){for(;;)switch(e.prev=e.next){case 0:s.selectDialogByRoute(t,s);case 1:case"end":return e.stop()}},e,n)})),function(t){return i.apply(this,arguments)}))},beforeRouteUpdate:function(t,e,s){this.selectDialogByRoute(t,this),s()},beforeDestroy:function(){this.closeDialog()}},b={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"im"},[s("div",{staticClass:"im__dialogs"},t._l(t.dialogs,function(e){return s("im-dialog",{key:e.id,attrs:{info:e,push:t.countPush(e.unread_count),me:e.last_message.isSentByMe,active:t.activeDialog&&e.id===t.activeDialog.id,online:t.checkOnlineUser(e.last_message.recipient.last_online_time)},nativeOn:{click:function(s){return t.clickOnDialog(e.id)}}})}),1),t.activeDialog?s("div",{staticClass:"im__chat"},[s("im-chat",{attrs:{info:t.activeDialog,messages:t.messages,online:t.checkOnlineUser(t.activeDialog.last_message.recipient.last_online_time)}})],1):t._e()])},staticRenderFns:[]};var T=s("VU/8")(C,b,!1,function(t){s("GBg1")},null,null);e.default=T.exports}});
//# sourceMappingURL=3.a165f312527a8f5d39a0.js.map