package personal.bank.transaction.analyzer.parser;

interface CsvRowMapper {

  void map(int rowNumber, String[] cells);

}