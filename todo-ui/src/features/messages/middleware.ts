import { AxiosError } from 'axios';
import { createListenerMiddleware, isRejected } from '@reduxjs/toolkit';
import { MessageType, showMessage } from '@/features/messages/slice';

const messagesMiddleware = createListenerMiddleware({ onError: console.error });

messagesMiddleware.startListening({
  matcher: isRejected,
  effect(action: any, { dispatch }) {
    const errorPayload = action.payload?.errors || action.error;

    if (action.payload?.customErrorMessage) {
      if (action.payload.customErrorMessage !== '') {
        const messageType = action.payload.messageType || MessageType.ERROR;
        dispatch(showMessage(action.payload.customErrorMessage, messageType));
        return;
      }
    }

    if (errorPayload?.status) {
      if (errorPayload.status >= 500) {
        dispatch(showMessage('Something went wrong. Try again in a minute.'));
      } else if (errorPayload.status === 403) {
        dispatch(showMessage(errorPayload.data?.message || 'Access Denied', MessageType.WARNING));
      } else if (errorPayload.status === 404) {
        dispatch(showMessage(errorPayload.data?.message || 'Not Found', MessageType.WARNING));
      } else if (errorPayload.status === 400) {
        dispatch(showMessage(errorPayload.data?.message || 'Bad Request', MessageType.WARNING));
      } else if (errorPayload.status >= 400 && errorPayload.status < 500 && errorPayload.status !== 422 && errorPayload.status !== 401) {
        dispatch(showMessage(errorPayload.data?.message || 'Client has some error', MessageType.WARNING));
      }
    } else if (errorPayload instanceof AxiosError) {
      dispatch(showMessage('Something went wrong. Try again in a minute.'));
    }
  },
});

export default messagesMiddleware.middleware;
