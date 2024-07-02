package sc.senai.twetter2.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sc.senai.twetter2.model.Role;
import sc.senai.twetter2.model.User;
import sc.senai.twetter2.repository.RoleRepository;
import sc.senai.twetter2.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void createUser(User user) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());
        Role basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        if (optionalUser.isPresent()) {
            throw new Exception("User already exist on database!");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Set.of(basicRole));
        userRepository.save(user);
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }
}
