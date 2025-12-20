import { combineReducers } from '@reduxjs/toolkit';
import { reducer as todos } from '@/features/todos';
import { reducer as spinner } from '@/features/spinner';
import { reducer as messages } from '@/features/messages';

const rootReducer = combineReducers({
  spinner,
  messages,
  todos,
});

export default rootReducer;
