webpackJsonp(["main"],{

/***/ "../../../../../src/$$_lazy_route_resource lazy recursive":
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncatched exception popping up in devtools
	return Promise.resolve().then(function() {
		throw new Error("Cannot find module '" + req + "'.");
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "../../../../../src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "\n<div class=\"container\">\n  <div class=\"jumbotron\">\n    <h1><i>Lib</i>:Intel</h1>\n  <router-outlet></router-outlet>\n  </div>\n</div>\n"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = (function () {
    function AppComponent() {
    }
    AppComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-root',
            template: __webpack_require__("../../../../../src/app/app.component.html"),
            styles: [__webpack_require__("../../../../../src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/esm5/platform-browser.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__app_routing__ = __webpack_require__("../../../../../src/app/app.routing.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_forms__ = __webpack_require__("../../../forms/esm5/forms.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__register_component__ = __webpack_require__("../../../../../src/app/register.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__login_component__ = __webpack_require__("../../../../../src/app/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__service_authentification_service__ = __webpack_require__("../../../../../src/app/service/authentification.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};









var AppModule = (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_1__angular_core__["I" /* NgModule */])({
            declarations: [
                __WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */], __WEBPACK_IMPORTED_MODULE_7__login_component__["a" /* LoginComponent */], __WEBPACK_IMPORTED_MODULE_6__register_component__["a" /* RegisterComponent */]
            ],
            imports: [
                __WEBPACK_IMPORTED_MODULE_4__angular_forms__["a" /* FormsModule */],
                __WEBPACK_IMPORTED_MODULE_5__angular_common_http__["b" /* HttpClientModule */],
                __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
                __WEBPACK_IMPORTED_MODULE_3__app_routing__["a" /* appRouting */]
            ],
            providers: [__WEBPACK_IMPORTED_MODULE_5__angular_common_http__["b" /* HttpClientModule */], __WEBPACK_IMPORTED_MODULE_8__service_authentification_service__["a" /* AuthentificationService */]],
            exports: [__WEBPACK_IMPORTED_MODULE_7__login_component__["a" /* LoginComponent */], __WEBPACK_IMPORTED_MODULE_6__register_component__["a" /* RegisterComponent */]],
            bootstrap: [__WEBPACK_IMPORTED_MODULE_2__app_component__["a" /* AppComponent */]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "../../../../../src/app/app.routing.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return appRouting; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__login_component__ = __webpack_require__("../../../../../src/app/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__register_component__ = __webpack_require__("../../../../../src/app/register.component.ts");



var routes = [
    { path: 'login', component: __WEBPACK_IMPORTED_MODULE_1__login_component__["a" /* LoginComponent */] },
    { path: 'register', component: __WEBPACK_IMPORTED_MODULE_2__register_component__["a" /* RegisterComponent */] },
    { path: '', redirectTo: '/login', pathMatch: 'full' }
];
var appRouting = __WEBPACK_IMPORTED_MODULE_0__angular_router__["b" /* RouterModule */].forRoot(routes);


/***/ }),

/***/ "../../../../../src/app/login.component.html":
/***/ (function(module, exports) {

module.exports = "  <div *ngIf=\"!authenticated\" class=\"col-md-6 col-md-offset-3\">\r\n    <h3>Login</h3>\r\n    <div class=\"panel-body\">\r\n      <form (submit)=\"login(username,password)\" (keyup.enter)=\"login(username,password)\">\r\n        <div class=\"form-group\">\r\n          <label for=\"username\">Username:</label> <input type=\"text\"\r\n                                                         class=\"form-control\" id=\"username\" name=\"username\"\r\n                                                         [(ngModel)]=\"username\"\r\n        autofocus />\r\n        </div>\r\n        <div class=\"form-group\">\r\n          <label for=\"password\">Password:</label> <input type=\"password\"\r\n                                                         class=\"form-control\" id=\"password\" name=\"password\"\r\n                                                         [(ngModel)]=\"password\" />\r\n        </div>\r\n        <button class=\"btn btn-success\">Login</button>\r\n        <button class=\"btn btn-primary\" routerLink=\"/register\">Neu anmelden</button>\r\n      </form>\r\n    </div>\r\n  </div>\r\n  <div *ngIf=\"authenticated\" class=\"col-md-6 col-md-offset-3\">\r\n    <div style=\"padding: 20px;\" class=\"row\">\r\n      <button *ngIf=\"fachreferent\" class=\"btn btn-primary\" onclick=\"window.location.href='/fachref'\">Fachref-Assistent</button>\r\n\r\n      <button *ngIf=\"admin\" class=\"btn btn-primary\" onclick=\"window.location.href='/admin'\">Administration</button>\r\n\r\n      <button *ngIf=\"media\" class=\"btn btn-primary\" onclick=\"window.location.href='/media'\">Medien</button>\r\n      <button *ngIf=\"analyst\" class=\"btn btn-primary\" onclick=\"window.location.href='/bibliometrics'\">DuEPublicA</button>\r\n    </div>\r\n    <div class=\"col-md-12\">\r\n      <h3>Logout</h3>\r\n      <div id=\"logout-panel\" class=\"panel-body\">\r\n          <button class=\"btn btn-primary\" (click)=\"logout()\">Logout</button>\r\n      </div>\r\n    </div>\r\n  </div>\r\n"

/***/ }),

/***/ "../../../../../src/app/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_authentification_service__ = __webpack_require__("../../../../../src/app/service/authentification.service.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var LoginComponent = (function () {
    function LoginComponent(authentificationService) {
        this.authentificationService = authentificationService;
    }
    LoginComponent.prototype.ngOnInit = function () {
        this.admin = false;
        this.fachreferent = false;
        this.media = false;
        this.analyst = false;
        this.authenticated = this.authentificationService.isAuthenticated();
        if (this.authenticated) {
            this.updateAuthorities();
        }
        else {
            this.authenticated = false;
        }
    };
    LoginComponent.prototype.login = function () {
        if (this.authentificationService.login(this.username, this.password) !== undefined) {
            this.updateAuthorities();
        }
        else {
            this.authenticated = false;
        }
    };
    LoginComponent.prototype.logout = function () {
        this.authentificationService.logout().subscribe(function () { });
        this.authenticated = false;
    };
    LoginComponent.prototype.updateAuthorities = function () {
        this.authenticated = true;
        console.log('authenticated');
        this.admin = this.authentificationService.hasRole('admin');
        console.log('admin');
        this.fachreferent = this.authentificationService.hasRole('fachreferent');
        console.log('fachreferent');
        this.media = this.authentificationService.hasRole('media');
        console.log('media');
        this.analyst = this.authentificationService.hasRole('analyst');
        console.log('analyst');
    };
    LoginComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-login',
            template: __webpack_require__("../../../../../src/app/login.component.html")
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__service_authentification_service__["a" /* AuthentificationService */]])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "../../../../../src/app/model/User.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return User; });
var User = (function () {
    function User(username, password) {
        this.username = username;
        this.password = password;
    }
    return User;
}());



/***/ }),

/***/ "../../../../../src/app/register.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"alert alert-danger\" *ngIf=\"errors\">\r\n  <p>Es gab ein Problem:</p>\r\n  <p *ngIf=\"!passwordsMatch\">Die eingegebenen Passwörter stimmen nicht überein.</p>\r\n  <p *ngIf=\"!passwordValid\">Das eingegebene Passwort ist ungültig.</p>\r\n  <p *ngIf=\"!usernameValid\">Der angegebene Nutzername ist ungültig.</p>\r\n  <p *ngIf=\"inUse\">Der angebene Nutzername ist bereits vergeben.</p>\r\n</div>\r\n<div class=\"col-md-6 col-md-offset-3\">\r\n  <h3>Anmeldung</h3>\r\n  <div class=\"panel-body\">\r\n    <form (submit)=\"register()\">\r\n    <div class=\"form-group\">\r\n      <label for=\"username\">Username:</label>\r\n      <input type=\"text\"\r\n             class=\"form-control\" id=\"username\" name=\"username\"\r\n             [(ngModel)]=\"username\"/>\r\n    </div>\r\n    <div class=\"form-group\">\r\n      <label for=\"password\">Password:</label> <input type=\"password\"\r\n                                                     class=\"form-control\" id=\"password\" name=\"password\"\r\n                                                     [(ngModel)]=\"password\"/>\r\n    </div>\r\n    <div class=\"form-group\">\r\n      <label for=\"passwordCheck\">Password wiederholen:</label> <input type=\"password\"\r\n                                                                      class=\"form-control\" id=\"passwordCheck\"\r\n                                                                      name=\"password\"\r\n                                                                      [(ngModel)]=\"passwordCheck\"/>\r\n    </div>\r\n    <button class=\"btn btn-primary\">Anmelden</button>\r\n  </form>\r\n  </div>\r\n</div>\r\n"

/***/ }),

/***/ "../../../../../src/app/register.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return RegisterComponent; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__service_authentification_service__ = __webpack_require__("../../../../../src/app/service/authentification.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/esm5/router.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__model_User__ = __webpack_require__("../../../../../src/app/model/User.ts");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var RegisterComponent = (function () {
    function RegisterComponent(authentificationService, router) {
        this.authentificationService = authentificationService;
        this.router = router;
    }
    RegisterComponent.prototype.register = function () {
        var _this = this;
        this.passwordsMatch = true;
        this.passwordValid = true;
        this.usernameValid = true;
        if (this.password !== this.passwordCheck) {
            this.passwordsMatch = false;
        }
        if (this.password === undefined) {
            this.passwordValid = false;
        }
        if (this.username === undefined) {
            this.usernameValid = false;
        }
        this.errors = !(this.passwordValid && this.passwordsMatch && this.usernameValid);
        if (!this.errors) {
        }
        this.authentificationService.register(new __WEBPACK_IMPORTED_MODULE_3__model_User__["a" /* User */](this.username, this.password)).subscribe(function (data) { return window.location.href = '/fachref'; }, function (error) { return _this.inUse = true; });
    };
    RegisterComponent = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["n" /* Component */])({
            selector: 'app-register',
            template: __webpack_require__("../../../../../src/app/register.component.html")
        }),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_1__service_authentification_service__["a" /* AuthentificationService */], __WEBPACK_IMPORTED_MODULE_2__angular_router__["a" /* Router */]])
    ], RegisterComponent);
    return RegisterComponent;
}());



/***/ }),

/***/ "../../../../../src/app/service/authentification.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthentificationService; });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/_esm5/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common_http__ = __webpack_require__("../../../common/esm5/http.js");
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AuthentificationService = (function () {
    function AuthentificationService(http) {
        this.http = http;
    }
    AuthentificationService.prototype.login = function (username, password) {
        var _this = this;
        var token = 'Basic ' + btoa(username + ':' + password);
        var headers = new __WEBPACK_IMPORTED_MODULE_2__angular_common_http__["c" /* HttpHeaders */]().set('Authorization', token);
        this.http.get('/activeuser', { headers: headers }).subscribe(function (data) {
            _this.principal = data;
            console.log(_this.principal);
        });
        return this.principal;
    };
    AuthentificationService.prototype.logout = function () {
        return this.http.post('/logout', {}, { responseType: 'text' });
    };
    AuthentificationService.prototype.register = function (user) {
        return this.http.post('/newUser', { 'username': user.username, 'password': user.password }, { responseType: 'text' });
    };
    AuthentificationService.prototype.isAuthenticated = function () {
        var _this = this;
        this.http.get('/activeuser').subscribe(function (data) {
            _this.principal = data;
            console.log(_this.principal);
            return !(_this.principal === undefined);
        });
        return false;
    };
    AuthentificationService.prototype.hasRole = function (role) {
        return this.principal.roles && (this.principal.roles.indexOf('ROLE_' + role.toUpperCase()) > -1);
    };
    AuthentificationService = __decorate([
        Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["A" /* Injectable */])(),
        __metadata("design:paramtypes", [__WEBPACK_IMPORTED_MODULE_2__angular_common_http__["a" /* HttpClient */]])
    ], AuthentificationService);
    return AuthentificationService;
}());



/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
var environment = {
    production: false
};


/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/esm5/core.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/esm5/platform-browser-dynamic.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    Object(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_13" /* enableProdMode */])();
}
Object(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[0]);
//# sourceMappingURL=main.bundle.js.map