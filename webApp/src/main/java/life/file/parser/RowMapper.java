package life.file.parser;

interface CsvRowMapper {

  void map(int rowNumber, String[] cells);

}