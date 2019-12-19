package com.backend.project.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidityCheckUtil
{
    public Boolean emailCheck(String target)
    {
        String emailRegex ="(?:[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&amp;'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
        return Pattern.matches(emailRegex, target);
    }

    public Boolean enNumCheck(String target)
    {
        String stringRegex = "^[a-zA-Z0-9]*$";
        return Pattern.matches(stringRegex, target);
    }

    public Boolean enNumkrCheck(String target)
    {
        String stringRegex = "^[a-zA-Z0-9가-힣]*$";
        return Pattern.matches(stringRegex, target);
    }

    /*
     *  부적절한 nickname을 체크하는 메소드입니다.
     */
    public Boolean usernickCheck(String nickname)
    {
        String[] checkChar = {
                "ADMIN", "Admin", "admin", "관리자", "최고관리자", "어드민", "운영자", "pugjjig", "푹찍",
                "씨발", "씨1발", "섹스", "섹1스", "보지", "보1지", "야한", "야1한", "19금", "병신", "병1신", "니애미",
                "자지", "자1지", "망가", "망1가", "FUCK", "fuck", "Fuck", "SEX"
        };

        for(String keyword : checkChar)
        {
            if(nickname.contains(keyword))
            {
                return true;
            }
        }

        return false;
    }
}
