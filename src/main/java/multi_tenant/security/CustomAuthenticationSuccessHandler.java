package multi_tenant.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import multi_tenant.auth.entity.UserClient;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws java.io.IOException {
        HttpSession session = request.getSession();
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        UserClient userClient = customUserDetails.getUserClient();
        session.setAttribute("userSession", userClient);
        response.sendRedirect("/home");
    }
}