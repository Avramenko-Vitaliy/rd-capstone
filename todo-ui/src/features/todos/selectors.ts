import { selectors } from '@/features/todos/slice';
import { createSelector } from '@reduxjs/toolkit';

export const {
  selectAll,
  selectById,
  selectIds,
  selectTotal,
  selectIsLoading,
  selectLimit,
  selectOffset,
} = selectors;

export const selectHasNext = createSelector(
  selectTotal,
  selectOffset,
  selectLimit,
  (total, offset, limit): boolean => Boolean(total && total > offset + limit),
);
