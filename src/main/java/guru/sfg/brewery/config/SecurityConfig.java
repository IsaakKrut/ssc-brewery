package guru.sfg.brewery.config;

import guru.sfg.brewery.security.RestHeaderAuthFilter;
import guru.sfg.brewery.security.RestRequestParamAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    public RestHeaderAuthFilter restHeaderAuthFilter(AuthenticationManager authenticationManager){
        RestHeaderAuthFilter filter = new RestHeaderAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    public RestRequestParamAuthFilter restRequestParamAuthFilter(AuthenticationManager authenticationManager){
        RestRequestParamAuthFilter filter = new RestRequestParamAuthFilter(new AntPathRequestMatcher("/api/**"));
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(restHeaderAuthFilter(authenticationManager())
                , UsernamePasswordAuthenticationFilter.class)
                .csrf().disable();
        http.addFilterBefore(restRequestParamAuthFilter(authenticationManager())
                , RestHeaderAuthFilter.class);
        http
                .authorizeRequests(authorize->
                        authorize
                                .antMatchers("/h2-console/**").permitAll()
                                .antMatchers("/", "/webjars/**", "/resources/**").permitAll()
                                .antMatchers("/beers/find", "/beers*").permitAll()
                                .antMatchers("/breweries", "/breweries/index", "/breweries/index.html", "/breweries.html").hasRole("CUSTOMER")
                                .mvcMatchers(HttpMethod.GET, "/api/v1/breweries").hasRole("CUSTOMER")
                                .antMatchers(HttpMethod.GET, "/api/v1/beer/**").permitAll()
                                .mvcMatchers(HttpMethod.DELETE, "/api/v1/beer/**").hasRole("ADMIN")
                                .mvcMatchers(HttpMethod.GET, "/api/v1/beerUpc/{upc}").permitAll())
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin().and()
                .httpBasic();

        // h2 console config
        http.headers().frameOptions().sameOrigin();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("spring")
//                .password("{bcrypt}$2a$10$7tYAvVL2/KwcQTcQywHIleKueg4ZK7y7d44hKyngjTwHCDlesxdla")
//                .roles("ADMIN")
//                .and()
//                .withUser("user")
//                .password("{sha256}1296cefceb47413d3fb91ac7586a4625c33937b4d3109f5a4dd96c79c46193a029db713b96006ded")
//                .roles("USER")
//                .and()
//                .withUser("scott")
//                .password("{ldap}{SSHA}A10yuLOEGbSTbHl7csQHk7X0X3rwrqdmBomRsA==s")
//                .roles("CUSTOMER");;
//    }

    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("isaak")
//                .password("pass")
//                .roles("ADMIN")
//                .build();
//
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//
//        return new InMemoryUserDetailsManager(admin, user);
//    }
}
