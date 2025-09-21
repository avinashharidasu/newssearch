import React, { useState, useEffect } from 'react';

const NewsSearchForm = ({ searchParams, onSearch, loading, isOffline }) => {
  const [formData, setFormData] = useState({
    query: searchParams.query || '',
    rangeFrom: searchParams.rangeFrom || '',
    rangeTo: searchParams.rangeTo || '',
    sortBy: searchParams.sortBy || 'relevancy',
    pageSize: searchParams.pageSize || 20,
    page: searchParams.page || 1,
    interval: searchParams.interval || 12,
    intervalType: searchParams.intervalType || 'hours'
  });

  useEffect(() => {
    setFormData(prev => ({
      ...prev,
      ...searchParams
    }));
  }, [searchParams]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (formData.query.trim()) {
      onSearch(formData);
    }
  };

  const handleQuickSearch = (query) => {
    setFormData(prev => ({
      ...prev,
      query
    }));
    onSearch({ ...formData, query });
  };

  const getCurrentDate = () => {
    return new Date().toISOString().split('T')[0];
  };

  const getDateWeekAgo = () => {
    const date = new Date();
    date.setDate(date.getDate() - 7);
    return date.toISOString().split('T')[0];
  };

  return (
    <div className="search-container">
      <form onSubmit={handleSubmit} className="search-form">
        <div className="form-group">
          <label htmlFor="query">Search Query *</label>
          <input
            type="text"
            id="query"
            name="query"
            value={formData.query}
            onChange={handleInputChange}
            placeholder="Enter search term (e.g., apple, tesla)"
            required
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="rangeFrom">From Date</label>
          <input
            type="date"
            id="rangeFrom"
            name="rangeFrom"
            value={formData.rangeFrom}
            onChange={handleInputChange}
            max={getCurrentDate()}
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="rangeTo">To Date</label>
          <input
            type="date"
            id="rangeTo"
            name="rangeTo"
            value={formData.rangeTo}
            onChange={handleInputChange}
            max={getCurrentDate()}
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="sortBy">Sort By</label>
          <select
            id="sortBy"
            name="sortBy"
            value={formData.sortBy}
            onChange={handleInputChange}
            disabled={loading}
          >
            <option value="relevancy">Relevancy</option>
            <option value="popularity">Popularity</option>
            <option value="publishedAt">Published Date</option>
          </select>
        </div>

        <div className="form-group">
          <label htmlFor="pageSize">Page Size</label>
          <input
            type="number"
            id="pageSize"
            name="pageSize"
            value={formData.pageSize}
            onChange={handleInputChange}
            min="1"
            max="100"
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="interval">Time Interval</label>
          <input
            type="number"
            id="interval"
            name="interval"
            value={formData.interval}
            onChange={handleInputChange}
            min="1"
            max="1000"
            disabled={loading}
          />
        </div>

        <div className="form-group">
          <label htmlFor="intervalType">Interval Type</label>
          <select
            id="intervalType"
            name="intervalType"
            value={formData.intervalType}
            onChange={handleInputChange}
            disabled={loading}
          >
            <option value="minutes">Minutes</option>
            <option value="hours">Hours</option>
            <option value="days">Days</option>
            <option value="weeks">Weeks</option>
            <option value="months">Months</option>
            <option value="years">Years</option>
          </select>
        </div>

        <button
          type="submit"
          className="search-button"
          disabled={loading || !formData.query.trim()}
        >
          {loading ? 'Searching...' : 'Search News'}
        </button>
      </form>

      <div className="quick-search">
        <h4>Quick Search:</h4>
        <div className="quick-search-buttons">
          {['apple', 'tesla', 'microsoft', 'google', 'amazon'].map(term => (
            <button
              key={term}
              type="button"
              className="quick-search-btn"
              onClick={() => handleQuickSearch(term)}
              disabled={loading}
            >
              {term}
            </button>
          ))}
        </div>
      </div>

      <div className="date-helpers">
        <h4>Date Helpers:</h4>
        <div className="date-helper-buttons">
          <button
            type="button"
            className="date-helper-btn"
            onClick={() => setFormData(prev => ({
              ...prev,
              rangeFrom: getDateWeekAgo(),
              rangeTo: getCurrentDate()
            }))}
            disabled={loading}
          >
            Last Week
          </button>
          <button
            type="button"
            className="date-helper-btn"
            onClick={() => setFormData(prev => ({
              ...prev,
              rangeFrom: '',
              rangeTo: ''
            }))}
            disabled={loading}
          >
            Clear Dates
          </button>
        </div>
      </div>

      {isOffline && (
        <div className="offline-notice">
          <p>⚠️ You are currently in offline mode. Results will be loaded from cached data.</p>
        </div>
      )}
    </div>
  );
};

export default NewsSearchForm;

