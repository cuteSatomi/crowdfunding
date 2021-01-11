package com.zzx.crowd.mvc.config;

import com.zzx.crowd.constant.CrowdConstant;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 将SpringSecurity 的配置类注入Spring容器，
 * 注解Configuration         包含了Component注解，表示该类是一个Spring的配置类
 * 注解EnableWebSecurity    表示启用web环境下权限控制功能
 *
 * @author zzx
 * @date 2021-01-10 12:30
 */
@Configuration
@EnableWebSecurity
public class WebAppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;

    /**
     * 将密码加密所需的类注入到IOC容器，由于这是SpringSecurity相关的而又没有xml文件，因此直接在此配置类中引入
     * 注解 Bean 表示将该get方法的返回值注入到IOC容器中
     * <p>
     * 发现一个问题，在这里声明将会注入SpringMVC的IOC容器，无法被Spring的IOC容器扫描到，所以最后还是在Spring的xml配置文件中配置，在本类中引入
     *
     * @return
     * @Bean public BCryptPasswordEncoder getPasswordEncoder() {
     * return new BCryptPasswordEncoder();
     * }
     */

    @Resource
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 临时使用内存版登陆的用户名和密码
        // builder.inMemoryAuthentication().withUser("zzx").password("zzx").roles("ADMIN");

        // 基于数据库的用户登陆及权限认证
        builder.userDetailsService(userDetailsService)
                // 使用密码加密
                .passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        security
                .authorizeRequests()
                // 放行登陆页
                .antMatchers("/admin/to/login/page.html")
                .permitAll()
                // 放行静态资源
                .antMatchers("/static/**")
                .permitAll()

                // 设置经理角色才能访问以下资源
                .antMatchers("/admin/get/page.html")
                .hasRole("经理")

                // .anyRequest().authenticated()表示其他任意请求认证后才能访问
                .anyRequest()
                .authenticated()

                // Spring Security发生异常时的一些配置
                .and()
                .exceptionHandling()
                // 对于异常的处理
                .accessDeniedHandler(new AccessDeniedHandler() {
                    @Override
                    public void handle(HttpServletRequest request, HttpServletResponse response,
                                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
                        request.setAttribute("exception", new Exception(CrowdConstant.MESSAGE_ACCESS_DENIED));
                        request.getRequestDispatcher("/WEB-INF/system-error.jsp").forward(request, response);
                    }
                })

                // 关于登陆表单的一些配置
                .and()
                // 暂时禁用csrf(防跨站请求伪造)功能
                .csrf()
                .disable()
                // 开启表单登陆功能
                .formLogin()
                // 指定的登陆页面
                .loginPage("/admin/to/login/page.html")
                // 指定处理登陆请求的地址，与登陆表单中的action属性值一致
                .loginProcessingUrl("/security/do/login.html")
                // 登陆成功以后前往的url地址
                .defaultSuccessUrl("/admin/to/main/page.html")
                // 登陆页面表单中input输入框中用户名和密码对应的name属性值
                .usernameParameter("loginAcct")
                .passwordParameter("userPswd")

                // 使用Spring Security的退出登陆功能
                .and()
                // 开启退出登陆的功能
                .logout()
                // 处理退出登陆操作的地址，与退出登陆按钮上的href地址一致
                .logoutUrl("/security/do/logout.html")
                // 退出登陆成功以后前往的地址，前往登陆页面
                .logoutSuccessUrl("/admin/to/login/page.html")
        ;
    }
}
