const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect
} = require("../../utils/commomUtil");
const app = getApp()

Page({
  data: {
    screenWidth: 350,
    currentPage: 1,
    totalPage: 1,
    videoList: [],
    serverUrl: "",
    searchContent: "",

    profileUrl: "../resource/images/noneface.png",
    vipIcon: "../resource/images/vip_video.png",

    displayBack: false,

  },

  onLoad: function (params) {
    console.log(params)

    let me = this;
    let screenWidth = wx.getSystemInfoSync().screenWidth;

    me.setData({
      screenWidth: screenWidth,
    });

    // get current page and searchContent
    let currentPage = me.data.currentPage;
    if (isBlanks(params.searchContent)) {
      me.setData({
        searchContent: "",
      })
    } else {
      me.setData({
        searchContent: params.searchContent,
        displayBack: true
      })
    }
    let searchContent = me.data.searchContent;
    me.getAllVideos(currentPage, searchContent)

  },

  // update 
  onPullDownRefresh: function () {
    let me = this;
    let searchContent = me.data.searchContent;
    wx.showNavigationBarLoading();
    this.getAllVideos(1, searchContent);
  },

  // get the next page when wipe up
  onReachBottom: function () {
    let me = this;
    let currentPage = me.data.currentPage;
    let totalPage = me.data.totalPage;
    let searchContent = me.data.searchContent;

    if (currentPage === totalPage) {
      wx.showToast({
        title: '没有视频了！！！That is all!',
        icon: 'none',
      })
      return;
    }
    currentPage += 1;

    me.getAllVideos(currentPage, searchContent);


  },

  getAllVideos: function (currentPage, searchContent) {
    let me = this;
    wx.showLoading({
      title: '加载中! Loading...',
    })
    wx.request({
      url: app.serverUrl + "/videos/covers?page=" + currentPage + "&searchContent=" + searchContent,
      method: "GET",
      success: function (res) {
        wx.hideLoading();
        wx.hideNavigationBarLoading();
        wx.stopPullDownRefresh();
        if (currentPage === 1) {
          me.setData({
            videoList: []
          })
        }
        let newVideoList = res.data.data.rows;
        let totalPage = res.data.data.total;
        me.setData({
          videoList: me.data.videoList.concat(newVideoList),
          currentPage: currentPage,
          totalPage: totalPage,
          serverUrl: app.serverUrl,
          searchContent: searchContent
        })

      }

    })
  },

  showVideoInfo: function (e) {
    let me = this;

    let videoList = me.data.videoList;
    let currentVideoIndex = e.target.dataset.arrindex;
    let videoInfo = JSON.stringify(videoList[currentVideoIndex])

    // app.setVideoList(videoList);

    wx.redirectTo({
      url: '../videoInfo/videoInfo?videoInfo=' + videoInfo,

    })
  },
  goBack: function () {
    wx.redirectTo({
      url: '../index/index',
    })
  }

})