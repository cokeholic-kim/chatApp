package org.chatapp.chatapp.db.chathistory;

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
@Table(name = "chat_history")
public class ChatHistoryEntity extends BaseEntity {
    private Long senderId;
    private Long roomId;
    private String content;
    private LocalDateTime contentAt;
}
