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
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY method, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.algolia.search.saas;

import com.algolia.search.saas.listeners.APIClientListener;
import com.algolia.search.saas.listeners.DeleteObjectsListener;
import com.algolia.search.saas.listeners.GetObjectsListener;
import com.algolia.search.saas.listeners.IndexingListener;
import com.algolia.search.saas.listeners.SearchListener;
import com.algolia.search.saas.listeners.SettingsListener;
import com.algolia.search.saas.listeners.WaitTaskListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class TaskParams {
    public static class Search {
        protected SearchListener listener;
        public Query query;

        protected JSONObject content;
        protected AlgoliaException error;

        protected Search(SearchListener listener, Query query) {
            this.listener = listener;
            this.query = query;
        }

        protected void sendResult(Index index) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.searchResult(index, query, content);
            } else {
                listener.searchError(index, query, error);
            }
        }
    }

    public static class Indexing {
        protected IndexingListener listener;
        public IndexMethod method;
        public JSONObject object;
        public JSONArray objects;
        public String objectID;

        protected JSONObject content;
        protected AlgoliaException error;

        protected Indexing(IndexingListener listener, IndexMethod method, JSONObject object) {
            this.listener = listener;
            this.method = method;
            this.object = object;
        }

        protected Indexing(IndexingListener listener, IndexMethod method, JSONObject object, String objectID) {
            this.listener = listener;
            this.method = method;
            this.object = object;
            this.objectID = objectID;
        }

        protected Indexing(IndexingListener listener, IndexMethod method, JSONArray objects) {
            this.listener = listener;
            this.method = method;
            this.objects = objects;
        }

        protected void sendResult(Index index) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.indexingResult(index, this, content);
            } else {
                listener.indexingError(index, this, error);
            }
        }
    }

    public static class GetObjects {
        protected GetObjectsListener listener;
        public IndexMethod method;
        public String objectID;
        public List<String> objectIDs;
        public List<String> attributesToRetrieve;

        protected JSONObject content;
        protected AlgoliaException error;

        protected GetObjects(GetObjectsListener listener, IndexMethod method, String objectID) {
            this.listener = listener;
            this.method = method;
            this.objectID = objectID;
        }

        protected GetObjects(GetObjectsListener listener, IndexMethod method, String objectID, List<String> attributesToRetrieve) {
            this.listener = listener;
            this.method = method;
            this.objectID = objectID;
            this.attributesToRetrieve = attributesToRetrieve;
        }

        protected GetObjects(GetObjectsListener listener, IndexMethod method, List<String> objectIDs) {
            this.listener = listener;
            this.method = method;
            this.objectIDs = objectIDs;
        }

        protected void sendResult(Index index) {
            if (error == null) {
                listener.getObjectsResult(index, this, content);
            } else {
                listener.getObjectsError(index, this, error);
            }
        }
    }

    public static class WaitTask {
        protected WaitTaskListener listener;
        public String taskID;

        protected AlgoliaException error;

        protected WaitTask(WaitTaskListener listener, String taskID) {
            this.listener = listener;
            this.taskID = taskID;
        }

        protected void sendResult(Index index) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.waitTaskResult(index, taskID);
            } else {
                listener.waitTaskError(index, taskID, error);
            }
        }
    }

    public static class DeleteObjects {
        protected DeleteObjectsListener listener;
        public IndexMethod method;
        public String objectID;
        public List<String> objectIDs;
        public Query query;

        protected JSONObject content;
        protected AlgoliaException error;

        protected DeleteObjects(DeleteObjectsListener listener, IndexMethod method, String objectID) {
            this.listener = listener;
            this.method = method;
            this.objectID = objectID;
        }

        protected DeleteObjects(DeleteObjectsListener listener, IndexMethod method, List<String> objectIDs) {
            this.listener = listener;
            this.method = method;
            this.objectIDs = objectIDs;
        }

        protected DeleteObjects(DeleteObjectsListener listener, IndexMethod method, Query query) {
            this.listener = listener;
            this.method = method;
            this.query = query;
        }

        protected void sendResult(Index index) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.deleteObjectsResult(index, this, content);
            } else {
                listener.deleteObjectsError(index, this, error);
            }
        }
    }

    public static class Settings {
        protected SettingsListener listener;
        public IndexMethod method;
        public JSONObject settings;

        protected JSONObject content;
        protected AlgoliaException error;

        protected Settings(SettingsListener listener, IndexMethod method) {
            this.listener = listener;
            this.method = method;
        }

        protected Settings(SettingsListener listener, IndexMethod method, JSONObject settings) {
            this.listener = listener;
            this.method = method;
            this.settings = settings;
        }

        protected void sendResult(Index index) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.settingsResult(index, this, content);
            } else {
                listener.settingsError(index, this, error);
            }
        }
    }

    public static class Client {
        protected APIClientListener listener;
        public APIMethod method;
        public String indexName;
        public String srcIndexName;
        public String dstIndexName;
        public int offset;
        public int length;
        public LogType logType;
        public String key;
        public JSONObject parameters;
        public List<IndexQuery> queries;
        public String strategy;
        public JSONArray actions;

        protected JSONObject content;
        protected AlgoliaException error;

        protected Client(APIClientListener listener, APIMethod method) {
            this.listener = listener;
            this.method = method;
        }

        protected Client(APIClientListener listener, APIMethod method, String str) {
            this.listener = listener;
            this.method = method;

            if (method == APIMethod.DeleteIndex) {
                this.indexName = str;
            } else {
                this.key = str;
            }
        }

        protected Client(APIClientListener listener, APIMethod method, String srcIndexName, String dstIndexName) {
            this.listener = listener;
            this.method = method;
            this.srcIndexName = srcIndexName;
            this.dstIndexName = dstIndexName;
        }

        protected Client(APIClientListener listener, APIMethod method, JSONArray actions) {
            this.listener = listener;
            this.method = method;
            this.actions = actions;
        }

        protected Client(APIClientListener listener, APIMethod method, List<IndexQuery> queries, String strategy) {
            this.listener = listener;
            this.method = method;
            this.queries = queries;
            this.strategy = strategy;
        }

        protected Client(APIClientListener listener, APIMethod method, JSONObject parameters) {
            this.listener = listener;
            this.method = method;
            this.parameters = parameters;
        }

        protected Client(APIClientListener listener, APIMethod method, JSONObject parameters, String key) {
            this.listener = listener;
            this.method = method;
            this.parameters = parameters;
            this.key = key;
        }

        protected Client(APIClientListener listener, APIMethod method, int offset, int length, LogType logType) {
            this.listener = listener;
            this.method = method;
            this.offset = offset;
            this.length = length;
            this.logType = logType;
        }

        protected void sendResult(APIClient client) {
            if (listener == null) {
                return;
            }

            if (error == null) {
                listener.APIResult(client, this, content);
            } else {
                listener.APIError(client, this, error);
            }
        }
    }
}