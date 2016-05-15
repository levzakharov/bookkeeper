package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.filters;

import javax.servlet.*;
import java.io.IOException;

public class EncodingFilter implements Filter {

    private static final String DEFAULT_ENCODING = "UTF-8";

    private String encoding;

    private boolean force;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
        if (encoding == null) {
            encoding = DEFAULT_ENCODING;
        }

        force = Boolean.parseBoolean(filterConfig.getInitParameter("force"));
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (force || request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(encoding);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
