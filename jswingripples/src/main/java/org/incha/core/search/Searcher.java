package org.incha.core.search;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcocl_000 on 05-05-2016.
 * Class that manages user searches using the indexes in the index Directory.
 */
public class Searcher {
    /**
     * Lucene class which handles user input and creates a Lucene query.
     */
    private QueryParser queryParser;
    /**
     * Lucene class for searching the created indexes.
     */
    private IndexSearcher indexSearcher;
    /**
     * Maximum number of query results.
     */
    private static final int maxHits = 10;

    /**
     * Default constructor.
     * @param indexDirectoryPath the filepath to the directory containing the indexes.
     * @throws IOException
     */
    public Searcher(String indexDirectoryPath) throws IOException {
        // The directory containing the indexes.
        Directory indexDirectory = FSDirectory.open(new File(indexDirectoryPath));

        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_36);
        queryParser = new QueryParser(Version.LUCENE_36, LuceneConstants.CONTENTS, analyzer);
        indexSearcher = new IndexSearcher(IndexReader.open(indexDirectory));
    }

    /**
     * Searches the indexes in the Directory.
     * @param searchQuery the user's query.
     * @return the query results.
     * @throws IOException
     * @throws ParseException
     */
    private TopDocs searchIndexes(String searchQuery) throws IOException, ParseException {
        Query query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, maxHits);
    }

    /**
     * Retrieves Lucene Document from ScoreDoc.
     * @param scoreDoc the document to be retrieved.
     * @return the corresponding Document.
     * @throws IOException
     */
    private Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }

    /**
     * Handles complete search process.
     * @param searchQuery the user's query string.
     * @return a list of Documents with the top search hits.
     * @throws IOException
     * @throws ParseException
     */
    public List<Document> search(String searchQuery) throws IOException, ParseException {
        TopDocs topDocs = searchIndexes(searchQuery);
        List<Document> results = new ArrayList<Document>();
        for(ScoreDoc doc : topDocs.scoreDocs) {
            results.add(getDocument(doc));
        }
        return results;
    }

    /**
     * Closes the IndexSearcher.
     * @throws IOException
     */
    public void close() throws IOException {
        indexSearcher.close();
    }
}
