package org.nkcoder.user.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    private String uuid;
    private String passWord;
    private String email;

    public User(String uuid, String passWord, String email) {
        this.uuid = uuid;
        this.passWord = passWord;
        this.email = email;
    }

    public boolean isPassWordCorrect(String password) {
        return this.passWord.equals(password);
    }
}
