package com.sda.video.datastore;


/**
 * Abstract class identifying Classes to be stored in CSV files
 */
public abstract class CSVStorable {

    // Id of this object
    private Integer id;

    /**
     * Getter of id
     *
     * @return id of this object or null if not yet stored
     */
    public final Integer getId() {
        return id;
    }

    /**
     * Setter of id
     *
     * @param id of this store
     */
    final void setId(Integer id) {
        this.id = id;
    }

    /**
     * The object implements the writeRow method to write its contents to the line of the row cells
     *
     * @return array of row cells
     */
    public abstract String[] writeRow();

    /**
     * The object implements the readRow method to restore its contents by parsing row cells
     *
     * @param cells array of row cells
     */
    public abstract void readRow(String[] cells);

    /**
     * Create new object
     *
     * @return new instance of this object
     */
    public abstract CSVStorable newInstance();
}
