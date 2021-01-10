package com.zzx.crowd.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

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

    @Override
    protected void configure(AuthenticationManagerBuilder builder) throws Exception {
        // 临时使用内存版登陆的用户名和密码
        // builder.inMemoryAuthentication().withUser("zzx").password("zzx").roles("ADMIN");

        // 基于数据库的用户登陆及权限认证
        builder.userDetailsService(userDetailsService);

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

                // .anyRequest().authenticated()表示其他任意请求认证后才能访问
                .anyRequest()
                .authenticated()

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
