package com.example.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterDtoValues {
    @NotBlank
    @Size(min = 2, max = 100, message = "Username is bigger then 100 or smaller then 2")
    private String username;
   @Email(message = "it is incorrect email")
   private String email;
    @NotBlank
    @Size(min = 7, max = 100, message = "password is bigger than 100 or less 7")
    private  String password;
    @Size(min = 7, max = 100, message = "password is bigger than 100 or less 7")
    private  String ConfirmPassword;

    private Data updatedAt;
}
