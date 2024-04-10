package com.example.business.dtos;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
//@XmlAccessorType(XmlAccessType.FIELD)
//@XmlRootElement
public class DepartmentDto {
    private Integer id;
    private String name;
    private String location;
    private Integer employeeId;
}
