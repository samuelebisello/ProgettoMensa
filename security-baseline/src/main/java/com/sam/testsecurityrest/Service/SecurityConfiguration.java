
package com.sam.testsecurityrest.Service;
/*
import com.google.common.collect.ImmutableList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
@Configuration
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private Provider provider;

    @Autowired
    public SecurityConfiguration(Provider provider) {
        super();
        this.provider = provider;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(provider);

    }

  */
/*  @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/users", "/user/**");
    }*//*


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .authorizeRequests().antMatchers("/public/**").permitAll()
                .and().exceptionHandling()
                .defaultAuthenticationEntryPointFor(new Http403ForbiddenEntryPoint(),new AntPathRequestMatcher("/protected/**"))
                .and()
                .authenticationProvider(provider)
                .addFilterBefore(authTokenFilter(), AnonymousAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated().and()
                .csrf()
                .disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "X-PINGOTHER"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    @Bean
    public AuthTokenFilter authTokenFilter() throws Exception{
        AuthTokenFilter authTokenFilter = new AuthTokenFilter(("protected/**"));
        authTokenFilter.setAuthenticationManager(authenticationManager());
        authTokenFilter.setAuthenticationSuccessHandler(successHandler());
        authTokenFilter.setAuthenticationFailureHandler(failureHandler());
        return authTokenFilter;
    }


    @Bean
    SimpleUrlAuthenticationSuccessHandler successHandler() {
        final SimpleUrlAuthenticationSuccessHandler successHandler = new SimpleUrlAuthenticationSuccessHandler();
        successHandler.setRedirectStrategy((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) ->{

        });
        return successHandler;
    }

    @Bean
    SimpleUrlAuthenticationFailureHandler failureHandler() {
        final SimpleUrlAuthenticationFailureHandler failure = new SimpleUrlAuthenticationFailureHandler();
        failure.setRedirectStrategy((HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String s) ->{
            httpServletResponse.getWriter().write("");
        });
        return failure;
    }

    @Bean
    public BasicAuthenticationFilter basicFilter() throws Exception {
        return new CustomBasicAuth(authenticationManager());
    }



}
*/

public class SecurityConfiguration {
}
