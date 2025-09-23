import React, { useState } from 'react';
import NewsSearchForm from './components/NewsSearchForm';
import NewsResults from './components/NewsResults';
import AuthButton from './components/AuthButton';

const GOOGLE_CLIENT_ID = '32358567694-dmas57prhp74tvubf72thlq9j30e68ns.apps.googleusercontent.com';
const API_BASE_URL = 'http://localhost:8080/v1/api/news/search';

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [accessToken, setAccessToken] = useState(null);
  const [searchResults, setSearchResults] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [currentPage, setCurrentPage] = useState(1);
  const [lastSearchParams, setLastSearchParams] = useState(null);

  // No gapi init; using Google Identity Services via AuthButton

  const handleAuthSuccess = (token) => {
    setAccessToken(token);
    setIsAuthenticated(true);
    setError(null);
  };

  const handleAuthError = (error) => {
    setError('Authentication failed: ' + error);
    setIsAuthenticated(false);
  };

  const handleLogout = () => {
    setAccessToken(null);
    setIsAuthenticated(false);
    setSearchResults(null);
    setCurrentPage(1);
  };

  const handleSearch = async (searchParams) => {
    if (!isAuthenticated) {
      setError('Please authenticate first');
      return;
    }

    setLoading(true);
    setError(null);
    setCurrentPage(1);
    setLastSearchParams(searchParams);

    try {
      const queryParams = new URLSearchParams({
        query: searchParams.query,
        rangeFrom: String(searchParams.rangeFrom).concat(":00"),
        rangeTo: String(searchParams.rangeTo).concat(":00"),
        sortBy: searchParams.sortBy,
        pageSize: '10',
        page: '1'
      });

      const response = await fetch(`${API_BASE_URL}?${queryParams}`, {
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      // Keep the search params so pagination can reuse them
      setSearchResults({ ...data, searchParams });
    } catch (err) {
      setError('Search failed: ' + err.message);
      setSearchResults(null);
    } finally {
      setLoading(false);
    }
  };

  const handlePageChange = async (page, searchParams) => {
    if (!isAuthenticated) return;
    const paramsToUse = searchParams || lastSearchParams;
    if (!paramsToUse) return;

    setLoading(true);
    setError(null);
    setCurrentPage(page);

    try {
      const queryParams = new URLSearchParams({
        searchquery: paramsToUse.query,
        rangeFrom: paramsToUse.rangeFrom,
        rangeTo: paramsToUse.rangeTo,
        sortBy: paramsToUse.sortBy,
        pageSize: '10',
        page: page.toString()
      });

      const response = await fetch(`${API_BASE_URL}?${queryParams}`, {
        headers: {
          'Authorization': `Bearer ${accessToken}`,
          'Content-Type': 'application/json'
        }
      });

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setSearchResults({ ...data, searchParams: paramsToUse });
    } catch (err) {
      setError('Page load failed: ' + err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="container">
      <h1 className="header">News Search Application</h1>
      
      <div className="auth-section">
        <AuthButton
          isAuthenticated={isAuthenticated}
          onAuthSuccess={handleAuthSuccess}
          onAuthError={handleAuthError}
          onLogout={handleLogout}
        />
      </div>

      {error && <div className="error">{error}</div>}

      {isAuthenticated && (
        <>
          <NewsSearchForm onSearch={handleSearch} />
          
          {loading && <div className="loading">Loading...</div>}
          
          {searchResults && (
            <NewsResults
              results={searchResults}
              currentPage={currentPage}
              onPageChange={(page) => handlePageChange(page, searchResults.searchParams)}
            />
          )}
        </>
      )}
    </div>
  );
}

export default App;
