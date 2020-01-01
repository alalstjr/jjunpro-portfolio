package com.backend.project.controller;

import com.backend.project.domain.Account;
import com.backend.project.domain.Alarm;
import com.backend.project.respone.WebProcessRespone;
import com.backend.project.service.AlarmService;
import com.backend.project.util.AccountUtill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/alarm")
public class AlarmController
{
    @Autowired
    private AlarmService alarmService;

    @Autowired
    private AccountUtill accountUtill;

    @Autowired
    private WebProcessRespone webProcessRespone;

    /**
     GET Alarm List DATA
     */
    @GetMapping("")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Alarm>> getAlarmList (
            Authentication authentication
    )
    {
        // Account Info
        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        List<Alarm> result =  alarmService.findByAlarmListWhereUserId(accountData.get());

        return new ResponseEntity<List<Alarm>>(result, HttpStatus.OK);
    }

    /**
     * DELETE Alarm DATA
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> deleteAlarmId(
            @PathVariable Long id,
            Authentication authentication
    )
    {
        String errorType = null;
        String errorText = null;

        Optional<Account> accountData = accountUtill.accountInfo(authentication);

        if(accountData.isPresent())
        {

            if(!accountUtill.userDataCheck(id, accountData, "alarm"))
            {
                errorType = "AuthenticationError";
                errorText = "잘못된 계정 접근입니다.";
                return webProcessRespone.webErrorRespone(errorType, errorText);
            }

            alarmService.deleteData(id);
        }
        else
        {
            errorType = "AuthenticationError";
            errorText = "올바른 접근이 아닙니다.";
            return webProcessRespone.webErrorRespone(errorType, errorText);
        }

        return new ResponseEntity<String>("처리가 완료되었습니다.", HttpStatus.OK);
    }
}
