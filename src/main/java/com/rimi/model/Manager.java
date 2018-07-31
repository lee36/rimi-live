package com.rimi.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="manager_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manager implements Serializable {
    @Id
    private Integer id;
    private String username;
    private String password;
}
