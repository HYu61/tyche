const app = getApp()
const {
  isBlanks,
  showErrorMessage,
  showMessageAndPauseredirect
} = require("../../utils/commomUtil");

Page({
    data: {
        reasonType: "请选择原因",
        reportReasonArray: app.reportReasonArray,
        publishUserId:"",
        videoId:""
    },

    onLoad:function(params) {
      let me = this;

      let videoId = params.videoId;
      let publishUserId = params.publisherId;

      me.setData({
        publishUserId: publishUserId,
        videoId: videoId
      });
    },

    changeMe:function(e) {
      let me = this;

      let index = e.detail.value;
      let reasonType = app.reportReasonArray[index];

      me.setData({
        reasonType: reasonType
      });
    },

    submitReport:function(e) {
      let me = this;

      let reasonIndex = e.detail.value.reasonIndex;
      let reasonContent = e.detail.value.reasonContent;

      let user = app.getGlobalUserInfo();
      let loginUserId = app.getUserId();
      // let currentUserId = user.id;

      if (isBlanks(reasonIndex) || isBlanks(reasonContent)) {
        wx.showToast({
          title: '选择举报理由和描述',
          icon: "none"
        })
        return;
      }

      let serverUrl = app.serverUrl;
      wx.request({
        url: serverUrl + '/illegal_video_reports',
        method: 'POST',
        data: {
          reportedUserId: me.data.publishUserId,
          reportedVideoId: me.data.videoId,
          title: app.reportReasonArray[reasonIndex],
          content:reasonContent,
          userId: loginUserId,
        },
        header: {
          'content-type': 'application/json', // 默认值
          "Authorization": app.getUserId() + "::" + app.getUserToken()
        },
        success:function(res) {
          if(res.data.code <0){
            let redirectUrl = "../report/report#videoId@" + me.data.videoId + "!publisherId@" + me.data.publishUserId;
            showMessageAndPauseredirect(res.data.message,'../userLogin/userLogin?redirectUrl=' + redirectUrl, "none" )
          }
          if(res.statusCode == 200){
            wx.showToast({
              title: res.data.data,
              icon: 'none',
              duration: 2000,
              success: function () {
                setTimeout(() => {
                   
              wx.navigateBack();
            
                }, 2000);
              }
            })
          }
          // else{
          //   showErrorMessage("please resend it!")
          // }
        }

      })

    }
    
})
