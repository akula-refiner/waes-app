package com.waes.assignment.waesapp.model.audit;

import lombok.Data;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.time.LocalDateTime;

@Data
@ToString
@Embeddable
public class Audit {

    @Column
    private LocalDateTime createdOn;
    @Column
    private String createdBy;
    @Column
    private LocalDateTime updatedOn;
    @Column
    private String updatedBy;
}
