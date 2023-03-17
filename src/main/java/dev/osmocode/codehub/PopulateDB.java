package dev.osmocode.codehub;

import dev.osmocode.codehub.entity.Authority;
import dev.osmocode.codehub.entity.QuestionTag;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.AuthorityService;
import dev.osmocode.codehub.service.QuestionTagService;
import dev.osmocode.codehub.service.UserService;
import dev.osmocode.codehub.utils.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class PopulateDB implements CommandLineRunner {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final QuestionTagService questionTagService;

    public PopulateDB(UserService userService, AuthorityService authorityService, PasswordEncoder passwordEncoder, QuestionTagService questionTagService) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
        this.questionTagService = questionTagService;
    }

    @Override
    public void run(String... args) throws Exception {
        populateUser(120);
        populateTags();
    }

    private void populateUser(int number) throws InterruptedException {
        Authority roleUser = authorityService.findAuthorityByName(Role.USER.name());
        List<Thread> users = IntStream.range(0, number).mapToObj(i -> new Thread(() -> {
            User user_i = new User(
                    "user_" + i,
                    passwordEncoder.encode("password"),
                    "user_" + i + "@uge-overflow.com",
                    roleUser
            );
            userService.saveUser(user_i);
        })).toList();
        users.forEach(Thread::start);
        for (Thread user : users) {
            user.join();
        }
        System.out.println(number + " more users created");
    }

    private void populateTags() {

        List<String> tags = List.of("java", "jee", "oop", "sql", "jpql", "spring", "spring-security", "spring-data",
                "spring-mvc", "algorithm", "system", "functional-programming", "mobile-programming", "devops", "bdd",
                "c", "python", "scala", "ocaml", "rust", "android", "angular", "reactjs", "vueJS", "javascript", "html",
                "css", "c", "c++", "haskell", "c#", "kotlin", "jetpack-compose", "jquery", "mysql", "sqlite", "nodejs");
        tags.forEach(s -> questionTagService.addQuestionTag(new QuestionTag(s)));
        System.out.println("Some question tags created");
    }
}
