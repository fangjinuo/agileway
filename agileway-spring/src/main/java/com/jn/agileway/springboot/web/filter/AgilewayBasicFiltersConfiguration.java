package com.jn.agileway.springboot.web.filter;

import com.jn.agileway.web.filter.accesslog.AccessLogFilter;
import com.jn.agileway.web.filter.accesslog.WebAccessLogProperties;
import com.jn.agileway.web.filter.rr.RRFilter;
import com.jn.agileway.web.filter.waf.sqlinjection.SqlFirewall;
import com.jn.agileway.web.filter.waf.sqlinjection.SqlInjectionFilter;
import com.jn.agileway.web.filter.waf.sqlinjection.SqlInjectionProperties;
import com.jn.agileway.web.filter.waf.sqlinjection.SqlInjectionWafFactory;
import com.jn.agileway.web.filter.waf.xcontenttype.XContentTypeOptionsFilter;
import com.jn.agileway.web.filter.waf.xcontenttype.XContentTypeOptionsProperties;
import com.jn.agileway.web.filter.waf.xss.XssFilter;
import com.jn.agileway.web.filter.waf.xss.XssFirewall;
import com.jn.agileway.web.filter.waf.xss.XssProperties;
import com.jn.agileway.web.filter.waf.xss.XssWafFactory;
import com.jn.agileway.web.prediates.HttpRequestPredicateGroup;
import com.jn.agileway.web.prediates.HttpRequestPredicateGroupFactory;
import com.jn.langx.util.collection.Collects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Map;

@Configuration
public class AgilewayBasicFiltersConfiguration {

    @Value("${agileway.web.encoding:UTF-8}")
    private String encoding = "UTF-8";

    @Value("${agileway.web.streamWrapper:false}")
    private boolean streamWrapper = false;

    @Order(-102)
    @Bean
    public FilterRegistrationBean baseFilterRegistrationBean() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        RRFilter filter = new RRFilter();
        registration.setFilter(filter);
        Map<String, String> initialParameters = Collects.emptyHashMap();
        initialParameters.put("streamWrapperEnabled", "" + streamWrapper);
        initialParameters.put("encoding", encoding);
        registration.setInitParameters(initialParameters);
        registration.setUrlPatterns(Collects.newArrayList("/*"));
        registration.setOrder(-102);
        registration.setName("StreamWrapper Filter");
        return registration;
    }


    @ConfigurationProperties(prefix = "agileway.web.access-log")
    @Bean
    public WebAccessLogProperties accessLogProperties() {
        return new WebAccessLogProperties();
    }

    @Order(-101)
    @Bean
    @Autowired
    public FilterRegistrationBean accessLogFilterRegistrationBean(WebAccessLogProperties accessLogProperties) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        AccessLogFilter filter = new AccessLogFilter();
        filter.setConfig(accessLogProperties);
        registration.setFilter(filter);
        registration.setName("AccessLog Filter");
        registration.setOrder(-101);
        return registration;
    }


    @ConfigurationProperties(prefix = "agileway.web.waf.xss")
    @Bean
    public XssProperties xssProperties() {
        return new XssProperties();
    }


    @Order(-100)
    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean(XssProperties xssProperties) {
        XssFirewall firewall = new XssWafFactory().get(xssProperties);
        XssFilter filter = new XssFilter();
        filter.setFirewall(firewall);

        firewall.init();

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("XSS Filter");
        registration.setFilter(filter);
        registration.setOrder(-100);
        return registration;
    }

    @Bean
    @ConfigurationProperties(prefix = "agileway.web.waf.xcontent")
    public XContentTypeOptionsProperties xContentTypeOptionsProperties() {
        return new XContentTypeOptionsProperties();
    }

    @Order(-98)
    @Bean
    public FilterRegistrationBean xContentTypeOptionsRegistrationBean(XContentTypeOptionsProperties properties) {
        XContentTypeOptionsFilter filter = new XContentTypeOptionsFilter();
        filter.setProperties(properties);
        HttpRequestPredicateGroup predicates = new HttpRequestPredicateGroupFactory().get(properties.getPredicates());
        filter.setPredicates(predicates);
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("X-ContentType-Options Filter");
        registration.setFilter(filter);
        registration.setOrder(-98);
        return registration;
    }

    @ConfigurationProperties(prefix = "agileway.web.waf.sql-injection")
    @Bean
    public SqlInjectionProperties sqlInjectProperties() {
        return new SqlInjectionProperties();
    }


    @Order(-97)
    @Bean
    public FilterRegistrationBean sqlInjectionRegistrationBean(SqlInjectionProperties sqlInjectProperties) {
        SqlFirewall firewall = new SqlInjectionWafFactory().get(sqlInjectProperties);
        SqlInjectionFilter filter = new SqlInjectionFilter();
        filter.setFirewall(firewall);

        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setName("SQLInjection Filter");
        registration.setFilter(filter);
        registration.setOrder(-97);
        return registration;
    }
}
