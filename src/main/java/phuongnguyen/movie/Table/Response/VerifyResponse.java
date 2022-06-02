package phuongnguyen.movie.Table.Response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VerifyResponse {

    private String username;
    private String email;
    private String token;
    private LocalDateTime expiredDate;
}
