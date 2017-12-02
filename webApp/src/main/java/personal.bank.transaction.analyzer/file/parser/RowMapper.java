package personal.bank.transaction.analyzer.file.parser;

interface CsvRowMapper {

  void map(int rowNumber, String[] cells);

}