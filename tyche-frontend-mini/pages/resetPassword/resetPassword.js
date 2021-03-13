// pages/resetPassword/resetPassword.js
const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect
} = require("../../utils/commomUtil");
const app = getApp();
Page({

  /**
   * 页面的初始数据
   */
  data: {
    
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function () {
   
  },
  doResetPassword: function(e){  
    let formObj = e.detail.value;
    let username = formObj.username;
    let securityAnswer = formObj.securityAnswer;
    let newPassword = formObj.newPassword;

    if (this.validateUserInfo(formObj)){
      wx.request({
        url: app.serverUrl + "/users/password",
        method:'PUT',
        data:{
          "username": username,
          "plainPassword": newPassword,
          "securityAnswer": securityAnswer,
        },
        header:{
          'content-type': 'application/json', // 默认值
        },
        success: function(res){
          if(res.statusCode == 200){
            showMessageAndPauseredirect("Ok","../userLogin/userLogin");
          }else if(res.statusCode == 403){
            showErrorMessage("Username or security answer incorrect!")
          }
          
        }
      })
    }
  
  },
  goBack: function(){
    wx.navigateBack({
      delta: 1,
    })
  },
  validateUserInfo: function (params) {
    let flag = true
    if (isBlanks(params.username) || isBlanks(params.newPassword) || isBlanks(params.securityAnswer)) {
      showErrorMessage("Username, Password and Security answer are required!");
      flag = false;
    }
    return flag;
  }
})