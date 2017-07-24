package ua.kiev.prog;

import java.util.List;

public interface UserService {
    List<CustomUser> getAllUsers();
    CustomUser getUserByLogin(String login);
    CustomUser getUserById(Long id);
    boolean existsByLogin(String login);
    boolean loginExistsForOtherId(String login, Long id);
    void addUser(CustomUser customUser);
    void updateUser(CustomUser customUser);
    void deleteUser(Long id);
}
