package ru.otus.homework06.adapter.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Loadable;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.service.impl.IoC;

@RequiredArgsConstructor
public class LoadableAdapter implements Loadable {
    private final UObject uObject;

    @Override
    public Object[] getClasses() {
        return IoC.resolve("LoadableAdapter.classesName.get", new Object[]{uObject});
    }

    @Override
    public void setClasses(Object[] value) {
        IoC.resolve("LoadableAdapter.classesName.set", new Object[]{
                uObject, value
        });
    }
}
