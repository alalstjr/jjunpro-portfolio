package com.jjunpro.project.controller;

import com.jjunpro.project.domain.Account;
import com.jjunpro.project.domain.Alarm;
import com.jjunpro.project.dto.AlarmDTO;
import com.jjunpro.project.service.AlarmService;
import com.jjunpro.project.util.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final AccountUtil  accountUtil;

    /**
     * GET Alarm List DATA
     */
    @GetMapping("")
    public ResponseEntity<List<Alarm>> getAlarmList(
            Authentication authentication
    ) {
        List<Alarm> result = null;

        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        if (accountData.isPresent()) {
            result = alarmService.findByAlarmListWhereUserId(accountData.get());
        }

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }

    /**
     * DELETE Alarm DATA
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<List<Alarm>> deleteAlarmId(
            @Valid AlarmDTO id,
            Authentication authentication,
            BindingResult bindingResult
    ) throws BindException {
        /* 유효성 검사 후 최종 반환합니다. */
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        List<Alarm> result = null;

        /* Account Info */
        Optional<Account> accountData = accountUtil.accountInfo(authentication);

        if (accountData.isPresent()) {
            alarmService.deleteData(id.getId());
            result = alarmService.findByAlarmListWhereUserId(accountData.get());
        }

        return new ResponseEntity<>(
                result,
                HttpStatus.OK
        );
    }
}