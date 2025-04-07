package com.DeskBooking.deskbooking.controller;

import com.DeskBooking.deskbooking.model.Role;
import com.DeskBooking.deskbooking.model.User;
import com.DeskBooking.deskbooking.service.impl.CustomUserDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class SuperAdminController {

    private final CustomUserDetailService usersService;

    public SuperAdminController(CustomUserDetailService usersService) {
        this.usersService = usersService;
    }

    @GetMapping("/superadmin/login")
    public String viewSuperAdminLogin() {
        return "superadmin";
    }

    @PostMapping("/superadmin/login*")
    public String doSuperAdminLogin() {
        return "redirect:/superadmin/panel";
    }

    @GetMapping("/superadmin/panel")
    public String viewOtherUsers(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
                                       Model model) {

        model.addAttribute("pageNo", page);
        model.addAttribute("pageSize", size);

        List<User> listUsers = usersService.getUsers(page, size);
        model.addAttribute("listUsers", listUsers);

        int numOfUsers = usersService.getNumOfUsers();

        if (numOfUsers > size) {
            List<Integer> listPageNumbers = IntStream.rangeClosed(0, numOfUsers / size).boxed().collect(Collectors.toList());
            model.addAttribute("listPageNumbers", listPageNumbers);
        }

        return "panel";
    }

    @PostMapping("/superadmin/panel")
    public String viewSuperAdminPannel(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer page,
                                       @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
                                       Model model) {

        model.addAttribute("pageNo", page);
        model.addAttribute("pageSize", size);

        List<User> listUsers = usersService.getUsers(page, size);
        model.addAttribute("listUsers", listUsers);

        int numOfUsers = usersService.getNumOfUsers();

        if (numOfUsers > size) {
            List<Integer> listPageNumbers = IntStream.rangeClosed(0, numOfUsers / size).boxed().collect(Collectors.toList());
            model.addAttribute("listPageNumbers", listPageNumbers);
        }

        return "panel";
    }

    @PostMapping ("/superadmin/login/error")
    public String viewSuperAdminError(Model model) {
        model.addAttribute("error", "Username or password is incorrect");
        return "superadmin";
    }

    @PostMapping("/superadmin/changeActivity")
    public String changeUserActivity(String username, Integer pageNo, Integer pageSize, Model model) {
        User user = usersService.getUser(username);
        usersService.changeUserActivity(user);
        return "redirect:/superadmin/panel?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString();
    }

    @PostMapping("/superadmin/delete")
    public String deleteUser(String username, Integer pageNo, Integer pageSize, Model model) {
        User user = usersService.getUser(username);
        usersService.deleteUser(user);
        return "redirect:/superadmin/panel?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString();
    }

    @PostMapping("/superadmin/changeAdminPrivilege")
    public String changeUserAdmin(String username, Integer pageNo, Integer pageSize, Model model) {
        User user = usersService.getUser(username);
        List<Role> roleList = user.getRoles();

        boolean isAdmin = roleList.stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_ADMIN"));
        boolean isSuperAdmin = roleList.stream().map(Role::getName).anyMatch(roleName -> roleName.equals("ROLE_ENJOYING_ADMIN"));

        if (!isAdmin) {
            usersService.addAdminRole(user);
        }
        else {
        	usersService.addRoleToUser(username, "ROLE_ENJOYING_ADMIN");
            usersService.removeAdminRole(user);
            usersService.removeUserRole(user);
        }
       
        if(isSuperAdmin) {
        	usersService.addRoleToUser(username, "ROLE_USER");
            usersService.removeAdminRole(user);
            usersService.removeSuperAdminRole(user);
        } 
        
        return "redirect:/superadmin/panel?pageNo=" + pageNo.toString() + "&pageSize=" + pageSize.toString();
    }

    @GetMapping("/superadmin/403")
    public String forbiddenAccess(Model model) {
        return "forbidden";
    }

    @GetMapping("/superadmin/searchUsers")
    public String searchUsers(@RequestParam(value = "pageNo", required = false, defaultValue = "0") Integer page,
                              @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer size,
                              @RequestParam String name,
                              Model model) {

        if (name == null) {
            return "redirect:/superadmin/panel?pageNo=" + page.toString() + "&pageSize=" + size.toString();
        }

        model.addAttribute("pageNo", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("name", name);

        List<User> listSearchedUsers = usersService.getUsersSearch(page, size, name);
        model.addAttribute("listUsers", listSearchedUsers);

        int numOfUsers = usersService.getUsersSearchCount(name);

        if (numOfUsers > size) {
            List<Integer> listPageNumbers = IntStream.rangeClosed(0, numOfUsers / size).boxed().collect(Collectors.toList());
            model.addAttribute("listPageNumbers", listPageNumbers);
        }

        return "panelSearch";
    }
}
