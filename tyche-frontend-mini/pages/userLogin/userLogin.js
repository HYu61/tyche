const { isBlanks, showErrorMessage } = require("../../utils/commomUtil");
// pages/userLogin/userLogin.js

const app = getApp();

Page({
  data: {
  },

  onLoad: function(params){
    let me =this;
    let redirectUrl = params.redirectUrl;
    if(redirectUrl != null){
      redirectUrl= redirectUrl.replace(/#/g, "?");
      redirectUrl= redirectUrl.replace(/@/g, "=");
      redirectUrl= redirectUrl.replace(/!/g, "&");
    }
   
   me.redirectUrl = redirectUrl;
    // debugger;
  },

  doLogin: function (e) {
    let me = this;
    let formObj = e.detail.value;
    let username = formObj.username;
    let password = formObj.password;

    //validate 
    if (isBlanks(username)|| isBlanks(password)) {
      showErrorMessage('Username and Password are reuired');
    }
    // login
    else {
      wx.showLoading({
        title: 'Loading...',
      })
      wx.request({
        url: app.serverUrl + "/sessions",
        method: 'POST',
        data: {
          username: username,
          plainPassword: password
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {

          wx.hideLoading();
          // successful authentitatoin with username and password
          if(res.statusCode == 200){
            // get and store the login user info 
            app.setUserId(res.data.data.id);
            app.setUserToken(res.data.data.userToken);
            app.setGlobalUserInfo(res.data.data);

            let redirectUrl = me.redirectUrl;
            let url = "";
            
            if(!isBlanks(redirectUrl)){
              url = redirectUrl;
            }else{
              url = "../userInfo/userInfo";
            }
            wx.showToast({
              title: 'Successful',
              duration: 2000,
              success: function () {
                setTimeout(() => {
                  wx.redirectTo({
                    url: url,
                  })
                }, 1000);
              }
            })
          }else if(res.statusCode == 401){
            showErrorMessage('Username or Password is incorrect');
          }else{
            showErrorMessage(res.data.message)
          }
        },
        
      })
    }
  },
  goRegisterPage: function () {
    wx.redirectTo({
      url: '../userRegister/userRegister',
    })
  },
  goResetPasswordPage:function(){
    wx.navigateTo({
      url: '../resetPassword/resetPassword',
    })
  }
})