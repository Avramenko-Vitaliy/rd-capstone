import { createTheme, Theme } from '@mui/material';

const theme: Theme = createTheme({
  components: {
    MuiOutlinedInput: {
      styleOverrides: {
        root: {
          fontSize: 14,
          fontWeight: 400,
          borderRadius: 8,
        },
        input: {
          padding: '8px 10px',
        },
      },
    },
  },
});

export default theme;
