package com.facebook.minifacebook.controller;


import com.facebook.minifacebook.POJO.Login;
import com.facebook.minifacebook.model.PostModel;
import com.facebook.minifacebook.model.User;
import com.facebook.minifacebook.repository.UserRepository;
import com.facebook.minifacebook.services.PostServices;
import com.facebook.minifacebook.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserServices userServices;

    @Autowired
    PostServices postServices;



//    @GetMapping(path = "")
//    public String viewLandingPage(@ModelAttribute User user){
//        return "index";
//    }

    @GetMapping(path = "/")
    public String signUp(Model model, HttpServletRequest request){

        HttpSession session = request.getSession(false);

        if(session != null){
            model.addAttribute("allPost", postServices.getAllPosts());
            return "homePage";
        }


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
            System.out.println("got here 1");


            return "homePage";
        }else{

            httpSession.setAttribute("mess", "Email or Password is wrong!!!");
            System.out.println("got here 2");
            return "index";

        }


     }


}