import axios from 'axios';

export class NewsService {
  constructor() {
    this.baseURL = process.env.REACT_APP_API_URL || 'http://localhost:8080/v1/api';
    this.apiClient = axios.create({
      baseURL: this.baseURL,
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json',
      },
    });
  }

  async searchNews(params) {
    try {
      const queryParams = new URLSearchParams();
      
      // Required parameter
      queryParams.append('query', params.query);
      
      // Optional parameters
      if (params.rangeFrom) {
        queryParams.append('rangeFrom', params.rangeFrom);
      }
      if (params.rangeTo) {
        queryParams.append('rangeTo', params.rangeTo);
      }
      if (params.sortBy) {
        queryParams.append('sortBy', params.sortBy);
      }
      if (params.pageSize) {
        queryParams.append('pageSize', params.pageSize.toString());
      }
      if (params.page) {
        queryParams.append('page', params.page.toString());
      }

      const response = await this.apiClient.get(`/news/search?${queryParams.toString()}`);
      
      if (response.data.status !== 'ok') {
        throw new Error('API returned error status');
      }
      
      return response.data;
    } catch (error) {
      if (error.response) {
        // Server responded with error status
        throw new Error(`API Error: ${error.response.data?.message || error.response.statusText}`);
      } else if (error.request) {
        // Network error
        throw new Error('Network error: Unable to connect to the server');
      } else {
        // Other error
        throw new Error(`Request failed: ${error.message}`);
      }
    }
  }

  storeOfflineData(query, data) {
    try {
      const offlineData = JSON.parse(localStorage.getItem('newsOfflineData') || '{}');
      offlineData[query.toLowerCase()] = {
        data,
        timestamp: Date.now()
      };
      localStorage.setItem('newsOfflineData', JSON.stringify(offlineData));
    } catch (error) {
      console.warn('Failed to store offline data:', error);
    }
  }

  getOfflineData(query) {
    try {
      const offlineData = JSON.parse(localStorage.getItem('newsOfflineData') || '{}');
      const queryData = offlineData[query.toLowerCase()];
      
      if (queryData) {
        // Check if data is not too old (24 hours)
        const maxAge = 24 * 60 * 60 * 1000; // 24 hours in milliseconds
        if (Date.now() - queryData.timestamp < maxAge) {
          return queryData.data;
        }
      }
      
      return null;
    } catch (error) {
      console.warn('Failed to retrieve offline data:', error);
      return null;
    }
  }

  clearOfflineData() {
    try {
      localStorage.removeItem('newsOfflineData');
    } catch (error) {
      console.warn('Failed to clear offline data:', error);
    }
  }
}

