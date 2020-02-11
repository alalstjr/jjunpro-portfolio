package com.backend.project.service;

import com.backend.project.domain.Account;
import com.backend.project.dto.AccountPwdUpdateDTO;
import com.backend.project.dto.AccountSaveDTO;
import com.backend.project.dto.AccountUpdateDTO;
import com.backend.project.dto.AlarmDTO;
import com.backend.project.projection.AccountPublic;
import com.backend.project.repository.AccountRepository;
import com.backend.project.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository     account;
    private final AlarmRepository       alarm;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Account save(AccountSaveDTO dto) {

        String rawPassword     = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);

        Account result = account.save(dto.toEntity());

        // 유저가 등록되면 안내문 알람 추가
        // 알람 테이블에 추가
        if (result.getId() != null) {
            AlarmDTO alearmDTO = new AlarmDTO();
            alearmDTO.setUserId(result.getId());
            alearmDTO.setDataId(result.getId());
            alearmDTO.setDataContent("회원가입을 축하합니다~!");
            alearmDTO.setWriteId("푹찍");
            alearmDTO.setDataType("Notice");
            alarm.save(alearmDTO.toEntity());
        }

        return result;
    }

    @Override
    public Long update(AccountUpdateDTO dto) {
        return account.update(dto.toEntity());
    }

    @Override
    public Long pwdUpdate(AccountPwdUpdateDTO dto) {

        String rawPassword     = dto.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        dto.setPassword(encodedPassword);

        return account.pwdUpdate(dto);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return account.findById(id);
    }

    @Override
    public Optional<Account> findByUserId(String userId) {
        return account.findByUserId(userId);
    }

    @Override
    public Optional<Account> findByNickname(String nickname) {
        return account.findByNickname(nickname);
    }

    @Override
    public Optional<Account> findByEmail(String email) {
        return account.findByEmail(email);
    }

    @Override
    public AccountPublic findOnePublicAccount(String userId) {
        return account.findOnePublicAccount(userId);
    }

    @Override
    public List<AccountPublic> findByPublicAccountList() {
        return account.findByPublicAccountList();
    }
}
