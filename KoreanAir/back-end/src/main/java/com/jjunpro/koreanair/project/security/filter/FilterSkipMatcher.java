package com.jjunpro.koreanair.project.security.filter;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class FilterSkipMatcher implements RequestMatcher {

    private OrRequestMatcher orRequestMatcher;
    private RequestMatcher processingMatcher;
    
    private static final Logger log = LoggerFactory.getLogger(FilterSkipMatcher.class);

    public FilterSkipMatcher(List<String> pathToSkip, String processingPath) {
        this.orRequestMatcher = new OrRequestMatcher(
        		pathToSkip
        		.stream()
        		.map(skipPath -> httpPath(skipPath)).collect(Collectors.toList())
        		);
        this.processingMatcher = new AntPathRequestMatcher(processingPath);
    }
    
    private AntPathRequestMatcher httpPath(String skipPath) {
    	String[] splitStr = skipPath.split(",");
    	
    	// 배열 [1] httpMathod 방식 post get 인지 구분
    	// 배열 [0] 제외하는 url
    	return new AntPathRequestMatcher(splitStr[1], splitStr[0]);
    }

    @Override
    public boolean matches(HttpServletRequest req) {
		//    	log.info("등장 1 {}", req.getRequestURL());
		//    	log.info("등장 2 {}", !orRequestMatcher.matches(req));	// false 
		//    	log.info("등장 3 {}", processingMatcher.matches(req));	// true 가 나와야 스킵
		//    	log.info("등장 4 {}", orRequestMatcher); 
		//    	log.info("등장 5 {}", processingMatcher);
        return !orRequestMatcher.matches(req) && processingMatcher.matches(req);
    }
}
