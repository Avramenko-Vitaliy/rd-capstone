import { useCallback, useEffect } from 'react';
import Grid from '@mui/material/Grid';
import InfiniteScroll from 'react-infinite-scroller';
import { useAppDispatch, useAppSelector } from '@/app/hooks';
import { readTodos, selectHasNext, selectIds, selectLimit, selectOffset } from '@/features/todos';
import { useFilterToQuery } from '@/components/filters/hooks/filterToQuery';
import settings from '@/settings';
import lodash from 'lodash';
import { withStyles } from 'tss-react/mui';
import { Typography } from '@mui/material';
import TodoCard from '@/features/todos/components/TodoCard';

const styles = () => ({
  container: {
    overflow: 'auto',
    maxHeight: 'calc(100vh - 263px)',
  },
});

function Todos({ classes }: { classes?: Partial<Record<keyof ReturnType<typeof styles>, string>> }) {
  const { query } = useFilterToQuery();

  const dispatch = useAppDispatch();

  const todoIds = useAppSelector(selectIds);
  const hasNext = useAppSelector(selectHasNext);
  const offset = useAppSelector(selectOffset);
  const limit = useAppSelector(selectLimit);

  useEffect(() => {
    const params = { ...query, offset: 0, limit: settings.limit, sort: 'date', asc: false };
    dispatch(readTodos({ params, withSpinner: true }));
  }, [query]);

  const handleReadNext = useCallback(() => {
    const params = { offset: offset + limit, limit, sort: 'date', asc: false };
    return dispatch(readTodos({ params, withSpinner: true }));
  }, [offset, limit]);

  if (lodash.isEmpty(todoIds)) {
    return (
      <Grid size={12}>
        <Typography variant="subtitle1" textAlign="center" py={2}>
          No ToDos found
        </Typography>
      </Grid>
    );
  }
  return (
    <Grid
      container
      size={12}
      spacing={2}
      className={classes?.container}
    >
      <InfiniteScroll
        size={12}
        container
        spacing={2}
        threshold={5}
        element={Grid}
        hasMore={hasNext}
        useWindow={false}
        loadMore={handleReadNext}
      >
        {todoIds.map(item => (
          <TodoCard key={item} id={item} />
        ))}
      </InfiniteScroll>
    </Grid>
  );
}

export default withStyles(Todos, styles);
