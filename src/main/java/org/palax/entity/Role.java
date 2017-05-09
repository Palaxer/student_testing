package org.palax.entity;

/**
 * The {@code role} enum represent {@link User} role
 *
 * @author Taras Palashynskyy
 */
public enum Role {
    STUDENT,
    TUTOR,
    ADMIN;

    private Long id;

    Role() {}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id + '\'' +
                ", name=" + this.name() +
                '}';
    }
}
