package pl.lodz.p.java.flyweight.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Permission {

    private long userId;

    private PermissionType type;
}
