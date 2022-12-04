package ru.otus.homework06.service.impl;

import com.sun.codemodel.JCodeModel;
import com.sun.codemodel.JDefinedClass;
import com.sun.codemodel.JMethod;
import com.sun.codemodel.JMod;
import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Generateable;
import ru.otus.homework06.service.Command;

import java.lang.reflect.Method;
import java.nio.file.Path;
import java.nio.file.Paths;

@RequiredArgsConstructor
public class GenerateAdapterCommand implements Command {
    private static final String ADAPTER_PACKAGE = "ru.otus.homework06.adapter.impl";
    private static final String PATH_TO_PROJECT = "src/main/java/";
    private final Generateable generateable;
    private final Class clazzAdapterInterface;

    private final Class clazzAdapteeInterface;

    @Override
    public void execute() {
        JCodeModel codeModel = new JCodeModel();

        try {
            String packageName = ADAPTER_PACKAGE;
            String adapterInterfaceSimpleName = clazzAdapterInterface.getSimpleName();
            String adapteeInterfaceSimpleName = clazzAdapteeInterface.getSimpleName();

            String adapterClassName = packageName + "." + adapterInterfaceSimpleName + "Adapter";

            JDefinedClass adapterClass = codeModel._class(adapterClassName);
            adapterClass._implements(clazzAdapterInterface);

            String adapteeFieldName = getVariableNameFromClassName(adapteeInterfaceSimpleName);

            adapterClass.field(
                    JMod.PRIVATE + JMod.FINAL,
                    clazzAdapteeInterface,
                    adapteeFieldName);

            JMethod constructor = adapterClass.constructor(JMod.PUBLIC);
            constructor.param(clazzAdapteeInterface, adapteeFieldName);

            constructor.body().directStatement(
                    "this." + adapteeFieldName + " = " +
                            adapteeFieldName + ";");

            for (Method method : clazzAdapterInterface.getMethods()) {
                JMethod jMethod = adapterClass.method(
                        JMod.PUBLIC,
                        method.getReturnType(),
                        method.getName());
                String body = "";
                String varInIoC;
                if (!method.getReturnType().equals(Void.TYPE)) {
                    varInIoC = getVariableNameFromClassName(method.getName().substring(3));
                    body = "return " +
                            "ru.otus.homework06.service.impl." +
                            "IoC.resolve(\"" +
                            adapterInterfaceSimpleName +
                            "." +
                            varInIoC +
                            ".get\", new Object[]{" +
                            adapteeFieldName +
                            "});";
                } else {
                    if (method.getParameters().length > 0) {
                        varInIoC = getVariableNameFromClassName(method.getName().substring(3));
                        body = "ru.otus.homework06.service.impl." +
                                "IoC.resolve(\"" +
                                adapterInterfaceSimpleName +
                                "." +
                                varInIoC +
                                ".set\", new Object[]{\n" +
                                adapteeFieldName + ", " +
                                "arg0\n" +
                                "});";

                        jMethod.param(method.getParameters()[0].getType(), method.getParameters()[0].getName());
                    }
                }
                jMethod.body().directStatement(body);
            }
            codeModel.build(Paths.get(PATH_TO_PROJECT).toAbsolutePath().toFile());

            Path path = Paths.get(
                    PATH_TO_PROJECT +
                            adapterClassName.replace(".", "/") +
                            ".java"
            );

            System.out.println("PATH=" + path.toAbsolutePath());
            generateable.setSourceFiles(
                    new Object[]{
                            path.toAbsolutePath()
                    }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String getSimpleName(String fullName) {
        return fullName.substring(
                fullName.lastIndexOf(".") + 1);
    }

    private String getVariableNameFromClassName(String className) {
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

}
