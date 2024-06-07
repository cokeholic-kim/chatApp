package org.chatapp.chatapp.db.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.chatapp.chatapp.db.BaseEntity;
import org.chatapp.chatapp.db.user.Enum.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "user")
public class UserEntity extends BaseEntity {

    @Column(length = 300, nullable = false)
    private String email;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(length = 100)
    private String nickName;

    @Column(length = 300)
    private String profileImage;

    @Column(columnDefinition = "varchar(255)",length = 50,nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;
}
