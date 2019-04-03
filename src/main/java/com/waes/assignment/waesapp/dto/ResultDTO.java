package com.waes.assignment.waesapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO implements Serializable {

    private static final long serialVersionUID = -738579116087234050L;

    private String code;
    private String detail;
}
