package org.example;

import com.intuit.fuzzymatcher.component.ElementMatch;
import com.intuit.fuzzymatcher.component.MatchService;
import com.intuit.fuzzymatcher.domain.Document;
import com.intuit.fuzzymatcher.domain.Element;
import com.intuit.fuzzymatcher.domain.Match;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static com.intuit.fuzzymatcher.domain.ElementType.ADDRESS;
import static com.intuit.fuzzymatcher.domain.ElementType.NAME;

public class Main {
  public static void main(String[] args) {

    String[][] input = {
            {"1", "Steven Wilson", "45th Avenue 5th st."},
            {"2", "John Doe", "546 freeman ave"},
            {"3", "Stephen Wilkson", "45th Ave 5th Street"}
    };

    List<Document> documentList = Arrays.asList(input).stream().map(contact -> {
      return new Document.Builder(contact[0])
              .addElement(new Element.Builder<String>().setValue(contact[1]).setType(NAME).createElement())
              .addElement(new Element.Builder<String>().setValue(contact[2]).setType(ADDRESS).createElement())
              .createDocument();
    }).collect(Collectors.toList());

    MatchService matchService = new MatchService();
    Map<String, List<Match<Document>>> result = matchService.applyMatchByDocId(documentList);

    result.entrySet().forEach(entry -> {
      entry.getValue().forEach(match -> {
        System.out.println("Data: " + match.getData() + " Matched With: " + match.getMatchedWith() + " Score: " + match.getScore().getResult());
      });
    });
  }
}