package oliver.tech.smartstock.client;

import oliver.tech.smartstock.client.dto.AuthRequest;
import oliver.tech.smartstock.client.dto.AuthResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "AuthClient", url = "${api.client-url}")
public interface AuthClient {

    @PostMapping(path = "/api/token")
    ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request);
}
