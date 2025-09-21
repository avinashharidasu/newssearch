import React from 'react';
import { format } from 'date-fns';

const NewsResults = ({ newsData, groupedArticles, searchParams }) => {
  if (!newsData || !newsData.articles) {
    return null;
  }

  const { status, totalArticles, articles } = newsData;

  if (status !== 'ok' || !articles || articles.length === 0) {
    return (
      <div className="no-results">
        <h3>No news articles found</h3>
        <p>Try adjusting your search criteria or check your internet connection.</p>
      </div>
    );
  }

  const timeBuckets = Object.keys(groupedArticles).sort((a, b) => {
    // Sort buckets by recency (most recent first)
    const aArticles = groupedArticles[a];
    const bArticles = groupedArticles[b];
    
    if (aArticles.length === 0) return 1;
    if (bArticles.length === 0) return -1;
    
    const aLatest = new Date(aArticles[0].publishedAt);
    const bLatest = new Date(bArticles[0].publishedAt);
    
    return bLatest - aLatest;
  });

  return (
    <div className="news-results">
      <div className="stats">
        <strong>Found {totalArticles} articles</strong>
        <span> • Grouped by {searchParams.interval} {searchParams.intervalType} intervals</span>
        <span> • Sorted by {searchParams.sortBy}</span>
      </div>

      {timeBuckets.map(bucketKey => {
        const bucketArticles = groupedArticles[bucketKey];
        
        if (!bucketArticles || bucketArticles.length === 0) {
          return null;
        }

        return (
          <div key={bucketKey} className="time-bucket">
            <div className="time-bucket-header">
              {bucketKey} ({bucketArticles.length} articles)
            </div>
            <div className="articles-list">
              {bucketArticles.map((article, index) => (
                <ArticleItem key={`${bucketKey}-${index}`} article={article} />
              ))}
            </div>
          </div>
        );
      })}
    </div>
  );
};

const ArticleItem = ({ article }) => {
  const {
    title,
    description,
    url,
    urlToImage,
    author,
    publishedAt,
    content
  } = article;

  const formatDate = (dateString) => {
    try {
      return format(new Date(dateString), 'MMM dd, yyyy • HH:mm');
    } catch (error) {
      return 'Invalid date';
    }
  };

  const handleImageError = (e) => {
    e.target.style.display = 'none';
  };

  return (
    <div className="article-item">
      <h3 className="article-title">
        <a 
          href={url} 
          target="_blank" 
          rel="noopener noreferrer"
          title="Open in new tab"
        >
          {title || 'No title available'}
        </a>
      </h3>
      
      <div className="article-meta">
        {author && <span>By {author}</span>}
        {publishedAt && <span>{formatDate(publishedAt)}</span>}
        <span>
          <a 
            href={url} 
            target="_blank" 
            rel="noopener noreferrer"
            className="article-link"
          >
            Read full article →
          </a>
        </span>
      </div>

      {description && (
        <p className="article-description">
          {description}
        </p>
      )}

      {content && content !== description && (
        <p className="article-content">
          {content.substring(0, 200)}
          {content.length > 200 && '...'}
        </p>
      )}

      {urlToImage && (
        <img
          src={urlToImage}
          alt={title || 'Article image'}
          className="article-image"
          onError={handleImageError}
          loading="lazy"
        />
      )}
    </div>
  );
};

export default NewsResults;

