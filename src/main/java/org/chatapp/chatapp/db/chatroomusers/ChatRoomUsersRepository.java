package org.chatapp.chatapp.db.chatroomusers;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomUsersRepository extends JpaRepository<ChatRoomUsersEntity,Long> {
     List<ChatRoomUsersEntity> findAllByUserId(Long sender);
}
