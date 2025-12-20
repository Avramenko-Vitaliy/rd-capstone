import { memo } from 'react';
import FactCheckIcon from '@mui/icons-material/FactCheck';
import { withStyles } from 'tss-react/mui';
import Typography from '@mui/material/Typography';
import Toolbar from '@mui/material/Toolbar';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';

const styles = () => ({
  toolbar: {
    width: '100%',
    display: 'flex',
    alignItems: 'center',
    gap: 8,
  },
});

type Classes = Partial<Record<keyof ReturnType<typeof styles>, string>>;

function Header({ classes }: { classes?: Classes }) {
  return (
    <AppBar position="static">
      <Box mx={2}>
        <Toolbar disableGutters className={classes?.toolbar}>
          <FactCheckIcon />
          <Typography
            noWrap
            variant="h6"
          >
            ToDo
          </Typography>
        </Toolbar>
      </Box>
    </AppBar>
  );
}

export default memo(withStyles(Header, styles));
