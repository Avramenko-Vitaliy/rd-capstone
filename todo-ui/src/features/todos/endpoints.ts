import { createAsyncThunk } from '@reduxjs/toolkit';
import { apiThunk } from '@/api';
import { selectIsLoading } from '@/features/todos';
import { RootState } from '@/app/store';

const GET_TODOS = 'todos/readAll';
const GET_TODO = 'todos/readOne';

export const readTodos = createAsyncThunk(
  GET_TODOS,
  apiThunk(({ params = {} }) => ({
    endpoint: '/api/todos',
    params,
  })),
  { condition: (_, api): boolean => !selectIsLoading(api.getState() as RootState) },
);

export const readTodo = createAsyncThunk(
  GET_TODO,
  apiThunk(({ id }) => ({
    endpoint: `/api/todos/${id}`,
  })),
  { condition: (_, api): boolean => !selectIsLoading(api.getState() as RootState) },
);
