package com.ming.blueprint.dagger.dagger.component;

import com.ming.blueprint.dagger.MainActivity;
import com.ming.blueprint.dagger.dagger.module.UserModule;

import dagger.Component;

/**
 * Created by ming on 2017/9/13.
 */

@Component(modules = UserModule.class)
public interface UserComponent {

    void inject(MainActivity activity);

}
