package ru.otus.homework06.adapter.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Generateable;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.service.impl.IoC;

@RequiredArgsConstructor
public class GenerateableAdapter implements Generateable {
    private final UObject uObject;

    @Override
    public Object[] getSourceFiles() {
        return IoC.resolve("GenerateableAdapter.sourceFiles.get", new Object[]{uObject});
    }

    @Override
    public void setSourceFiles(Object[] value) {
        IoC.resolve("GenerateableAdapter.sourceFiles.set", new Object[]{
                uObject, value
        });
    }
}
