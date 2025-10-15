package vn.ledeem.jobhunter.service;

import java.util.List;

import org.springframework.stereotype.Service;
import vn.ledeem.jobhunter.domain.User;

import vn.ledeem.jobhunter.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) {
        return this.userRepository.save(user);
    }

    public void handleDeleteUser(Long id) {
        this.userRepository.deleteById(id);
    }

    // public User fetchUserById(long id) {
    // Optional<User> userOptional = this.userRepository.findById(id);
    // if (userOptional.isPresent()) {
    // return userOptional.get();
    // }
    // return null;
    // }
    public User fetchUserById(Long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    public List<User> fetchAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleUpdateUser(User reqUser) {
        User currentUser = this.fetchUserById(reqUser.getId());
        if (currentUser != null) {
            currentUser.setName(reqUser.getName());
            currentUser.setEmail(reqUser.getEmail());
            currentUser.setPassword(reqUser.getPassword());
            // update user
            return this.userRepository.save(currentUser);
        }
        return currentUser;
    }

    public User handleGetUserByUsername(String username) {
        return this.userRepository.findByEmail(username);
    }
}
