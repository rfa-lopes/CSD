package csd.wallet.Services.SmartContracts;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class PluginLoader extends ClassLoader {

    // The directory where the plugin .class files are located.
    private String pluginDirectory = "";

    public void setPluginDirectory(String pluginDirectory) {
        this.pluginDirectory = pluginDirectory;
    }

    // It takes all .class files in the plugin directory and returns their names without extension.
    public String[] listPlugins() {
        File pluginFolder = new File(pluginDirectory);
        return Arrays.asList(pluginFolder.list()).stream()
                .filter((str) -> str.endsWith(".class"))
                .map((str) -> str.substring(0, str.length()-6))
                .toArray(String[]::new);
    }

    // Given the name of a class, it returns a Class object.
    @Override
    public Class<?> loadClass(String name) {
        // Is the class already loaded?
        Class<?> pluginClass = findLoadedClass(name);

        // Is it a system class?
        if (pluginClass == null) {
            try { pluginClass = findSystemClass(name); }
            catch (Exception ex) {}
        }

        // If none of the above, read from the corresponding .class file.
        if (pluginClass == null) {
            File file = new File(pluginDirectory + "/" + name + ".class");

            try {
                byte[] fileContent = Files.readAllBytes(file.toPath());
                pluginClass = defineClass(name, fileContent, 0, fileContent.length);
                resolveClass(pluginClass);
            } catch (Exception e) {}
        }

        return pluginClass;
    }
}