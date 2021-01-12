package com.swappingpositive.login;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

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

    public String toDataUri() {
        String contentType;
        String base64Str;
        StringBuilder sb = new StringBuilder();

        try {
            contentType = Files.probeContentType((Path) icon);
            base64Str = Base64.getEncoder()
                    .encodeToString(Files.readAllBytes((Path) icon));

            sb.append("data:")
                    .append(contentType)
                    .append(";base64,")
                    .append(base64Str);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
