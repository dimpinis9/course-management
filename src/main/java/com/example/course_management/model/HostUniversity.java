package com.example.course_management.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "host_universities")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HostUniversity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hostID")
    private Integer hostId;

    @Column(name = "userID", nullable = false)
    private Integer userId;

    @Column(name = "university_name", length = 255)
    private String universityName;

    @Column(length = 100)
    private String country;

    @Column(length = 100)
    private String city;

    @Column(name = "contact_email", length = 100)
    private String contactEmail;

    @Column(name = "contact_phone", length = 50)
    private String contactPhone;

    // Relationship with User
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userID", insertable = false, updatable = false)
    private User user;
}

