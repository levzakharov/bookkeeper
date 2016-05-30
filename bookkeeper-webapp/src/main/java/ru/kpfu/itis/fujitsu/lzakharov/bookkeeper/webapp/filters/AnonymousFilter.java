package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AnonymousFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        if (!(servletRequest instanceof HttpServletRequest &&
                servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("Non-HTTP requests are not supported");
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (request.getSession().getAttribute("login") == null) {
            chain.doFilter(request, response);
        } else {
            String login = request.getSession().getAttribute("login").toString();
            request.setAttribute("login", login);

            response.sendRedirect("/home");
        }
    }

    @Override
    public void destroy() {

    }
}
