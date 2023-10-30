package com.everyschool.userservice.api.service.user;

import com.everyschool.userservice.api.controller.client.response.UserInfo;
import com.everyschool.userservice.api.controller.user.response.UserClientResponse;
import com.everyschool.userservice.api.controller.user.response.UserInfoResponse;
import com.everyschool.userservice.api.service.user.dto.SearchEmailDto;
import com.everyschool.userservice.domain.user.Parent;
import com.everyschool.userservice.domain.user.Student;
import com.everyschool.userservice.domain.user.User;
import com.everyschool.userservice.domain.user.repository.UserQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class UserQueryService {

    private final UserQueryRepository userQueryRepository;

    public UserInfoResponse searchUser(String userKey) {
        Optional<UserInfoResponse> response = userQueryRepository.findByUserKey(userKey);
        if (response.isEmpty()) {
            throw new NoSuchElementException("존재하지 않는 회원입니다.");
        }
        return response.get();
    }

    public String searchEmail(String name, String birth) {
        Optional<SearchEmailDto> findContent = userQueryRepository.findEmailByNameAndBirth(name, birth);
        if (findContent.isEmpty()) {
            throw new NoSuchElementException("일치하는 회원 정보가 존재하지 않습니다.");
        }
        SearchEmailDto content = findContent.get();

        if (content.isDeleted()) {
            throw new IllegalArgumentException("이미 탈퇴한 회원입니다.");
        }

        String email = content.getEmail();

        String[] emailPart = email.split("@");

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < emailPart[0].length(); i++) {
            if (i < 3) {
                builder.append(emailPart[0].charAt(i));
                continue;
            }
            builder.append("*");
        }

        builder.append("@");
        builder.append(emailPart[1]);

        return String.valueOf(builder);
    }

    public UserClientResponse searchUserId(String userKey) {
        Optional<UserClientResponse> findUserId = userQueryRepository.findIdByUserKey(userKey);
        if (findUserId.isEmpty()) {
            throw new NoSuchElementException("일치하는 회원 정보가 존재하지 않습니다.");
        }
        return findUserId.get();
    }

    public UserInfo searchUserInfo(String userKey) {
        Optional<User> findUser = userQueryRepository.findUserInfoByUserKey(userKey);
        if (findUser.isEmpty()) {
            throw new NoSuchElementException();
        }
        User user = findUser.get();

        if (user instanceof Student) {
            Student student = (Student) user;
            return UserInfo.of(student);
        }

        Parent parent = (Parent) user;
        return UserInfo.of(parent);
    }
}
