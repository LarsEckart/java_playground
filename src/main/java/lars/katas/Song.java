package lars.katas;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Song {

  public String lyrics() {
    return IntStream.range(1, 9)
        .mapToObj(
            i ->
                switch (i) {
                  case 1 -> """
          There was an old lady who swallowed a fly;

              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 2 -> """
          There was an old lady who swallowed a spider
          That wriggled and jiggled and tickled inside her!

              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 3 -> """
          There was an old lady who swallowed a bird;
          How absurd to swallow a bird!

              She swallowed the bird to catch the spider
              That wriggled and jiggled and tickled inside her!
              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 4 -> """
          There was an old lady who swallowed a cat;
          Imagine that! She swallowed a cat!

              She swallowed the cat to catch the bird,
              She swallowed the bird to catch the spider
              That wriggled and jiggled and tickled inside her!
              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 5 -> """
          There was an old lady that swallowed a dog;
          What a hog, to swallow a dog!

              She swallowed the dog to catch the cat,
              She swallowed the cat to catch the bird,
              She swallowed the bird to catch the spider
              That wriggled and jiggled and tickled inside her!
              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 6 -> """
          There was an old lady who swallowed a goat;
          She just opened her throat and swallowed a goat!

              She swallowed the goat to catch the dog,
              She swallowed the dog to catch the cat,
              She swallowed the cat to catch the bird,
              She swallowed the bird to catch the spider
              That wriggled and jiggled and tickled inside her!
              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she'll die!
              """;
                  case 7 -> """
          There was an old lady who swallowed a cow;
          I don't know how she swallowed a cow!

              She swallowed the cow to catch the goat,
              She swallowed the goat to catch the dog,
              She swallowed the dog to catch the cat,
              She swallowed the cat to catch the bird,
              She swallowed the bird to catch the spider
              That wriggled and jiggled and tickled inside her!
              She swallowed the spider to catch the fly;
              I don't know why she swallowed a fly - Perhaps she’ll die!
              """;
                  default -> """
          There was an old lady who swallowed a horse;

              ...She's dead, of course""";
                })
        .collect(Collectors.joining("\n"));
  }
}
