package multi_tenant.config;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.stereotype.Component;

@Component
public class CurrentTenantIdentifierResolverImpl implements CurrentTenantIdentifierResolver {

    @Override
    public String resolveCurrentTenantIdentifier() {
        String tenantId = TenantContext.getCurrentTenant();
        return (tenantId != null) ? tenantId : "users_app";
    }

    @Override
    public boolean validateExistingCurrentSessions() {
        return true;
    }
}
