package com.choongang.concert.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter, jakarta.servlet.Filter {

    // 세션없이도 입장가능한 view , api 들의 매핑주소를 whitelist에 담는다.
    private static final String[] whitelist =
            {"/" , "/user/signup" , "/user/login" , "/user/logout" , "/user/find" , "/css/*" , "/img/*" , "/js/*" , "/user/reset"
            , "/user/findId" , "/user/findPw" , "/user/IdCheck" , "/user/nicknameCheck"};



    @Override
    public void destroy(){
        log.info("loginCheckFilter destroy");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("loginCheckFilter init");
    }

    @Override
    public void doFilter(ServletRequest request , ServletResponse response , FilterChain chain)
        throws IOException , ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        try {
            log.info("인증 체크 필터 시작 {}", requestURI);
            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                // getSession(false)로 세션 생성 막고 있는지만 확인.
                HttpSession session = httpRequest.getSession(false);
                // 세션이 없냐? 세션이 있다면 안에 loginId는 있냐? 없다면...
                if (session == null || session.getAttribute("loginId") == null) {
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect
                    httpResponse.sendRedirect("/user/login?redirectURL=" +
                            requestURI);
                    return; //
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e; //
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

}
