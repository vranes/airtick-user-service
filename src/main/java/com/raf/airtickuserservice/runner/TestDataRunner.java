package com.raf.airtickuserservice.runner;


import com.raf.airtickuserservice.domain.Rank;
import com.raf.airtickuserservice.domain.Role;
import com.raf.airtickuserservice.domain.User;
import com.raf.airtickuserservice.domain.UserRank;
import com.raf.airtickuserservice.repository.RoleRepository;
import com.raf.airtickuserservice.repository.UserRankRepository;
import com.raf.airtickuserservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"default"})
@Component
public class TestDataRunner implements CommandLineRunner {

    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserRankRepository userRankRepository;

    public TestDataRunner(RoleRepository roleRepository, UserRepository userRepository, UserRankRepository userRankRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.userRankRepository = userRankRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        //Insert roles
        Role roleUser = new Role("ROLE_USER", "User role");
        Role roleAdmin = new Role("ROLE_ADMIN", "Admin role");
        roleRepository.save(roleUser);
        roleRepository.save(roleAdmin);
        //Insert admins
        User admin = new User();
        admin.setEmail("admin@gmail.com");
        //admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setRole(roleAdmin);
        admin.setVerified(true);
        userRepository.save(admin);
        User admin2 = new User();
        admin2.setEmail("admin2@gmail.com");
        //admin2.setUsername("admin2");
        admin2.setPassword("admin2");
        admin2.setRole(roleAdmin);
        admin.setVerified(true);
        userRepository.save(admin2);

        User user = new User();
        user.setEmail("vranesnikolina@gmail.com");
        user.setPassword("12345678");
        user.setRole(roleUser);
        user.setVerified(true);
        user.setFirstName("nikolina");
        user.setLastName("vranes");
        user.setPassport(Long.parseLong("11111111"));
        user.setMiles(0);
        userRepository.save(user);
        //User statuses
        userRankRepository.save(new UserRank(Rank.BRONZE, 0, 1000, 0));
        userRankRepository.save(new UserRank(Rank.SILVER, 1000, 10000, 10));
        userRankRepository.save(new UserRank(Rank.GOLD, 10000, Integer.MAX_VALUE, 20));
    }
}
