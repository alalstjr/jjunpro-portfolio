package com.jjunpro.project.service;

import com.jjunpro.project.context.AccountContext;
import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.File;
import com.jjunpro.project.domain.Store;
import com.jjunpro.project.dto.*;
import com.jjunpro.project.enums.AlarmType;
import com.jjunpro.project.projection.AccountPublic;
import com.jjunpro.project.repository.AccountRepository;
import com.jjunpro.project.repository.AlarmRepository;
import com.jjunpro.project.util.StoreUtil;
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

    private final StoreUtil         storeUtil;
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
        Optional<Account> accountData = accountRepository.findById(dto.getId());

        if (accountData.isPresent()) {
            accountData.get().setNickname(dto.getNickname());
            accountData.get().setUrlList(dto.getUrlList());

            /* 업로드 되는 파일이 있는경우 수정 */
            if (dto.getFile() != null) {
                accountData.get().setPhoto(dto.getFileData());
            }

            /* 이메일 정보가 없는경우 NULL 저장 */
            if (dto.getEmail() != null && dto.getEmail().trim().length() == 0) {
                dto.setEmail(null);
            } else {
                accountData.get().setEmail(dto.getEmail());
            }

            return accountRepository.save(accountData.get());
        }

        return null;
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

    @Override
    public Long updateAccountRoleSeller(SellerDTO sellerDTO) {
        /* Store 정보를 등록 */
        Store store = storeUtil.storeDataHandler(
                sellerDTO.getStoId(),
                sellerDTO.getStoName(),
                sellerDTO.getStoAddress(),
                sellerDTO.getStoUrl(),
                null
        );


        /* 유저의 권한을 Seller, Store 변경 */
        return accountRepository.updateAccountRoleSeller(
                sellerDTO.getAccount(),
                store
        );
    }
}
