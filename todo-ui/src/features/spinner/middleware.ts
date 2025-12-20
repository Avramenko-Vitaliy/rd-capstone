import { createListenerMiddleware, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { hideSpinner, showSpinner } from '@/features/spinner/slice';

const spinner = createListenerMiddleware({ onError: console.error });

spinner.startListening({
  predicate: (action: any) => !!action?.meta?.arg?.withSpinner,
  effect(action, { dispatch }) {
    if (isPending(action)) {
      dispatch(showSpinner(action.meta.requestId));
    } else if (isRejected(action) || isFulfilled(action)) {
      dispatch(hideSpinner(action.meta.requestId));
    }
  },
});

export default spinner.middleware;
