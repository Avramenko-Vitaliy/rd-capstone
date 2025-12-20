import { useSelector } from 'react-redux';

import CircularProgress from '@mui/material/CircularProgress';

import { withStyles } from 'tss-react/mui';

import { selectShowSpinner } from '@/features/spinner/selectors';

const styles = () => ({
  container: {
    position: 'fixed',
    width: '100%',
    height: '100%',
    zIndex: 99990,
    textAlign: 'center',
    display: 'flex',
    columns: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: 'rgba(232, 232, 232, 0.5)',
  },
  progress: {
    position: 'fixed',
  },
} as const);

type Classes = Partial<Record<keyof ReturnType<typeof styles>, string>>;

function Spinner({ classes }: { classes?: Classes }) {
  const isShow = useSelector(selectShowSpinner);
  if (!isShow) {
    return null;
  }
  return (
    <div className={classes?.container}>
      <CircularProgress
        size={64}
        disableShrink
        color="primary"
        className={classes?.progress}
      />
    </div>
  );
}

export default withStyles(Spinner, styles);
