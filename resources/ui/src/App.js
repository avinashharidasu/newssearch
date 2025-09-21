import React, { useState, useEffect } from 'react';
import NewsSearchForm from './components/NewsSearchForm';
import NewsResults from './components/NewsResults';
import OfflineToggle from './components/OfflineToggle';
import { NewsService } from './services/NewsService';
import { DateGroupingService } from './services/DateGroupingService';
import './App.css';

function App() {
  const [searchParams, setSearchParams] = useState({
    query: '',
    rangeFrom: '',
    rangeTo: '',
    sortBy: 'relevancy',
    pageSize: 20,
    page: 1,
    interval: 12,
    intervalType: 'hours'
  });

  const [newsData, setNewsData] = useState(null);
  const [groupedArticles, setGroupedArticles] = useState({});
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [isOffline, setIsOffline] = useState(false);

  const newsService = new NewsService();
  const dateGroupingService = new DateGroupingService();

  useEffect(() => {
    // Check if we're offline on component mount
    setIsOffline(!navigator.onLine);
    
    // Listen for online/offline events
    const handleOnline = () => setIsOffline(false);
    const handleOffline = () => setIsOffline(true);
    
    window.addEventListener('online', handleOnline);
    window.addEventListener('offline', handleOffline);
    
    return () => {
      window.removeEventListener('online', handleOnline);
      window.removeEventListener('offline', handleOffline);
    };
  }, []);

  const handleSearch = async (params) => {
    setLoading(true);
    setError(null);
    
    try {
      let data;
      
      if (isOffline) {
        // Load from localStorage in offline mode
        data = newsService.getOfflineData(params.query);
        if (!data) {
          throw new Error('No offline data available for this search');
        }
      } else {
        // Fetch from API
        data = await newsService.searchNews(params);
        // Store in localStorage for offline access
        newsService.storeOfflineData(params.query, data);
      }
      
      setNewsData(data);
      
      // Group articles by time intervals
      const grouped = dateGroupingService.groupArticlesByTimeIntervals(
        data.articles || [],
        params.interval,
        params.intervalType
      );
      
      setGroupedArticles(grouped);
    } catch (err) {
      setError(err.message);
      setNewsData(null);
      setGroupedArticles({});
    } finally {
      setLoading(false);
    }
  };

  const handleOfflineToggle = (offline) => {
    setIsOffline(offline);
  };

  return (
    <div className="App">
      <div className="container">
        <header className="header">
          <h1>News Search Application</h1>
          <p>Search for news articles and view them grouped by time intervals</p>
        </header>

        <OfflineToggle 
          isOffline={isOffline} 
          onToggle={handleOfflineToggle} 
        />

        <NewsSearchForm 
          searchParams={searchParams}
          onSearch={handleSearch}
          loading={loading}
          isOffline={isOffline}
        />

        {error && (
          <div className="error">
            <strong>Error:</strong> {error}
          </div>
        )}

        {loading && (
          <div className="loading">
            <p>Searching for news articles...</p>
          </div>
        )}

        {newsData && !loading && (
          <NewsResults 
            newsData={newsData}
            groupedArticles={groupedArticles}
            searchParams={searchParams}
          />
        )}
      </div>
    </div>
  );
}

export default App;

