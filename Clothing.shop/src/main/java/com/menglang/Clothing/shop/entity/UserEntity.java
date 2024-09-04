package com.menglang.Clothing.shop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.menglang.Clothing.shop.entity.base.BaseAuditEntity;
import com.menglang.Clothing.shop.entity.embedded.PaymentInformation;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_tbl")
public class UserEntity extends BaseAuditEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    private String phone;

    private String password;

    private int attempt;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "id")
    )
    private Set<RoleEntity> roles=new HashSet<>();

    @OneToMany(mappedBy ="user", cascade = CascadeType.ALL)
    private Set<AddressEntity> address=new HashSet<>();


    @ElementCollection
    @CollectionTable(name = "payment_information",
                    joinColumns = @JoinColumn(name = "user_id")
    )
    private List<PaymentInformation> paymentInformation=new ArrayList<>();

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<RatingEntity> rating=new HashSet<>();


    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<ReviewEntity> review=new HashSet<>();




}
