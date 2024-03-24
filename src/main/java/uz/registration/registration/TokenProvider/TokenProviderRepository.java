package uz.registration.registration.TokenProvider;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.registration.registration.Entities.AppUser;

@Repository
public interface TokenProviderRepository extends JpaRepository<TokenProvider,Long> {
    TokenProvider findByToken(String token);

    @Modifying
    @Query("update TokenProvider set confirmedAt = current_timestamp where id=:id")
    void confirmedAt(@Param("id") Long id);
}
