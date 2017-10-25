package com.laba.booklibrary.service.users.model;

import com.laba.booklibrary.service.books.model.BookOnHoldTO;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Blueprint for a user object. Contain all fields to work with a user.
 */

@Entity
@Table(name = "users")
public class UserTO implements Serializable{

    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private Set<UserRoleTO> userRoleTOs = new HashSet<>();
    private Set<BookOnHoldTO> bookOnHoldTOs = new HashSet<>();


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role_mapping",
            joinColumns =  { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn( name = "role_id") })
    public Set<UserRoleTO> getUserRoleTOs(){
        return userRoleTOs;
    }

    public void setUserRoleTOs(Set<UserRoleTO> userRoleTOs) {
        this.userRoleTOs = userRoleTOs;
    }


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.userTO")
    public Set<BookOnHoldTO> getBookOnHoldTOs(){
        return this.bookOnHoldTOs;
    }

    public void setBookOnHoldTOs(Set<BookOnHoldTO> bookOnHoldTOs) {
        this.bookOnHoldTOs = bookOnHoldTOs;
    }


    @Override
    public String toString() {
        return "UserTO{" +
                "username='" + username + '\'' +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "first_name")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "user_password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "user_name")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
