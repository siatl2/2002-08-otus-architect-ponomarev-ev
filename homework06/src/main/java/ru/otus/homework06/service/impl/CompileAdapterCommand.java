package ru.otus.homework06.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Adapter;
import ru.otus.homework06.adapter.Compilable;
import ru.otus.homework06.service.Command;

import javax.tools.*;
import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CompileAdapterCommand implements Command {
    private static final String PATH_TARGET = Adapter.class
            .getProtectionDomain()
            .getCodeSource().getLocation().getPath();
    private static final String ADAPTER_PACKAGE = "ru.otus.homework06.adapter.impl.";
    private final Compilable compilable;
    private final Object[] javaFilesPath;

    @Override
    public void execute() {
        String javaFilePath = String.valueOf(javaFilesPath[0]);
        System.out.println("JAVA_FILE_PATH=" + javaFilePath);

        try {
            List<File> files = new ArrayList<>();
            files.add(Path.of(javaFilePath).toFile());

            System.out.println("PATH_OF=" + Path.of(javaFilePath));

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

            StandardJavaFileManager fileManager = compiler.getStandardFileManager(
                    null
                    , null
                    , null
            );

            Iterable<? extends JavaFileObject> javaFiles = fileManager.getJavaFileObjectsFromFiles(files);

            DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();

            JavaCompiler.CompilationTask task = compiler.getTask(
                    null,
                    fileManager,
                    diagnostics,
                    null,
                    null,
                    javaFiles
            );
            //выполняем задачу
            System.out.println("RESULT_COMPILE=" + task.call());
            //выводим ошибки, возникшие в процессе компиляции
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                System.out.format("Error on line %d in %s%n",
                        diagnostic.getLineNumber(),
                        diagnostic.getSource());
            }


            String compileFileName = ADAPTER_PACKAGE +
                    javaFilePath.substring(javaFilePath.lastIndexOf("/") + 1)
                            .replace(".java", "");

            System.out.println("COMPILE_NAME=" + compileFileName);

            compilable.setCompileFiles(
                    new Object[]{
                            compileFileName
                    }
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
