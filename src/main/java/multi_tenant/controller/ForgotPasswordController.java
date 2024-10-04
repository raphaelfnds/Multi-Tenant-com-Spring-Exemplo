package multi_tenant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import multi_tenant.auth.service.PasswordResetService;

@Controller
public class ForgotPasswordController {

    private final PasswordResetService passwordResetService;

    @Autowired
    public ForgotPasswordController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @GetMapping("/forgot-password")
    public String showForgotPasswordForm() {
        return "forgot-password";
    }
    
    @PostMapping("/forgot-password")
    public String processForgotPasswordForm(@RequestParam("email") String email, Model model) {
        try {
            passwordResetService.generatePasswordResetToken(email);
            model.addAttribute("success", "Um link de redefinição de senha foi enviado para o email cadastrado.");
        } catch (UsernameNotFoundException ex) {
            model.addAttribute("error", "O email fornecido não está cadastrado.");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", "Existe uma solicitação ativa, por favor, aguarde 10 minutos antes de fazer uma nova solicitação.");
        }
        return "forgot-password";
    }
    
}
