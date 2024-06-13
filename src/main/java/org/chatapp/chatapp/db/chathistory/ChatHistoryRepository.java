package org.chatapp.chatapp.db.chathistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatHistoryRepository extends JpaRepository<ChatHistoryEntity,Long> {
}
