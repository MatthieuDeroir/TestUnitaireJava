package fr.epsi.ManagersTest;

import fr.epsi.App.Enums.eSentences;
import fr.epsi.App.Managers.IOManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Stream;

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

    private static Stream<Object[]> greetingMessageTest_VALID() {
        return Stream.of(
                new Object[]{9, eSentences.MORNING.getKey(), "Good morning"},
                new Object[]{15, eSentences.AFTERNOON.getKey(), "Good afternoon"},
                new Object[]{20, eSentences.EVENING.getKey(), "Good evening"}
        );
    }

    @ParameterizedTest
    @MethodSource("greetingMessageTest_VALID")
    public void testGetGreetingMessage(int hour, String greetingKey, String expectedMessage) {
        when(mockResourceBundle.getString(greetingKey)).thenReturn(expectedMessage);
        String greeting = ioManager.GetGreetingMessage(hour);
        assertEquals(expectedMessage, greeting);
    }

    private static Stream<Object[]> goodByeMessageTest_VALID() {
        return Stream.of(
                new Object[]{9, "Goodbye"},
                new Object[]{15, "Goodbye"},
                new Object[]{20, "Goodbye"}
        );
    }


}
