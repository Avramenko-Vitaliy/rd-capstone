import { createSlice } from '@reduxjs/toolkit';

const spinner = createSlice({
  name: 'spinner',
  initialState: [] as string[],
  reducers: {
    showSpinner: {
      reducer: (state, { payload: { requestId } }) => [...state, requestId],
      prepare: (requestId: any): any => ({ payload: { requestId } }),
    },
    hideSpinner: {
      reducer: (state, { payload: { requestId } }) => state.filter(item => item !== requestId),
      prepare: (requestId: any): any => ({ payload: { requestId } }),
    },
  },
  selectors: {
    selectShowSpinner: state => !!state.length,
  },
});

export const { selectors, actions: { showSpinner, hideSpinner } } = spinner;
export default spinner.reducer;
