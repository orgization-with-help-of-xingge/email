package com.hdu.emailuser.api.user;

import com.hdu.email.dto.Users;

public interface UserApi {
    Users queryById(String id);
}
