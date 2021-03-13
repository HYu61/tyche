package pers.hyu.tyche.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import pers.hyu.tyche.enums.ErrorMessages;
import pers.hyu.tyche.enums.RedisKeyEnum;
import pers.hyu.tyche.utils.ResponseEnvelope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * The interceptor is used for validate the user token.
 * When the user token is null or expired or incorrect, the user can not access the resource.
 *
 * @author Heng Yu
 * @version 1.0
 */
public class AuthTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        String url = request.getRequestURI();
        boolean video = request.getRequestURL().indexOf("/videos/") != -1;
//        boolean vipVideo = request.getRequestURL().indexOf("/videos/vip") != -1;

        int authorizationResult = isUserTokenCorrect(authorization);

        if (request.getMethod().equalsIgnoreCase("get")
                && (request.getRequestURL().indexOf("/comments") != -1
                || video)) {
            return true;
        }

        switch (authorizationResult){
            case -1:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                returnErrorResponse(response, ResponseEnvelope.error(-1, ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage()));
                return false;
            case -2:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                returnErrorResponse(response, ResponseEnvelope.error(-2, "Token Expired! Please login again!"));
                return false;
            case -3:
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                returnErrorResponse(response, ResponseEnvelope.error(-3, "Login in other device already, please login again!"));
                return false;
            default:
                return true;

        }


//
//        if (StringUtils.isNotBlank(authorization)) {
//            String userId = authorization.split("::")[0];
//            String userToken = authorization.split("::")[1];
//
//            String keyPrefix = RedisKeyEnum.USER_SESSION_TOKEN_KEY.getKey();
//            String redisToken = stringRedisTemplate.boundValueOps(keyPrefix + "::" + userId).get();
//            int authorizationResult = isUserTokenCorrect(authorization);
//            if (authorizationResult == -2) {
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                returnErrorResponse(response, ResponseEnvelope.error(-2, "Token Expired! Please login again!"));
//                return false;
//            } else if (authorizationResult==-3) {
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                returnErrorResponse(response, ResponseEnvelope.error(-3, "Login in other device already, please login again!"));
//                return false;
//            }
//        } else {
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            returnErrorResponse(response, ResponseEnvelope.error(-1, ErrorMessages.AUTHENTICATION_FAILED.getErrorMessage()));
//            return false;
//        }
//
//        return true;

    }

    /**
     * Check the user token.
     *
     * @param authorization The user token from the request
     * @return The result
     */
    private int  isUserTokenCorrect(String authorization){

        if (StringUtils.isNotBlank(authorization)) {
            String userId = authorization.split("::")[0];
            String userToken = authorization.split("::")[1];

            String keyPrefix = RedisKeyEnum.USER_SESSION_TOKEN_KEY.getKey();
            String redisToken = stringRedisTemplate.boundValueOps(keyPrefix + "::" + userId).get();
            if (StringUtils.isBlank(redisToken)) {
//                Token Expired!
                return -2;
            } else if (!redisToken.equals(userToken)) {
//                Login in other device already
                return -3;
            }
        } else {
// not login
            return -1;
        }
        // authorization is correct
        return 1;
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
