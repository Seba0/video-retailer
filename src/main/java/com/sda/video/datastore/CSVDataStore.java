package com.sda.video.datastore;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Class to simplify writing and reading object from CSV files
 *
 * @param <T> type of storable objects
 */
public final class CSVDataStore<T extends CSVStorable> {

    // CSV file storing data
    private final File file;

    // Template object required for creating new instances of reading objects
    private final T template;

    /// Id for next element;
    private int nextId;

    /**
     * Constructs CSVDataStore with file and template
     *
     * @param file     CSV file storing data
     * @param template template object required for creating new instances of reading objects
     */
    public CSVDataStore(File file, T template) {
        this.file = file;
        this.template = template;
        this.nextId = 1;
    }

    /**
     * Method for writing storable elements in CSV file
     *
     * @param storables storable elements
     * @throws IOException any problem with writing to file
     */
    public void write(Set<T> storables) throws IOException {
        CSVWriter writer = null;
        try {
            // Open file and create new CSV writer
            writer = new CSVWriter(new FileWriter(file));

            for (T storable : storables) {
                Integer id = storable.getId();
                if (id != null && id >= nextId) {
                    nextId = id + 1;
                }
            }

            for (T storable : storables) {
                Integer id = storable.getId();
                if (id == null) {
                    storable.setId(nextId++);
                }
            }

            // Write next id and number of rows in first row
            writer.writeNext(new String[]{
                    Integer.toString(nextId),
                    Integer.toString(storables.size())
            });

            // For each element in storables
            for (T storable : storables) {

                // Serialize storable object in to CSV columns
                String[] strings = storable.writeRow();

                // Create new table for data from storable and id
                String[] row = new String[strings.length + 1];

                // Write id in first cell
                row[0] = Integer.toString(storable.getId());

                // Copy data from old table to new
                System.arraycopy(strings, 0, row, 1, strings.length);

                // Write row into file
                writer.writeNext(strings, false);
            }
        } finally {
            // Finally close file and writer
            if (writer != null) {
                writer.close();
            }
        }
    }

    /**
     * Method for reading storable elements from CSV file
     *
     * @return storable elements
     * @throws IOException any problem with reading from file
     */
    public Set<T> read() throws IOException {
        // Set for result
        Set<T> out;
        CSVReader reader = null;
        try {
            // Open file and create new CSV reader
            reader = new CSVReader(new FileReader(file));

            // Read configuration of this data store file
            String[] row = reader.readNext();
            // If file is empty
            if (row == null) {
                // Set next id to 1
                nextId = 1;
                return null;
            }
            // Read next id from first cell of first row in file
            nextId = Integer.parseInt(row[0]);

            // Read number of rows in file
            out = new HashSet<>(Integer.parseInt(row[1]));

            // Read first row from CSV file
            // if no more rows in file method return null
            row = reader.readNext();
            // If no more rows in file stop loop
            while (row != null) {
                // Create new instance of storable object
                T csvStorable = (T) template.newInstance();

                // Restore serialized object from row
                csvStorable.readRow(row);

                // Add row in to results
                out.add(csvStorable);

                // Read next row from file
                row = reader.readNext();
            }
        } finally {
            // Finally close file and reader
            if (reader != null) {
                reader.close();
            }
        }
        // Return result
        return out;
    }

}
