package com.jjunpro.project.service;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.dto.AlarmDTO;
import com.jjunpro.project.dto.CreateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountDTO;
import com.jjunpro.project.dto.UpdateAccountPwdDTO;
import com.jjunpro.project.enums.AlarmType;
import com.jjunpro.project.projection.AccountPublic;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService, UserDetailsService {

    private final PasswordEncoder   passwordEncoder;
    private final AccountRepository accountRepository;
    private final AlarmRepository   alarmRepository;

    @Override
    public Account createAccount(CreateAccountDTO dto) {
        dto.encodePassword(passwordEncoder);

        Account result = accountRepository.save(dto.toEntity());

        /* 유저가 등록되면 안내문 알람 추가 */
        if (result.getId() != null) {
            AlarmDTO alearmDTO = AlarmDTO
                    .builder()
                    .userId(result.getId())
                    .dataId(result.getId())
                    .dataContent("회원가입을 축하합니다~!")
                    .writeId("푹찍")
                    .dataType(AlarmType.NOTICE)
                    .build();

            alarmRepository.save(alearmDTO.toEntity());
        }

        return result;
    }

    @Override
    public Account updateAccount(UpdateAccountDTO dto) {
        Optional<Account> byId = accountRepository.findById(dto.getId());
        if (byId.isPresent()) {
            dto.setUsername(byId
                    .get()
                    .getUsername());
            dto.setPassword(byId
                    .get()
                    .getPassword());
            dto.setRole(byId
                    .get()
                    .getRole());
        }

        return accountRepository.save(dto.toEntity());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);

        /**
         * Username 값이 DATA DB 에 존재하지 않는 경우
         * UsernameNotFoundException 에러 메소드를 사용합니다.
         * */
        if (account.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        /**
         * Username 값이 DATA DB 에 존재하는 경우
         * Account 타입을 -> UserDetails 타입으로 변경하여 반환해야합니다.
         * 이때 타입을 변환하도록 도와주는 User.class 를 사용합니다.
         *
         * @see User
         * */
        return new AccountContext(account.get());
    }

    @Override
    public Optional<Account> findById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public Long updatePassword(UpdateAccountPwdDTO dto) {
        dto.encodePassword(passwordEncoder);

        return accountRepository.updatePassword(dto);
    }

    @Override
    public AccountPublic findOnePublicAccount(String username) {
        return accountRepository.findOnePublicAccount(username);
    }
}
