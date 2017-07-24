package ua.kiev.prog;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UserRole {
    @Id
    private Integer id;

    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRoleEnum;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    private List<CustomUser> customUserList = new ArrayList<>();

    public UserRole() {
    }

    public UserRole(UserRoleEnum userRoleEnum) {
        this.id=userRoleEnum.ordinal();
        this.userRoleEnum = userRoleEnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public UserRoleEnum getUserRoleEnum() {
        return userRoleEnum;
    }

    public void setUserRoleEnum(UserRoleEnum userRoleEnum) {
        this.id = userRoleEnum.ordinal();
        this.userRoleEnum = userRoleEnum;
    }

    public List<CustomUser> getCustomUserList() {
        return customUserList;
    }

    public void addCustomUser(CustomUser customUser) {
        this.customUserList.add(customUser);
    }

    @Override
    public String toString() {
        return userRoleEnum.name();
    }

    public String getString() {
        return "ROLE_" + this.userRoleEnum.name();
    }
}
