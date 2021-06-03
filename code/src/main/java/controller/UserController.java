package controller;

import business.UserService;
import data.entities.User;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Controller
public class UserController {

    @GetMapping({"/","/login"})
    public String index() {
        return "index";
    }

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/chat")
    public String chat(Authentication auth, HttpSession session) {
        String username = auth.getName();
        if (session.getAttribute("user") == null){
            var user = userRepository.findByUsername(username);
            user.setPassword(null);
            session.setAttribute("user",user);
        }

        return "chat";
    }

    @GetMapping({"/registro"})
    public String registroForm(Model model){
        model.addAttribute("user", new User());
        return "registro";
    }

    @PostMapping("/registro")
    public String registro(@Valid @ModelAttribute User user, BindingResult result, Model model){
        if(result.hasErrors()){
            return "redirect:/registro?error";
        }
        else {
            model.addAttribute("user", userService.registrar(user));
            return "redirect:/registro?success";
        }
    }

    @ResponseBody
    @GetMapping("/fetchAllUsers")
    public List<User> fetchALl() {
        List<User> lista = userRepository.findAll();

        var logger = Logger.getLogger(UserController.class.getName());

        for (var i : lista){
            logger.log(Level.INFO, i.getUsername());
        }
        return lista;
    }

}
