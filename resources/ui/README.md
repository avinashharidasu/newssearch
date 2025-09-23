# News Search React Application

A minimalistic React-based news search application that integrates with a backend API and Google OAuth authentication.

## Features

- Google OAuth authentication
- News search with customizable parameters
- Default 12-hour time range when optional inputs are empty
- Pagination support
- Responsive design with minimal CSS

## Setup Instructions

1. Install dependencies:
```bash
cd resources/ui
npm install
```

2. Start the development server:
```bash
npm start
```

The application will be available at `http://localhost:8081`

## API Integration

The application connects to the backend API at `http://localhost:8080/v1/api/news/search` with the following parameters:
- `searchquery`: Search keywords
- `rangeFrom`: Start date/time (ISO format)
- `rangeTo`: End date/time (ISO format)
- `sortBy`: Sort order (relevancy, popularity, publishedAt)
- `pageSize`: Number of results per page
- `page`: Page number

## Authentication

Uses Google OAuth with client ID: `32358567694-dmas57prhp74tvubf72thlq9j30e68ns.apps.googleusercontent.com`

## Default Behavior

- If date range fields are left empty, the application automatically applies a 12-hour interval from 12 hours ago to the current time
- Default sort order is "relevancy"
- Page size is set to 10 results per page

## Project Structure

```
src/
├── components/
│   ├── AuthButton.js      # Google OAuth authentication
│   ├── NewsSearchForm.js  # Search form with parameters
│   └── NewsResults.js     # Results display and pagination
├── App.js                 # Main application component
├── index.js              # Application entry point
└── index.css             # Minimal styling
```
