package com.nobblecrafts.challenge.devsecopssr.security.web.filter;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public abstract class WhitelistedOncePerRequestFilter extends OncePerRequestFilter {

    private final Set<AntPathRequestMatcher> whiteList;

    protected WhitelistedOncePerRequestFilter() {
        this.whiteList = new HashSet<>();
    }

    protected WhitelistedOncePerRequestFilter(Set<AntPathRequestMatcher> whiteList) {
        this.whiteList = Collections.unmodifiableSet(whiteList);
    }

    public void addToWhiteList(HttpMethod httpMethod, String pattern) {
        AntPathRequestMatcher matcher = new AntPathRequestMatcher(pattern, httpMethod.toString());
        whiteList.add(matcher);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        log.debug("\n\nShould not filter? {}\n\n", whiteList);
        log.debug("\n\nRequest is: {}\n\n", request.getRequestURI());
        log.debug("\n\nThere is any match? {}\n\n", whiteList.stream().anyMatch(m -> m.matches(request)));
        return whiteList.stream().anyMatch(m -> m.matches(request));
    }


    public static abstract class WhiteListedOncePerRequestFilterBuilder<T extends WhitelistedOncePerRequestFilter, B extends WhiteListedOncePerRequestFilterBuilder<T, B>> {
        private final T filter;
        protected final Set<AntPathRequestMatcher> whiteList = new HashSet<>();

        protected WhiteListedOncePerRequestFilterBuilder(T filter) {
            this.filter = filter;
        }

        public B whiteList(Set<AntPathRequestMatcher> requestMatchers) {
            this.whiteList.addAll(requestMatchers);
            return self();
        }

        public B addToWhiteList(HttpMethod httpMethod, String pattern) {
            this.whiteList.add(new AntPathRequestMatcher(pattern, httpMethod.name()));
            return self();
        }

        public B addToWhiteList(String pattern) {
            this.whiteList.add(new AntPathRequestMatcher(pattern));
            return self();
        }

        protected abstract B self();

        public T build() {
            return filter;
        }
    }

}
