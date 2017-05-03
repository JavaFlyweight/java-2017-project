package com.example.java.domain.model;

import com.example.java.commons.enums.PermissionType;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class Permission {

	private String userEmail;

	private PermissionType permissionType;
}
