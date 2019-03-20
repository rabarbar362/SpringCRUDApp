package springProba;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/")
public class MainController {
    private final UserRepository userRepository;

    @Autowired
    public MainController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(path="/add")
    public @ResponseBody String addNewUser (@RequestParam String name, String email) {
        User n = new User();
        n.setName(name);
        n.setEmail(email);
        userRepository.save(n);
        return "User with name " + name + " and email " + email + " saved!";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable <User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping(path = "/delete")
    public @ResponseBody String deleteUser (@RequestParam int id) {
        userRepository.deleteById(id);
        return "User with id = " + id + " has been deleted.";
    }

    @GetMapping(path="/modify")
    public @ResponseBody String changeUserEmail (@RequestParam int id, String newEmail) {
        boolean isUserPresent = userRepository.findById(id).isPresent();
        if (isUserPresent) {
            User modifiedUser = userRepository.findById(id).get();
            modifiedUser.setEmail(newEmail);
            userRepository.save(modifiedUser);
            return "User's email changed successfully.";
        } else {
            return "There is no user with id = " + id + " in the database. Try again!";
        }
    }
}
