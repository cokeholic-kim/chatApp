package org.chatapp.chatapp.db.offlinechathistory;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfflineChatHistoryRepository extends JpaRepository<OfflineChatHistoryEntity,Long> {
    boolean existsByReceiverId(Long receiverId);
    List<OfflineChatHistoryEntity> findByReceiverId(Long receiverId);
    void deleteAllByReceiverId(Long receiverId);
}
