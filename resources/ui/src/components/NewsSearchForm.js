import React, { useState } from 'react';

const NewsSearchForm = ({ onSearch }) => {
  const [formData, setFormData] = useState({
    query: '',
    rangeFrom: '',
    rangeTo: '',
    sortBy: 'relevancy'
  });

  const getDefaultTimeRange = () => {
    const now = new Date();
    const twelveHoursAgo = new Date(now.getTime() - 12 * 60 * 60 * 1000);
    
    return {
      from: twelveHoursAgo.toISOString().slice(0, 16),
      to: now.toISOString().slice(0, 16)
    };
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    
    if (!formData.query.trim()) {
      alert('Please enter a search query');
      return;
    }

    // Apply defaults for empty optional inputs
    const defaultRange = getDefaultTimeRange();
    const searchParams = {
      query: formData.query.trim(),
      rangeFrom: formData.rangeFrom || defaultRange.from,
      rangeTo: formData.rangeTo || defaultRange.to,
      sortBy: formData.sortBy
    };

    onSearch(searchParams);
  };

  const handleSetDefaults = () => {
    const defaultRange = getDefaultTimeRange();
    setFormData(prev => ({
      ...prev,
      rangeFrom: defaultRange.from,
      rangeTo: defaultRange.to
    }));
  };

  return (
    <form className="search-form" onSubmit={handleSubmit}>
      <div className="form-group">
        <label htmlFor="query">Search Query *</label>
        <input
          type="text"
          id="query"
          name="query"
          value={formData.query}
          onChange={handleInputChange}
          placeholder="Enter keywords like 'apple', 'QUALCOMM'"
          required
        />
      </div>

      <div className="form-row">
        <div className="form-group">
          <label htmlFor="rangeFrom">From Date/Time</label>
          <input
            type="datetime-local"
            id="rangeFrom"
            name="rangeFrom"
            value={formData.rangeFrom}
            onChange={handleInputChange}
            required
          />
        </div>

        <div className="form-group">
          <label htmlFor="rangeTo">To Date/Time</label>
          <input
            type="datetime-local"
            id="rangeTo"
            name="rangeTo"
            value={formData.rangeTo}
            onChange={handleInputChange}
            required
          />
        </div>
      </div>

      <div className="form-group">
        <label htmlFor="sortBy">Sort By</label>
        <select
          id="sortBy"
          name="sortBy"
          value={formData.sortBy}
          onChange={handleInputChange}
        >
          <option value="relevancy">Relevancy</option>
          <option value="popularity">Popularity</option>
          <option value="publishedAt">Published Date</option>
        </select>
      </div>

      <div style={{ marginBottom: '10px' }}>
        <button 
          type="button" 
          onClick={handleSetDefaults}
          style={{ 
            background: '#6c757d', 
            color: 'white', 
            padding: '8px 16px', 
            border: 'none', 
            borderRadius: '4px', 
            cursor: 'pointer',
            marginRight: '10px'
          }}
        >
          Set 12-hour Default Range
        </button>
        <small style={{ color: '#666' }}>
          Leave date fields empty to use 12-hour default range
        </small>
      </div>

      <button type="submit" className="search-button">
        Search News
      </button>
    </form>
  );
};

export default NewsSearchForm;
