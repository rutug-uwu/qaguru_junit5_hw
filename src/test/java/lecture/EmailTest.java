package lecture;

import org.junit.jupiter.api.*;


@DisplayName("Тесты на email рассылку")
public class EmailTest {

    @Test
    @DisplayName("Email должен быть отправлен новому юзеру")
    @Tags({
            @Tag("Smoke"),
            @Tag("Web")
    })
    void emailShouldBeSendForNewUser() {
        System.out.println("Hello");
    }

    @Test
    @DisplayName("Email должен быть отправлен забаненному юзеру")
    @Tag("Smoke")
    void emailShouldBeSendForBannedUser() {
        System.out.println("Hello");
    }

    @Test
    @DisplayName("Email должен быть отправлен в случае изменения PaymentMethod")
    void emailShouldBeSendAfterChangePaymentMethod() {
        System.out.println("Hello");
    }


    @Test
    @DisplayName("Первый простой позитивный тест")
    void simpleTest() {
        System.out.println("Hello");
    }
}