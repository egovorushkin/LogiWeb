package com.egovorushkin.logiweb;

import com.egovorushkin.logiweb.config.PersistenceJPAConfig;
import com.egovorushkin.logiweb.config.SpringMvcDispatcherServletInitializer;
import com.egovorushkin.logiweb.config.WebConfig;
import com.egovorushkin.logiweb.config.security.AuthenticationFacade;
import com.egovorushkin.logiweb.config.security.CustomAuthenticationSuccessHandler;
import com.egovorushkin.logiweb.config.security.SecurityConfig;
import com.egovorushkin.logiweb.config.security.SecurityWebApplicationInitializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {SecurityConfig.class, WebConfig.class,
        AuthenticationFacade.class, CustomAuthenticationSuccessHandler.class,
        SecurityWebApplicationInitializer.class,
        SpringMvcDispatcherServletInitializer.class,
        PersistenceJPAConfig.class})
@AutoConfigureMockMvc
public class LogiWebSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginWithValidUserThenAuthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login =
                formLogin()
                .user("driver")
                .password("driver");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("driver"));
    }

    @Test
    public void loginWithInvalidUserThenUnauthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login =
                formLogin()
                .user("12345")
                .password("12345");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    @Test
    public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @WithMockUser
    public void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/driver"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "DRIVER")
    public void loginWithRoleDriverThenExpectAdminPageForbidden() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void loginWithRoleAdminThenExpectAdminPage() throws Exception {
        mockMvc.perform(get("/admin"))
                .andExpect(status().isOk());
    }

    @Test
    public void loginWithRoleDriverThenExpectIndexPageRedirect() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login =
                formLogin()
                .user("driver")
                .password("driver");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("driver"))
                .andExpect(redirectedUrl("/driver"));
    }

    @Test
    public void loginWithRoleAdminThenExpectAdminPageRedirect() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login =
                formLogin()
                .user("admin")
                .password("admin");

        mockMvc.perform(login)
                .andExpect(authenticated().withUsername("admin"))
                .andExpect(redirectedUrl("/admin"));
    }
}
