//app.js
App({
  serverUrl: "http://192.168.0.17:8080",
  userInfo: null,

  setGlobalUserInfo: function(user){
    wx.setStorageSync('userInfo', user)
  },
  getGlobalUserInfo: function(){
    return wx.getStorageSync('userInfo')
  },

  setUserToken: function(userToken){
    wx.setStorageSync('userToken', userToken)
  },
  getUserToken: function(){
    return wx.getStorageSync('userToken')
  },
  setUserId: function(userId){
    wx.setStorageSync('userId', userId)
  },
  getUserId: function(){
    return wx.getStorageSync('userId')
  },

  // setVideoList: function(videoList){
  //   wx.setStorageSync('videoList', videoList)
  // },
  // getVideoList: function(){
  //   return wx.getStorageSync('videoList')
  // },

  
  reportReasonArray: [
    "色情低俗",
    "政治敏感",
    "涉嫌诈骗",
    "辱骂谩骂",
    "广告垃圾",
    "诱导分享",
    "引人不适",
    "过于暴力",
    "违法违纪",
    "其它原因"
  ]
})