package com.nobblecrafts.challenge.devsecopssr.util;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.dataaccess.useractivity.entity.UserActivityEntity;
import com.nobblecrafts.challenge.devsecopssr.config.test.context.ContextEntitySupplier;

public class EntitySupplier extends ContextEntitySupplier {
    public static AccountEntity anAccount(String username, String password) {
        return AccountEntity.builder()
                .username(username)
                .password(password)
                .build();
    }

    public static UserActivityEntity anUserActivity(AccountEntity account) {
        return UserActivityEntity.builder()
                .account(account)
                .build();
    }
}
