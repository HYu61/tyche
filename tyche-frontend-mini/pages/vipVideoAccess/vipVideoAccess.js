// pages/vipVideoAccess/vipVideoAccess.js

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
    vipVideoAccessQuestion: "",
    // videoInfo:[]
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {
    let me = this;
    let videoInfo = JSON.parse(params.videoInfo)

    // let videoInfo = JSON.parse(redirectUrl)
    let publisherId = videoInfo.userId;
    wx.request({
      url: app.serverUrl + "/users/" + publisherId,
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值
        "Authorization": app.getUserId() + "::" + app.getUserToken()
      },
      success: function (res) {
        me.setData({
          vipVideoAccessQuestion: res.data.data.vipVideoAccessQuestion,
          videoInfo: videoInfo
        })
      }

    })

  },

  doVipVideoAccess: function (e) {
    let me = this
    let vipVideoAccessAnswer = e.detail.value.vipVideoAccessAnswer;
    let publisherId = me.data.videoInfo.userId;
    let loginUserId = app.getUserId();
    if (isBlanks(loginUserId)) {
      showMessageAndPauseredirect("Please login", "/pages/userLogin/userLogin", "none")
    }
    wx.showLoading({
      title: 'Loading',
    })
    wx.request({
      url: app.serverUrl + "/vip_videos/access_relationships",
      method: "POST",
      data: {
        publisherId: publisherId,
        loginUserId: loginUserId,
        vipVideoAccessAnswer: vipVideoAccessAnswer
      },
      header: {
        "Authorization": loginUserId + "::" + app.getUserToken(),
      },
      success: function (res) {
        wx.hideLoading()

        if (res.statusCode == 200) {

          let redirectUrl = "../videoInfo/videoInfo?videoInfo=" + JSON.stringify(me.data.videoInfo);
          let reUrl = "../userLogin/userLogin"
          showMessageAndPauseredirect(res.data.message,
             redirectUrl, "none")

        } else if (res.statusCode == 403) {
          showErrorMessage(res.data.message);
        }
      }

    })
  },
  goBack: function () {
    wx.navigateBack({
      delta: 1,
    })
  }
})