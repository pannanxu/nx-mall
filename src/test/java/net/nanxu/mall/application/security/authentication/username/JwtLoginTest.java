package net.nanxu.mall.application.security.authentication.username;

import net.nanxu.mall.application.security.WebMvcSecurityConfig;
import net.nanxu.mall.application.security.authorization.validator.ValidatorFilter;
import net.nanxu.mall.application.security.oauth.OAuthSecurityConfigurer;
import net.nanxu.mall.infra.config.ApplicationProperties;
import net.nanxu.mall.infra.exception.AppErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = WebMvcSecurityConfig.class)
@WebMvcTest
@Import({
        OAuthSecurityConfigurer.class,
        ValidatorFilter.class,
        UsernameAuthenticationConfigurer.class,
        ApplicationProperties.class
})
class JwtLoginTest {

    @MockBean
    UserDetailsService userDetailsService;
    private MockMvc mvc;

    @BeforeEach
    public void setup(@Autowired WebApplicationContext context,
                      @Autowired PasswordEncoder encoder) {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        String password = encoder.encode("123456");
        when(userDetailsService.loadUserByUsername("admin"))
                .thenReturn(User.withUsername("admin")
                        .password(password)
                        .authorities(new ArrayList<>())
                        .build());
    }

    @Test
    public void onAuthenticationSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/token")
                        .with(anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "123456"
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.authType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").exists())
        ;
    }


    @Test
    public void onAuthenticationFailure() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/auth/token")
                        .with(anonymous())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "username": "admin",
                                  "password": "654321"
                                }
                                """)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.errCode").value(AppErrorCode.AUTHENTICATION_FAILURE.getErrCode()))
        ;
    }
}