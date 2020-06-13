package csd.wallet.Services.SmartContracts;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.security.AllPermission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.util.PropertyPermission;

public class PluginSecurityPolicy extends Policy {

    @Override
    public PermissionCollection getPermissions(ProtectionDomain domain) {
        Permissions permissions = new Permissions();

        if (domain.getClassLoader() instanceof PluginLoader) {  // if it's a plugin
            permissions.add(new FilePermission("*","read" ));
            permissions.add(new SocketPermission("*:1024-", "connect, resolve"));
            permissions.add(new PropertyPermission("*", "read"));
        } else {
            permissions.add(new AllPermission());  // give all permissions to other classes
        }
        return permissions;
    }
}
