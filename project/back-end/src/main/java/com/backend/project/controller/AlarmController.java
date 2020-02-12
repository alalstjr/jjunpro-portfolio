package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;
import com.backend.project.dto.AlarmDTO;
import com.backend.project.service.AlarmService;
import com.backend.project.util.AccountUtill;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final AccountUtill accountUtill;

    /**
     * GET Alarm List DATA
     */
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Alarm>> getAlarmList(
            Authentication authentication
    ) {
        List<Alarm> result = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

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
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Alarm>> deleteAlarmId(
            @Valid AlarmDTO id,
            Authentication authentication,
            BindingResult bindingResult
    ) throws BindException {
        // 유효성 검사 후 최종 반환합니다.
        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        List<Alarm> result = null;

        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

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
