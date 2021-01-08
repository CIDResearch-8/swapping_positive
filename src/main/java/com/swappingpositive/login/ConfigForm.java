package com.swappingpositive.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

class UsernameForm {
    @Getter @Setter
    private String username;
}

class EmailForm {
    @Getter @Setter
    private String email;
}

class IconForm {
    @Getter @Setter
    private MultipartFile icon;
}
