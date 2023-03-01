package net.nanxu.mall.domain.user.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import net.nanxu.mall.domain.user.repository.converter.IdentityExtra;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_identity", schema = "nx_mall")
public class TIdentity implements Serializable {
    @Serial
    private static final long serialVersionUID = 7285084259987241405L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull
    @Column(name = "identity_type", nullable = false)
    private Integer identityType;

    @Size(max = 100)
    @NotNull
    @Column(name = "identifier", nullable = false, length = 100)
    private String identifier;

    @Size(max = 255)
    @Column(name = "credential")
    private String credential;

    @Size(max = 50)
    @Column(name = "nickname", length = 50)
    private String nickname;

    @Size(max = 255)
    @Column(name = "avatar")
    private String avatar;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @NotNull
    @Column(name = "created_time", nullable = false)
    private Instant createdTime;

    @Column(name = "last_modified_time")
    private Instant lastModifiedTime;

    @Column(name = "last_modified_by")
    private Long lastModifiedBy;

    @Column(name = "extra")
    @JdbcTypeCode(SqlTypes.JSON)
    private IdentityExtra extra;

}