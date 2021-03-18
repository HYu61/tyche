// pages/videoEdit/videoEdit.js
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
    videoId: "",
    videoDesc: "",
    videoStatus: "",
    // vipVideoChecked: 0,

    videoInfo: null,
    isVipUser: false

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad: function (params) {

    let me = this;
    let videoInfo = JSON.parse(params.videoInfo);
    me.setData({
      videoId: videoInfo.id,
      videoDesc: videoInfo.videoDesc,
      videoStatus: videoInfo.status,
      isVipUser: params.isVipUser == "1",
      videoInfo: videoInfo
    })


  },

  doEditVideo: function (e) {
    let me = this;
    let videoDesc = e.detail.value.videoDesc;
    let videoStatus = me.data.videoStatus;
    let videoId = me.data.videoId;

    let url = app.serverUrl + "/videos/" + videoId;

    wx.request({
      url: url,
      method: 'PUT',
      data: {
        videoDesc: videoDesc,
        vipVideo: videoStatus
      },
      header: {
        'content-type': 'application/json', // 默认值
        "Authorization": app.getUserId() + "::" + app.getUserToken(),
      },
      success: function (res) {
    
        if (res.data.code == 200) {
          wx.redirectTo({
            url: '../userInfo/userInfo',
          })
        }  else if(res.statusCode == 403){
          showErrorMessage(res.data.message)
        } 
        else if (res.statusCode == 401) {
          let redirectUrl = "../videoEdit/videoEdit#videoInfo@" + JSON.stringify(me.data.videoInfo) + "!isVipUser@" + JSON.stringify(me.data.isVipUser ? 1 : 0);
          showMessageAndPauseredirect(res.data.message,
            '../userLogin/userLogin?redirectUrl=' + redirectUrl,
            "none")

        } else {
          showErrorMessage(res.data.message);

        }

      }
    })
  },


  // set if this video is vip video
  switchChange: function (e) {
    let me = this;
    me.setData({
      videoStatus: e.detail.value ? 1 : 0
    })
  },

  goBack: function () {
    wx.navigateBack({
      delta: 1,
    })
  }

})