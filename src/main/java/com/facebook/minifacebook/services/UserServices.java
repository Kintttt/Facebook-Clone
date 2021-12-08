package com.facebook.minifacebook.services;

import com.facebook.minifacebook.model.User;
import com.facebook.minifacebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    @Autowired
     UserRepository userRepo;

    public boolean createUser(User user){
        boolean flag = false;

        try {

            User userData = userRepo.findUserByEmail(user.getEmail());

            if(userData == null){
                System.out.println(user);
                userRepo.save(user);
                flag = true;
            } else flag = false;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  flag;
    }

    public User getUserDetailsByEmail(String email){
        return userRepo.findUserByEmail(email);
    }



    public boolean validateUser(String email, String password){
        return userRepo.existsByEmailAndPassword(email, password);
    }


}