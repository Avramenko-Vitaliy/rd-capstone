import { StrictMode } from 'react';
import { Provider } from 'react-redux';
import { createRoot } from 'react-dom/client';
import { BrowserRouter } from 'react-router';
import { StyledEngineProvider, ThemeProvider } from '@mui/material';
import App from '@/App';
import store from '@/app/store';
import '@/assets/styles/main.css';
import dayjs from 'dayjs';
import utc from 'dayjs/plugin/utc';
import theme from '@/theme';

dayjs.extend(utc);

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <StyledEngineProvider injectFirst>
      <ThemeProvider theme={theme}>
        <Provider store={store}>
          <BrowserRouter>
            <App />
          </BrowserRouter>
        </Provider>
      </ThemeProvider>
    </StyledEngineProvider>
  </StrictMode>,
);
