package code;

import com.djawnstj.mvcframework.annotation.Autowired;
import com.djawnstj.mvcframework.annotation.Controller;

@Controller
public class UserController {

    public UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    public UserService getUserService() {
        return userService;
    }
}
