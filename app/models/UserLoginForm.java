package models;

import play.data.validation.Constraints.Required;

public class UserLoginForm {

    @Required(message="emailは必須項目です")
    private String email;

    @Required(message="passwordを入力してください")
    private String password;

    public UserLoginForm() {
        super();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

}
