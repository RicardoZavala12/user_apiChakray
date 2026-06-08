package com.chakrayFZRD.users_api.controller;

import com.chakrayFZRD.users_api.model.User;
import com.chakrayFZRD.users_api.service.UserService;

// import methods
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
// import para logearnos  
import com.chakrayFZRD.users_api.model.LoginRequest;

import java.util.List;

@RestController
@RequestMapping("/ricky-api/users") // ruta para mi controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
     }

    // get all users
    /*@GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }*/

    // sorted by id
    /*@GetMapping
    public List<User> getUsersSortedById(@RequestParam(required = false) String sortedBy) {
        return userService.getUsersSortedById(sortedBy);
    }*/
    
    // delete user by id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        boolean eliminado = userService.deleteUser(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 y status OK si es eliminado correcatmente
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe nuestro user
        }
    }

    // add new user
    /*@PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        User nuevoUser = userService.addUser(user);
        return ResponseEntity.status(201).body(nuevoUser); // 201 y la data del nuevo user creado
    }*/

    // update user by id
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User cambio) {
        User userActualizado = userService.updateUser(id, cambio);
        if (userActualizado != null) {
            return ResponseEntity.status(201).body(userActualizado); // 201 y la data del user actualizado
        } else {
            return ResponseEntity.notFound().build(); // 404 si no existe nuestro user
        }
    }

    // get users filtered 
    @GetMapping
    public List<User> getUsers(
            @RequestParam(required = false) String sortedBy,
            @RequestParam(required = false) String filter) {

        if (filter != null && !filter.isEmpty()) {
            return userService.getUsersFiltered(filter);
        }
        return userService.getUsersSortedById(sortedBy);
    }


    // login user
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest pass) {
        boolean loginExitoso = userService.login(pass.getTaxId(), pass.getPassword());
        if (loginExitoso) {
            return ResponseEntity.ok("Logeado con exito :)"); // me debe de arrojar un sstatus 200
        } else {
            return ResponseEntity.status(401).body("Credenciales invalidas ):"); // mandara 401 y mensaje cuando sea incorrecta la credencial
        }
    }


    // add new user peroooo;
    // ahora para manejar los errores de las validaciones de las 2 de taxID y del numero celular
    @PostMapping
    public ResponseEntity<?> addUser(@RequestBody User user){
        try {
            User nuevoUser = userService.addUser(user);
            return ResponseEntity.status(201).body(nuevoUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }



    


}
