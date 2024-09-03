package com.onefactor.app.entity;

import jakarta.persistence.*;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.onefactor.app.entity.BillShare;
import com.onefactor.app.entity.Groups;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "bills")  // Plural table name to follow conventions
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private Double totalAmount;

    @Column(nullable = false)
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "groups_id", nullable = false)
    private Groups groups;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillShare> billShares = new ArrayList<>();
}
