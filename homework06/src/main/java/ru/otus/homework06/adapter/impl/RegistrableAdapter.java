package ru.otus.homework06.adapter.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.homework06.adapter.Registrable;
import ru.otus.homework06.repository.UObject;
import ru.otus.homework06.service.impl.IoC;

@RequiredArgsConstructor
public class RegistrableAdapter implements Registrable {
    private final UObject uObject;

    @Override
    public Object[] getRegisterNames() {
        return IoC.resolve("RegistrableAdapter.registerName.get", new Object[]{uObject});
    }

    @Override
    public void setRegisterNames(Object[] value) {
        IoC.resolve("RegistrableAdapter.registerName.set", new Object[]{
                uObject, value
        });
    }
}
