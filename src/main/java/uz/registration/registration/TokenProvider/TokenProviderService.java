package uz.registration.registration.TokenProvider;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import uz.registration.registration.Entities.AppUser;
import uz.registration.registration.Repositories.AppUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TokenProviderService {

    private final TokenProviderRepository tokenProviderRepository;
    private final AppUserRepository appUserRepository;

    public TokenProviderService(TokenProviderRepository tokenProviderRepository, AppUserRepository appUserRepository) {
        this.tokenProviderRepository = tokenProviderRepository;
        this.appUserRepository = appUserRepository;
    }


    public String generateToken(AppUser user) {
        String token = UUID.randomUUID().toString();
        TokenProvider tokenProvider = new TokenProvider();
        tokenProvider.setToken(token);
        tokenProvider.setCreatedAt(LocalDateTime.now());
        tokenProvider.setExpiresAt(LocalDateTime.now().plusMinutes(5));
        tokenProvider.setAppUser(user);
        saveToken(tokenProvider);
        return token;
    }

    public void saveToken(TokenProvider token) {
        tokenProviderRepository.save(token);
    }

    @Transactional
    public String confirmToken(String token) {
        TokenProvider tokenProvider = tokenProviderRepository.findByToken(token);
        AppUser user = appUserRepository.findAppUserById(tokenProvider.getAppUser().getId());

        LocalDateTime expiredAt = tokenProvider.getExpiresAt();
        if (tokenProvider.getConfirmedAt() != null) {
            throw new IllegalStateException("already confirmed");
        }
        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token has expired");
        }
        appUserRepository.setEnable(user.getId());
        tokenProviderRepository.confirmedAt(tokenProvider.getId());
        return "confirmed";
    }
}
