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
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.Version;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "t_user", schema = "nx_mall")
public class TUser implements Serializable {
    @Serial
    private static final long serialVersionUID = 1084567362626140133L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Size(max = 50)
    @NotNull
    @Column(name = "nickname", nullable = false, length = 50)
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
    private String extra;

    @Version
    @NotNull
    @Column(name = "version", nullable = false)
    private Integer version;

}