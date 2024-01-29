package com.sparta.springauth.repository;

import com.sparta.springauth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> { // extends JpaRepository하고 User 테이블이랑 연결
                                                                    // (<>에 Users 이렇게 넣으면 안되고 해당하는 entity class를 넣어줘야 한다)
    // 쿼리 메소드 만들기
    Optional<User> findByUsername(String username); // 저 딴에서 Username 넣어주려면 파라미터에 데이터 넣어줘야 하니 username
    Optional<User> findByEmail(String email); // 참고) String email 변수명은 여기서 임의로 정한 거
}

