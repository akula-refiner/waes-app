package com.waes.assignment.waesapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Table(name = "ATTACHMENT")
@Entity
@NoArgsConstructor
@Data
public class Attachment {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "length")
    private Integer length;

    @Column(name = "one_part")
    @Lob
    private byte[] onePart;

    public Attachment(Integer length, byte[] onePart) {
        this.length = length;
        this.onePart = onePart;
    }
}
