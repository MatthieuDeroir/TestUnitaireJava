package fr.espi;

import fr.epsi.Managers.IOManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class IOManagerTest {
    private Scanner mockScanner;

    @BeforeEach
    public void setUp() {

        IOManager ioManager = new IOManager(mockScanner, null);
    }
    @Test
    public void getUserInput_Valid() {
        when(mockScanner.nextLine()).thenReturn("valid_input");
        String userInput = ioManager.GetInput(mockScanner);
        assertEquals("valid_input", userInput);
    }

    @Test
    public void getUserInput_Invalid() {
        // TODO
    }

    @Test
    public void showMessages() {
        // TODO
    }
}
