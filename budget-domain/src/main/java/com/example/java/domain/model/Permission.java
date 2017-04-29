package com.example.java.domain.model;

import com.example.java.commons.enums.PermissionType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Permission {

	private long userId;

	private PermissionType permissionTtype;
}
