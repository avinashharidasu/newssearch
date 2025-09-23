import React from 'react';

const NewsResults = ({ results, currentPage, onPageChange }) => {
  if (!results || !results.articles || results.articles.length === 0) {
    return (
      <div className="results-section">
        <h2>No results found</h2>
        <p>Try adjusting your search parameters.</p>
      </div>
    );
  }

  const formatDate = (dateString) => {
    if (!dateString) return 'N/A';
    try {
      return new Date(dateString).toLocaleString();
    } catch (error) {
      return dateString;
    }
  };

  const renderPagination = () => {
    if (!results.totalPages || results.totalPages <= 1) return null;

    const pages = [];
    const maxVisiblePages = 5;
    const startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    const endPage = Math.min(results.totalPages, startPage + maxVisiblePages - 1);

    for (let i = startPage; i <= endPage; i++) {
      pages.push(
        <button
          key={i}
          className={i === currentPage ? 'active' : ''}
          onClick={() => onPageChange(i)}
        >
          {i}
        </button>
      );
    }

    return (
      <div className="pagination">
        {currentPage > 1 && (
          <button onClick={() => onPageChange(currentPage - 1)}>
            Previous
          </button>
        )}
        {pages}
        {currentPage < results.totalPages && (
          <button onClick={() => onPageChange(currentPage + 1)}>
            Next
          </button>
        )}
      </div>
    );
  };

  return (
    <div className="results-section">
      <h2>Search Results</h2>
      <p>
        Showing {results.articles.length} of {results.totalResults || 0} results
        {results.totalPages && ` (Page ${currentPage} of ${results.totalPages})`}
      </p>

      <div>
        {results.articles.map((article, index) => (
          <div key={index} className="news-item">
            <h3 className="news-title">
              {article.url ? (
                <a href={article.url} target="_blank" rel="noopener noreferrer">
                  {article.title || 'No title available'}
                </a>
              ) : (
                article.title || 'No title available'
              )}
            </h3>
            
            {article.description && (
              <p className="news-description">
                {article.description}
              </p>
            )}
            
            <div className="news-meta">
              {article.source && article.source.name && (
                <span>Source: {article.source.name} | </span>
              )}
              {article.publishedAt && (
                <span>Published: {formatDate(article.publishedAt)} | </span>
              )}
              {article.author && (
                <span>Author: {article.author}</span>
              )}
            </div>
          </div>
        ))}
      </div>

      {renderPagination()}
    </div>
  );
};

export default NewsResults;
