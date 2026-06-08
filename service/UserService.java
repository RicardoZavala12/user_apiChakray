package com.chakrayFZRD.users_api.service;

import com.chakrayFZRD.users_api.model.User;
import com.chakrayFZRD.users_api.model.Addres;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.ArrayList;

@Service
public class UserService {

    private final List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        // usuarios de ejemplo
        List<Addres> addresList1 = new ArrayList<>();
        addresList1.add(new Addres(1, "Home", "Gilboa Pte116", "MX"));
        addresList1.add(new Addres(2, "High Schol", "Lic Jose Benitez", "US"));
        
        // user 1
        User user1 = new User(
            java.util.UUID.randomUUID().toString(),
            "rickyesc1202011@gmail.com",
            "Rickyesc",
            "+52 5511724771",
            "7c4a8d09ca3762af61e59520943dc26494f8941b",
            "FOZR010212B81",
            "2024-06-01 12:00",
            addresList1
        );

        // user 2
        User user2 = new User(
            java.util.UUID.randomUUID().toString(),
            "evelin@gmail.com",
            "eveZavala",
            "+52 0987654321",
            "7c4a8d09ca3762af61e59520943dc26494f8941b",
            "AARR990101AB1",
            "2024-06-01 12:00",
            addresList1
        );

        // user 3
        User user3 = new User(
            java.util.UUID.randomUUID().toString(),
            "aileen@gmail.com",
            "aileenOchoa",
            "+52 8876990965",
            "7c4a8d09ca3762af61e59520943dc26494f8941b",
            "BBSS880202CD2",
            "2024-06-01 12:00",
            addresList1
        );

        users.add(user1);
        users.add(user2);
        users.add(user3);
    }

}