package com.smartcontact.entities;

import java.util.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ContactS {
    @Id
    private String id;
    private String name;
    private String email;
    private String address;
    private String picture;
    private String phoneNumber;
    @Column(length = 5000)
    private String description;
    private boolean favorite = false;

    private String cloudinaryImagePublicId;

    private String websiteLink;
    private String linkdinLink;
    // private List<String> socialLink = new arrayList<>();

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "contacts", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SocialLinks> links = new ArrayList<>();

}
