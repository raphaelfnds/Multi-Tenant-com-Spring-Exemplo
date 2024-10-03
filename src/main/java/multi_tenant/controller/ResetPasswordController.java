package multi_tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import multi_tenant.service.PasswordResetService;

@Controller
public class ResetPasswordController {

	private final PasswordResetService passwordResetService;

	@Autowired
	public ResetPasswordController(PasswordResetService passwordResetService) {
		this.passwordResetService = passwordResetService;
	}

	@GetMapping("/reset-password")
	public String showResetPasswordForm(@RequestParam("token") String token, Model model) {
		model.addAttribute("token", token);
		return "reset-password";
	}

	@PostMapping("/reset-password")
	public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password, Model model) {
	    try {
	        passwordResetService.resetPassword(token, password);
	        model.addAttribute("success", "Senha redefinida com sucesso!");
	    } catch (Exception e) {
	        model.addAttribute("error", e.getMessage());
	    }
		return "reset-password";
	}


}
