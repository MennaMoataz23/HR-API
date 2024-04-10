package com.example.business.dtos;

import com.example.business.adapters.LocalDateAdapter;
import com.example.business.entities.EmployeeStatus;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class AttendanceDto {
    private Integer id;
    private Integer employeeId;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @JsonbDateFormat("yyyy-MM-dd")
    private LocalDate date;
    private String status;
}
