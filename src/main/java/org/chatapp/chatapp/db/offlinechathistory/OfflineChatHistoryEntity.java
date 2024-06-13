package org.chatapp.chatapp.db.offlinechathistory;

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
@Table(name = "offline_chat_history")
public class OfflineChatHistoryEntity extends BaseEntity {
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime createdAt;
}
