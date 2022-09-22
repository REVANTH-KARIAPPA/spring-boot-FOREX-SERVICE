package com.forexservice.service;

import com.forexservice.model.Role;

public interface RoleService {
    Role findByName(String name);
}
