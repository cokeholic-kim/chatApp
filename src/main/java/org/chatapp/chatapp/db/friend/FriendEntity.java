package org.chatapp.chatapp.db.friend;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.chatapp.chatapp.db.BaseEntity;
import org.chatapp.chatapp.db.friend.Enum.FriendStatus;
import org.chatapp.chatapp.db.user.UserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "friend")
public class FriendEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "friendId",nullable = false)
    private UserEntity friend;

    @Column(columnDefinition = "varchar(255)",length = 50,nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatus status;

    private LocalDateTime createdAt;
}
