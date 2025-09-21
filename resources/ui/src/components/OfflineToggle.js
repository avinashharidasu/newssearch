import React from 'react';

const OfflineToggle = ({ isOffline, onToggle }) => {
  const handleToggle = () => {
    onToggle(!isOffline);
  };

  return (
    <div className="offline-toggle">
      <label htmlFor="offline-toggle">
        <strong>Offline Mode:</strong>
      </label>
      <div className="toggle-switch" onClick={handleToggle}>
        <div className={`toggle-slider ${isOffline ? 'active' : ''}`}></div>
      </div>
      <span className="toggle-label">
        {isOffline ? 'ON' : 'OFF'}
      </span>
      <span className="toggle-description">
        {isOffline 
          ? 'Using cached data from previous searches' 
          : 'Fetching fresh data from API'
        }
      </span>
    </div>
  );
};

export default OfflineToggle;

