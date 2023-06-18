package com.bshsalumni.auction.util;

import com.bshsalumni.auction.common.Constants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class RequestFilter implements Filter {
    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        log.info("Initializing filter :{}", this);
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        MDC.put(Constants.MDC_TRACKER, String.valueOf(DateTimeUtility.getCurrentTimeUnix()));
        log.info("API hit -> {} at {}", req.getRequestURI(), DateTimeUtility.getCurrentDateTimeISTString());

//        res.setHeader("Access-Control-Allow-Origin", req.getHeader("Origin"));
//        res.setHeader("Access-Control-Allow-Credentials", "true");
//        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        res.setHeader("Access-Control-Max-Age", "3600");
//        res.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me");


        chain.doFilter(request, response);

        log.info("API responded with code {} at {}", res.getStatus(), DateTimeUtility.getCurrentDateTimeISTString());
        MDC.clear();
    }

    @Override
    public void destroy() {
        log.warn("Destructing filter :{}", this);
    }
}
