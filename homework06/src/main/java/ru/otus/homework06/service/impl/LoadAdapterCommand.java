package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Loadable;
import ru.otus.homework06.service.Command;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

@RequiredArgsConstructor
public class LoadAdapterCommand implements Command {
    private static final String PATH_CLASSES = "/home/siatl/dev/2002-08-otus-architect-ponomarev-ev/homework06/target/classes/";
    private final Loadable loadable;
    private final Object[] classNames;

    @Override
    public void execute() {
        // ru.otus.homework06.adapter.impl.MovableAdapter
        String className = String.valueOf(classNames[0]);
        System.out.println("CLASS_NAME=" + className);
        Class returnClass = null;

        try {
            File file = new File(PATH_CLASSES);
            URL url = file.toURI().toURL();
            URL[] urls = new URL[]{url};
            ClassLoader cl = new URLClassLoader(urls);

            returnClass = cl.loadClass(className);
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
