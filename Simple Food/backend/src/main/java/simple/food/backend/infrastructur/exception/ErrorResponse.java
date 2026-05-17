package simple.food.backend.infrastructur.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Instant timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<String> details;

    public static ErrorResponse of(HttpStatusLike statusLike, String message, String path, List<String> details) {
        return ErrorResponse.builder()
                .timestamp(Instant.now())
                .status(statusLike.getStatus())
                .error(statusLike.getReason())
                .message(message)
                .path(path)
                .details(details)
                .build();
    }

    public interface HttpStatusLike {
        int getStatus();
        String getReason();
    }
}

