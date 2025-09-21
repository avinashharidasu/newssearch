import { format, subMinutes, subHours, subDays, subWeeks, subMonths, subYears, isAfter, isBefore } from 'date-fns';

export class DateGroupingService {
  groupArticlesByTimeIntervals(articles, interval = 12, intervalType = 'hours') {
    if (!articles || articles.length === 0) {
      return {};
    }

    const now = new Date();
    const grouped = {};

    // Sort articles by publishedAt (newest first)
    const sortedArticles = [...articles].sort((a, b) => {
      const dateA = new Date(a.publishedAt);
      const dateB = new Date(b.publishedAt);
      return dateB - dateA;
    });

    sortedArticles.forEach(article => {
      const articleDate = new Date(article.publishedAt);
      const bucketKey = this.getTimeBucketKey(articleDate, now, interval, intervalType);
      
      if (!grouped[bucketKey]) {
        grouped[bucketKey] = [];
      }
      grouped[bucketKey].push(article);
    });

    return grouped;
  }

  getTimeBucketKey(articleDate, now, interval, intervalType) {
    const intervals = this.getTimeIntervals(now, interval, intervalType);
    
    for (let i = 0; i < intervals.length; i++) {
      const interval = intervals[i];
      if (isAfter(articleDate, interval.start) && isBefore(articleDate, interval.end)) {
        return interval.label;
      }
    }
    
    // If article is older than all intervals, put it in the last bucket
    return intervals[intervals.length - 1]?.label || 'Older';
  }

  getTimeIntervals(now, interval, intervalType) {
    const intervals = [];
    let currentTime = new Date(now);
    
    // Generate intervals going backwards from now
    for (let i = 0; i < 10; i++) { // Limit to 10 intervals to avoid infinite loops
      const nextTime = this.subtractTime(currentTime, interval, intervalType);
      
      intervals.push({
        start: nextTime,
        end: currentTime,
        label: this.formatTimeInterval(nextTime, currentTime, interval, intervalType)
      });
      
      currentTime = nextTime;
    }
    
    return intervals;
  }

  subtractTime(date, amount, type) {
    switch (type) {
      case 'minutes':
        return subMinutes(date, amount);
      case 'hours':
        return subHours(date, amount);
      case 'days':
        return subDays(date, amount);
      case 'weeks':
        return subWeeks(date, amount);
      case 'months':
        return subMonths(date, amount);
      case 'years':
        return subYears(date, amount);
      default:
        return subHours(date, amount);
    }
  }

  formatTimeInterval(start, end, interval, intervalType) {
    const now = new Date();
    
    if (isAfter(start, subMinutes(now, 1))) {
      return 'Last minute';
    } else if (isAfter(start, subMinutes(now, 60))) {
      return 'Last hour';
    } else if (isAfter(start, subHours(now, 24))) {
      return 'Last 24 hours';
    } else if (isAfter(start, subDays(now, 7))) {
      return 'Last week';
    } else if (isAfter(start, subDays(now, 30))) {
      return 'Last month';
    } else {
      return `${format(start, 'MMM dd, yyyy')} - ${format(end, 'MMM dd, yyyy')}`;
    }
  }

  getDefaultInterval() {
    return {
      interval: 12,
      intervalType: 'hours'
    };
  }
}
