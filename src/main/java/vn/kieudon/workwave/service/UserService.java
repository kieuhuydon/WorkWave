package vn.kieudon.workwave.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.kieudon.workwave.domain.User;
import vn.kieudon.workwave.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // save user
    public User handleSaveUser(User user) {
       return  this.userRepository.save(user);

    }

    // find all users
    public List<User> fetchAllUser(){
        return this.userRepository.findAll();
    }

    // find user by id 
    public User fetchUserById(long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        if(userOptional.isPresent()){
            return userOptional.get();
        }
        return null;

        
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }

    // delete user
    public User handleUpdateUser(long id, User reqUser){
        User currentUser = this.fetchUserById(id);
        if(currentUser !=null){
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setName(reqUser.getName());
            currentUser.setPassword(reqUser.getPassword());

            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username){
        return this.userRepository.findUserByEmail(username);
    }
}
