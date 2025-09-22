# News Search React Application

A minimalistic React-based news search application that finds relevant news articles and groups them by time intervals.

## Features

- **News Search**: Search for news articles using keywords
- **Time Interval Grouping**: Group results by configurable time intervals (minutes, hours, days, weeks, months, years)
- **Offline Mode**: Toggle between online and offline modes with cached data support
- **Parameter Controls**: Customize search with various parameters (date range, sort order, page size)
- **Responsive Design**: Works on desktop and mobile devices

## Requirements Met

✅ **News Search**: Uses the Everything API from NewsAPI via backend service  
✅ **Time Grouping**: Groups results by publishedAt date intervals with configurable N & Interval  
✅ **Default Intervals**: 12-hour intervals as default when not specified  
✅ **Parameter Controls**: End users can change input parameters dynamically  
✅ **Offline Mode**: Toggle support with localStorage caching  
✅ **API Integration**: Connects to `http://mynews.com/v1/api/news/search` with all required parameters  

## API Parameters

The application supports all required query parameters:
- `query` (required): Search keyword
- `rangeFrom`: Start date (YYYY-MM-DD format)
- `rangeTo`: End date (YYYY-MM-DD format)  
- `sortBy`: Sort order (relevancy, popularity, publishedAt)
- `pageSize`: Number of results per page (1-100)
- `page`: Page number (1+)

## Installation & Setup

1. **Install Dependencies**:
   ```bash
   cd resources/ui
   npm install
   ```

2. **Configure API URL** (optional):
   ```bash
   # Set environment variable for API URL
   export REACT_APP_API_URL=http://localhost:8080/v1/api
   ```

3. **Start Development Server**:
   ```bash
   npm start
   ```

4. **Build for Production**:
   ```bash
   npm run build
   ```

## Usage

### Basic Search
1. Enter a search term in the "Search Query" field
2. Click "Search News" or use quick search buttons
3. Results will be grouped by time intervals

### Advanced Search
1. Set date range using "From Date" and "To Date" fields
2. Choose sort order (relevancy, popularity, publishedAt)
3. Adjust page size and time interval settings
4. Use date helper buttons for quick date selection

### Offline Mode
1. Toggle "Offline Mode" switch
2. Previously searched terms will load from cache
3. New searches will be cached for offline use

