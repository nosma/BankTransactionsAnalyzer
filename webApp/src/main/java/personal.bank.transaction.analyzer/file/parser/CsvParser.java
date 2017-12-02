package personal.bank.transaction.analyzer.file.parser;

import org.apache.log4j.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

abstract class CsvParser implements FileParser<File, List> {

  private static Logger logger = Logger.getLogger(CsvParser.class);

  public boolean canParse(File file) throws IOException {
    return file.length() > 0 && file.getName().toLowerCase().endsWith(".csv");
  }

  void read(InputStream inputStream, CsvRowMapper rowMapper) {
    try (BufferedInputStream bis = new BufferedInputStream(inputStream)) {
      final byte[] bytes = new byte[1024];
      final int[] rowIndex = {0};
      final List<String> cells = new ArrayList<>();

      while (bis.available() > 0) {
        bis.read(bytes);
        StringBuffer cell = new StringBuffer();
        boolean isEscaped = false;

        for (byte aByte : bytes) {
          char character = (char) aByte;
          switch (character) {
            case '"':
              isEscaped = !isEscaped;
              break;

            case ',':
              if (!readEscaped(isEscaped, cell, aByte)) {
                cells.add(cell.toString());
                cell = new StringBuffer();
              }
              break;

            case '\r':
            case '\n':
              if (!readEscaped(isEscaped, cell, aByte)) {
                cells.add(cell.toString());
                cell = new StringBuffer();
                rowMapper.map(rowIndex[0]++, cells.toArray(new String[0]));
                cells.clear();
              }
              break;

            default:
              cell.append(character);
          }
        }

      }

    } catch (IOException e) {
      logger.error("Error while parsing midata row. ", e);
    }
  }

  private boolean readEscaped(boolean isEscaped, StringBuffer cell, byte aByte) {
    if (isEscaped) {
      cell.append((char) aByte);
    }
    return isEscaped;
  }

}
