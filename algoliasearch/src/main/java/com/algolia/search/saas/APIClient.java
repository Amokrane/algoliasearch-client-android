/*
 * Copyright (c) 2015 Algolia
 * http://www.algolia.com/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.algolia.search.saas;

import android.os.AsyncTask;

import com.algolia.search.saas.listeners.APIClientListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Entry point in the Java API.
 * You should instantiate a Client object with your ApplicationID, ApiKey and Hosts 
 * to start using Algolia Search API
 */
public class APIClient extends BaseAPIClient {
    /**
     * Algolia Search initialization
     * @param applicationID the application ID you have in your admin interface
     * @param apiKey a valid API key for the service
     */
    public APIClient(String applicationID, String apiKey) {
        this(applicationID, apiKey, null);
    }

    /**
     * Algolia Search initialization
     * @param applicationID the application ID you have in your admin interface
     * @param apiKey a valid API key for the service
     * @param hostsArray the list of hosts that you have received for the service
     */
    public APIClient(String applicationID, String apiKey, List<String> hostsArray) {
        this(applicationID, apiKey, hostsArray, false, null);
    }

    /**
     * Algolia Search initialization
     * @param applicationID the application ID you have in your admin interface
     * @param apiKey a valid API key for the service
     * @param enableDsn set to true if your account has the Distributed Search Option
     */
    public APIClient(String applicationID, String apiKey, boolean enableDsn) {
        this(applicationID, apiKey, null, enableDsn, null);
    }

    /**
     * Algolia Search initialization
     * @param applicationID the application ID you have in your admin interface
     * @param apiKey a valid API key for the service
     * @param hostsArray the list of hosts that you have received for the service
     * @param enableDsn set to true if your account has the Distributed Search Option
     * @param dsnHost override the automatic computation of dsn hostname
     */
    public APIClient(String applicationID, String apiKey, List<String> hostsArray, boolean enableDsn, String dsnHost) {
        super(applicationID, apiKey, hostsArray, enableDsn, dsnHost);
    }

    /**
     * Get the index object initialized (no server call needed for initialization)
     *
     * @param indexName the name of index
     */
    public Index initIndex(String indexName) {
        return new Index(this, indexName);
    }

    private class ASyncClientTask extends AsyncTask<TaskParams.Client, Void, TaskParams.Client> {
        @Override
        protected TaskParams.Client doInBackground(TaskParams.Client... params) {
            TaskParams.Client p = params[0];
            try {
                switch (p.method) {
                    case ListIndexes:
                        p.content = listIndexes();
                        break;
                    case DeleteIndex:
                        p.content = deleteIndex(p.indexName);
                        break;
                    case MoveIndex:
                        p.content = moveIndex(p.srcIndexName, p.dstIndexName);
                        break;
                    case CopyIndex:
                        p.content = copyIndex(p.srcIndexName, p.dstIndexName);
                        break;
                    case GetLogs:
                        p.content = getLogs(p.offset, p.length, p.logType);
                        break;
                    case GetUserKey:
                        p.content = deleteUserKey(p.key);
                        break;
                    case ListUserKeys:
                        p.content = listUserKeys();
                        break;
                    case DeleteUserKey:
                        p.content = deleteUserKey(p.key);
                        break;
                    case AddUserKey:
                        p.content = addUserKey(p.parameters);
                        break;
                    case UpdateUserKey:
                        p.content = updateUserKey(p.key, p.parameters);
                        break;
                    case MultipleQueries:
                        p.content = multipleQueries(p.queries, p.strategy);
                        break;
                    case Batch:
                        p.content = batch(p.actions);
                        break;
                }
            } catch (AlgoliaException e) {
                p.error = e;
            }

            return p;
        }

        @Override
        protected void onPostExecute(TaskParams.Client p) {
            p.sendResult(APIClient.this);
        }
    }

    /**
     * List all existing user keys with their associated ACLs
     *
     * @param listener the listener that will receive the result or error.
     */
    public void listIndexesASync(APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.ListIndexes);
        new ASyncClientTask().execute(params);
    }

    /**
     * Delete an index
     *
     * @param indexName the name of index to delete
     */
    public void deleteIndexASync(String indexName, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.DeleteIndex, indexName);
        new ASyncClientTask().execute(params);
    }

    /**
     * Move an existing index.
     * @param srcIndexName the name of index to copy.
     * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist).
     */
    public void moveIndexASync(String srcIndexName, String dstIndexName, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.MoveIndex, srcIndexName, dstIndexName);
        new ASyncClientTask().execute(params);
    }

    /**
     * Copy an existing index.
     * @param srcIndexName the name of index to copy.
     * @param dstIndexName the new index name that will contains a copy of srcIndexName (destination will be overriten if it already exist).
     */
    public void copyIndexASync(String srcIndexName, String dstIndexName, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.CopyIndex, srcIndexName, dstIndexName);
        new ASyncClientTask().execute(params);
    }

    /**
     * Return last logs entries.
     * @param offset Specify the first entry to retrieve (0-based, 0 is the most recent log entry).
     * @param length Specify the maximum number of entries to retrieve starting at offset. Maximum allowed value: 1000.
     * @param logType Specify the type of log to retrieve
     */
    public void getLogsASync(int offset, int length, LogType logType, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.GetLogs, offset, length, logType);
        new ASyncClientTask().execute(params);
    }

    /**
     * List all existing user keys with their associated ACLs
     */
    public void listUserKeysASync(APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.ListUserKeys);
        new ASyncClientTask().execute(params);
    }

    /**
     * Get ACL of a user key
     */
    public void getUserKeyACLASync(String key, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.GetUserKey, key);
        new ASyncClientTask().execute(params);
    }

    /**
     * Delete an existing user key
     */
    public void deleteUserKeyASync(String key, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.DeleteUserKey, key);
        new ASyncClientTask().execute(params);
    }

    /**
     * Create a new user key
     */
    public void addUserKeyASync(JSONObject parameters, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.AddUserKey, parameters);
        new ASyncClientTask().execute(params);
    }

    /**
     * Update a user key asynchronously
     *
     * @param parameters the list of parameters for this key. Defined by a JSONObject that
     * can contains the following values:
     *   - acl: array of string
     *   - indices: array of string
     *   - validity: int
     *   - referrers: array of string
     *   - description: string
     *   - maxHitsPerQuery: integer
     *   - queryParameters: string
     *   - maxQueriesPerIPPerHour: integer
     */
    public void updateUserKeyASync(String key, JSONObject parameters, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.UpdateUserKey, parameters, key);
        new ASyncClientTask().execute(params);
    }

    /**
     * This method allows to query multiple indexes with one API call asynchronously
     */
    public void multipleQueriesASync(List<IndexQuery> queries, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.MultipleQueries, queries, "none");
        new ASyncClientTask().execute(params);
    }

    /**
     * This method allows to query multiple indexes with one API call asynchronously
     */
    public void multipleQueriesASync(List<IndexQuery> queries, String strategy, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.MultipleQueries, queries, strategy);
        new ASyncClientTask().execute(params);
    }

    /**
     * Custom batch asynchronous
     *
     * @param actions the array of actions
     */
    public void batchASync(JSONArray actions, APIClientListener listener) {
        TaskParams.Client params = new TaskParams.Client(listener, APIMethod.Batch, actions);
        new ASyncClientTask().execute(params);
    }
}