package pers.hyu.tyche.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pers.hyu.tyche.service.VipVideoAccessService;
import pers.hyu.tyche.utils.ResponseEnvelope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

public class VipVideoInterceptor implements HandlerInterceptor {
    @Autowired
    VipVideoAccessService vipVideoAccessService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!request.getMethod().equalsIgnoreCase("get")){
            response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
            returnErrorResponse(response, ResponseEnvelope.error(-405, "Method not allowed!"));
            return false;
        }
        String publisherId = request.getParameter("publisherId");
        String loginUserId = request.getParameter("loginUserId");
        if(StringUtils.isBlank(publisherId) || StringUtils.isBlank(loginUserId)){
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            returnErrorResponse(response, ResponseEnvelope.error(-400, "Missing publisherId or loginUserId"));
            return false;
        }

        if(vipVideoAccessService.isUserAccessible(publisherId, loginUserId)){
            return true;
        }else {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            returnErrorResponse(response, ResponseEnvelope.error(-403, "Forbidden, pleas answer the question to access the video."));
            return false;
        }

    }


    /**
     * JSON the error message for response.
     *
     * @param response The response for the request.
     * @param result   The error message for the invalid situation.
     * @throws IOException
     */
    private void returnErrorResponse(HttpServletResponse response, ResponseEnvelope result) throws IOException {
        OutputStream out = null;
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("text/json");
            out = response.getOutputStream();
            String resultJson = new ObjectMapper().writeValueAsString(result);
            out.write(resultJson.getBytes("utf-8"));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
