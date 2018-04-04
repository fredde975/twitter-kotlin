package com.example.twitterkotlin.filters;


import org.apache.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
@Order(1)
public class ConcurrentRequestsFilter implements Filter {
    private static final org.apache.log4j.Logger LOG = Logger.getLogger(ConcurrentRequestsFilter.class);
    private static int concurrentRequests = 0;
    private final int MAX_CONCURRENT_REQUESTS = 3;

    @Override
    public void init(final FilterConfig filterConfig) throws ServletException {
        LOG.info("init filter");
    }

    @Override
    public void destroy() {
        LOG.info("Destroy filter");
    }

    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        incrementConcurrentRequests();
        System.out.println("Concurrent Requests: " + concurrentRequests);

        if (concurrentRequests > MAX_CONCURRENT_REQUESTS) {
            decrementConcurrentRequests();
            throw new ServletException("The number of concurrent requests is already at 3");
        }

        chain.doFilter(request, response);

        decrementConcurrentRequests();
        System.out.println("Concurrent Requests: " + concurrentRequests);
    }


    public static synchronized void incrementConcurrentRequests() {
        concurrentRequests++;
    }

    public static synchronized void decrementConcurrentRequests() {
        concurrentRequests--;
    }
}