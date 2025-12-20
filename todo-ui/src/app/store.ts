import { configureStore } from '@reduxjs/toolkit';
import rootReducer from '@/app/reducers';
import middlewares from '@/app/middlewares';

export type RootState = ReturnType<typeof rootReducer>;

const store = configureStore({
  reducer: rootReducer,
  middleware: getDefaultMiddleware => getDefaultMiddleware({
    serializableCheck: false,
  }).concat(middlewares),
  devTools: process.env['NODE_ENV'] !== 'production',
});

export type AppDispatch = typeof store.dispatch;

export default store;
