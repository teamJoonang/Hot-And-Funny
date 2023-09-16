package com.choongang.concert.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";

    // 컨트롤러 호출전 동작
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();

//        String uuid = UUID.randomUUID().toString();

        //@RequestMapping: HandlerMethod
        //정적 resource: ResourceHttpRequestHandler

        if (handler instanceof HandlerMethod) {
            // 호출할 컨트롤러 메서드 정보가 모두 포함
            HandlerMethod hm = (HandlerMethod) handler;

        }

        log.info("REQUEST [{}] [{}]", requestURI, handler);

        return true; // false 일경우 진행하지 않음, true일 경우 다음 인터셉터 or 컨트롤러 호출
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

        log.info("postHandle [{}]", modelAndView);

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute(LOG_ID);

        log.info("RESPONSE [{}] [{}]", logId, requestURI);

        if (ex != null) {
            log.error("afterCompletion error!!", ex);
        }
    }
}
