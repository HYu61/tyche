// pages/editNickname/editNickname.js
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
    nickname:"",
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (options) {
    let me = this;
    let nickname = app.getGlobalUserInfo().nickname;
    me.setData({
      nickname: nickname
    })
  },
  doEditNickname: function(e){
    let newNickname = isBlanks(e.detail.value.nickname) ? this.data.nickname : e.detail.value.nickname;
  
    let userId = app.getUserId()
    let url = app.serverUrl + "/users/" + userId
    wx.request({
      url: url,
      method: 'PUT',
      data: {
        "nickname": newNickname,
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
  },
  goBack: function(){
    wx.navigateBack({
      delta: 1,
    })
  }
})