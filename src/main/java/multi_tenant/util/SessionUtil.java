package multi_tenant.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import multi_tenant.entity.Client;
import multi_tenant.entity.UserClient;
import multi_tenant.security.CustomUserDetails;

@Component
public class SessionUtil {

	public Client getClientFromSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		return customUserDetails.getUserClient().getClient();
	}

	public UserClient getUserClientFromSession() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
		return customUserDetails.getUserClient();
	}

}
