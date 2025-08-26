package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/authenticated/**").authenticated()
                .antMatchers("/only_for_admins/**").hasRole("ADMIN")
                .antMatchers("/read_profile/**").hasAuthority("READ_PROFILE")
                .and()
                .formLogin()
                .and()
                .logout().logoutSuccessUrl("/");


    }


//@Bean
//public  JdbcUserDetailsManager users(DataSource dataSource) {
//
//
//    UserDetails user =
//            User.withDefaultPasswordEncoder()
//                    .username("user")
//                    .password("user")
//                    .roles("USER")
//                    .build();
//    UserDetails admin = User.withDefaultPasswordEncoder()
//            .username("admin")
//            .password("admin")
//            .roles("ADMIN", "USER")
//            .build();
//
//    JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
//    if (jdbcUserDetailsManager.userExists(user.getUsername())) {
//        jdbcUserDetailsManager.deleteUser(user.getUsername());
//    }
//    if (jdbcUserDetailsManager.userExists(admin.getUsername())) {
//    jdbcUserDetailsManager.deleteUser(admin.getUsername());
//    }
//    jdbcUserDetailsManager.createUser(user);
//    jdbcUserDetailsManager.createUser(admin);
//
//
//
//    return jdbcUserDetailsManager;
//
//    }

@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
 DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
authenticationProvider.setPasswordEncoder(passwordEncoder());
//authenticationProvider.setUserDetailsService();

 return authenticationProvider;
    }
}
