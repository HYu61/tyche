let videoUtils = require("../../utils/videoUtils")
const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect,
  showMessageAndPauseNavigate
} = require("../../utils/commomUtil");
const app = getApp()

Page({
  data: {
    fit: "",
    src: "",
    profileSrc: "../resource/images/noneface.png",
    videoDetail: [],
    userLikeVideo: false,
    loginUserId: "",
    videoLikeCount: "",
    play: true,
    // videoInfo:null,

    // bgmAuthor:"",
    // bgmName:"",


    commentsPage: 1,
    commentsTotalPage: 1,
    commentsList: [],


    placeholder: "说点什么..."

  },
  videoCtx: {},


  onLoad: function (params) {
    let me = this;
    let videoInfo = JSON.parse(params.videoInfo);
      me.setData({
      videoInfo: videoInfo,
    })
    let userToken = app.getUserToken();

    let isVipVideo = videoInfo.vipVideo;
    let videoId = videoInfo.id;
    let publisherId = videoInfo.userId;

   


    if (!isBlanks(app.getUserId())) {
      me.setData({
        loginUserId: app.getUserId(),
      })
    }

    let url = isVipVideo ? app.serverUrl + "/vip_videos/" + videoId + "?publisherId=" + publisherId + "&loginUserId=" + app.getUserId() :
      app.serverUrl + "/videos/" + videoId + "?loginUserId=" + app.getUserId();
    let authorization = "";
    if (isVipVideo && !isBlanks(app.getUserId() && !isBlanks(userToken))) {
      authorization = app.getUserId() + "::" + userToken;
    }
    // get the video detail
    wx.showLoading({
      title: 'Loading...',
    })
    wx.request({
      url: url,
      method: "GET",
      header: {
        "Authorization": authorization,
      },
      success: function (res) {
        wx.hideLoading();
        if (res.statusCode == 200) {

          let videoDetail = res.data.data;
          let fit = videoDetail.videoWidth > videoDetail.videoHeight ? '' : 'cover';
          let profileSrc = isBlanks(videoDetail.profilePhoto) ? "../resource/images/noneface.png" : app.serverUrl + videoDetail.profilePhoto;
          let loginUserLikeTheVideo = videoDetail.loginUserLikeTheVideo;
          me.setData({
            videoDetail: res.data.data,
            fit: fit,
            src: app.serverUrl + videoDetail.videoPath,
            profileSrc: profileSrc,
            userLikeVideo: loginUserLikeTheVideo,
            videoLikeCount: videoDetail.likeCounts,
            bgmAuthor:videoDetail.author,
            bgmName:videoDetail.name
       
          })

          me.getCommentsList(1);

        }
        // Only vip user can access the vip video
        else if (res.statusCode == 401) { // user not login or token expired
          let redirectUrl = "../videoInfo/videoInfo#videoInfo@" + JSON.stringify(me.data.videoInfo);
          let reUrl = "../userLogin/userLogin"
          showMessageAndPauseredirect(res.data.message,
            reUrl + "?redirectUrl=" + redirectUrl, "none")
        } else if (res.statusCode == 403) { // need to answer the publihser's vip video access question
          // let redirectUrl = "../videoInfo/videoInfo#videoInfo@" + JSON.stringify(me.data.videoInfo);
          let reUrl = "../vipVideoAccess/vipVideoAccess"
          let videoInfo = JSON.stringify(me.data.videoInfo);
          showMessageAndPauseNavigate("Answer question to access this video!",
            reUrl + "?videoInfo=" + videoInfo, "none")


        }
      }
    })


    // let videoCreatorId = me.data.videoDetail.userId;

    me.videoCtx = wx.createVideoContext('myVideo', me)


  },
  videoTap: function () {

    let me = this;
    if (me.data.play) {
      me.videoCtx.pause();
      me.setData({
        play: false,
      })
    } else {
      me.videoCtx.play();
      me.setData({
        play: true,
      })
    }
  },
  onShow: function () {
    let me = this;
    me.videoCtx.play();
    me.setData({
      play: true,
    })
  },
  onHide: function () {
    let me = this;
    me.videoCtx.pause();
    me.setData({
      play: false,
    })
  },
  onUnload: function () {
    let me = this;
    me.videoCtx.stop();
    me.setData({
      play: false,
    })
  },

  uploadVideo: function () {
    let me = this;
    let userId = app.getUserId();
    let redirectUrl = "../videoInfo/videoInfo#videoInfo@" + JSON.stringify(me.data.videoDetail);
    if (isBlanks(userId)) {
      wx.redirectTo({
        url: '../userLogin/userLogin?redirectUrl=' + redirectUrl,
      })
    } else {
      videoUtils.uploadVideo(app.getGlobalUserInfo().status);
    }
  },
  showSearch: function () {
    wx.redirectTo({
      url: '../searchVideo/searchVideo',
    })
  },

  showIndex: function () {
    wx.redirectTo({
      url: '../index/index',
    })
  },

  showMine: function () {
    let userId = app.getUserId();

    if (isBlanks(userId)) {
      wx.redirectTo({
        url: '../userLogin/userLogin'
      })
    } else {
      wx.navigateTo({
        url: '../userInfo/userInfo',
      })
    }

  },

  likeVideoOrNot: function () {
    let loginUserId = app.getUserId();
    let me = this;

    let redirectUrl = "../videoInfo/videoInfo#videoInfo@" + JSON.stringify(me.data.videoDetail);
    if (isBlanks(loginUserId)) {
      showMessageAndPauseredirect("Please login first", "../userLogin/userLogin?redirectUrl=" + redirectUrl, "none");
    } else {
      let videoId = me.data.videoDetail.id;
      let vidoePublisherUserId = me.data.videoDetail.userId;
      let likeVideo = !me.data.userLikeVideo;
      wx.request({
        url: app.serverUrl + "/videos/" + videoId + "/like_count",
        method: "PUT",
        data: {
          "likeVideoOrFollowPublisher": likeVideo,
          "loginUserId": loginUserId,
          "videoPublishUserId": vidoePublisherUserId
        },
        header: {
          'content-type': 'application/json', // 默认值
          "Authorization": app.getUserId() + "::" + app.getUserToken()
        },
        success: function (res) {
          if (res.data.code < 0) {
            showMessageAndPauseredirect(res.data.message, "../userLogin/userLogin?redirectUrl=" + redirectUrl, "none")
          }
          if (likeVideo) {
            me.setData({
              videoLikeCount: me.data.videoLikeCount + 1
            })
          } else {
            me.setData({
              videoLikeCount: me.data.videoLikeCount - 1
            })
          }
          me.setData({
            userLikeVideo: likeVideo,
          })
        }
      })
    }
  },

  showPublisher: function () {
    let me = this;
    let loginUserId = app.getUserId();
    // let videoInfo = me.data.videoInfo;
    let publisherUserId = me.data.videoDetail.userId;
    let redirectUrl = "../userInfo/userInfo#publisherId@" + publisherUserId;

    if (isBlanks(loginUserId)) {
      wx.redirectTo({
        url: "../userLogin/userLogin?redirectUrl=" + redirectUrl,
      })
    } else {

      wx.navigateTo({
        url: '../userInfo/userInfo?publisherId=' + publisherUserId,
      })
    }
  },

  moreInfo: function () {
    let me = this;
    let userId = app.getUserId();
    let itemList = ["举报用户", "下载到本地"];
    let videoInfo = JSON.stringify(me.data.videoDetail);
    let redirectUrl = "../videoInfo/videoInfo#videoInfo@" + videoInfo;
    // me.setData({
    //   play: true,
    // })
    if (isBlanks(userId)) {
      wx.redirectTo({
        url: '../userLogin/userLogin?redirectUrl=' + redirectUrl,
      })
    } else {
      wx.showActionSheet({
        itemList: itemList,
        success: function (res) {
          // report the video
          if (res.tapIndex == 0) {
            let publisherId = me.data.videoDetail.userId;
            let videoId = me.data.videoDetail.id;
            wx.navigateTo({
              url: '../report/report?videoId=' + videoId + "&publisherId=" + publisherId,
            })
          } else if (res.tapIndex == 1) {

            //download
            wx.showLoading({
              title: '下载中...',
            })
            wx.downloadFile({
              url: app.serverUrl + me.data.videoDetail.videoPath,
              success(res) {
                if (res.statusCode === 200) {
                  wx.saveVideoToPhotosAlbum({
                    filePath: res.tempFilePath,
                    success(res) {
                      wx.hideLoading();
                      wx.showToast({
                        title: '下载完成',
                        duration: 1500
                      })
                    }
                  })
                }
              }
            })
          }
        }
      })
    }
  },


  onShareAppMessage: function (res) {
    let me = this;
    let videoInfo = me.data.videoDetail;
    me.videoCtx.pause();
    me.setData({
      play: false,
    })
    return {
      title: videoInfo.videoDesc,
      path: 'pages/videoInfo/videoInfo?videoInfo=' + JSON.stringify(videoInfo),
    }
  },

  onShareTimeline: function (res) {
    let me = this;
    let videoInfo = me.data.videoDetail;
    me.videoCtx.pause();
    me.setData({
      play: false,
    })
    return {
      title: "小金鱼Vlog" + videoInfo.videoDesc,
      query: 'videoInfo=' + JSON.stringify(videoInfo),
    }
  },

  leaveComment: function () {
    this.setData({
      commentFocus: true
    });
  },

  replyFocus: function (e) {
    let parenetCommentId = e.currentTarget.dataset.parenetcommentid;
    let toUserId = e.currentTarget.dataset.touserid;
    let toNickname = e.currentTarget.dataset.tonickname;

    this.setData({
      placeholder: "回复  " + toNickname,
      replyParenetCommentId: parenetCommentId,
      replyToUserId: toUserId,
      commentFocus: true
    });
  },

  saveComment: function (e) {
    let me = this;
    let content = e.detail.value;

    // 获取评论回复的fatherCommentId和toUserId
    let parenetCommentId = e.currentTarget.dataset.replyparenetcommentid;
    let toUserId = e.currentTarget.dataset.replytouserid;
    parenetCommentId = isBlanks(parenetCommentId) ? " " : parenetCommentId;
    toUserId = isBlanks(toUserId) ? " " : toUserId;


    let user = app.getGlobalUserInfo();
    let loginUserId = app.getUserId();
    let userToken = app.getUserToken();
    let videoInfo = JSON.stringify(me.data.videoDetail);
    let videoId = me.data.videoDetail.id;
    let realUrl = '../videoInfo/videoInfo#videoInfo@' + videoInfo;

    if (isBlanks(loginUserId)) {
      wx.navigateTo({
        url: '../userLogin/userLogin?redirectUrl=' + realUrl,
      })
    } else {
      wx.showLoading({
        title: 'Loading...',
      })
      wx.request({
        url: app.serverUrl + '/comments',
        method: 'POST',
        header: {
          'content-type': 'application/json', // 默认值
          "Authorization": loginUserId + "::" + userToken,
        },
        data: {
          fromUserId: loginUserId,
          videoId: videoId,
          comment: content,
          parentCommentId: parenetCommentId,
          toUserId: toUserId

        },
        success: function (res) {
          wx.hideLoading();
          if (res.statusCode == 401) {
            showMessageAndPauseredirect(res.data.message, '../userLogin/userLogin?redirectUrl=' + realUrl, 'none')
            // wx.navigateTo({
            //   url: '../userLogin/userLogin?redirectUrl=' + realUrl,
            // })
          } else if (res.statusCode == 200) {
            me.setData({
              contentValue: "",
              commentsList: []

            });
            me.getCommentsList(1);

          }
        }
      })
    }
  },

  getCommentsList: function (page) {
    let me = this;

    let videoId = me.data.videoDetail.id;
    let userToken = app.getUserToken();
    wx.request({
      url: app.serverUrl + '/comments/videos/' + videoId + '?page=' + page,
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值
        // "Authorization": app.getUserId() + "::"+userToken,
      },
      success: function (res) {
        let commentsList = res.data.data.rows;
        let newCommentsList = me.data.commentsList;

        me.setData({
          commentsList: newCommentsList.concat(commentsList),
          commentsPage: page,
          commentsTotalPage: res.data.data.total
        });
      }
    })
  },


})
