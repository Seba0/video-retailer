package com.sda.video.datastore;


/**
 * Interface identifying Classes to be stored in CSV files
 */
public interface CSVStorable {

    /**
     * The object implements the writeRow method to write its contents to the line of the row cells
     *
     * @return array of row cells
     */
    String[] writeRow();

    /**
     * The object implements the readRow method to restore its contents by parsing row cells
     *
     * @param cells array of row cells
     */
    void readRow(String[] cells);

    /**
     * Create new object
     *
     * @return new instance of this object
     */
    CSVStorable newInstance();
}
