package com.study.controller;

import com.study.model.Resources;
import com.study.model.User;
import com.study.service.ResourcesService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class HomeController {
    @Resource
    private ResourcesService resourcesService;
    /**
     * 登录页面
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }
    /**
     * 登录跳转
     * @param request
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, User user, Model model) {
        if (StringUtils.isEmpty(user.getUsername()) || StringUtils.isEmpty(user.getPassword())) {
            request.setAttribute("msg", "用户名或密码不能为空！");
            return "login";
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        try {
            subject.login(token);
            return "redirect:index";
        }catch (LockedAccountException lae) {
            token.clear();
            request.setAttribute("msg", "用户已经被锁定不能登录，请与管理员联系！");
            return "login";
        } catch (AuthenticationException e) {
            token.clear();
            request.setAttribute("msg", "用户或密码不正确！");
            return "login";
        }
    }

    /**
     * Description: 注销
     *
     * @param
     * @author Easong
     * @version 2018/1/26
     * @since JDK1.7
     */
    @RequestMapping("/loginOut")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    /**
     * 首页
     * @param request
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request){
      Map<String,Object> map = new HashMap<>();
      Integer userid = (Integer) SecurityUtils.getSubject().getSession().getAttribute("userSessionId");
      map.put("type",1);
      map.put("userid",userid);
      List<Resources> resourcesList = resourcesService.loadUserResources(map);
      request.setAttribute("resources", resourcesList);
      return "index";
    }
    
    @RequestMapping(value="/resources/add",method = RequestMethod.GET)
    public String resources_add(HttpServletRequest request){
        Resources resources=new Resources();
        resources.setType(1);
        List<Resources> resourcesList = resourcesService.queryByType(resources, null);
        request.setAttribute("resources", resourcesList);
        return "resources/resources_add";
    }
         
    @RequestMapping(value={"/usersPage",""})
    public String usersPage(HttpServletRequest request){
        return "user/users";
    }

    @RequestMapping("/rolesPage")
    public String rolesPage() {
        return "role/roles";
    }

    @RequestMapping("/resourcesPage")
    public String resourcesPage() {
        return "resources/resources";
    }

    @RequestMapping("/403")
    public String forbidden() {
        return "403";
    }
}
