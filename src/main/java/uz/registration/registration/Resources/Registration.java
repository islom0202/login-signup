package uz.registration.registration.Resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.registration.registration.Models.RegistrationRequest;
import uz.registration.registration.Services.AppUserService;
import uz.registration.registration.TokenProvider.TokenProviderService;

@RestController
@RequestMapping("/api")
public class Registration {
    private final AppUserService appUserService;
    private final TokenProviderService tokenProviderService;

    public Registration(AppUserService appUserService, TokenProviderService tokenProviderService) {
        this.appUserService = appUserService;
        this.tokenProviderService = tokenProviderService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity register(@RequestBody RegistrationRequest request) {
        if (!appUserService.emailValid(request.getEmail())) {
            return new ResponseEntity("not valid", HttpStatus.BAD_REQUEST);
        }
        if (!appUserService.existAppUser(request.getEmail())) {
            return ResponseEntity.ok(appUserService.register(request));
        } else
            return new ResponseEntity("already exist", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/sign-up/confirmation")
    public ResponseEntity<String> confirm(@RequestParam("token") String token) {
        return ResponseEntity.ok(tokenProviderService.confirmToken(token));
    }
}
