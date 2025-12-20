import { useCallback } from 'react';

import { AlertColor } from '@mui/material/Alert';

import { withStyles } from 'tss-react/mui';

import SnackbarMessage from '@/features/messages/components/SnackbarMessage';
import { selectMessages } from '@/features/messages/selectors';
import { hideMessage } from '@/features/messages';
import { useAppDispatch, useAppSelector } from '@/app/hooks';

const styles = () => ({
  other: {
    position: 'fixed',
    bottom: 15,
    right: 44,
    zIndex: 9999999,
  },
});

function SnackbarMessages(props: { classes?: any }) {
  const messages = useAppSelector(selectMessages);
  const dispatch = useAppDispatch();

  const handleHideMessage = useCallback((id: string) => {
    dispatch(hideMessage(id));
  }, [dispatch, hideMessage]);

  if (!messages?.length) {
    return null;
  }
  return (
    <div className={props.classes.other}>
      {messages.map(item => (
        <SnackbarMessage
          key={item.id}
          id={item.id}
          message={item.message}
          details={item.details}
          severity={item.messageType.toLowerCase() as AlertColor}
          onClose={handleHideMessage}
        />
      ))}
    </div>
  );
}

export default withStyles(SnackbarMessages, styles);
