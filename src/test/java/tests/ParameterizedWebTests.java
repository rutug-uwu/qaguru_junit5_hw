package tests;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.CollectionCondition.texts;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class ParameterizedWebTests {

    @BeforeAll
    static void setUp() {

        Configuration.browserSize = "1920x1080";
        Configuration.pageLoadTimeout = 20000;
    }

    @AfterEach
    void tearDown() {
        closeWebDriver();
    }

    @ValueSource(strings = {
            "Одежда", "Техника", "Обувь"
    })
    @ParameterizedTest(name = "Для поиского запроса {0} должен возвращаться не пустой список карточек")
    @Tag("WEB")
    void searchReturnsNonEmptyResultsTest(String searchText) {
        open("https://mm.ru/");
        $("[data-test-id='input__search']").setValue(searchText).pressEnter();
        $$("[data-test-id='list__products'] div").shouldBe(sizeGreaterThan(0));
    }

    @CsvSource(value = {
            "Холодильник/ Холодильники",
            "Техника/ Бытовая техника",
            "Компьютер/ Компьютеры, мониторы, моноблоки"
    }, delimiter = '/')
    @ParameterizedTest(name = "Для поиского запроса {0} должен возвращаться заголовок {1}")
    @Tag("WEB")
    void searchResultShouldHaveExpectedTitleTest(String searchText, String expectedTitle) {
        open("https://mm.ru/");
        $("[data-test-id='input__search']").setValue(searchText).pressEnter();
        $("[data-test-id='text__title']").shouldHave(text(expectedTitle));
    }

    static Stream<Arguments> searchResultShouldHaveExpectedSubsectionsTest() {
        return Stream.of(
                Arguments.of("Одежда", List.of("Главная", "Все категории", "Одежда")),
                Arguments.of("Мебель", List.of("Главная", "Все категории", "Товары для дома", "Мебель")),
                Arguments.of("Техника", List.of("Главная", "Все категории", "Бытовая техника"))
        );
    }

    @MethodSource
    @ParameterizedTest(name = "Для поиского запроса {0} должны возвращаться следующие подразделы: {1}")
    @Tag("WEB")
    void searchResultShouldHaveExpectedSubsectionsTest(String searchText, List<String> expectedSubsections) {
        open("https://mm.ru/");
        $("[data-test-id='input__search']").setValue(searchText).pressEnter();
        $(".breadcrumbs-list").$$("[data-test-id='list__breadcrumb']").shouldHave(texts(expectedSubsections));
    }
}
