function isBlanks(obj) {
  let flag = false;
  if(obj == null || obj==undefined){
    flag = true;
  }else if (Object.prototype.toString.call(obj) === "[object String]"){
    if(obj.trim().length == 0){
      flag = true;
    }
  }
  return flag;
}

function showErorMessage(errorMessage){
  wx.showToast({
    title: errorMessage,
    icon: 'none',
    duration: 3000
  })
}

function showMessageAndPauseredirect(message, url, icon ){
  wx.showToast({
    title: message,
    icon: icon = null ? "success" : icon,
    duration: 2000,
    success: function () {
      setTimeout(() => {
        wx.redirectTo({
          url: url,
        })
      }, 2000);
    }
  })
}

function showMessageAndPauseNavigate(message, url, icon ){
  wx.showToast({
    title: message,
    icon: icon = null ? "success" : icon,
    duration: 2000,
    success: function () {
      setTimeout(() => {
        wx.navigateTo({
          url: url,
        })
      }, 2000);
    }
  })
}

module.exports ={
  isBlanks : isBlanks,
  showErrorMessage: showErorMessage,
  showMessageAndPauseredirect: showMessageAndPauseredirect,
  showMessageAndPauseNavigate: showMessageAndPauseNavigate,
  
}