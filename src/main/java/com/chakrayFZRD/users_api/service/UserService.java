package com.chakrayFZRD.users_api.service;

import com.chakrayFZRD.users_api.model.User;
// para la hora de madagascar y cifrado
import com.chakrayFZRD.users_api.utils.AesUtil;
import com.chakrayFZRD.users_api.model.Address;
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
        List<Address> addresList1 = new ArrayList<>();
        addresList1.add(new Address(1, "Home", "Gilboa Pte116", "MX"));
        addresList1.add(new Address(2, "High Schol", "Lic Jose Benitez", "US"));
        
        // user 1
        User user1 = new User(
            java.util.UUID.randomUUID().toString(),
            "Rickyesc",
            "rickyesc1202011@gmail.com",
            "+52 5511724771",
            "7c4a8d09ca3762af61e59520943dc26494f8941b",
            "FOZR010212B81",
            "2024-06-01 12:00",
            addresList1
        );

        // user 2
        User user2 = new User(
            java.util.UUID.randomUUID().toString(),
            "eveZavala",
            "evelin@gmail.com",
            "+52 0987654321",
            "7c4a8d09ca3762af61e59520943dc26494f8941b",
            "AARR990101AB1",
            "2024-06-01 12:00",
            addresList1
        );

        // user 3
        User user3 = new User(
            java.util.UUID.randomUUID().toString(),
            " aileenOchoa",
            "aileen@gmail.com",
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

    // get all users
    public List<User> getAllUsers() {
        return users;
    }


    // delete user by id
    public boolean deleteUser(String id) {
        return users.removeIf(user -> user.getId().equals(id));
    }


    // add new user
    public User addUser(User user) {
        // valiudaciones de RFC, numero celular y RFC o taxId unico
        if (!rfcValido(user.getTaxId())) {
            throw new IllegalArgumentException("Tu RFC no tiene un formato correcto");
        }
        // validar numero de celular        
        if (!numCelValido(user.getPhone())) {
            throw new IllegalArgumentException("Debe tener al menos 10 digitos");
        }
        // validar taxId unico
        if (taxIdUni(user.getTaxId())) {
            throw new IllegalArgumentException("Ya existe un RFC registrado, debe ser unico");
        }

        // generate id automatic
        user.setId(java.util.UUID.randomUUID().toString());
        // cifrar password
        user.setPassword(AesUtil.encrypt(user.getPassword())); 
        // date de madagascar
        user.setCreatedAt(generarDatetoMadagascar());
        users.add(user);
        return user;
    }

    // metodo para date de madagascar
    public String generarDatetoMadagascar() {
        java.time.ZonedDateTime now = java.time.ZonedDateTime.now(java.time.ZoneId.of("Indian/Antananarivo"));
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return now.format(formato);
    }


    // metodo para actualizar usuario
    public User updateUser(String id, User update) {
        for (User user : users) {
            if (user.getId().equals(id)) {
                if (update.getName() != null) user.setName(update.getName());
                if (update.getEmail() != null) user.setEmail(update.getEmail());
                if (update.getPhone() != null) user.setPhone(update.getPhone());
                if (update.getTaxId() != null) user.setTaxId(update.getTaxId());
                if (update.getPassword() != null) user.setPassword(AesUtil.encrypt(update.getPassword()));
                if (update.getAddres() != null) user.setAddres(update.getAddres());
                return user;
            }
        }
        // si no se encuentra el usuario
        return null; 

    }

    // sorted by id
    public List<User> getUsersSortedById(String sortedBy) {
        List<User> sortedUsers = new ArrayList<>(users);

        if (sortedBy == null || sortedBy.isEmpty()) {
            // devuelve la lista vacía si no hay usuarios
            return sortedUsers; 
        }

        switch (sortedBy) {
            case "email": sortedUsers.sort((a,b) -> a.getEmail().compareTo(b.getEmail())); break;
            case "id": sortedUsers.sort((a,b) -> a.getId().compareTo(b.getId())); break;
            case "name": sortedUsers.sort((a,b) -> a.getName().compareTo(b.getName())); break;
            case "phone": sortedUsers.sort((a,b) -> a.getPhone().compareTo(b.getPhone())); break;
            case "tax_id": sortedUsers.sort((a,b) -> a.getTaxId().compareTo(b.getTaxId())); break;
            case "created_at": sortedUsers.sort((a,b) -> a.getCreatedAt().compareTo(b.getCreatedAt())); break;
        }
        return sortedUsers;
    }


    public List<User> getUsersFiltered(String filter) {
        List<User> res = new ArrayList<>();

        // el filter viene como  ejemplo email co @gmail.com
        String[] partes = filter.trim().split("\\s+");
            if (partes.length < 3) {
                return res; 
            }

        String campo = partes[0];
        String operador = partes[1];
        String valor = partes[2];

        for (User user : users) {
            String valorCampo = obtenerCampo(user, campo);
            if (valorCampo == null) continue;

            boolean coincide = false;
            switch (operador) {
                case "co": coincide = valorCampo.contains(valor); break;
                case "eq": coincide = valorCampo.equals(valor); break;
                case "sw": coincide = valorCampo.startsWith(valor); break;
                case "ew": coincide = valorCampo.endsWith(valor); break;
            }
            if (coincide) {
                res.add(user);
            }
        }
        return res;
    }

    //  obtener valor de un campo 
    private String obtenerCampo(User user, String campo) {
        switch (campo) {
            case "email":      return user.getEmail();
            case "id":         return user.getId();
            case "name":       return user.getName();
            case "phone":      return user.getPhone();
            case "tax_id":     return user.getTaxId();
            case "created_at": return user.getCreatedAt();
            default:           return null;
        }
    }


    // method to login
    public boolean login(String taxId, String password) {
        for (User user : users) {
            if (user.getTaxId().equals(taxId))  {
                // cifra y compara pasword 
                String passwordCifrada = AesUtil.encrypt(password);
                return user.getPassword().equals(passwordCifrada);
            }
        }
        return false; 
    }


    // metodos para realizar validaciones 
    // tipo del rfc, digitos del numero de celular, y tax id unico

    // rfc o taxId valido
    public boolean rfcValido(String taxId) {
        if (taxId == null) return false;
        return taxId.matches("^[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}$");
    }


    // numero de 10 digitos 
    public boolean numCelValido(String phone) {
        if (phone == null) return false;
        // delet spaces and other characters 
        String numCel = phone.replaceAll("[^0-9]", "");
        // validar 10 digitos por lo menps
        return numCel.length() >= 10;
    }

    
    // taxId unico
    public boolean taxIdUni(String taxId) {
        for (User user : users) {
            if (user.getTaxId() != null && user.getTaxId().equals(taxId)) {
                return true;
            }
        }
        return false;
    }

    
}