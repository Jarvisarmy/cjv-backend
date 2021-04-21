package com.example.fake_amazon_API.services;

import com.example.fake_amazon_API.models.UserModel;
import com.example.fake_amazon_API.models.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModel addUser(UserModel user) throws Exception{
        boolean isFound = true;
        String emailPattern = "^(.+)@(.+)$";
        if (user.getPassword() == "") {
            throw new Exception("the password cannot be empty");
        } else if (user.getPassword().length() < 6) {
            throw new Exception("the password must has length at least 6");
        } else if (user.getPassword().length() > 12) {
            throw new Exception("the password must has length at most 12");
        }
        if (!Pattern.compile(emailPattern).matcher(user.getEmail()).find()) {
            throw new Exception("the email is not valid");
        }
        if (user.getUsername() == "") {
            throw new Exception("the username cannot be empty");
        }
        UserModel foundUser = userRepository.findByUsername(user.getUsername());
        if (foundUser == null) {
            String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            UserModel insertedUser = userRepository.insert(user);
            return insertedUser;

        } else {
            throw new Exception("the username already exists");
        }

    }

    public List<UserModel> getUsers() {
        return userRepository.findAll();
    }
    public Optional<UserModel> getAUser(String id) throws Exception{
        Optional<UserModel> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new Exception("the user with id "+id +" is not found");
        }
        return userRepository.findById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel foundUser = userRepository.findByUsername(username);
        String userN = foundUser.getUsername();
        String password = foundUser.getPassword();
        return new User(userN,password,new ArrayList<>());
    }
}
