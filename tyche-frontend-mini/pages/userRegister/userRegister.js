// pages/userRegister/userRegister.js
const {
  isBlanks,
  showErrorMessage
} = require("../../utils/commomUtil");

const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  doRegister: function (e) {
    let formObj = e.detail.value;
    let username = formObj.username;
    let password = formObj.password;
    let securityAnswer = formObj.securityAnswer;
    let url = app.serverUrl + "/users";

    if (this.validateUserInfo(formObj)) {
      // create the user to server
      wx.showLoading({
        title: 'Loading...',
      })

      wx.request({
        url: url,
        method: "POST",
        data: {
          "username": username,
          "plainPassword": password,
          "securityAnswer": securityAnswer,
        },
        header: {
          'content-type': 'application/json' // 默认值
        },
        success: function (res) {
          wx.hideLoading();
          // success
          if (res.statusCode == 200) {
            wx.showToast({
              title: 'Sucessful!',
              duration: 1000,
              success: function () {
                setTimeout(() => {
                  wx.redirectTo({
                    url: '../userLogin/userLogin',
                  })
                }, 2000);
              }
            })

            // error
          } else {
            let errMsg = "";
            switch(res.statusCode){
              case 400:
                errMsg = "Username and Password are required!";
                break;
              case 409:
                errMsg = "Username already exists!";
            }
          showErrorMessage(errMsg);
          }
        },
      })
    }
  },

  goLoginPage: function () {
    wx.navigateTo({
      url: '../userLogin/userLogin'
    })
  },

  /**
   * Validate username and password
   * return true if username and password are valid; otherwise return false
   * @param {*} params 
   */
  validateUserInfo: function (params) {
    let flag = true
    if (isBlanks(params.username) || isBlanks(params.password) || isBlanks(params.rePassword) || isBlanks(params.securityAnswer)) {
      showErrorMessage("Username, Password and Security answer are required!");
      flag = false;
    } else if (params.password != params.rePassword) {
      showErrorMessage("Password must be match!");
      flag = false;
    }
    return flag;
  }

})