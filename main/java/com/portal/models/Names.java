package com.portal.models;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Names implements Serializable {

    private static final long serialVersionUID = 1L;
    private String fName;
    private String lName;
    @Column(unique =true)
    private String username;
}
