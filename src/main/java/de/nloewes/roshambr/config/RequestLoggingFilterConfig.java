package de.nloewes.roshambr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

/**
 * Configuration class to provide beans specific for request logging
 *
 * @author nloewes
 */
@Configuration
public class RequestLoggingFilterConfig {

    /**
     * Generic request logging bean
     */
    @Bean
    public CommonsRequestLoggingFilter loggingFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1000); // too small for real applications, but should be fine here
        filter.setAfterMessagePrefix("Request data: ");
        return filter;
    }
}
