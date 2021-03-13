const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect
} = require("../../utils/commomUtil");
const app = getApp()

Page({
  data: {
    bgmList: [],
    serverUrl: "",
    videoParams: {},
    isVip: false,
    vipVideoChecked: 0,
  },

  onLoad: function (params) {
    let me = this;
    let user = app.getGlobalUserInfo();
    let userToken = app.getUserToken();
    let userId = app.getUserId();
    me.setData({
      isVip: user.status === 1 ? true : false
    })

    wx.showLoading({
      title: 'Loading...',
    })
    wx.request({
      url: app.serverUrl + '/bgms',
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值
        "Authorization": userId + "::" + userToken,
      },
      success: function (res) {
        if(res.data.code < 0){
          showMessageAndPauseredirect(res.data.message,'../userLogin/userLogin', "none" )
        }
        if (res.statusCode == 200) {
          wx.hideLoading()
          me.setData({
            bgmList: res.data.data,
            serverUrl: app.serverUrl
          })
        } else if (res.statusCode == 204) {
          showErrorMessage("No Background Music Available!");
        }
        else{
          showMessageAndPauseredirect("Please login", "../userLogin/userLogin", "none");
        }
      }
    })

    // store the basic info of the video 
    me.setData({
      videoParams: params
    })

  },

  // set if this video is vip video
  switchChange:function(e){
    let me = this;
    me.setData({
      vipVideoChecked : e.detail.value ? 1 : 0
    })
  },



  upload: function (e) {
    let me = this
    let userId = app.getUserId();
    let userToken = app.getUserToken();
    let bgmId = e.detail.value.bgmId;
    let desc = e.detail.value.desc;

    let vipVideo = me.data.vipVideoChecked;
    let videoHeight = me.data.videoParams.videoHeight;
    let videoWidth = me.data.videoParams.videoWidth;
    let videoSize = me.data.videoParams.videoSize;
    let videoDuration = me.data.videoParams.duration;
    let temFilePath = me.data.videoParams.temFilePath;

    wx.showLoading({
      title: 'Loading...',
    })

    wx.uploadFile({
      url: app.serverUrl +"/videos",
      filePath: temFilePath,
      name: 'file',
      header: {
        'content-type': 'multipart/form-data', 
        "Authorization": userId + "::" + userToken,
      },
      formData: {
        userId: userId,
        bgmId: bgmId,
        videoDesc: desc,   
        videoSeconds: videoDuration,
        videoWidth: videoWidth,
        videoHeight: videoHeight,
        vipVideo: vipVideo
      },
      success(res) {
        wx.hideLoading();
        //do something
        if (res.statusCode == 200) {
          wx.redirectTo({
            url: '../index/index',
          })
        } else if (res.statusCode == 400 || res.statusCode == 500) {
          let data = JSON.parse(res.data)
          showErrorMessage(data.message);
        }

      }
    })


  }

})