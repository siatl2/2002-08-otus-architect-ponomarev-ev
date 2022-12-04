package ru.otus.homework06.adapter.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Compilable;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.service.impl.IoC;

@RequiredArgsConstructor
public class CompilableAdapter implements Compilable {
    private final UObject uObject;

    @Override
    public Object[] getCompileFiles() {
        return IoC.resolve("CompilableAdapter.compileFiles.get", new Object[]{uObject});
    }

    @Override
    public void setCompileFiles(Object[] value) {
        IoC.resolve("CompilableAdapter.compileFiles.set", new Object[]{
                uObject, value
        });
    }
}
