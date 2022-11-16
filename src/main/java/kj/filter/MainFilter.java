package kj.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kj.entity.User;
import lombok.extern.java.Log;

import java.io.IOException;

@Log
@WebFilter("/*")
public class MainFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        log.info("访问资源" + url);
        if(!(url.contains("/static/") || url.endsWith("login") || url.endsWith("forgot") || url.endsWith("register"))) {
            HttpSession session = req.getSession();
            User user = (User)session.getAttribute("user");
            if(user == null) {
                log.info("没有登录下访问非静态资源，重定向到登录页面");
                res.sendRedirect("login");
                return;
            }
            log.info(user.getNickname() + "用户可以访问");
        }
        if(url.contains("/static/"))
            log.info("访问静态资源");
        chain.doFilter(req, res);
        log.info("已放行" + url);
    }
}
