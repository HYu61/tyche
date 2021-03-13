package pers.hyu.tyche.admin.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {
    private List<String> unCheckUrls;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();

        if(unCheckUrls.contains(requestUrl)){
            return true;
        }

        if(request.getSession().getAttribute("admin") != null){
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/admin/login.action");
        return false;
    }
    public List<String> getUnCheckUrls() {
        return unCheckUrls;
    }

    public void setUnCheckUrls(List<String> unCheckUrls) {
        this.unCheckUrls = unCheckUrls;
    }
}
