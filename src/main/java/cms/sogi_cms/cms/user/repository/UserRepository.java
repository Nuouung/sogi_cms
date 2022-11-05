package cms.sogi_cms.cms.user.repository;

import cms.sogi_cms.cms.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByUsername(String username);
}
