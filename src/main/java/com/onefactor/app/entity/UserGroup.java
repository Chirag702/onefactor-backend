package com.onefactor.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "user_groups")  // Plural table name to follow conventions
public class UserGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "groups_id", nullable = false)
    private Groups groups;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Additional fields like role in the group can be added here
}
