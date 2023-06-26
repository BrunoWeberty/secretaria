package com.management.secretaria.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.br.CPF;

import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentDto {
    private UUID studentId;
    @Size(min = 4, max = 50)
    @NotBlank
    private String userName;
    @Min(0)
    private Short age;
    @Email
    @NotBlank
    private String email;
    @CPF
    private String cpf;
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
    @NotBlank
    private String fullName;
}
