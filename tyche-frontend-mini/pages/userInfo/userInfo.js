const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect
} = require("../../utils/commomUtil");

let videoUtil = require("../../utils/videoUtils");
const app = getApp()
Page({
  data: {
    profileUrl: "../resource/images/noneface.png",
    defaultProfileUrl: "../resource/images/noneface.png",
    isMe: true,
    isVip: false,
    vipIcon: "../resource/images/vip.png",
    vipVideoIcon: "../resource/images/vip_video.png",

    isFollowed: false,

    videoSelClass: "video-info",
    isSelectedVideos: "video-info-selected",
    isSelectedLike: "",
    isSelectedFollow: "",


    myVideoList: [],
    myVideoPage: 1,
    myVideoTotal: 1,

    likeVideoList: [],
    likeVideoPage: 1,
    likeVideoTotal: 1,

    followList: [],
    followPage: 1,
    followTotal: 1,

    myVideoFalg: false,
    myLikesFalg: true,
    myFollowFalg: true

  },

  onLoad: function (params) {
    let me = this;
    let userId = app.getUserId();
    let userToken = app.getUserToken();
    let followerId = null;
    wx.showLoading({
      title: 'Loading...',
    })

    // 登录用户和发布视频用户不同
    if (userId != params.publisherId && !isBlanks(params.publisherId)) {
      me.setData({
        isMe: false,
        publisherId: params.publisherId,
      })

      // get publisher id
      userId = params.publisherId;

      followerId = app.getUserId();
    }

    // get the info of the user

    wx.request({

      // url: app.serverUrl + '/users/' + userId + "?fanId=" + user.id,
      url: app.serverUrl + "/users/" + userId + "?followerId=" + followerId,
      method: 'GET',
      header: {
        'content-type': 'application/json', // 默认值
        "Authorization": app.getUserId() + "::" + userToken
      },
      success: function (res) {

        wx.hideLoading()
        if (res.data.code < 0) {

          if (!isBlanks(me.data.publisherId)) {
            let redirectUrl = "../userInfo/userInfo#publisherId@" + me.data.publisherId;
            showMessageAndPauseredirect(res.data.message, "../userLogin/userLogin?redirectUrl=" + redirectUrl, "none")
          } else {
            showMessageAndPauseredirect(res.data.message, "../userLogin/userLogin", "none")
          }

        }
        if (res.statusCode == 200) {
          let userInfo = res.data.data;
          me.setData({
            nickname: userInfo.nickname,
            followerCounts: userInfo.followerCounts,
            followCounts: userInfo.followCounts,
            receiveLikeCounts: userInfo.receivedLikeCounts,
            // profileUrl: userInfo.profilePhoto == null ? "../resource/images/noneface.png" : app.serverUrl + userInfo.profilePhoto.replace(/\\/g, "/"),
            profileUrl: userInfo.profilePhoto == null ? "../resource/images/noneface.png" : app.serverUrl + userInfo.profilePhoto,

            isFollowed: userInfo.hasFollowed,
            isVip: userInfo.status === 1 ? true : false
          })

          app.setGlobalUserInfo(userInfo);
          // the user does not find
        } else if (res.statusCode == 204) // can not find user
        {
          showMessageAndPauseredirect("No info, please contact us!", null, "none")
        } else {
          showMessageAndPauseredirect(res.data.message, '../userLogin/userLogin', "none")
        }
      }
    })
    me.getMyVideoList(1);
  },

  changeNickname: function () {
    wx.navigateTo({
      url: '../editNickname/editNickname',
    })
  },

  upgradeVip: function () {
    wx.navigateTo({
      url: '../vipUser/vipUser',
    })
  },

  logout: function () {
    let userId = app.getUserId();
    let userToken = app.getUserToken();
    wx.request({
      url: app.serverUrl + "/sessions/" + userId,
      method: "DELETE",
      header: {
        'content-type': 'application/json', // 默认值
        'Authorization': userId + "::" + userToken,
      },
      success: function (res) {
        wx.removeStorageSync('userId')
        wx.removeStorageSync('userToken')
        wx.removeStorageSync('userInfo')
        showMessageAndPauseredirect("Logouting...", '../index/index', "none")
      }
    })
  },

  changeProfile: function () {
    let me = this;
    wx.chooseImage({
      count: 1,
      sizeType: ['compressed'],
      sourceType: ['album', 'camera'],
      success(res) {
        // tempFilePath可以作为img标签的src属性显示图片
        let tempFilePaths = res.tempFilePaths
        let userId = app.getUserId();
        let userToken = app.getUserToken();
        wx.showLoading({
          title: 'Uploading...',
        })
        wx.uploadFile({
          url: app.serverUrl + "/users/" + userId + "/profile_photo",
          filePath: tempFilePaths[0],
          name: 'file',
          header: {
            'content-type': ' multipart/form-data',
            'Authorization': userId + "::" + userToken,
          },
          success(res) {
            wx.hideLoading();
            let data = JSON.parse(res.data)
            //do something
            if (res.statusCode == 200) {
              wx.showToast({
                title: 'Upload Successful!!!',
              })

              //display the profileg

              me.setData({
                profileUrl: app.serverUrl + data.data.replace(/\\/g, "/"),
              })

            } else {
              showErrorMessage("Image upload failed!");
            }

          }
        })


      }
    })
  },

  uploadVideo: function () {
    let userStatus = app.getGlobalUserInfo().status;
    videoUtil.uploadVideo(userStatus);
  },

  followMe: function (e) {
    let me = this;
    let publisherId = me.data.publisherId;
    let userId = app.getUserId();
    let followType = e.currentTarget.dataset.followtype;

    let url = "/users/" + userId + "/follow_relationship";
    let likeVideoOrFollowPublisher = followType == '1' ? true : false;

    wx.showLoading();
    wx.request({
      url: app.serverUrl + url,
      method: "PUT",
      header: {
        'content-type': 'application/json', // 默认值
        "Authorization": app.getUserId() + "::" + app.getUserToken(),
      },
      data: {
        likeVideoOrFollowPublisher: likeVideoOrFollowPublisher,
        videoPublishUserId: publisherId
      },
      success: function (res) {
        wx.hideLoading();
        if (followType == "1") {
          me.setData({
            isFollowed: true,
            followerCounts: ++me.data.followerCounts,
          })
        } else {
          me.setData({
            isFollowed: false,
            followerCounts: --me.data.followerCounts,
          })
        }

      }
    })

  },
  // update 
  onPullDownRefresh: function () {
    let me = this;
    if (me.data.isMe) {
      wx.redirectTo({
        url: '../userInfo/userInfo',
      })
    } else {
      wx.redirectTo({
        url: '../userInfo/userInfo?publisherId=' + me.data.publisherId,
      })
    }
  },

  doSelectVideos: function () {

    this.setData({
      isSelectedVideos: "video-info-selected",
      isSelectedLike: "",
      isSelectedFollow: "",

      myVideoFalg: false,
      myLikesFalg: true,
      myFollowFalg: true,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followList: [],
      followPage: 1,
      followTotal: 1
    });

    this.getMyVideoList(1);

  },

  doSelectLike: function () {
    this.setData({
      isSelectedVideos: "",
      isSelectedLike: "video-info-selected",
      isSelectedFollow: "",

      myVideoFalg: true,
      myLikesFalg: false,
      myFollowFalg: true,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followList: [],
      followPage: 1,
      followTotal: 1
    });

    this.getMyLikesList(1);
  },

  doSelectFollow: function () {
    this.setData({
      isSelectedVideos: "",
      isSelectedLike: "",
      isSelectedFollow: "video-info-selected",

      myVideoFalg: true,
      myLikesFalg: true,
      myFollowFalg: false,

      myVideoList: [],
      myVideoPage: 1,
      myVideoTotal: 1,

      likeVideoList: [],
      likeVideoPage: 1,
      likeVideoTotal: 1,

      followList: [],
      followPage: 1,
      followTotal: 1
    });


    this.getMyFollowList(1)
  },

  getMyVideoList: function (page) {
    let me = this;
    let userId = me.data.isMe ? app.getUserId() : me.data.publisherId;

    console.log(userId)
    // 查询视频信息
    wx.showLoading();
    // 调用后端
    var serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + "/users/" + userId + "/videos/covers?page=" + page,
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值
      },
      success: function (res) {
        console.log(res.data);
        let myVideoList = res.data.data.rows;
        wx.hideLoading();

        var newVideoList = me.data.myVideoList;
        me.setData({
          myVideoPage: page,
          myVideoList: newVideoList.concat(myVideoList),
          myVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },

  getMyLikesList: function (page) {
    let me = this;
    let userId = me.data.isMe ? app.getUserId() : me.data.publisherId;

    // 查询视频信息
    wx.showLoading();
    // 调用后端
    let serverUrl = app.serverUrl;
    wx.request({
      // /users/{userId}/videos/liked/covers"
      url: serverUrl + '/users/' + userId + '/videos/liked/covers?page=' + page,
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值

      },
      success: function (res) {

        let likeVideoList = res.data.data.rows;
        wx.hideLoading();
        let newVideoList = me.data.likeVideoList;
        me.setData({
          likeVideoPage: page,
          likeVideoList: newVideoList.concat(likeVideoList),
          likeVideoTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });

        console.log(me.data.likeVideoList);

      }
    })
  },


  getMyFollowList: function (page) {
    let me = this;
    let userId = me.data.isMe ? app.getUserId() : me.data.publisherId;

    // 查询视频信息
    wx.showLoading();
    // 调用后端
    let serverUrl = app.serverUrl;
    wx.request({
      url: serverUrl + '/users/' + userId + '/follows?page=' + page,
      method: "GET",
      header: {
        'content-type': 'application/json', // 默认值
        // "Authorization": app.getUserId() + "::" + app.getUserToken()
      },
      success: function (res) {
        console.log(res.data);
        let followList = res.data.data.rows;
        wx.hideLoading();

        let newFollowList = me.data.followList;
        me.setData({
          followPage: page,
          followList: newFollowList.concat(followList),
          followTotal: res.data.data.total,
          serverUrl: app.serverUrl
        });
      }
    })
  },




  // 点击跳转到视频详情页面
  showVideo: function (e) {
    let myVideoFalg = this.data.myVideoFalg;
    let myLikesFalg = this.data.myLikesFalg;
    // let myFollowFalg = this.data.myFollowFalg;

    let videoList = []
    if (!myVideoFalg) {
      videoList = this.data.myVideoList;
    } else if (!myLikesFalg) {
      videoList = this.data.likeVideoList;
    }
    // } else if (!myFollowFalg) {
    //   var videoList = this.data.followVideoList;
    // }

    let arrindex = e.target.dataset.arrindex;
    let videoInfo = JSON.stringify(videoList[arrindex]);

    wx.redirectTo({
      url: '../videoInfo/videoInfo?videoInfo=' + videoInfo
    })

  },

  // 点击跳转到user详情页面
  showUser: function (e) {

    // let myVideoFalg = this.data.myVideoFalg;
    // let myLikesFalg = this.data.myLikesFalg;
    let myFollowFalg = this.data.myFollowFalg;

    let followList = []
    if (!myFollowFalg) {
      followList = this.data.followList;
    }

    let arrindex = e.target.dataset.arrindex;
    let publisherUserId = followList[arrindex].id;
    console.log(publisherUserId)
    // let redirectUrl = "../userInfo/userInfo#publisherId@" + publisherUserId;
    wx.navigateTo({
      url: '../userInfo/userInfo?publisherId=' + publisherUserId,
    })

  },

  // 到底部后触发加载
  onReachBottom: function () {
    let myVideoFalg = this.data.myVideoFalg;
    let myLikesFalg = this.data.myLikesFalg;
    let myFollowFalg = this.data.myFollowFalg;

    if (!myVideoFalg) {
      let currentPage = this.data.myVideoPage;
      let totalPage = this.data.myVideoTotal;

      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      let page = currentPage + 1;

      this.getMyVideoList(page);
    } else if (!myLikesFalg) {
      let currentPage = this.data.likeVideoPage;
      let totalPage = this.data.likeVideoTotal;

      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经没有视频啦...',
          icon: "none"
        });
        return;
      }
      let page = currentPage + 1;
      this.getMyLikesList(page);
    } else if (!myFollowFalg) {
      let currentPage = this.data.followVideoPage;
      let totalPage = this.data.followVideoTotal;
      // 获取总页数进行判断，如果当前页数和总页数相等，则不分页
      if (currentPage === totalPage) {
        wx.showToast({
          title: '已经到底了...',
          icon: "none"
        });
        return;
      }
      let page = currentPage + 1;
      this.getMyFollowList(page);
    }

  },

  videoAction: function (e) {
    let me = this;
    let userId = app.getUserId();
    let itemList = ["edit", "delete"];
    let videoList = me.data.myVideoList;
    let arrindex = e.target.dataset.arrindex;
    // let videoInfo = videoList[arrindex];
    let videoInfo = JSON.stringify(videoList[arrindex]);
    let videoId = videoList[arrindex].id;
    // let videoDesc = videoInfo.videoDesc;
    // let videoStatus = videoInfo.status;

    if (isBlanks(userId)) {
      wx.redirectTo({
        url: '../userLogin/userLogin',
      })
    } else {
      wx.showActionSheet({
        itemList: itemList,
        success: function (res) {
          // edit the video
          if (res.tapIndex == 0) {
            let isVipUser = me.data.isVip ? 1 : 0
            wx.navigateTo({
              url: '../videoEdit/videoEdit?videoInfo=' + videoInfo + "&isVipUser=" + isVipUser,
            })
            console.log(url)
          } else if (res.tapIndex == 1) {
            wx.showModal({
              title: 'Delete',
              content: '是否删除该视频',
              success(res) {
                if (res.confirm) {
                  console.log('用户点击确定' + videoId)
                  wx.request({
                    url: app.serverUrl + '/videos/' + videoId,
                    method: "DELETE",
                    header: {
                      'content-type': 'application/json', // 默认值
                      "Authorization": app.getUserId() + "::" + app.getUserToken(),
                    },
                    success: function (res) {
                      console.log(res)
                      if (res.statusCode == 401) {
                        let redirectUrl = "../userInfo/userInfo";
                        showMessageAndPauseredirect(res.data.message, "../userLogin/userLogin?redirectUrl=" + redirectUrl, "none")
                      } else if (res.statusCode == 200) {
                        showMessageAndPauseredirect(res.data.message, "../userInfo/userInfo", "success")

                      } else {
                        showErrorMessage(res.data.message)
                      }

                    }
                  })
                }
                // } else if (res.cancel) {
                //   console.log('用户点击取消' + videoDesc)
                // }
              }
            })
          }
        }
      })
    }
  }

})