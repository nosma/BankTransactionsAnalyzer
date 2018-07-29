package personal.bank.transaction.analyzer.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatementGenerator {

  private static final String FILENAME = "SmallStatementFile.csv";
  private static File transactionsFile = new File(Paths.get(FILENAME).toString());

  private static final Logger log = LoggerFactory.getLogger(StatementGenerator.class);
  private static long minimumFileSize = 157286400L; // 150 MB

  public static void main(String[] args) {
    generateFile();
  }

  private static void generateFile() {
    try {
      String transactionsFileName = transactionsFile.getName();
      if(!transactionsFile.exists()) {
        if(transactionsFile.createNewFile()) {
          try (FileWriter writer = new FileWriter(transactionsFile)) {
            while (transactionsFile.length() < minimumFileSize) {
              writer.write(getRandomDate() + "," + getRandomDescription() + "," + getRandomCost() + "\n");
            }
            writer.flush();
          }
          log.info("Big Statement File file created!");
        } else {
          String exception = String.format("File %s did not created.", transactionsFileName);
          log.error(exception);
        }
      } else {
        String exception = String.format("File %s already exists.", transactionsFileName);
        log.warn(exception);
      }
    } catch(IOException e) {
      String exception = "Error while creating file." + e;
      log.error(exception);
    }
  }

  // DATE FORMAT 6/30/15
  private static String getRandomDate() {
    int month = ThreadLocalRandom.current().nextInt(1, 12);
    int day = ThreadLocalRandom.current().nextInt(1, 28);
    int year = 16;
    return month + "/" + day + "/" + year;
  }

  // DESCRIPTION FORMAT: NEXT RETAIL LTD STRATFORD
  private static String getRandomDescription() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    while (salt.length() < 18) {
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    return salt.toString();
  }

  private static double getRandomCost() {
    DecimalFormat df = new DecimalFormat("###.##");
    return Double.valueOf(df.format(ThreadLocalRandom.current().nextDouble(-1000.0, 3000.0)));
  }
}
