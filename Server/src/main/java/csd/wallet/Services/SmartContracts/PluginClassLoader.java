package csd.wallet.Services.SmartContracts;

import java.io.FilePermission;
import java.net.SocketPermission;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.PropertyPermission;

public class PluginClassLoader extends URLClassLoader {

    public PluginClassLoader(URL classUrl) {
        super(new URL[] {classUrl});
    }

    @Override
    protected PermissionCollection getPermissions(CodeSource codesource) {
        Permissions perms = new Permissions();
        SocketPermission socketPermission = new SocketPermission("*:1024-", "connect, resolve");
        PropertyPermission propertyPermission = new PropertyPermission("*", "read");
        FilePermission filePermission = new FilePermission("<<ALL FILES>>", "read");

        perms.add(socketPermission);
        perms.add(propertyPermission);
        perms.add(filePermission);

        return perms;
    }

}
