package ua.kiev.prog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Set;

@Controller
public class MyController {
    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String index(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);

        model.addAttribute("login", login);
        model.addAttribute("roles", user.getAuthorities());
        model.addAttribute("email", dbUser.getEmail());
        model.addAttribute("phone", dbUser.getPhone());

        return "index";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(required = false) String email, @RequestParam(required = false) String phone) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();

        CustomUser dbUser = userService.getUserByLogin(login);
        dbUser.setEmail(email);
        dbUser.setPhone(phone);

        userService.updateUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping(path = "/admin/get_user/{id}", method = RequestMethod.GET)
    public String getUser(@PathVariable("id") Long id, Model model) {
        CustomUser dbUser = userService.getUserById(id);
        model.addAttribute("user", dbUser);
        return "edit";
    }

    @RequestMapping(value = "/admin/edit_user", method = RequestMethod.POST)
    public String updateUser(@RequestParam Long id, @RequestParam String login, @RequestParam String role,
                             @RequestParam(required = false) String email,
                             @RequestParam(required = false) String phone,
                             Model model) {
        CustomUser dbUser = userService.getUserById(id);

        boolean isOwnAccountEdited = false;
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String loginAuth = user.getUsername();
        if(dbUser.getLogin().equals(loginAuth)) {
            isOwnAccountEdited = true;
        }
        dbUser.setLogin(login);
        dbUser.setRole(new UserRole(UserRoleEnum.valueOf(role)));
        dbUser.setEmail(email);
        dbUser.setPhone(phone);
        if (userService.loginExistsForOtherId(login, id)) {
            model.addAttribute("user", dbUser);
            model.addAttribute("exists", true);
            return "edit";
        }
        userService.updateUser(dbUser);
        if(isOwnAccountEdited) {
            return "redirect:/logout";
        }
        model.addAttribute("msg", "User with id " + id + " has been updated!");
        return "forward:/admin";
    }

    private void reAuthenticateUser(CustomUser dbUser) {
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(dbUser.getRole().toString()));
        User user = new User(dbUser.getLogin(), dbUser.getPassword(), roles);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @RequestMapping(path = "/admin/delete_user/{id}", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id, Model model) {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        CustomUser dbUser = userService.getUserByLogin(login);
        userService.deleteUser(id);
        if(dbUser.getId() == id) {
            return "redirect:/logout";
        } else {
            model.addAttribute("msg", "User with id " + id + " has been deleted!");
            return "forward:/admin";
        }
    }

    @RequestMapping(value = "/newuser", method = RequestMethod.POST)
    public String update(@RequestParam String login,
                         @RequestParam String password,
                         @RequestParam(required = false) String email,
                         @RequestParam(required = false) String phone,
                         Model model) {
        if (userService.existsByLogin(login)) {
            model.addAttribute("exists", true);
            return "register";
        }

        ShaPasswordEncoder encoder = new ShaPasswordEncoder();
        String passHash = encoder.encodePassword(password, null);

        CustomUser dbUser = new CustomUser(login, passHash, new UserRole(UserRoleEnum.USER), email, phone);
        userService.addUser(dbUser);

        return "redirect:/";
    }

    @RequestMapping("/register")
    public String register() {
        return "register";
    }

    @RequestMapping("/admin")
    public String admin(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String login = user.getUsername();
        model.addAttribute("id", userService.getUserByLogin(login).getId());
        model.addAttribute("usersList", userService.getAllUsers());
        return "admin";
    }

    @RequestMapping("/unauthorized")
    public String unauthorized(Model model){
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("login", user.getUsername());
        return "unauthorized";
    }
}
