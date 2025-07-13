//package ipp.ipp;
//
//import com.google.cloud.spring.data.spanner.core.SpannerTemplate;
//import com.google.cloud.spring.data.spanner.core.admin.SpannerDatabaseAdminTemplate;
//
//import com.google.cloud.spring.data.spanner.core.mapping.SpannerMappingContext;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import com.google.cloud.spring.data.spanner.core.SpannerOperations;
//
//@Configuration
//public class SpannerConfig {
//
//    @Bean
//    public SpannerTemplate spannerTemplate(SpannerDatabaseAdminClient spannerDatabaseAdminClient, SpannerMappingContext spannerMappingContext) {
//        return new SpannerTemplate(spannerDatabaseAdminClient, spannerMappingContext);
//    }
//}
//
