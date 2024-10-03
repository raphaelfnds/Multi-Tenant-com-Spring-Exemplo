package multi_tenant.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import multi_tenant.entity.UserClient;
import multi_tenant.repository.UserClientRepository;
import multi_tenant.security.CustomUserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private final UserClientRepository userClientRepository;

	public CustomUserDetailsService(UserClientRepository userClientRepository) {
		this.userClientRepository = userClientRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserClient userClient = userClientRepository.findUserAcessByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o email: " + username));

	    return new CustomUserDetails(userClient);
	}

}