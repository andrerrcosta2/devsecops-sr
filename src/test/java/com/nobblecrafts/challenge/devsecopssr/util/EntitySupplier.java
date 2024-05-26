package com.nobblecrafts.challenge.devsecopssr.util;

import com.nobblecrafts.challenge.devsecopssr.dataaccess.account.entity.AccountEntity;
import com.nobblecrafts.challenge.devsecopssr.util.context.ContextEntitySupplier;

public class EntitySupplier extends ContextEntitySupplier {
    public static AccountEntity anAccount(String username, String password) {
        return AccountEntity.builder()
                .username(username)
                .password(password)
                .build();
    }
}
