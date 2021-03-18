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

  doVip: function (e) {

    if (this.validateUserInfo(e.detail.value)) {

      let vipPass = e.detail.value.vipPass;
      let vipVideoAccessQuestion = e.detail.value.vipVideoAccessQuestion;
      let vipVideoAccessAnswer = e.detail.value.vipVideoAccessAnswer;

      let userId = app.getUserId();
      wx.request({
        url: app.serverUrl + "/users/" + userId,
        method: "PUT",
        data: {

          vipPass: vipPass,
          vipVideoAccessQuestion: vipVideoAccessQuestion,
          vipVideoAccessAnswer: vipVideoAccessAnswer,
        },
        header: {
          'content-type': 'application/json', // 默认值
          "Authorization": userId + "::" + app.getUserToken(),
        },
        success: function (res) {
          // vipPass is incorrect
          if (res.statusCode === 403) {
            showErrorMessage(res.data.message)
          } else {
            // vipPass is correct
            showMessageAndPauseredirect("VIP Comfirmed", "../userInfo/userInfo")
          }

        }
      })
    }

  },

  goBack: function () {
    wx.navigateBack({
      delta: 1,
    })
  },

  /**
   * Validate username and password
   * return true if username and password are valid; otherwise return false
   * @param {*} params 
   */
  validateUserInfo: function (params) {
    let flag = true
    if (isBlanks(params.vipPass) || isBlanks(params.vipVideoAccessQuestion) || isBlanks(params.vipVideoAccessAnswer)) {
      showErrorMessage("All fields are required!");
      flag = false;
    }
    return flag;
  }

})