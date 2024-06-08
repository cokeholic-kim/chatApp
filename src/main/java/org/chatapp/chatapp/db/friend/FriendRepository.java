package org.chatapp.chatapp.db.friend;

import org.chatapp.chatapp.db.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<FriendEntity,Long> {
    Boolean existsByFriend(UserEntity friend);
}
