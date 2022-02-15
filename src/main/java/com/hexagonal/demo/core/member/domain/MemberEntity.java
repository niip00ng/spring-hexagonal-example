package com.hexagonal.demo.core.member.domain;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "member")
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MemberEntity {
    enum Grade {
        MASTER, ADMIN, NONE
    }

    String name;
    String nickName;
    Grade grade;
    int age;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    public void changeAge(int age) {

    }
}
