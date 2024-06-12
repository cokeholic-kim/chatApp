package org.chatapp.chatapp.db.chatroom;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.chatapp.chatapp.db.BaseEntity;
import org.chatapp.chatapp.db.chatroomusers.ChatRoomUsersEntity;
import org.springframework.web.socket.WebSocketSession;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "chat_room")
public class ChatRoom extends BaseEntity {
    private String name;
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "chatRoomId")
    private List<ChatRoomUsersEntity> chatRoomUsers;
}


