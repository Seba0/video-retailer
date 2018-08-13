package com.sda.video.datastore;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.opencsv.*;

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

    /**
     * Constructs CSVDataStore with file and template
     *
     * @param file     CSV file storing data
     * @param template template object required for creating new instances of reading objects
     */
    public CSVDataStore(File file, T template) {
        this.file = file;
        this.template = template;
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

            // For each element in storables
            for (T storable : storables) {

                // Serialize storable object in to CSV columns
                String[] strings = storable.writeRow();

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
        Set<T> out = new HashSet<>();
        CSVReader reader = null;
        try {
            // Open file and create new CSV reader
            reader = new CSVReader(new FileReader(file));

            // Read first row from CSV file
            // if no more rows in file method return null
            String[] row = reader.readNext();

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
