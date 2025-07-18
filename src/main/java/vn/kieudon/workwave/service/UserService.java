package vn.kieudon.workwave.service;

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

    public User handleSaveUser(User user) {
       return  this.userRepository.save(user);

    }

    public Optional<User> findUserById(long id){
        return this.userRepository.findById(id);
    }

    public void deleteUserById(long id){
        this.userRepository.deleteById(id);
    }
}
