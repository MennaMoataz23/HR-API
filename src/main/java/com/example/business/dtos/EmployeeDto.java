package com.example.business.dtos;

import com.example.business.adapters.LocalDateAdapter;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Double salary;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate hireDate;
    private Integer jobId;
    private Integer managerId;
    private Integer departmentId;
}
