package com.intuit.fuzzymatcher.function;

import com.intuit.fuzzymatcher.domain.CivilStatus;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.ItemRange;

import org.junit.Assert;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

import static com.intuit.fuzzymatcher.domain.ElementType.*;
import static org.junit.Assert.assertEquals;

public class PreProcessFunctionTest {
    @Test
    public void itShouldRemoveSuffixFromName_Success(){
        String value = "James Parker JR.";
        Element element = new Element.Builder().setType(NAME).setValue(value).createElement();
        Assert.assertEquals("james parker", element.getPreProcessedValue());
    }

    @Test
    public void itShouldRemoveSlashes(){
        String value = "fundacion/special";
        Element element = new Element.Builder().setType(PATH).setValue(value).createElement();
        Assert.assertEquals("fundacion special", element.getPreProcessedValue());
    }

    @Test
    public void itShouldRemoveSpecialCharactersOnEverySubPath(){
        String value = "fundacion/special.txt%$#&";
        Element element = new Element.Builder().setType(PATH).setValue(value).createElement();
        Assert.assertEquals("fundacion specialtxt", element.getPreProcessedValue());
    }

    @Test
    public void itShouldRemoveAdditionalCharactersIfItIsADuplicatedFileName(){
        String value = "fundacion/special(1).txt";
        Element element = new Element.Builder().setType(PATH).setValue(value).createElement();
        Assert.assertEquals("fundacion specialtxt", element.getPreProcessedValue());
    }

    @Test
    public void itShouldRemoveIfNumberOfDuplicatedFileContainsMoreThanTwoDigits(){
        String value = "fundacion/special(123).txt";
        Element element = new Element.Builder().setType(PATH).setValue(value).createElement();
        Assert.assertEquals("fundacion specialtxt", element.getPreProcessedValue());
    }

    @Test
    public void itShouldPreprocessAddress(){
        String value = "123 XYZ Ltd st, TX";
        Element element = new Element.Builder().setType(ADDRESS).setValue(value).createElement();
        Assert.assertEquals("123 xyz ltd street texas", element.getPreProcessedValue());
    }

   
    @Test
    public void itShouldCustomPreprocessAddress(){
        String value = "123_XYZ_Ltd_st, TX";
        Function<String, String> customPreProcessing = (str -> str.replaceAll("_", " "));
        customPreProcessing = customPreProcessing.andThen(PreProcessFunction.addressPreprocessing());

        Element element = new Element.Builder().setType(ADDRESS)
                .setPreProcessingFunction(customPreProcessing)
                .setValue(value)
                .createElement();

        Assert.assertEquals("123 xyz ltd street texas", element.getPreProcessedValue());
    }


    @Test
    public void itShouldGetNullString_Success(){
        String value = "   ";
        Element element = new Element.Builder().setType(NAME).setValue(value).createElement();
        Assert.assertEquals("", element.getPreProcessedValue());
    }
    
    @Test
    public void itShouldRemoveTrailingNumbersFromName_Success(){
        String value = "Nova LLC-1";
        Element element = new Element.Builder().setType(NAME).setValue(value).createElement();
        Assert.assertEquals("nova", element.getPreProcessedValue());
    }

    @Test
    public void itShouldTestComposingFunction() {
        String value = "James Parker jr.";
        Element element = new Element.Builder().setType(TEXT).setValue(value).createElement();
        Assert.assertEquals("james parker jr", element.getPreProcessedValue());

        Element element1 = new Element.Builder().setType(TEXT).setValue(value)
                .setPreProcessingFunction(PreProcessFunction.removeSpecialChars()
                        .compose(str -> str.toString().replace("jr.", ""))).createElement();
        Assert.assertEquals("james parker", element1.getPreProcessedValue());
    }

    @Test
    public void itShouldNormalizeAddress_Success(){
        String value = "123 some-street ave PLano, TX";
        Element element = new Element.Builder().setType(ADDRESS).setValue(value).createElement();
        Assert.assertEquals("123 somestreet avenue plano texas", element.getPreProcessedValue());
    }

    @Test
    public void itShouldRemoveSpecialCharPhone_Success(){
        String value = "+1-(123)-456-4345";
        String result = (String)PreProcessFunction.removeSpecialChars().apply(value);
        Assert.assertEquals("11234564345", result);
    }

    @Test
    public void itShouldApplyNumberPreprocessing_Success(){
        String value = "$ value -34.76";
        String result = (String)PreProcessFunction.numberPreprocessing().apply(value);
        Assert.assertEquals("-34.76",result);
    }

    @Test
    public void itShouldApplyNumberPreprocessing_Failure(){
        String value = "$ value thirty four";
        String result = (String)PreProcessFunction.numberPreprocessing().apply(value);
        Assert.assertEquals(value, result);
    }

    @Test
    public void itShouldApplyNonePreprocessing_Success() throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date input = df.parse("04/01/2020");
        Date result = (Date) PreProcessFunction.none().apply(input);
        Assert.assertEquals(input, result);
    }


    @Test
    public void itShouldRecognizePhoneNumber_Success() {
        String value = "My phone number is 123-456-7890. Call me!";
        String result = PreProcessFunction.phoneNumberRecognition().apply(value);
        Assert.assertEquals("1234567890", result);
    }

    @Test
    public void itShouldRecognizeMultiplePhoneNumbers_Success() {
        String value = "Contact me at 123-456-7890 or 987-654-3210.";
        String result = PreProcessFunction.phoneNumberRecognition().apply(value);
        Assert.assertEquals("1234567890 9876543210", result);
    }

    @Test
    public void itShouldHandleNoPhoneNumberFound_Success() {
        String value = "No phone number in this text.";
        String result = PreProcessFunction.phoneNumberRecognition().apply(value);
        Assert.assertEquals("", result);
    }

    @Test
    public void itShouldHandlePhoneNumberAtBeginningAndEnd_Success() {
        String value = "123-456-7890 is my number. Call me at 987-654-3210.";
        String result = PreProcessFunction.phoneNumberRecognition().apply(value);
        Assert.assertEquals("1234567890 9876543210", result);
    }

    @Test
    public void itShouldHandleBooleanTextValueTrueAndEnd_Success() {
        String value = "True";
        Object result = PreProcessFunction.boolPreprocessing().apply(value);
        Assert.assertEquals(true, result);
    }

    @Test
    public void itShouldHandleBooleanTextValueFalseAndEnd_Success() {
        String value = "False";
        Object result = PreProcessFunction.boolPreprocessing().apply(value);
        Assert.assertEquals(false, result);
    }

    @Test
    public void itShouldHandleRangeItemValue(){
        String rangeItem = "short";
        Object result = PreProcessFunction.itemRangeRecognition().apply(rangeItem);
        assertEquals(ItemRange.SHORT.name(), result);

        rangeItem = "medium";
        result = PreProcessFunction.itemRangeRecognition().apply(rangeItem);
        assertEquals(ItemRange.MEDIUM.name(), result);

        rangeItem = "long";
        result = PreProcessFunction.itemRangeRecognition().apply(rangeItem);
        assertEquals(ItemRange.LONG.name(), result);

        rangeItem = "adfsdgbsd";
        result = PreProcessFunction.itemRangeRecognition().apply(rangeItem);
        assertEquals(ItemRange.SHORT.name(), result);
    }

    @Test
    public void itShouldHandleCivilStateValue(){
        String civilStatus = "single";
        Object result = PreProcessFunction.civilStateRecognition().apply(civilStatus);
        assertEquals(CivilStatus.SINGLE.name(), result);

        civilStatus = "married";
        result = PreProcessFunction.civilStateRecognition().apply(civilStatus);
        assertEquals(CivilStatus.MARRIED.name(), result);

        civilStatus = "divorced";
        result = PreProcessFunction.civilStateRecognition().apply(civilStatus);
        assertEquals(CivilStatus.DIVORCED.name(), result);

        civilStatus = "widowed";
        result = PreProcessFunction.civilStateRecognition().apply(civilStatus);
        assertEquals(CivilStatus.WIDOWED.name(), result);

        civilStatus = "asfasfsa";
        result = PreProcessFunction.civilStateRecognition().apply(civilStatus);
        assertEquals(CivilStatus.SINGLE.name(), result);
    }
}