package ua.kiev.prog;

public interface UserRoleService {
    UserRole getUserRole(UserRoleEnum userRoleEnum);
    void addUserRole(UserRole userRole);
}
