import { createEntityAdapter, createSlice, isPending, isRejected } from '@reduxjs/toolkit';
import { Todo } from '@/types/models';
import { readTodo, readTodos } from '@/features/todos/endpoints';
import settings from '@/settings';

const adapter = createEntityAdapter<Todo>();

const initialState = {
  ...adapter.getInitialState(),
  loading: false,
  total: 0,
  limit: settings.limit,
  offset: 0,
};

const todos = createSlice({
  name: 'todos',
  initialState,
  reducers: {},
  selectors: {
    selectAll: state => adapter.getSelectors().selectAll(state),
    selectById: (state, id) => adapter.getSelectors().selectById(state, id),
    selectIds: state => adapter.getSelectors().selectIds(state),
    selectTotal: state => state.total,
    selectIsLoading: state => state.loading,
    selectLimit: state => state.limit,
    selectOffset: state => state.offset,
  },
  extraReducers(builder) {
    builder.addCase(readTodos.fulfilled, (state, action) => {
      const newState = {
        ...state,
        total: action.payload?.data.total,
        loading: false,
        limit: action.meta.arg?.params?.limit ?? settings.limit,
        offset: action.meta.arg?.params?.offset ?? 0,
      };
      if (action.meta.arg?.params?.offset === 0) {
        return adapter.setAll(newState, action.payload?.data.data);
      }
      return adapter.addMany(newState, action.payload?.data.data);
    });
    builder.addCase(readTodo.fulfilled, (state, action) => (
      adapter.upsertOne(
        { ...state, total: action.payload?.data.total, loading: false },
        action.payload?.data,
      )
    ));
    builder.addMatcher(isRejected(readTodos, readTodo), (state) => ({
      ...state,
      loading: false,
    }));
    builder.addMatcher(isPending(readTodos, readTodo), (state) => ({
      ...state,
      loading: true,
    }));
  },
});

export const { selectors } = todos;
export default todos.reducer;
