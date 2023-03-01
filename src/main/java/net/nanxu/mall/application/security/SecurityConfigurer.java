package net.nanxu.mall.application.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

/**
 * SecurityConfigurer.
 *
 * @author: pan
 **/
public interface SecurityConfigurer {

    void configure(HttpSecurity http);

}
