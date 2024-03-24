package uz.registration.registration.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.registration.registration.Entities.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    boolean existsAppUserByEmail(String email);

    AppUser findByEmail(String username);

    AppUser findAppUserById(Long id);

    @Modifying
    @Query("update AppUser set enable = true where id=:id")
    void setEnable(@Param("id") Long id);
}
