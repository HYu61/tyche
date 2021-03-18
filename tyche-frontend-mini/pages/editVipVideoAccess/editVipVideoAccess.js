// pages/editVipVideoAccess/editVipVideoAccess.js
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
  onLoad: function (options) {
    let me = this;
  },

   /**
   * Validate username and password
   * return true if username and password are valid; otherwise return false
   * @param {*} params 
   */
  validateUserInfo: function (params) {
    let flag = true
    if (isBlanks(params.vipVideoAccessQuestion) || isBlanks(params.vipVideoAccessAnswer)) {
      showErrorMessage("All fields are required!");
      flag = false;
    }
    return flag;
  },

  editVipVideoAccess: function(e){
    if (this.validateUserInfo(e.detail.value)){
      let vipVideoAccessQuestion = e.detail.value.vipVideoAccessQuestion;
      let vipVideoAccessAnswer = e.detail.value.vipVideoAccessAnswer;
      let userId = app.getUserId()
      let url = app.serverUrl + "/users/" + userId
      wx.request({
        url: url,
        method: 'PUT',
        data: {
          vipVideoAccessQuestion: vipVideoAccessQuestion,
          vipVideoAccessAnswer: vipVideoAccessAnswer,
          deleteVipVideoAccess: true
        },
        header:{
          'content-type': 'application/json', // 默认值
          "Authorization": app.getUserId() + "::" + app.getUserToken(),
        },
        success: function(res){
          if(res.data.code == 200){
            wx.redirectTo({
              url: '../userInfo/userInfo',
            })
          }else if(res.data.code < 0){
            showErrorMessage(res.data.message);
          }
         
        }
      })
    }  
   
  },
  goBack: function(){
    wx.navigateBack({
      delta: 1,
    })
  }
})