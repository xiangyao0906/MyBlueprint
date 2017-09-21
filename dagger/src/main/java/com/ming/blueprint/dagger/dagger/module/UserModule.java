package com.ming.blueprint.dagger.dagger.module;

import com.ming.blueprint.dagger.dagger.bean.User;

import dagger.Module;
import dagger.Provides;

/**
 * Created by ming on 2017/9/13.
 */

@Module
public class UserModule {

    @Provides
    User provideUser() {
        return new User("宙斯", "男");
    }
}
