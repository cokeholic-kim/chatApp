package org.chatapp.chatapp.db.chatroomusers;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.chatapp.chatapp.db.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "chat_room_users")
public class ChatRoomUsersEntity extends BaseEntity {
    private Long userId;
    private Long chatRoomId;
    private LocalDateTime joinedAt;
}
