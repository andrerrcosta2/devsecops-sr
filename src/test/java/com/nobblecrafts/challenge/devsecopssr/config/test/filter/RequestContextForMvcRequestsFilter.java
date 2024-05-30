package com.nobblecrafts.challenge.devsecopssr.config.test.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class RequestContextForMvcRequestsFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("RequestContextForMvcRequestsFilter: before setting attributes");
            if (RequestContextHolder.getRequestAttributes() == null)
                RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request, response));
            log.info("RequestContextForMvcRequestsFilter: after setting attributes");
            filterChain.doFilter(request, response);
            log.info("RequestContextForMvcRequestsFilter: after filter chain");
        } finally {
            RequestContextHolder.resetRequestAttributes();
            log.info("RequestContextForMvcRequestsFilter: after resetting attributes");
        }
    }
}
