package com.waes.assignment.waesapp.model;


import com.waes.assignment.waesapp.model.audit.Audit;
import com.waes.assignment.waesapp.model.audit.AuditListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "DIFF", indexes = {@Index(name = "idx_diff_corr_id", columnList = "correlation_id", unique = true)})
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
//@EntityListeners(AuditListener.class)
public class DiffEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "correlation_id")
    private String correlationId;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment leftPart;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment rightPart;

//    @Embedded
//    private Audit audit;

}
