package fr.epsi.ManagersTest;

import fr.epsi.App.Enums.Weekdays;
import fr.epsi.App.Managers.InputOutputManager;
import fr.epsi.App.Managers.MessageManager;
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

    private InputOutputManager inputOutputManager;

    private final MessageManager messageManager = new MessageManager(mockResourceBundle);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(mockResourceBundle.getString(anyString())).thenReturn("Test Message");
        inputOutputManager = new InputOutputManager(messageManager);
    }

    @Test
    public void testGetInput() {
        when(mockScanner.nextLine()).thenReturn("test input");
        String input = inputOutputManager.GetInput();
        assertEquals("test input", input);
    }

    @Test
    public void testShowMessage() {
        inputOutputManager.ShowMessage("test");
        verify(mockResourceBundle, times(1)).getString("test");
    }

    @Test
    public void testAskToContinue() {
        when(mockScanner.nextLine()).thenReturn("Y").thenReturn("N");
        assertTrue(inputOutputManager.AskToContinue());
        assertFalse(inputOutputManager.AskToContinue());
    }

    private static Stream<Object[]> greetingMessageTest_VALID() {
        return Stream.of(
                new Object[]{9, Weekdays.MORNING.getKey(), "Good morning"},
                new Object[]{15, Weekdays.AFTERNOON.getKey(), "Good afternoon"},
                new Object[]{20, Weekdays.EVENING.getKey(), "Good evening"}
        );
    }

    @ParameterizedTest
    @MethodSource("greetingMessageTest_VALID")
    public void testGetGreetingMessage(int hour, String greetingKey, String expectedMessage) {
        when(mockResourceBundle.getString(greetingKey)).thenReturn(expectedMessage);
        String greeting = inputOutputManager.GetGreetingMessage(hour);
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
