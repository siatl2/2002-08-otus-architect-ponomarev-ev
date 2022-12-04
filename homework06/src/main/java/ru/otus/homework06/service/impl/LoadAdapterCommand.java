package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Loadable;
import ru.otus.homework06.service.Command;
import ru.otus.homework06.service.Movable;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

@RequiredArgsConstructor
public class LoadAdapterCommand implements Command {
    private static final String PATH_CLASSES = Movable.class
            .getProtectionDomain()
            .getCodeSource().getLocation().getPath();
    private final Loadable loadable;
    private final Object[] classNames;

    @Override
    public void execute() {
        String className = String.valueOf(classNames[0]);
        System.out.println("CLASS_NAME=" + className);
        Class returnClass = null;

        try {
            File file = new File(PATH_CLASSES);
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            System.out.println("urls complete");

            ClassLoader cl = new URLClassLoader(urls);
            System.out.println("CLASS_LOADER=" + cl.getName());

            returnClass = cl.loadClass(className);
            System.out.println("RETURN_CLASS=" + returnClass.getSimpleName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        loadable.setClasses(
                new Object[]{
                        returnClass
                }
        );

    }
}
