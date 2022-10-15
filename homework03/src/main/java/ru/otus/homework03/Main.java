package ru.otus.homework03;

//import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.BasicConfigurator;
import ru.otus.homework03.repository.ExceptionHandler;
import ru.otus.homework03.repository.QueueOfCommand;
import ru.otus.homework03.repository.impl.ExceptionHandlerImpl;
import ru.otus.homework03.repository.impl.QueueOfCommandImpl;
import ru.otus.homework03.service.Initializer;
import ru.otus.homework03.service.Processor;
import ru.otus.homework03.service.impl.InitializeExceptionHandlerWriteToLog;
import ru.otus.homework03.service.impl.InitializeQueueOfCommands;
import ru.otus.homework03.service.impl.ProcessCommand;

public class Main {
    public static void main(String[] args) {
        BasicConfigurator.configure();

        QueueOfCommand queue = QueueOfCommandImpl.getInstance();

        Initializer<QueueOfCommand> queueOfCommandInitializer = new InitializeQueueOfCommands();
        queueOfCommandInitializer.initialize(queue);

        ExceptionHandler handler = ExceptionHandlerImpl.getInstance();

        Initializer<ExceptionHandler> exceptionHandlerInitializer = new InitializeExceptionHandlerWriteToLog();
        exceptionHandlerInitializer.initialize(handler);

        Processor processor = new ProcessCommand(queue, handler);
        processor.process();
    }
}
