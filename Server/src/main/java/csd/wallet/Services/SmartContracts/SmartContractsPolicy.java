/*package csd.wallet.Services.SmartContracts;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Policy;
import java.util.*;

public class SmartContractsPolicy extends Policy {

    private static PermissionCollection perms;

    public SmartContractsPolicy() {
        super();
        if (perms == null) {
            perms = new SCPermissionsCollection();
            addPermissions();
        }
    }

    @Override
    public PermissionCollection getPermissions(CodeSource codesource) {
        return perms;
    }

    private void addPermissions() {
        SocketPermission socketPermission = new SocketPermission("*:1024-", "connect, resolve");
        PropertyPermission propertyPermission = new PropertyPermission("*", "read");
        FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read");

        perms.add(socketPermission);
        perms.add(propertyPermission);
        perms.add(filePermission);
    }

}

class SCPermissionsCollection extends PermissionCollection {

    private static final long serialVersionUID = 614300921365729272L;

    ArrayList<Permission> perms = new ArrayList<Permission>();

    public void add(Permission p) {
        perms.add(p);
    }

    public boolean implies(Permission p) {
        for (Permission perm : perms)
            if (perm.implies(p))
                return true;
        return false;
    }

    public Enumeration<Permission> elements() {
        return Collections.enumeration(perms);
    }

    public boolean isReadOnly() {
        return false;
    }

}
*/