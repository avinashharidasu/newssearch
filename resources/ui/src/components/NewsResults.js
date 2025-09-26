import React from 'react';

const NewsResults = ({ results, currentPage, pageSize, onPageChange, onPageSizeChange }) => {
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

  const renderPageSizeSelector = () => {
    const pageSizeOptions = [5, 10, 20, 50];
    
    return (
      <div className="page-size-selector">
        <label htmlFor="pageSize">Results per page:</label>
        <select
          id="pageSize"
          value={pageSize}
          onChange={(e) => onPageSizeChange(parseInt(e.target.value))}
        >
          {pageSizeOptions.map(size => (
            <option key={size} value={size}>{size}</option>
          ))}
        </select>
      </div>
    );
  };

  const renderPagination = () => {
    if (!results.totalArticles || results.totalArticles <= 1) return null;

    const pages = [];
    const maxVisiblePages = 5;
    const startPage = Math.max(1, currentPage - Math.floor(maxVisiblePages / 2));
    const endPage = Math.min(results.totalArticles, startPage + maxVisiblePages - 1);

    // Add first page and ellipsis if needed
    if (startPage > 1) {
      pages.push(
        <button
          key={1}
          onClick={() => onPageChange(1)}
        >
          1
        </button>
      );
      if (startPage > 2) {
        pages.push(<span key="ellipsis1" className="ellipsis">...</span>);
      }
    }

    // Add visible page numbers
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

    // Add last page and ellipsis if needed
    if (endPage < results.totalArticles) {
      if (endPage < results.totalArticles - 1) {
        pages.push(<span key="ellipsis2" className="ellipsis">...</span>);
      }
      pages.push(
        <button
          key={results.totalArticles}
          onClick={() => onPageChange(results.totalArticles)}
        >
          {results.totalArticles}
        </button>
      );
    }

    return (
      <div className="pagination">
        <div className="pagination-controls">
          <button 
            onClick={() => onPageChange(currentPage - 1)}
            disabled={currentPage <= 1}
            className="pagination-nav"
          >
            ← Previous
          </button>
          <div className="page-numbers">
            {pages}
          </div>
          <button 
            onClick={() => onPageChange(currentPage + 1)}
            disabled={currentPage >= results.totalArticles}
            className="pagination-nav"
          >
            Next →
          </button>
        </div>
        <div className="pagination-info">
          {renderPageSizeSelector()}
        </div>
      </div>
    );
  };

  return (
    <div className="results-section">
      <h2>Search Results</h2>
      <div className="results-info">
        <p>
          Showing {((currentPage - 1) * pageSize) + 1} to {Math.min(currentPage * pageSize, results.totalArticles || 0)} of {results.totalArticles || 0} results
          {results.totalArticles && ` (Page ${currentPage} of ${results.totalArticles})`}
        </p>
      </div>

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
