function uploadVideo(userStatus) {
  let maxDuration = 30;
  let compressed = true;
  if (userStatus === 1) {
    maxDuration = 60;
    compressed = false;
  }
  wx.chooseVideo({
    sourceType: ['album', 'camera'],
    maxDuration: maxDuration,
    camera: 'back',
    compressed: compressed,
    success(res) {
      let duration = res.duration;
      let temHeight = res.height;
      let temWidth = res.width;
      let temSize = res.size;
      let temFilePath = res.tempFilePath;
      let errorMsg = "";
      if (duration > 1 && duration < maxDuration + 1) {
        wx.navigateTo({
          url: '/pages/chooseBgm/chooseBgm?duration=' + duration +
            '&videoHeight=' + temHeight +
            '&videoWidth=' + temWidth +
            '&videoSize=' + temSize +
            '&temFilePath=' + temFilePath
        })
      } else {
        if (duration < 1) {
          errorMsg = "Video can not shorter than 1s";
        } else if (duration > maxDuration + 1) {
          if (userStatus === 0) {
            errorMsg = "Video can not over "+ maxDuration+"s!!!";
          } else if (userStatus === 1) {
            errorMsg = "The video can not over " + maxDuration+ "s!!!"
          }
        }

        wx.showToast({
          title: errorMsg,
          icon: 'none',
          duration: 2500
        })
      }

    }
  })
}

module.exports = {
  uploadVideo: uploadVideo
}