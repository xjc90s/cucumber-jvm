package io.cucumber.core.snippets;

import cucumber.api.SnippetType;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FunctionNameGeneratorTest {

    private SnippetType.FunctionNameGenerator underscore = SnippetType.UNDERSCORE.getFunctionNameGenerator();
    private SnippetType.FunctionNameGenerator camelCase = SnippetType.CAMELCASE.getFunctionNameGenerator();

    private void assertFunctionNames(String expectedUnderscore, String expectedCamelCase, String sentence) {
        assertEquals(expectedUnderscore, underscore.generateFunctionName(sentence));
        assertEquals(expectedCamelCase, camelCase.generateFunctionName(sentence));
    }

    @Test
    public void testSanitizeEmptyFunctionName() {
        final Executable testMethod = () -> underscore.generateFunctionName("");
        final IllegalArgumentException expectedThrown = assertThrows(IllegalArgumentException.class, testMethod);
        assertThat(expectedThrown.getMessage(), is(equalTo("Cannot create function name from empty sentence")));
    }

    @Test
    public void testSanitizeFunctionName() {
        assertFunctionNames(
                "test_function_123",
                "testFunction123",
                ".test function 123 ");
    }

    @Test
    public void sanitizes_simple_sentence() {
        assertFunctionNames(
                "i_am_a_function_name",
                "iAmAFunctionName",
                "I am a function name");
    }

    @Test
    public void sanitizes_sentence_with_multiple_spaces() {
        assertFunctionNames(
                "i_am_a_function_name",
                "iAmAFunctionName",
                "I am a function name");
    }

    @Test
    public void sanitizes_pascal_case_word() {
        assertFunctionNames(
                "function_name_with_pascalCase_word",
                "functionNameWithPascalCaseWord",
                "Function name with pascalCase word");
    }

    @Test
    public void sanitizes_camel_case_word() {
        assertFunctionNames(
                "function_name_with_CamelCase_word",
                "functionNameWithCamelCaseWord",
                "Function name with CamelCase word");
    }

    @Test
    public void sanitizes_acronyms() {
        assertFunctionNames(
                "function_name_with_multi_char_acronym_HTTP_Server",
                "functionNameWithMultiCharAcronymHTTPServer",
                "Function name with multi char acronym HTTP Server");
    }

    @Test
    public void sanitizes_two_char_acronym() {
        assertFunctionNames(
                "function_name_with_two_char_acronym_US",
                "functionNameWithTwoCharAcronymUS",
                "Function name with two char acronym US");
    }

}