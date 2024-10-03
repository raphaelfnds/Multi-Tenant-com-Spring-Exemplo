package multi_tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import multi_tenant.entity.UserClient;
import multi_tenant.util.SessionUtil;

@Controller
public class HomeController {

	@Autowired
	private SessionUtil sessionUtil;

	//TODO: adicionar usuario da sessão diretamente ao model
	@GetMapping("/home")
	public String home(Model model, @AuthenticationPrincipal UserDetails userDetails) {
		UserClient userClient = sessionUtil.getUserClientFromSession();

		model.addAttribute("userSession", userClient);

		return "home";
	}
}
