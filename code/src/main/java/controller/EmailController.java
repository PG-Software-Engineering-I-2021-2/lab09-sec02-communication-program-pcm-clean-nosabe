package controller;

import business.EmailSenderService;
import data.model.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class EmailController {

    @Autowired
    private EmailSenderService emailService;

    @GetMapping({"/email"})
    public String emailForm(Model model){
        model.addAttribute("email", new Email());
        return "email";
    }

    @PostMapping("/email")
    public String email(@Valid @ModelAttribute Email email, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/email?error";
        }
        else {
            model.addAttribute("email", emailService.sendSimpleEmail(email.getTo(),email.getContent(),email.getSubject()));
            return "redirect:/email?success";
        }
    }
}
