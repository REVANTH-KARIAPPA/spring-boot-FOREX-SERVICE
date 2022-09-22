package com.forexservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserDto {
    @Pattern(regexp="^[A-Za-z]*$",message = "only alphabets")
    @NotBlank(message="username can not be Blank")
    @NotNull(message="username can not be null")
    @Size(max=10,message="username max 10 letters")
    private String username;
    @Size(min=4,message="password should be more then 4 letters")
    private String password;
    @Email(message="Please provide a valid email address")
    private String email;
    private String phone;
    private String name;



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public User getUserFromDto(){
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setPhone(phone);


        
        return user;
    }
    
}