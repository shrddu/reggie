package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author shr567
 * @create 2022/7/25 - 14:40
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest= (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse= (HttpServletResponse) servletResponse;
        //1、获取本次请求的URI
        String uri = httpServletRequest.getRequestURI();
        log.info("拦截到请求：{}",uri);

        String[] uris=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(uris, uri);

        //3、如果不需要处理，则直接放行
        if (check){
            log.info("本次请求不需要处理");
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }

        //4、判断登录状态，如果已登录，则直接放行
        if(httpServletRequest.getSession().getAttribute("employee") != null){
            log.info("用户已登录，放行");
            BaseContext.setCurrentId((Long) httpServletRequest.getSession().getAttribute("employee"));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        //4.5、判断手机端登录状态
         if(httpServletRequest.getSession().getAttribute("user") != null){
            log.info("用户已登录，放行");
            BaseContext.setCurrentId((Long) httpServletRequest.getSession().getAttribute("user"));
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }


        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        log.info("用户未登录");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }

    public boolean check(String[] uris,String requestUri){
        for (String s : uris) {
            boolean match = PATH_MATCHER.match(s, requestUri);
            if(match){
                return true;
            }
        }
        return false;
    }

}
