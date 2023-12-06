package fr.epsi;

import fr.epsi.Managers.IOManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ResourceBundle;
import java.util.Scanner;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class IOManagerTest {

    @Mock
    private Scanner mockScanner;

    @Mock
    private ResourceBundle mockResourceBundle;

    private IOManager ioManager;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockResourceBundle.getString(anyString())).thenReturn("Test Message");
        ioManager = new IOManager(mockScanner, mockResourceBundle);
    }

    @Test
    public void testGetInput() {
        when(mockScanner.nextLine()).thenReturn("test input");
        String input = ioManager.GetInput();
        assertEquals("test input", input);
    }

    @Test
    public void testShowMessage() {
        ioManager.ShowMessage("test");
        verify(mockResourceBundle, times(1)).getString("test");
    }

    @Test
    public void testAskToContinue() {
        when(mockScanner.nextLine()).thenReturn("Y").thenReturn("N");
        assertTrue(ioManager.AskToContinue());
        assertFalse(ioManager.AskToContinue());
    }

    @Test
    public void testGetGreetingMessage() {
        ioManager.GetGreetingMessage();
        verify(mockResourceBundle, times(1)).getString(anyString());
    }
}
