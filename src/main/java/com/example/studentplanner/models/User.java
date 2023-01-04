package com.example.studentplanner.models;

import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Column(nullable = true)
    private String department;

    @Column(nullable = false)
    private Boolean commentCertified;

    @Column(length = 64)
    private String verificationCode;

    @Column(nullable = false)
    private boolean enabled;

    @Column(nullable = true)
    private Boolean admin;


    public User(String name, String password, String email,
                String department, Boolean commentCertified,Boolean admin ){
        this.name = name;
        this.password = password;
        this.email = email;
        this.department = department;
        this.commentCertified = commentCertified;
        this.admin = admin;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String username) {
        this.name = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Boolean getCommentCertified() {
        return commentCertified;
    }

    public void setCommentCertified(Boolean commentCertified) {
        this.commentCertified = commentCertified;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
