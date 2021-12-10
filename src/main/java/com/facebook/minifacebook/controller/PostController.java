package com.facebook.minifacebook.controller;

import com.facebook.minifacebook.POJO.Post;
import com.facebook.minifacebook.model.CommentModel;
import com.facebook.minifacebook.model.PostModel;
import com.facebook.minifacebook.services.CommentServices;
import com.facebook.minifacebook.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class PostController {

    private final PostServices postServices;
    private final CommentServices commentServices;


    @Autowired
    public PostController(PostServices postServices, CommentServices commentServices) {
        this.postServices = postServices;
        this.commentServices = commentServices;
    }


    @PostMapping(path = "/post")
    public String  savePost(HttpServletRequest request, @ModelAttribute("post") Post postText, Model model){

        HttpSession session = request.getSession(false);
        session.setAttribute("postServices", postServices);

        if(postText.getPostText().length() == 0){

            model.addAttribute("allPost", postServices.getAllPosts());

            return "redirect:/";
        }

        Long userId = (Long) session.getAttribute("userId");
        String firstName = (String) session.getAttribute("firstName");
        String lastName = (String) session.getAttribute("lastName");
        String fullName = (String) session.getAttribute("fullName");

//        String postText, int noOfLikes, int noOfComments, Long posterId, String posterName
        PostModel post = new PostModel(postText.getPostText(), 0, 0 ,userId, fullName);
        postServices.createPost(post);
        System.out.println(userId);

        model.addAttribute("allPost", postServices.getAllPosts());

        return "redirect:/";
    }


    @RequestMapping(path = "/delete/{postId}/{posterId}")
    public String removePost(HttpServletRequest request, @PathVariable String posterId, @PathVariable String postId, Model model){

        HttpSession session = request.getSession(false);

        Long userId = (Long) session.getAttribute("userId");


        if(userId.equals(Long.parseLong(posterId))){

            postServices.deletePost(Long.parseLong(postId));
            model.addAttribute("postServices", postServices);
            System.out.println("deleted " + postId);
//            model.addAttribute("allPost", postServices.getAllPosts());
            return "redirect:/";
        }

        System.out.println("couldn't delete " + postId +" " + posterId + " "+ userId);
        model.addAttribute("allPost", postServices.getAllPosts());
        return "redirect:/";
    }

    @PostMapping("/comment/{postId}")
    public String CommentPost(HttpServletRequest request, @PathVariable String postId, @RequestParam("commentText") String commentTxt){

        HttpSession session = request.getSession(false);

        if(commentTxt.length() == 0){

            return "redirect:/";
        }

        session.setAttribute("postId", Long.parseLong(postId));

        Long userId = (Long) session.getAttribute("userId");
        String fullName = (String) session.getAttribute("fullName");

        CommentModel commentModel = new CommentModel(Long.parseLong(postId), userId, commentTxt, fullName);

        commentServices.createComment(commentModel);

        return "redirect:/";
    }


    @PostMapping("/edit/{postId}")
    public String editPost(HttpServletRequest request, @PathVariable String postId, @RequestParam("postEditor") String postEditor, Model model){

        HttpSession session = request.getSession(false);
        session.setAttribute("postId", Long.parseLong(postId));
        session.setAttribute("postServices", postServices);
        Long userId = (Long) session.getAttribute("userId");

        PostModel postsId = postServices.getPostById(Long.parseLong(postId));

        Long posterId = postsId.getPosterId();

        if(userId.equals(posterId)){
            model.addAttribute("postServices", postServices);
            postsId.setPostText(postEditor);
            postServices.createPost(postsId);

//            model.addAttribute("allPost", postServices.getAllPosts());

            return "redirect:/";

        }


        return "redirect:/";
    }


    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
        System.out.println("got here");
        return "index";
    }



}
