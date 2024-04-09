package com.example.presentation.exceptionhandlers;

import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;

@XmlRootElement
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private String errorMessage;
    private int errorCode;
}
