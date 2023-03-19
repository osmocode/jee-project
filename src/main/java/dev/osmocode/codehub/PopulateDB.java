package dev.osmocode.codehub;

import dev.osmocode.codehub.dto.QuestionAskedDto;
import dev.osmocode.codehub.entity.Authority;
import dev.osmocode.codehub.entity.QuestionTag;
import dev.osmocode.codehub.entity.User;
import dev.osmocode.codehub.service.AuthorityService;
import dev.osmocode.codehub.service.QuestionService;
import dev.osmocode.codehub.service.QuestionTagService;
import dev.osmocode.codehub.service.UserService;
import dev.osmocode.codehub.utils.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
public class PopulateDB implements CommandLineRunner {

    private final UserService userService;
    private final AuthorityService authorityService;
    private final PasswordEncoder passwordEncoder;
    private final QuestionTagService questionTagService;
    private final QuestionService questionService;

    private final List<String> tags = List.of("java", "jee", "oop", "sql", "jpql", "spring", "spring-security", "spring-data",
            "spring-mvc", "algorithm", "system", "functional-programming", "mobile-programming", "devops", "bdd",
            "c", "python", "scala", "ocaml", "rust", "android", "angular", "reactjs", "vueJS", "javascript", "html",
            "css", "c", "c++", "haskell", "c#", "kotlin", "jetpack-compose", "jquery", "mysql", "sqlite", "nodejs");

    private final static int NB_USER = 60;

    public PopulateDB(
            UserService userService,
            AuthorityService authorityService,
            PasswordEncoder passwordEncoder,
            QuestionTagService questionTagService,
            QuestionService questionService
    ) {
        this.userService = userService;
        this.authorityService = authorityService;
        this.passwordEncoder = passwordEncoder;
        this.questionTagService = questionTagService;
        this.questionService = questionService;
    }

    @Override
    public void run(String... args) throws Exception {
        populateUser(NB_USER);
        populateTags();
        populateQuestion();
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
        tags.forEach(s -> questionTagService.addQuestionTag(new QuestionTag(s)));
        System.out.println("Some question tags created");
    }

    private void populateQuestion() {
        var body = """
                I am using [m2r2](https://pypi.org/project/m2r2/) to render html document using sphinx. My folder structure is
                                        
                    project
                      |_ docs
                          |_ sphinx root
                      |_ README.md
                      |_ image_to_include_in_readme.png
                   \s
                                        
                README.md looks like this:
                                        
                    # This is heading
                   \s
                    Some text. refer to the image : [Image](image_to_include_in_readme.png)
                   \s
                                        
                But this image is not readable from sphinx root folder.
                                        
                I am able to render readme.md correctly using `mdinclude` directive in html but images are not rendered. I am getting `WARNING: image file not readable:` \s
                I know i can copy the images into build folder but I don't want to do that. How do I fix it?
                """;


        List<QuestionAskedDto> questions = List.of(
                new QuestionAskedDto("Scale animation when user touches a recyclerview item", body),
                new QuestionAskedDto("Problem in finding duplicates in a string using bit manipulation", body),
                new QuestionAskedDto("Error when converting saved_model to .tflite using tflite converter", body),
                new QuestionAskedDto("python smtplib failing to run (SMTP AUTH extension not supported)", body),
                new QuestionAskedDto("Creating a class \"Vault\" that saves a message protected by a code", body),
                new QuestionAskedDto("Why is my react .map showing same result twice when fetching from API?", body),
                new QuestionAskedDto("How to create a service worker with workbox in Manifest V3?", body),
                new QuestionAskedDto("Query with laravel", body),
                new QuestionAskedDto("Check if file system is case-insensitive in Python", body),
                new QuestionAskedDto("R one-to-many mappings", body),
                new QuestionAskedDto("How to mock Interface in JUnit instead of its implementation class in Spring Boot", body),
                new QuestionAskedDto("Django using rest_framework url_pattern not mapping to model instance view", body),
                new QuestionAskedDto("str_replace_all with multiple vector patterns", body),
                new QuestionAskedDto("C++ process snapshot returns [System process] and cant find a target exe in snapshot", body),
                new QuestionAskedDto("What is the procedure of creating a DB view in a JHispter project?", body),
                new QuestionAskedDto("Firebase: Why is my set (with merge : true) operation removes all other fields in my document?", body),
                new QuestionAskedDto("fatal error: 'GL/glu.h' file not found although path included", body),
                new QuestionAskedDto("How to show cypress test result when running `nx affected --target=e2e`", body),
                new QuestionAskedDto("take only some values from a column in python (from VCF file)", body),
                new QuestionAskedDto("JOOQ parsing issue postgres bitwise and (&)", body),
                new QuestionAskedDto("Can I call a process from a function?", body),
                new QuestionAskedDto("What is the right way to download image and persist with Spring webflux", body),
                new QuestionAskedDto("HttpPost response \"BadRequest\" in Blazor Webassembly hosted model", body)
        );

        List<String> usernames = List.of("user", "ypicker", "user_1", "user_2", "user_3");

        Set<Set<Long>> tags = new HashSet<>();
        tags.add(Set.of(1L, 5L, 9L, 8L, 7L));
        tags.add(Set.of(1L, 2L));
        tags.add(Set.of(5L, 8L, 3L, 10L, 6L));
        tags.add(Set.of(1L, 5L, 9L, 8L, 7L));
        tags.add(Set.of(3L, 4L, 18L, 16L, 14L));
        tags.add(Set.of(13L, 4L));
        tags.add(Set.of(2L, 12L, 16L));
        tags.add(Set.of(6L, 7L, 8L, 14L));
        
        questions.forEach(question -> questionService.performAskQuestion(
                        question,
                        usernames.stream().skip((long) (Math.random() * usernames.size())).findFirst().orElseThrow(),
                        tags.stream().skip((long) (Math.random() * tags.size())).findFirst().orElseThrow()
                )
        );
        System.out.println("Some question created");
    }
}
