import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

// Mock the services to avoid API calls during testing
jest.mock('./services/NewsService', () => ({
  NewsService: jest.fn().mockImplementation(() => ({
    searchNews: jest.fn().mockResolvedValue({
      status: 'ok',
      totalArticles: 2,
      articles: [
        {
          title: 'Test Article 1',
          description: 'Test description 1',
          url: 'https://example.com/1',
          publishedAt: new Date().toISOString(),
          author: 'Test Author 1'
        },
        {
          title: 'Test Article 2', 
          description: 'Test description 2',
          url: 'https://example.com/2',
          publishedAt: new Date(Date.now() - 3600000).toISOString(), // 1 hour ago
          author: 'Test Author 2'
        }
      ]
    }),
    getOfflineData: jest.fn().mockReturnValue(null),
    storeOfflineData: jest.fn()
  }))
}));

jest.mock('./services/DateGroupingService', () => ({
  DateGroupingService: jest.fn().mockImplementation(() => ({
    groupArticlesByTimeIntervals: jest.fn().mockReturnValue({
      'Last hour': [
        {
          title: 'Test Article 1',
          description: 'Test description 1',
          url: 'https://example.com/1',
          publishedAt: new Date().toISOString(),
          author: 'Test Author 1'
        }
      ],
      'Last 24 hours': [
        {
          title: 'Test Article 2',
          description: 'Test description 2', 
          url: 'https://example.com/2',
          publishedAt: new Date(Date.now() - 3600000).toISOString(),
          author: 'Test Author 2'
        }
      ]
    })
  }))
}));

test('renders news search application', () => {
  render(<App />);
  
  // Check if main elements are rendered
  expect(screen.getByText('News Search Application')).toBeInTheDocument();
  expect(screen.getByText('Search for news articles and view them grouped by time intervals')).toBeInTheDocument();
  expect(screen.getByText('Offline Mode:')).toBeInTheDocument();
  expect(screen.getByText('Search Query *')).toBeInTheDocument();
  expect(screen.getByText('Search News')).toBeInTheDocument();
});

test('renders quick search buttons', () => {
  render(<App />);
  
  // Check if quick search buttons are present
  expect(screen.getByText('apple')).toBeInTheDocument();
  expect(screen.getByText('tesla')).toBeInTheDocument();
  expect(screen.getByText('microsoft')).toBeInTheDocument();
  expect(screen.getByText('google')).toBeInTheDocument();
  expect(screen.getByText('amazon')).toBeInTheDocument();
});

test('renders date helper buttons', () => {
  render(<App />);
  
  // Check if date helper buttons are present
  expect(screen.getByText('Last Week')).toBeInTheDocument();
  expect(screen.getByText('Clear Dates')).toBeInTheDocument();
});

test('renders form controls', () => {
  render(<App />);
  
  // Check if form controls are present
  expect(screen.getByLabelText('Search Query *')).toBeInTheDocument();
  expect(screen.getByLabelText('From Date')).toBeInTheDocument();
  expect(screen.getByLabelText('To Date')).toBeInTheDocument();
  expect(screen.getByLabelText('Sort By')).toBeInTheDocument();
  expect(screen.getByLabelText('Page Size')).toBeInTheDocument();
  expect(screen.getByLabelText('Time Interval')).toBeInTheDocument();
  expect(screen.getByLabelText('Interval Type')).toBeInTheDocument();
});

