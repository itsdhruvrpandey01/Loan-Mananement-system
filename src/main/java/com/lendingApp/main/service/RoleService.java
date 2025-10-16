package com.lendingApp.main.service;

import java.util.List;

import com.lendingApp.main.dto.RoleDto;
import com.lendingApp.main.dto.RoleResponseDto;
import com.lendingApp.main.entity.Role;

public interface RoleService {
    RoleResponseDto addRole(RoleDto role);
    List<RoleResponseDto> getAllRoles();
    Role getRoleByName(String name);
}
