package cn.edu.bistu.cs.projectmaterialmanagement.webserver.config;


import cn.edu.bistu.cs.projectmaterialmanagement.webserver.filter.LoginFilter;
import cn.edu.bistu.cs.projectmaterialmanagement.webserver.service.account.IUserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalAuthentication
public class SecurityConfig {
    private final LoginFilter loginFilter;


    private final CustomSecurityMetadataSource customSecurityMetadataSource;
    private final CustomAccessDecisionManager customAccessDecisionManager;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomLogoutSuccessHandler customLogoutSuccessHandler;


    private IUserService userService;

    public SecurityConfig(LoginFilter loginFilter,
                          IUserService userService,
                          CustomSecurityMetadataSource customSecurityMetadataSource,
                          CustomAccessDecisionManager customAccessDecisionManager,
                          CustomAccessDeniedHandler customAccessDeniedHandler,
                          CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                          CustomLogoutSuccessHandler customLogoutSuccessHandler) {
        this.loginFilter = loginFilter;

        this.userService = userService;
        this.customSecurityMetadataSource = customSecurityMetadataSource;
        this.customAccessDecisionManager = customAccessDecisionManager;
        this.customAccessDeniedHandler = customAccessDeniedHandler;

        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customLogoutSuccessHandler = customLogoutSuccessHandler;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        return http
                //禁用Basic明文验证
              //  .httpBasic(AbstractHttpConfigurer::disable)
                // 前后端分离架构，不需要csrf的保护

                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth.requestMatchers(
                                //登录、注册、获得公钥等地址不需要认证，可以直接访问
                                "/user/v1/login",
                                "/user/v1/signUp",
                                "/user/v1/publicKey",
                                "/user/v1/captcha.jpg").permitAll()
                        .anyRequest().authenticated()//表示所有的请求都要经过认证之后才能访问
                )
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .userDetailsService(userService)
                .logout(logout -> logout
                        .logoutUrl("/user/v1/logout")
                        .logoutSuccessHandler(customLogoutSuccessHandler)
                )

                //过滤器之前执行jwt验证
                .addFilterBefore(loginFilter, UsernamePasswordAuthenticationFilter.class)

                //错误处理
                .exceptionHandling(except -> except.accessDeniedHandler(customAccessDeniedHandler)
                        .authenticationEntryPoint(customAuthenticationEntryPoint))

                .build();


/*


                .apply(new UrlAuthorizationConfigurer<>(applicationContext))
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(customSecurityMetadataSource);
                        object.setAccessDecisionManager(customAccessDecisionManager);
                        return object;
                    }
                })

*/


    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    SecurityFilterChain web(HttpSecurity http)
            throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .anyRequest().authenticated());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userService);
        dao.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(dao);
    }


}
