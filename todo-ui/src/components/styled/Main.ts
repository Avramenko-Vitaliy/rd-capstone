import { styled } from '@mui/material/styles';

interface MainProps {
  open?: boolean;
}

const Main = styled('main')<MainProps>(({ theme }) => ({
  flexGrow: 1,
  boxSizing: 'border-box',
  height: 'calc(100dvh - 64px)',
  overflow: 'auto',
  padding: theme.spacing(2),
  backgroundColor: '#eff3f8',
  transition: theme.transitions.create('margin', {
    easing: theme.transitions.easing.sharp,
    duration: theme.transitions.duration.leavingScreen,
  }),
  variants: [
    {
      style: {
        transition: theme.transitions.create('margin', {
          easing: theme.transitions.easing.easeOut,
          duration: theme.transitions.duration.enteringScreen,
        }),
      },
    },
  ],
}));

export default Main;
