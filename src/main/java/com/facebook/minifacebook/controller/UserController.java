package com.facebook.minifacebook.controller;


import com.facebook.minifacebook.POJO.Login;
import com.facebook.minifacebook.model.PostModel;
import com.facebook.minifacebook.model.User;
import com.facebook.minifacebook.repository.UserRepository;
import com.facebook.minifacebook.services.CommentServices;
import com.facebook.minifacebook.services.PostServices;
import com.facebook.minifacebook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {


    private final UserRepository userRepo;

    private final CommentServices commentServices;

    private final UserServices userServices;

    private final PostServices postServices;

    @Autowired
    public UserController(UserRepository userRepo, CommentServices commentServices, UserServices userServices, PostServices postServices) {
        this.userRepo = userRepo;
        this.commentServices = commentServices;
        this.userServices = userServices;
        this.postServices = postServices;
    }

    @GetMapping(path = "/")
    public String signUp(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false);
        model.addAttribute("allPost", postServices.getAllPosts());
        model.addAttribute("comment", commentServices);

        if(session != null){
            model.addAttribute("allPost", postServices.getAllPosts());
            model.addAttribute("commentService", commentServices);

            return "homePage";
        }

        model.addAttribute("commentService", commentServices);
        model.addAttribute("user", new User());

        return "index";
    }

    @PostMapping(path = "/signUp")
    public String addUser(HttpServletRequest request, HttpServletResponse response,
                          @ModelAttribute("user") User user) {

        HttpSession httpSession = request.getSession();

        if(userServices.createUser(user)){
            httpSession.setAttribute("mess", "Successfully registered!!!");
        }else{
            httpSession.setAttribute("mess", "Failed to register or email already exist");
        }

        return "index";
    }


     @PostMapping(path = "/homePage")
    public String loginUser(HttpServletRequest request, @ModelAttribute("login") Login login, Model model){

        HttpSession httpSession = request.getSession();


        boolean result = userServices.validateUser(login.getEmail(), login.getPassword());

        if(result){
            httpSession.setAttribute("userId", userServices.getUserDetailsByEmail(login.getEmail()).getUserId());
            httpSession.setAttribute("firstName", userServices.getUserDetailsByEmail(login.getEmail()).getFirstName());
            httpSession.setAttribute("lastName", userServices.getUserDetailsByEmail(login.getEmail()).getLastName());
            httpSession.setAttribute("fullName", userServices.getUserDetailsByEmail(login.getEmail()).getFirstName() + " " + userServices.getUserDetailsByEmail(login.getEmail()).getLastName());

            model.addAttribute("firstName", userServices.getUserDetailsByEmail(login.getEmail()).getFirstName());
            model.addAttribute("lastName", userServices.getUserDetailsByEmail(login.getEmail()).getLastName());
            model.addAttribute("allPost", postServices.getAllPosts());
            model.addAttribute("commentService", commentServices);
            System.out.println("got here 1");


            return "homePage";
        }else{

            httpSession.setAttribute("mess", "Email or Password is wrong!!!");
            System.out.println("got here 2");
            return "index";

        }


     }


}
