package org.incha.core.search;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.eclipse.jdt.core.ICompilationUnit;
import org.incha.core.JavaProject;
import org.incha.core.jswingripples.eig.JSwingRipplesEIG;
import org.incha.core.jswingripples.eig.JSwingRipplesEIGNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by fcocl_000 on 26-04-2016.
 * Indexes data to make it searchable with the Lucene library.
 */
public class Indexer {
    /**
     * Object that creates and maintains an index.
     */
    private IndexWriter writer;

    /**
     * Default class constructor.
     * @param indexDirectoryPath the path to the directory that will contain the indexes.
     * @throws IOException
     */
    public Indexer (String indexDirectoryPath) throws IOException {
        // Directory that will contain indexes
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));

        // Create the new writer
        StandardAnalyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, analyzer);
        writer = new IndexWriter(indexDirectory, indexWriterConfig);
    }

    /**
     * Close the current writer.
     */
    public void close() throws IOException {
        writer.close();
    }

    /**
     * Creates a Lucene Document object from the specified file.
     * @param file the file to be indexed.
     * @return a new Document instance with file content, name and path fields.
     * @throws FileNotFoundException
     */
    private Document createDocument(File file) throws IOException {
        // Create new document.
        Document document = new Document();

        // Create document fields
        Field content = new Field(LuceneConstants.CONTENTS, new FileReader(file));
        Field name = new Field(LuceneConstants.FILE_NAME, file.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED);
        Field path = new Field(LuceneConstants.FILE_PATH, file.getCanonicalPath(),  Field.Store.YES, Field.Index.NOT_ANALYZED);

        // Add fields to document
        document.add(content);
        document.add(name);
        document.add(path);

        return document;
    }

    /**
     * Indexes the file by adding it to the writer.
     * @param file the file to be indexed.
     * @throws IOException
     */
    private void indexFile(File file) throws IOException {
        Document document = createDocument(file);
        writer.addDocument(document);
    }

    /**
     * Creates an index of all files in the project.
     * @throws IOException
     */
    public void indexProject(JavaProject project) throws IOException {
        for (File file : project.getSources()) {
            indexFile(file);
        }
    }
}
