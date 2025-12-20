import { memo } from 'react';
import { useAppSelector } from '@/app/hooks';
import { selectById } from '@/features/todos';
import { Todo } from '@/types/models';
import BorderedGrid from '@/components/styled/BorderedGrid';
import Grid from '@mui/material/Grid';
import dayjs from 'dayjs';
import { DATE_TIME } from '@/utils/formater';

function TodoCard({ id }: { id: number }) {
  const todo: Todo = useAppSelector(state => selectById(state, id));

  return (
    <BorderedGrid
      container
      spacing={2}
      size={{ xs: 12, md: 6, lg: 3 }}
    >
      <Grid size={8}>
        {todo.name}
      </Grid>
      <Grid size={4} textAlign="right">
        {dayjs(todo.date).local().format(DATE_TIME)}
      </Grid>
      <Grid size={12}>
        {todo.description}
      </Grid>
    </BorderedGrid>
  );
}

export default memo(TodoCard);
