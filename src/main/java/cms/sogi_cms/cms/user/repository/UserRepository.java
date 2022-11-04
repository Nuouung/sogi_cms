package cms.sogi_cms.cms.user.repository;

import cms.sogi_cms.cms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
