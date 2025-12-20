import { memo, useCallback, useEffect } from 'react';
import Alert, { AlertProps } from '@mui/material/Alert';
import AlertTitle from '@mui/material/AlertTitle';
import settings from '@/settings';

interface SnackbarMessageProps extends Omit<AlertProps, 'onClose'> {
  id: string;
  message: string;
  details?: string;
  onClose: (id: string) => void;
}

function SnackbarMessage(props: SnackbarMessageProps) {
  const handleClose = useCallback(() => {
    props.onClose(props.id);
  }, [props.id, props.onClose]);

  useEffect(() => {
    setTimeout(handleClose, settings.messageDelay);
  }, [handleClose]);

  return (
    <Alert
      variant="filled"
      severity={props.severity}
      sx={{ marginTop: 1 }}
      onClose={handleClose}
    >
      <AlertTitle>{props.message}</AlertTitle>
      {props.details}
    </Alert>
  );
}

export default memo(SnackbarMessage);
