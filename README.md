📌 CSRF 공격 XSS 공격을 시뮬레이션 하기 위한 공격자 용 서버입니다.
-
* 아래와 같이 설정 파일인 application.yml 내용을 수정하고 실행 합니다.
    1. application.yml 파일에 메일 전용 용도의 계정 입력
    ```yml
      mail:
        host: smtp.gmail.com
        protocol: smtp
        port: 587 
        toAddress: '' # 공격 대상 이메일을 올립니다. username 와 동일해도 됩니다.
        username: '' # 이메일 전송을 위한 계정 address (1)
        password: '' # (1)번 계정의 비밀번호
    ```
* 공격 대상 쇼핑몰은 <https://github.com/donghyeon0725/hacker_client> 입니다.

> CSRF 설정을 사용하지 않은 서버를 공격할 경우

1. 아래 메일을 통해 공격용 메일을 보냅니다.
```text
http://localhost:8081/csrf
``` 
2. 쇼핑몰 서버를 none_csrf 커밋 상태로 돌린 뒤 서버를 켭니다.
3. 쇼핑몰 서버에 아래 주소를 통해서 로그인을 합니다.
```text
http://localhost:8080/login
```
4. 공격용 메일의 버튼을 누릅니다.
5. 인증이 필요한 아래 자원에 대한 접근이 가능한 것을 확인 합니다.
```text
http://localhost:8080/user/update (POST Method)
```

> 위 공격의 결과
* 시스템은 위 url 에 대해서 ROLE_USER 권한을 요구하고 있는데도 불구하고 접근이 가능합니다. (SecurityConfig.java)
```java
...
.antMatchers("/user/update").hasRole("USER")
```


> CSRF 설정을 사용한 서버를 공격할 경우

1. 공격자용 서버를 켭니다.
2. 쇼핑몰 서버를 csrf 커밋 상태로 돌린 뒤 서버를 켭니다.
3. 아래 링크로 들어가서, 쇼핑몰에 로그인을 합니다. (클라이언트가 쇼핑몰을 이용하는 상황)
```text
http://localhost:8080/login
```
4. 아래 링크로 들어가서, 해커가 공격용 스크립트를 심어둔 페이지에 접근합니다. (쇼핑몰을 정상 이용하는 상황)
    * 클라이언트의 csrf token 이 공격자용 서버에 전송 되고 잠시 후 클라이언트에게 메일이 전송됩니다.
```text
http://localhost:8080/test
```
5. 클라이언트는 이메일을 확인 후에 메일을 엽니다.
    * 이 메일을 열면 사용자 인증을 요구하는 기능에 csrf 보안 설정이 되어 있음에도 불구하고 접근이 가능해집니다.
