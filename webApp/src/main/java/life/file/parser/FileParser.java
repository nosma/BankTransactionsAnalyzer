package life.file.parser;

import java.io.IOException;
import java.text.ParseException;

public interface FileParser<I,O> {

  boolean canParse(I i) throws IOException;

  O parse(I i) throws IOException, ParseException;

}
