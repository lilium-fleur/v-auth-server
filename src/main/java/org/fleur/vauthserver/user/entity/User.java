package org.fleur.vauthserver.user.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {"email", "provider"}
        ))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProviderType provider;

    @Column(nullable = false)
    private String providerUserId;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant lastModifiedAt;
}
