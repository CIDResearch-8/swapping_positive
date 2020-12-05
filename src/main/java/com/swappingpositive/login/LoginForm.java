package com.swappingpositive.login;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

//フォーム内容と紐付けるためのクラス
public class LoginForm {

    @Getter @Setter @NonNull
    private String userId;

    @Getter @Setter @NonNull
    private String password;
}
