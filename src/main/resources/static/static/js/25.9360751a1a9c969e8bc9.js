webpackJsonp([25],{ZNFZ:function(e,i,t){"use strict";Object.defineProperty(i,"__esModule",{value:!0});var a=t("Dd8w"),s=t.n(a),n=t("NYxO"),l=t("+cKO"),r=t("a2KH"),c=t("/QaM"),u=t("IcnI"),m={name:"ShiftEmail",components:{NumberField:r.a,EmailField:c.a},data:function(){return{email:"",code:0,number:""}},computed:s()({},Object(n.c)(["getCode"])),methods:s()({},Object(n.b)("profile/account",["emailShift"]),{submitHandler:function(){this.$v.$invalid?this.$v.$touch():this.emailShift({email:this.email})}}),mounted:function(){this.code=this.getCode},validations:{email:{required:l.required,email:l.email},number:{required:l.required,numeric:l.numeric,isCode:function(e){return+e===u.a.state.code}}}},o={render:function(){var e=this,i=e.$createElement,t=e._self._c||i;return t("div",{staticClass:"shift-email"},[t("form",{staticClass:"shift-email__form",on:{submit:function(i){return i.preventDefault(),e.submitHandler(i)}}},[t("div",{staticClass:"form__block"},[t("h4",{staticClass:"form__subtitle"},[e._v("Смена почтового ящика")]),t("email-field",{class:{checked:e.$v.email.required&&e.$v.email.email},attrs:{id:"shift-email",v:e.$v.email,placeholder:"Новый почтовый ящик"},model:{value:e.email,callback:function(i){e.email=i},expression:"email"}})],1),t("div",{staticClass:"form__block"},[t("h4",{staticClass:"form__subtitle"},[e._v("Введите код")]),t("span",{staticClass:"form__code"},[e._v(e._s(e.code))]),t("number-field",{class:{checked:e.$v.number.required&&e.$v.number.isCode},attrs:{id:"shift-number",v:e.$v.number},model:{value:e.number,callback:function(i){e.number=i},expression:"number"}})],1),t("div",{staticClass:"shift-email__action"},[t("button-hover",{attrs:{tag:"button",type:"submit",variant:"white"}},[e._v("Сменить")])],1)])])},staticRenderFns:[]};var d=t("VU/8")(m,o,!1,function(e){t("coW6")},null,null);i.default=d.exports},coW6:function(e,i){}});
//# sourceMappingURL=25.9360751a1a9c969e8bc9.js.map