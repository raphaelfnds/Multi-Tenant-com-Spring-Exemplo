package multi_tenant.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import multi_tenant.security.CustomUserDetails;

@Component
public class TenantFilter extends OncePerRequestFilter {

    @Autowired
    private MultiTenantConnectionProviderImpl multiTenantConnectionProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()
                    && authentication.getPrincipal() instanceof CustomUserDetails) {
                CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
                String clientName = customUserDetails.getUserClient().getClient().getName();
                String tenantId = multiTenantConnectionProvider.getTenantIdForClientName(clientName);

                if (tenantId != null) {
                    TenantContext.setCurrentTenant(tenantId);
                    System.out.println("Tenant definido pelo TenantFilter: " + tenantId);
                } else {
                    //TODO: lançar uma exceção
                    TenantContext.setCurrentTenant("common");
                }
            } else {
                // Não definir o tenantId aqui; o CurrentTenantIdentifierResolver usará 'users_app' como padrão
            }

            filterChain.doFilter(request, response);
        } finally {
            // Não limpar o TenantContext aqui para manter o tenantId após a autenticação
            // TenantContext.clear();
        }
    }
}
