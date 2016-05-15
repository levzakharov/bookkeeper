package ru.kpfu.itis.fujitsu.lzakharov.bookkeeper.webapp.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Writes log in Common Logging format.
 */

public class LogFilter implements Filter {
    private static final Logger log = Logger.getLogger(LogFilter.class);
    private Locale locale;
    private TimeZone timezone;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String language = filterConfig.getInitParameter("language");
        this.locale = language != null ? new Locale(language) : Locale.getDefault();

        String timezone = filterConfig.getInitParameter("timezone");
        this.timezone = timezone != null ? TimeZone.getTimeZone(timezone) : TimeZone.getDefault();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        Date reqDate = new Date();
        DateFormat format = DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale);
        format.setTimeZone(timezone);
        String date = format.format(reqDate);

        if (!(servletRequest instanceof HttpServletRequest &&
                servletResponse instanceof HttpServletResponse)) {
            throw new ServletException("Non-HTTP requests are not supported");
        }

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String ip = servletRequest.getRemoteAddr();
        String username = request.getRemoteUser();
        if (username == null) {
            username = "-";
        }
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String protocol = request.getProtocol();

        filterChain.doFilter(servletRequest, servletResponse);

        Integer status = response.getStatus();
        String contentLength = response.getHeader("Content-Length");
        if (contentLength == null) {
            contentLength = "-";
        }

        log.info(String.format("%s - %s [%s] \"%s %s %s\" %d %s", ip, username, date, method, uri, protocol, status,
                contentLength));
    }

    @Override
    public void destroy() {
    }
}
