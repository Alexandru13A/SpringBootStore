package com.alexandru.SpringBootStore.dto;

import lombok.Data;

import javax.validation.constraints.*;

import java.util.List;

@Data
public class UserDTO {

    private long userId;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @NotEmpty
    @Size(min = 2, message = "users name should have at least 2 characters")
    private String firstName;
    @NotEmpty
    @Size(min = 2, message = "users name should have at least 2 characters")
    private String lastName;

    @NotEmpty
    private String confirmPassword;

    private String fullName = firstName + lastName;

    private AddressDTO address;

    private List<OrderDTO> orders;






    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }
}
