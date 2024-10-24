export const environment = {
  production: true, // Indicates production mode
  baseUrl: 'https://api.mysite.com/', // Production API URL
  apiVersion: 'v1', // API version

  // Security and API keys
  apiKey: 'YOUR_PROD_API_KEY', // Secure API key if required
  auth: {
    clientId: 'PROD_CLIENT_ID',
    clientSecret: 'PROD_CLIENT_SECRET',
  },

  // Analytics and monitoring
  enableAnalytics: true, // Enable user behavior analytics in production
  analyticsKey: 'PROD_ANALYTICS_KEY',

  // Logging management
  logLevel: 'error', // Restrict logs to higher levels (error, warn, etc.)

  // Feature toggles or flags
  featureFlags: {
    enableNewFeature: false,
  },

  // App-specific configurations
  timeoutDuration: 30000, // HTTP request timeout
  retryAttempts: 3, // Retry attempts in case of failure

  // Deployment or performance-specific configs
  cacheControl: 'public, max-age=3600', // HTTP resource caching
  useSentry: true, // Enable Sentry or other error tracking service

  // i18n (Internationalization) settings for production
  defaultLanguage: 'fr', // Example default language
};
