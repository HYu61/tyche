// pages/vipUser/vipUser.js
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

  },

  doVip: function(e){
    let vipPass = e.detail.value.vipPass;
    let userId = app.getUserId();
    wx.request({
      url: app.serverUrl+ "/users/" + userId,
      method: "PUT",
      data:{

        vipPass : vipPass
      },
      header:{
        'content-type': 'application/json', // 默认值
        "Authorization": userId + "::" + app.getUserToken(),
      },
      success: function(res){
        // vipPass is incorrect
        if(res.statusCode === 403){
          showErrorMessage(res.data.message)
        }else{
          // vipPass is correct
          showMessageAndPauseredirect("VIP Comfirmed", "../userInfo/userInfo")
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