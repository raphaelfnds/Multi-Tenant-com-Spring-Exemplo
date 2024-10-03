package multi_tenant.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import multi_tenant.entity.UserClient;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = 2891672725159639170L;

	private final UserClient userClient;

	public CustomUserDetails(UserClient userClient) {
		this.userClient = userClient;
	}

	public UserClient getUserClient() {
		return userClient;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		String role = "ROLE_" + userClient.getAcessType().name().toUpperCase();
        return List.of(new SimpleGrantedAuthority(role));
	}

	@Override
	public String getPassword() {
		return userClient.getPassword();
	}

	@Override
	public String getUsername() {
		return userClient.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return userClient.getSituation();
	}
}