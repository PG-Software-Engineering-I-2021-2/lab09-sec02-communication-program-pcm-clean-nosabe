package controller;

import business.UserService;
import data.entities.User;
import data.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

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
            User user = userRepository.findByUsername(username);
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

    @GetMapping("/fetchAllUsers")
    public List<User> fetchALl() {
        System.out.println("asasdasdasd");
        List<User> lista = userRepository.findAll();
        for (var i : lista){
            System.out.println(i.getUsername());
        }
        return lista;
    }

}
