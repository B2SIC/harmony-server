package harmony.dev.harmonyserver.form;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@RequiredArgsConstructor
public class LoginForm {
    @NotNull
    private String userId;
    @NotNull
    private String password;

    public LoginForm(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }
}
