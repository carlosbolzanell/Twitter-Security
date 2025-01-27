package sc.senai.twetter2.security;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import sc.senai.twetter2.entity.Role;
import sc.senai.twetter2.entity.User;
import sc.senai.twetter2.repository.RoleRepository;
import sc.senai.twetter2.repository.UserRepository;

import java.util.Optional;
import java.util.Set;

@Configuration
@AllArgsConstructor
public class AdminUserConfig implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Role roleAdmin = roleRepository.findByName(Role.Values.ADMIN.name());
        Optional<User> userAdmin = userRepository.findByUsername("admin");

        userAdmin.ifPresentOrElse(
                user -> System.out.println("already exist!"),
                () -> {
                    User user = new User(null, "admin","admin@twitter", passwordEncoder.encode("admin")
                    ,Set.of(roleAdmin));
                    userRepository.save(user);
                }

        );
    }
}
