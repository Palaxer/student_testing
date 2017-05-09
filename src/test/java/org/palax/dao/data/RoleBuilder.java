package org.palax.dao.data;

import org.palax.entity.Role;

/**
 * {@code RoleBuilder} class for building test data to {@link Role} entity
 */
public class RoleBuilder implements Builder<Role> {

    private Role roles;

    private RoleBuilder() {
    }

    public static RoleBuilder getBuilder() {
        return new RoleBuilder();
    }

    public RoleBuilder constructRole(Role role) {
        switch (role) {
            case ADMIN:
                roles = Role.ADMIN;
                roles.setId(3L);
                break;
            case TUTOR:
                roles = Role.TUTOR;
                roles.setId(2L);
                break;
            case STUDENT:
                roles = Role.STUDENT;
                roles.setId(1L);
        }

        return this;
    }

    @Override
    public Role build() {
        return roles;
    }
}
