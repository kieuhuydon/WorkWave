package vn.kieudon.workwave.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.kieudon.workwave.domain.User;
import vn.kieudon.workwave.domain.dto.Meta;
import vn.kieudon.workwave.domain.dto.ResultPagination;
import vn.kieudon.workwave.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // save user
    public User handleSaveUser(User user) {
        return this.userRepository.save(user);

    }

    // find all users
    public ResultPagination fetchAllUser(Pageable pageable) {
        // findAll(pageable) return type là page
        Page<User> pg = this.userRepository.findAll(pageable);
        ResultPagination rs = new ResultPagination();
        Meta mt = new Meta();

        mt.setPage(pg.getNumber());
        mt.setPageSize(pg.getSize());

        mt.setPages(pg.getTotalPages());
        mt.setTotal(pg.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pg.getContent());

        return rs;
    }

    // find user by id
    public User fetchUserById(Long id) {
        Optional<User> userOptional = this.userRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        }
        return null;

    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);
    }

    public User handleUpdateUser(Long id, User reqUser) {
        User currentUser = this.fetchUserById(id);
        if (currentUser != null) {
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setName(reqUser.getName());
            currentUser.setPassword(reqUser.getPassword());

            currentUser = this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findUserByEmail(username);
    }
}
